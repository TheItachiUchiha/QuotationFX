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
import com.kc.util.DBConnector;

public class SalesDAO {
	private static final Logger LOG = LogManager.getLogger(SalesDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public ObservableList<EnquiryVO> getListOfEnquiresForSales() throws SQLException
	{
		LOG.info("Enter : getListOfEnquiresForSales");
		ObservableList<EnquiryVO> listOfEnquries = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM ENQUIRY WHERE priceestimation='Y' AND quotationpreparation='Y' AND emailsent='Y' AND salesdone='N'");
			
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
		LOG.info("Exit : getListOfEnquiresForSales");
		return listOfEnquries;
	}
	
	public void saveSalesDate(int id, String date ) throws Exception
	{
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE ENQUIRY SET sales_date=?, salesdone=? where ID=?");
			preparedStatement.setString(1, date);
			preparedStatement.setString(2, "Y");
			preparedStatement.setInt(3, id);
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateSalesDate(int id, String date ) throws Exception
	{
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE ENQUIRY SET sales_date=? where ID=?");
			preparedStatement.setString(1, date);
			preparedStatement.setInt(2, id);
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
