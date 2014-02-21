package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.ComponentsVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.DBConnector;


public class PriceEstimationDAO {
	private static final Logger LOG = LogManager.getLogger(PriceEstimationDAO.class);
	private Connection conn = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet = null;
	public void savePriceEstimation(EnquiryVO enquiryVO)
	{
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE ENQUIRY SET priceestimation = 'Y', margin=?, pe_date=?,total_revenue=?,customer_type=? where id=? ");
			preparedStatement.setDouble(1, enquiryVO.getMargin());
			preparedStatement.setString(2, enquiryVO.getPeDate());
			preparedStatement.setDouble(3, enquiryVO.getTotalRevenue());
			preparedStatement.setString(4, enquiryVO.getEnquiryCustomerType());
			preparedStatement.setInt(5, enquiryVO.getId());
			preparedStatement.execute();
			statement = conn.createStatement();
			for(ComponentsVO component : enquiryVO.getList())
			{
				statement.addBatch("INSERT into enquiry_component(enquiry_id, component_id, quantity) values("+enquiryVO.getId()+","+component.getId()+","+component.getQuantity()+")");
			}
			statement.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void modifyPriceEstimation(EnquiryVO enquiryVO) throws Exception
	{
		LOG.info("Enter : modifyPriceEstimation");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("DELETE FROM ENQUIRY_COMPONENT WHERE ENQUIRY_ID=?");
			preparedStatement.setInt(1, enquiryVO.getId());
			preparedStatement.execute();
			
			preparedStatement = conn
					.prepareStatement("UPDATE ENQUIRY SET MARGIN=?,total_revenue=?, customer_type=? WHERE ID=?");
			preparedStatement.setDouble(1, enquiryVO.getMargin());
			preparedStatement.setDouble(2, enquiryVO.getTotalRevenue());
			preparedStatement.setString(3, enquiryVO.getEnquiryCustomerType());
			preparedStatement.setInt(4, enquiryVO.getId());
			preparedStatement.execute();
			
			statement = conn.createStatement();
			for(ComponentsVO component : enquiryVO.getList())
			{
				statement.addBatch("INSERT into enquiry_component(enquiry_id, component_id, quantity) values("+enquiryVO.getId()+","+component.getId()+","+component.getQuantity()+")");
			}
			statement.executeBatch();
			
		}catch (Exception e) {
			//e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
	}
	
	public ObservableList<EnquiryVO> getPriceEstimation() throws Exception
	{
		LOG.info("Enter : getPriceEstimation");
		ObservableList<EnquiryVO> listOfEnquries = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("SELECT * FROM ENQUIRY WHERE PRICEESTIMATION ='Y'");
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
				enquiryVO.setTotalRevenue(resultSet.getDouble(22));
				enquiryVO.setEnquiryCustomerType(resultSet.getString(23));
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
	public void deletePriceEstimation(EnquiryViewVO enquiryViewVO) throws Exception
	{
		LOG.info("Enter : deletePriceEstimation");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("DELETE FROM ENQUIRY_COMPONENT WHERE ENQUIRY_ID=?");
			preparedStatement.setInt(1, enquiryViewVO.getId());
			preparedStatement.execute();
			
			preparedStatement = conn.prepareStatement("UPDATE ENQUIRY SET priceestimation=?, pe_date='N/A' WHERE ID=?");
			preparedStatement.setString(1,"N");
			preparedStatement.setInt(2, enquiryViewVO.getId());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : deletePriceEstimation");
	}
}

