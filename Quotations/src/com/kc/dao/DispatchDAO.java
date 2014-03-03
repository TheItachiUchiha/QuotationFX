package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.constant.SQLConstants;
import com.kc.model.DispatchVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.DBConnector;

public class DispatchDAO {
	
	private static final Logger LOG = LogManager.getLogger(DispatchDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public ObservableList<EnquiryViewVO> getDispatchEnquiryList(String startDate , String endDate) throws Exception
	{
		LOG.info("Enter : getDispatchEnquiryList");
		ObservableList<EnquiryViewVO> listOfEnquries = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement(SQLConstants.GET_DISPATH_ENQUIRY_LIST);
			preparedStatement.setString(1, "Y");
			preparedStatement.setString(2, "N");
			preparedStatement.setString(3, startDate);
			preparedStatement.setString(4, endDate);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
				enquiryViewVO.setReferenceNo(resultSet.getString(1));
				enquiryViewVO.setEmailId(resultSet.getString(2));
				listOfEnquries.add(enquiryViewVO);
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
		LOG.info("Exit : getDispatchEnquiryList");
		return listOfEnquries;
	}
	
	public void newDispatch(DispatchVO dispatchVO)
	{
		LOG.info("Enter : newDispatch");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.NEW_DISPATCH);
			preparedStatement.setString(1, dispatchVO.getReferenceNo());
			preparedStatement.setString(2, dispatchVO.getInvoiceNo());
			preparedStatement.setString(3, dispatchVO.getInvoiceDate());
			preparedStatement.setString(4, dispatchVO.getBillingName());
			preparedStatement.setString(5, dispatchVO.getShippingTo());
			preparedStatement.setString(6, dispatchVO.getTransporter());
			preparedStatement.setString(7, dispatchVO.getDispatchDate());
			preparedStatement.setString(8, dispatchVO.getTrackingNo());
			preparedStatement.setInt(9, dispatchVO.getNoOfItems());
			preparedStatement.setString(10, dispatchVO.getFreightMode());
			preparedStatement.setDouble(11, dispatchVO.getFreightAmount());
			preparedStatement.setString(12, dispatchVO.getCompanyName());
			preparedStatement.setString(13, dispatchVO.getCustomerEmail());
			
			preparedStatement.execute();
			
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.NEW_DISPATCH_SUB);
			preparedStatement.setString(1, dispatchVO.getReferenceNo());
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : newDispatch");
	}
	
	public void updateDispatch(DispatchVO dispatchVO)
	{
		LOG.info("Enter : updateDispatch");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.UPDATE_DISPATCH);
			
			preparedStatement.setString(1, dispatchVO.getInvoiceNo());
			preparedStatement.setString(2, dispatchVO.getInvoiceDate());
			preparedStatement.setString(3, dispatchVO.getBillingName());
			preparedStatement.setString(4, dispatchVO.getShippingTo());
			preparedStatement.setString(5, dispatchVO.getTransporter());
			preparedStatement.setString(6, dispatchVO.getDispatchDate());
			preparedStatement.setString(7, dispatchVO.getTrackingNo());
			preparedStatement.setInt(8, dispatchVO.getNoOfItems());
			preparedStatement.setString(9, dispatchVO.getFreightMode());
			preparedStatement.setDouble(10, dispatchVO.getFreightAmount());
			preparedStatement.setString(11, dispatchVO.getCompanyName());
			preparedStatement.setInt(12, dispatchVO.getId());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : updateDispatch");
	}
	
	public ObservableList<DispatchVO> getProductDispatch() throws SQLException
	{
		LOG.info("Enter : getProductDispatch");
		ObservableList<DispatchVO> listOfDispatch = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQLConstants.GET_PRODUCT_DISPATCHS);
			
			while(resultSet.next())
			{
				DispatchVO dispatchVO = new DispatchVO();
				dispatchVO.setId(resultSet.getInt(1));
				dispatchVO.setReferenceNo(resultSet.getString(2));
				dispatchVO.setInvoiceNo(resultSet.getString(3));
				dispatchVO.setInvoiceDate(resultSet.getString(4));
				dispatchVO.setBillingName(resultSet.getString(5));
				dispatchVO.setShippingTo(resultSet.getString(6));
				dispatchVO.setTransporter(resultSet.getString(7));
				dispatchVO.setDispatchDate(resultSet.getString(8));
				dispatchVO.setTrackingNo(resultSet.getString(9));
				dispatchVO.setNoOfItems(resultSet.getInt(10));
				dispatchVO.setFreightMode(resultSet.getString(11));
				dispatchVO.setFreightAmount(resultSet.getDouble(12));
				dispatchVO.setCompanyName(resultSet.getString(13));
				dispatchVO.setCustomerEmail(resultSet.getString(14));
				
				listOfDispatch.add(dispatchVO);
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
		LOG.info("Exit : getProductDispatch");
		return listOfDispatch;
	}
	
	public void deleteDispatch(DispatchVO dispatchVO) throws Exception
	{
		LOG.info("Enter : deleteDispatch");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.DELETE_DISPATCH);
			preparedStatement.setInt(1, dispatchVO.getId());
			preparedStatement.execute();
			
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.DELETE_DISPATCH_SUB);
			preparedStatement.setString(1, dispatchVO.getReferenceNo());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : deleteDispatch");
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
	
	
	public Map<String, String> getDispatchOptionDefaultValues()
	{
		LOG.info("Enter : getDispatchOptionDefaultValues");
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT `KEY`, VALUE FROM STATIC_UTIL");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				if(resultSet.getString(1).equals(CommonConstants.KEY_DISPATCH_EMAIL))
				{
					map.put(CommonConstants.KEY_DISPATCH_EMAIL,resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_DISPATCH_MESSAGE))
				{
					map.put(CommonConstants.KEY_DISPATCH_MESSAGE, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_DISPATCH_PASSWORD))
				{
					map.put(CommonConstants.KEY_DISPATCH_PASSWORD, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_DISPATCH_USERNAME))
				{
					map.put(CommonConstants.KEY_DISPATCH_USERNAME, resultSet.getString(2));
				}
			}
			LOG.info("Exit : getDispatchOptionDefaultValues");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return map;
	}

}
