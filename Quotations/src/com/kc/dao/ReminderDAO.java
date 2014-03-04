package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.constant.SQLConstants;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ReminderVO;
import com.kc.util.DBConnector;

public class ReminderDAO {
	
	private static final Logger LOG = LogManager.getLogger(ReminderDAO.class);
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
			preparedStatement = conn.prepareStatement(SQLConstants.CREATE_REMINDER);
			preparedStatement.setString(1, reminderVO.getReferenceNo());
			preparedStatement.setInt(2, reminderVO.getTotalReminder());
			preparedStatement.setInt(3, reminderVO.getFrequency());
			preparedStatement.setString(4, reminderVO.getLastSent());
			preparedStatement.setString(5, reminderVO.getNextSend());
			preparedStatement.setString(6, reminderVO.getStatus());
			preparedStatement.setString(7, reminderVO.getSubject());
			preparedStatement.setString(8, reminderVO.getEmailMessage());
			preparedStatement.setString(9, reminderVO.getReciever());
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : createReminder");
	}
	public void updateReminder(ReminderVO reminderVO, String string)
	{

		LOG.info("Enter : createReminder");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.UPDATE_REMINDER);
			preparedStatement.setInt(1, reminderVO.getTotalReminder());
			preparedStatement.setInt(2, reminderVO.getFrequency());
			preparedStatement.setString(3, reminderVO.getLastSent());
			preparedStatement.setString(4, reminderVO.getNextSend());
			preparedStatement.setString(5, reminderVO.getStatus());
			preparedStatement.setString(6, reminderVO.getSubject());
			preparedStatement.setString(7, reminderVO.getEmailMessage());
			preparedStatement.setString(8, reminderVO.getReciever());
			preparedStatement.setString(9, string);
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : createReminder");
	}
	
	public ObservableList<ReminderVO> getReminderDetails() throws SQLException
	{

		LOG.info("Enter : getReminders");
		ObservableList<ReminderVO> listOfReminders = FXCollections.observableArrayList();
		try{
				
				conn = DBConnector.getConnection();
				preparedStatement=conn.prepareStatement(SQLConstants.GET_REMINDERS);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next())
				{
					ReminderVO reminderVO = new ReminderVO();
					reminderVO.setId(resultSet.getInt(1));
					reminderVO.setReferenceNo(resultSet.getString(2));
					reminderVO.setTotalReminder(resultSet.getInt(3));
					reminderVO.setFrequency(resultSet.getInt(4));
					reminderVO.setLastSent(resultSet.getString(5));
					reminderVO.setNextSend(resultSet.getString(6));
					reminderVO.setStatus(resultSet.getString(7));
					reminderVO.setSubject(resultSet.getString(8));
					reminderVO.setEmailMessage(resultSet.getString(9));
					reminderVO.setReciever(resultSet.getString(10));
					listOfReminders.add(reminderVO);
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
	
	public ObservableList<EnquiryViewVO> getModifyReminders(String startDate,String endDate) throws SQLException
	{
		LOG.info("Enter : getModifyReminders");
		ObservableList<EnquiryViewVO> listOfReminders = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			preparedStatement=conn.prepareStatement(SQLConstants.GET_MODIFY_REMINDERS);
			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
				enquiryViewVO.setReferenceNo(resultSet.getString(1));
				listOfReminders.add(enquiryViewVO);
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
		LOG.info("Exit : getModifyReminders");
		return listOfReminders;
	}

	public ObservableList<EnquiryViewVO> getCreateReminders(String startDate,String endDate ) throws SQLException
	{
		LOG.info("Enter : getCreateReminders");
		ObservableList<EnquiryViewVO> listOfReminders = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_CREATE_REMINDERS);
			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
				enquiryViewVO.setReferenceNo(resultSet.getString(1));
				enquiryViewVO.setEmailId(resultSet.getString(2));
				listOfReminders.add(enquiryViewVO);
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
		LOG.info("Exit : getCreateReminders");
		return listOfReminders;
	}
	public ObservableList<EnquiryVO> getStatusEnquires(String startDate, String endDate, String status) throws Exception
	{
		LOG.info("Enter : getStatusEnquires");
		ObservableList<EnquiryVO> listOfEnquries = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_STATUS_ENQUIRY);
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
	public void saveConfiguration(Map<String, String> map, String date)
	{
		try{
			Set<String> keys = map.keySet();
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			for(String key : keys)
			{
				String query = "UPDATE STATIC_UTIL SET `value`='";
				query=query+map.get(key);
				query = query + "' ,`last_updated`='";
				query = query + date;
				query = query + "' where `key`='" + key + "'";
				statement.addBatch(query);
			}
			statement.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	public Map<String, String> getReminderMailDetails()
	{
		LOG.info("Enter : getReminderMailDetails");
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT `KEY`, VALUE FROM STATIC_UTIL");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				if(resultSet.getString(1).equals(CommonConstants.KEY_REMINDER_EMAIL))
				{
					map.put(CommonConstants.KEY_REMINDER_EMAIL,resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_REMINDER_PASSWORD))
				{
					map.put(CommonConstants.KEY_REMINDER_PASSWORD, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_REMINDER_USERNAME))
				{
					map.put(CommonConstants.KEY_REMINDER_USERNAME, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_REMINDER_MESSAGE))
				{
					map.put(CommonConstants.KEY_REMINDER_MESSAGE, resultSet.getString(2));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : getReminderMailDetails");
		return map;
	}
	public void deleteReminder(String ref) throws Exception {
		LOG.info("Enter : deleteReminder");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.DELETE_REMINDER);
			preparedStatement.setString(1, ref);
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : deleteReminder");
	}
	
	public List<ReminderVO> getReminderDetailsForSendingEmail(String date)
	{
		LOG.info("Enter : getReminderDetailsForSendingEmail");
		List<ReminderVO> list = new ArrayList<ReminderVO>();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_REMINDERS_FOR_EMAIL);
			preparedStatement.setString(1, date);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				ReminderVO reminderVO = new ReminderVO();
				reminderVO.setId(resultSet.getInt(1));
				reminderVO.setReferenceNo(resultSet.getString(2));
				reminderVO.setTotalReminder(resultSet.getInt(3));
				reminderVO.setFrequency(resultSet.getInt(4));
				reminderVO.setLastSent(resultSet.getString(5));
				reminderVO.setNextSend(resultSet.getString(6));
				reminderVO.setStatus(resultSet.getString(7));
				reminderVO.setSubject(resultSet.getString(8));
				reminderVO.setEmailMessage(resultSet.getString(9));
				reminderVO.setReciever(resultSet.getString(10));
				list.add(reminderVO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : getReminderMailDetails");
		return list;
	}
	
	public void updateNoOfReminderSent(String ref) throws Exception {
		LOG.info("Enter : updateNoOfReminderSent");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.UPDATE_REMINDER_SENT);
			preparedStatement.setString(1, ref);
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : updateNoOfReminderSent");
	}
	
	public int getNoOfReminderSent(String refNumber)
	{
		LOG.info("Enter : getReminderDetailsForSendingEmail");
		int number = 0;
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_REMINDER_SENT);
			preparedStatement.setString(1, refNumber);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				number = resultSet.getInt(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : getReminderMailDetails");
		return number;
	}
}
