package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.constant.SQLConstants;
import com.kc.model.EnquiryVO;
import com.kc.util.DBConnector;

public class ReportsDAO 
{
	private static final Logger LOG = LogManager.getLogger(ReportsDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	
	public ObservableList<EnquiryVO> salesReport(String startDate, String endDate) throws Exception {
		LOG.info("Enter : salesReportCategory");
		ObservableList<EnquiryVO> listOfEnquiries = FXCollections.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.SALES_REPORT);
			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				EnquiryVO enquiryVO = new EnquiryVO();
				enquiryVO.setRefNumber(resultSet.getString(1));
				enquiryVO.setSales(resultSet.getString(2));
				enquiryVO.setProductId(resultSet.getInt(3));
				enquiryVO.setCustomerId(resultSet.getInt(4));
				enquiryVO.setTotalRevenue(resultSet.getDouble(5));
				listOfEnquiries.add(enquiryVO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return listOfEnquiries;
	}
	
	public ObservableList<Integer> getServicingChargeFromReference(String referenceNumber) throws SQLException {
		LOG.info("Enter : getNumberOfService");
		ObservableList<Integer> listOfCharges = FXCollections.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_SERVICE_CHARGE);
			preparedStatement.setString(1, referenceNumber);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				listOfCharges.add(resultSet.getInt(1));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getNumberOfService");
		return listOfCharges;
	}
	
	public ObservableList<Map<String, Object>> getServicingEngineerDetails(String startDate, String endDate, String serviceEnggName) throws SQLException {
		LOG.info("Enter : getNumberOfService");
		ObservableList<Map<String, Object>> listOfDetails = FXCollections.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_SERVICE_ENGINEER_DETAILS);
			preparedStatement.setString(1, serviceEnggName);
			preparedStatement.setString(2, startDate);
			preparedStatement.setString(3, endDate);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(CommonConstants.KEY_REPORT_REF, resultSet.getObject(1));
				map.put(CommonConstants.KEY_REPORT_DATE, resultSet.getObject(2));
				map.put(CommonConstants.KEY_REPORT_CUST, resultSet.getObject(3));
				map.put(CommonConstants.KEY_REPORT_COMP, resultSet.getObject(4));
				map.put(CommonConstants.KEY_REPORT_LOC, resultSet.getObject(5));
				map.put(CommonConstants.KEY_REPORT_REVENUE, resultSet.getObject(6));
				listOfDetails.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getNumberOfService");
		return listOfDetails;
	}
	public ObservableList<Map<String, Object>> getEnquriesFromReference(String ref_number) throws SQLException
	{
		LOG.info("Enter : getEnquriesFromReference");
		ObservableList<Map<String, Object>> listOfEnquries = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_ENQUIRIES_FROM_REFNO);
			preparedStatement.setString(1, ref_number);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.KEY_REPORT_REF, resultSet.getString(1));
				map.put(CommonConstants.KEY_REPORT_DATE_ENQUIRY, resultSet.getString(2));
				map.put(CommonConstants.KEY_REPORT_DATE_QUOTATION, resultSet.getString(3));
				map.put(CommonConstants.KEY_REPORT_DATE_SALES, resultSet.getString(4));
				map.put(CommonConstants.KEY_REPORT_PROD, resultSet.getString(5));
				map.put(CommonConstants.KEY_REPORT_CUST, resultSet.getString(6));
				map.put(CommonConstants.KEY_REPORT_COMP, resultSet.getString(7));
				map.put(CommonConstants.KEY_REPORT_LOC, resultSet.getString(8));
				map.put(CommonConstants.KEY_REPORT_REFD, resultSet.getString(9));
				map.put(CommonConstants.KEY_REPORT_SERV_NO, resultSet.getString(10));
				map.put(CommonConstants.KEY_REPORT_REVENUE, resultSet.getString(11));
				map.put(CommonConstants.KEY_REPORT_SERV, resultSet.getString(12));
				listOfEnquries.add(map);
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
		LOG.info("Exit : getEnquriesFromReference");
		return listOfEnquries;
	}
	
	public ObservableList<Map<String, Object>> getEnquriesFromReferredBy(String referredBy) throws SQLException
	{
		LOG.info("Enter : getEnquriesFromReferredBy");
		ObservableList<Map<String, Object>> listOfEnquries = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_ENQUIRIES_FROM_REFEREDBY);
			preparedStatement.setString(1, referredBy);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.KEY_REPORT_REF, resultSet.getString(1));
				map.put(CommonConstants.KEY_REPORT_DATE_ENQUIRY, resultSet.getString(2));
				map.put(CommonConstants.KEY_REPORT_DATE_QUOTATION, resultSet.getString(3));
				map.put(CommonConstants.KEY_REPORT_DATE_SALES, resultSet.getString(4));
				map.put(CommonConstants.KEY_REPORT_PROD, resultSet.getString(5));
				map.put(CommonConstants.KEY_REPORT_CUST, resultSet.getString(6));
				map.put(CommonConstants.KEY_REPORT_COMP, resultSet.getString(7));
				map.put(CommonConstants.KEY_REPORT_LOC, resultSet.getString(8));
				map.put(CommonConstants.KEY_REPORT_REFD, resultSet.getString(9));
				map.put(CommonConstants.KEY_REPORT_SERV_NO, resultSet.getString(10));
				map.put(CommonConstants.KEY_REPORT_REVENUE, resultSet.getString(11));
				map.put(CommonConstants.KEY_REPORT_SERV, resultSet.getString(12));
				listOfEnquries.add(map);
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
		LOG.info("Exit : getEnquriesFromReferredBy");
		return listOfEnquries;
	}
	public ObservableList<Map<String, Object>> getEnquriesFromCustomerName(String customerName) throws SQLException
	{
		LOG.info("Enter : getEnquriesFromCustomerId");
		ObservableList<Map<String, Object>> listOfEnquries = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_ENQUIRIES_FROM_CUST_NAME);
			preparedStatement.setString(1, customerName);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.KEY_REPORT_REF, resultSet.getString(1));
				map.put(CommonConstants.KEY_REPORT_DATE_ENQUIRY, resultSet.getString(2));
				map.put(CommonConstants.KEY_REPORT_DATE_QUOTATION, resultSet.getString(3));
				map.put(CommonConstants.KEY_REPORT_DATE_SALES, resultSet.getString(4));
				map.put(CommonConstants.KEY_REPORT_PROD, resultSet.getString(5));
				map.put(CommonConstants.KEY_REPORT_CUST, resultSet.getString(6));
				map.put(CommonConstants.KEY_REPORT_COMP, resultSet.getString(7));
				map.put(CommonConstants.KEY_REPORT_LOC, resultSet.getString(8));
				map.put(CommonConstants.KEY_REPORT_REFD, resultSet.getString(9));
				map.put(CommonConstants.KEY_REPORT_SERV_NO, resultSet.getString(10));
				map.put(CommonConstants.KEY_REPORT_REVENUE, resultSet.getString(11));
				map.put(CommonConstants.KEY_REPORT_SERV, resultSet.getString(12));
				listOfEnquries.add(map);
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
		LOG.info("Exit : getEnquriesFromCustomerId");
		return listOfEnquries;
	}
	
	public ObservableList<Map<String, Object>> getEnquriesFromCompanyName(String companyName) throws SQLException
	{
		LOG.info("Enter : getEnquriesFromCustomerId");
		ObservableList<Map<String, Object>> listOfEnquries = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_ENQUIRIES_FROM_COMP_NAME);
			preparedStatement.setString(1, companyName);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.KEY_REPORT_REF, resultSet.getString(1));
				map.put(CommonConstants.KEY_REPORT_DATE_ENQUIRY, resultSet.getString(2));
				map.put(CommonConstants.KEY_REPORT_DATE_QUOTATION, resultSet.getString(3));
				map.put(CommonConstants.KEY_REPORT_DATE_SALES, resultSet.getString(4));
				map.put(CommonConstants.KEY_REPORT_PROD, resultSet.getString(5));
				map.put(CommonConstants.KEY_REPORT_CUST, resultSet.getString(6));
				map.put(CommonConstants.KEY_REPORT_COMP, resultSet.getString(7));
				map.put(CommonConstants.KEY_REPORT_LOC, resultSet.getString(8));
				map.put(CommonConstants.KEY_REPORT_REFD, resultSet.getString(9));
				map.put(CommonConstants.KEY_REPORT_SERV_NO, resultSet.getString(10));
				map.put(CommonConstants.KEY_REPORT_REVENUE, resultSet.getString(11));
				map.put(CommonConstants.KEY_REPORT_SERV, resultSet.getString(12));
				listOfEnquries.add(map);
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
		LOG.info("Exit : getEnquriesFromCustomerId");
		return listOfEnquries;
	}
	public ObservableList<String> getReferenceNos() throws SQLException
	{
		LOG.info("Enter : getReferenceNos");
		ObservableList<String> listOfReference = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQLConstants.GET_REFNO);
			
			while(resultSet.next())
			{
				listOfReference.add(resultSet.getString(1));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : getReferenceNos");
		return listOfReference;
	}
	public ObservableList<String> getReferedBy() throws SQLException
	{
		LOG.info("Enter : getReferenceNos");
		ObservableList<String> listOfReferedby = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQLConstants.GET_REFEREDBY);
			
			while(resultSet.next())
			{
				if(!listOfReferedby.contains(resultSet.getString(1)))
				{
					listOfReferedby.add(resultSet.getString(1));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : getReferenceNos");
		return listOfReferedby;
	}
	public ObservableList<String> getCompany() throws SQLException
	{
		LOG.info("Enter : getCompany");
		ObservableList<String> listOfCompany = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQLConstants.GET_COMPANY);
			
			while(resultSet.next())
			{
				if(!listOfCompany.contains(resultSet.getString(1)))
				{
					listOfCompany.add(resultSet.getString(1));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : getCompany");
		return listOfCompany;
	}
	public ObservableList<String> getCustomers() throws SQLException
	{
		LOG.info("Enter : getCustomers");
		ObservableList<String> listOfCustomer = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQLConstants.GET_CUST_NAME);
			
			while(resultSet.next())
			{
				if(!listOfCustomer.contains(resultSet.getString(1)))
				{
					listOfCustomer.add(resultSet.getString(1));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : getCustomers");
		return listOfCustomer;
	}
}
