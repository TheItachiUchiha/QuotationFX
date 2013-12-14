package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.EnquiryVO;
import com.kc.model.ReminderVO;
import com.kc.util.DBConnector;

public class StatusReminderDAO {
	
	private static final Logger LOG = LogManager.getLogger(StatusReminderDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void createReminder(ReminderVO reminderVO)
	{

		LOG.info("Enter : createReminder");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO REMINDER(reference_no,total_reminder,frequency,last_sent,next_send) VALUES(?, ?, ?, ?, ?)");
			preparedStatement.setString(1, reminderVO.getReferenceNo());
			preparedStatement.setInt(2, reminderVO.getTotalReminder());
			preparedStatement.setInt(3, reminderVO.getFrequency());
			preparedStatement.setString(4, reminderVO.getLastSent());
			preparedStatement.setString(5, reminderVO.getNextSend());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : createReminder");
	}
	
	public ObservableList<String> getModifyReminders(String startDate,String endDate) throws SQLException
	{
		LOG.info("Enter : getReminders");
		ObservableList<String> listOfReminders = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT e.REF_NUMBER FROM quotation.ENQUIRY e ,quotation.REMINDER r WHERE e.REF_NUMBER = r.REFERENCE_NO" +
					"STR_TO_DATE(e.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and " + 
					"STR_TO_DATE(e.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')");
			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				listOfReminders.add(resultSet.getString(1));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		finally{
			if(conn !=null)
			{
				conn.close();
			}
		}
		LOG.info("Exit : getReminders");
		return listOfReminders;
	}

	public ObservableList<String> getCreateReminders(String startDate,String endDate ) throws SQLException
	{
		LOG.info("Enter : getReminders");
		ObservableList<String> listOfReminders = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT e.REF_NUMBER FROM quotation.ENQUIRY e , quotation.REMINDER r WHERE e.salesdone='N' and e.REF_NUMBER not in (select r1.reference_no from quotation.reminder r1)" +
							"STR_TO_DATE(e.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and " + 
							"STR_TO_DATE(e.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')");
			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				listOfReminders.add(resultSet.getString(1));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		finally{
			if(conn !=null)
			{
				conn.close();
			}
		}
		LOG.info("Exit : getReminders");
		return listOfReminders;
	}
	public ObservableList<EnquiryVO> getStatusEnquires(String startDate, String endDate, String status) throws Exception
	{
		LOG.info("Enter : getStatusEnquires");
		ObservableList<EnquiryVO> listOfEnquries = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("select * from quotation.enquiry where salesdone=? and " +
							"STR_TO_DATE(`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and " + 
							"STR_TO_DATE(`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')");
			preparedStatement.setString(1, status);
			preparedStatement.setString(2, startDate);
			preparedStatement.setString(3, endDate);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				EnquiryVO enquiryVO = new EnquiryVO();
				enquiryVO.setId(resultSet.getInt(1));
				enquiryVO.setCustomerId(resultSet.getInt(2));
				enquiryVO.setReferedBy(resultSet.getString(3));
				enquiryVO.setCustomerrequirements(resultSet.getString(4));
				enquiryVO.setPurchasePeriod(resultSet.getString(5));
				enquiryVO.setCustomerDocument(resultSet.getString(6));
				enquiryVO.setPriceEstimation(resultSet.getString(7));
				enquiryVO.setQuotationPreparation(resultSet.getString(8));
				enquiryVO.setEmailSent(resultSet.getString(9));
				enquiryVO.setDate(resultSet.getString(10));
				enquiryVO.setProductName(resultSet.getString(11));
				enquiryVO.setSales(resultSet.getString(12));
				enquiryVO.setFlag(resultSet.getString(13));
				enquiryVO.setRefNumber(resultSet.getString(14));
				enquiryVO.setProductId(resultSet.getInt(15));
				enquiryVO.setMargin(resultSet.getDouble(16));
				enquiryVO.setPeDate(resultSet.getString(17));
				enquiryVO.setQpDate(resultSet.getString(18));
				enquiryVO.setMailSentDate(resultSet.getString(19));
				enquiryVO.setSalesDate(resultSet.getString(20));
				enquiryVO.setReminderSent(resultSet.getInt(21));
				listOfEnquries.add(enquiryVO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		finally{
			if(conn !=null)
			{
				conn.close();
			}
		}
		LOG.info("Exit : getStatusEnquires");
		return listOfEnquries;
	}
}
