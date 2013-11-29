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

public class EnquiryDAO {
	private static final Logger LOG = LogManager.getLogger(EnquiryDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void saveEnquiry(EnquiryVO enquiryVO) throws Exception
	{
		LOG.info("Enter : saveEnquiry");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO enquiry(cust_id,referedby,cust_req,purchase_period,cust_doc,priceestimation,quotationpreparation,emailsent,date,prod_name,salesdone,type) VALUES(?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?)");
			preparedStatement.setInt(1, enquiryVO.getCustomerId());
			preparedStatement.setString(2, enquiryVO.getReferedBy());
			preparedStatement.setString(3, enquiryVO.getCustomerrequirements());
			preparedStatement.setString(4, enquiryVO.getPurchasePeriod());
			preparedStatement.setString(5, enquiryVO.getCustomerDocument());
			preparedStatement.setString(6, enquiryVO.getPriceEstimation());
			preparedStatement.setString(7, enquiryVO.getQuotationPreparation());
			preparedStatement.setString(8, enquiryVO.getEmailSent());
			preparedStatement.setString(9, enquiryVO.getDate());
			preparedStatement.setString(10, enquiryVO.getProductName());
			preparedStatement.setString(11, enquiryVO.getSales());
			preparedStatement.setString(12, enquiryVO.getFlag());
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : saveEnquiry");
	}
	
	public ObservableList<EnquiryVO> getEnquries() throws SQLException
	{
		LOG.info("Enter : getEnquries");
		ObservableList<EnquiryVO> listOfEnquries = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM ENQUIRY");
			
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
		LOG.info("Exit : getEnquries");
		return listOfEnquries;
	}
}
