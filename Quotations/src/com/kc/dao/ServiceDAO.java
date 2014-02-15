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
import com.kc.model.ComplaintVO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ServiceVO;
import com.kc.util.DBConnector;

public class ServiceDAO {
	private static final Logger LOG = LogManager.getLogger(ServiceDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void newComplaint(ServiceVO serviceVO)
	{
		LOG.info("Enter : newComplaint");
		try
		{
			int count=0;
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE ENQUIRY SET complaint_count = complaint_count + 1 where REF_NUMBER=?");
			preparedStatement.setString(1, serviceVO.getReferenceNo());
			preparedStatement.execute();
		
			preparedStatement = conn.prepareStatement("SELECT complaint_count FROM ENQUIRY WHERE REF_NUMBER = ?");
			preparedStatement.setString(1, serviceVO.getReferenceNo());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				count=resultSet.getInt(1);
			}
			
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO service(reference_no,engineer_name,complaint,date,rating,charge,complaint_date,feedback,complaint_id,product_name,customer_id,customer_name,contact_no) VALUES(?,?,?,?,?,?,?, ?, ?, ?, ?, ?,?)");
			preparedStatement.setString(1, serviceVO.getReferenceNo());
			preparedStatement.setString(2, serviceVO.getEngineerName());
			preparedStatement.setString(3, serviceVO.getComplaint());
			preparedStatement.setString(4, serviceVO.getDate());
			preparedStatement.setString(5, serviceVO.getRating());
			preparedStatement.setDouble(6, serviceVO.getCharge());
			preparedStatement.setString(7, serviceVO.getComplaintDate());
			preparedStatement.setString(8, serviceVO.getFeedback());
			preparedStatement.setString(9, serviceVO.getReferenceNo()+"-"+count);
			preparedStatement.setString(10, serviceVO.getProductName());
			preparedStatement.setInt(11, serviceVO.getCustomerId());
			preparedStatement.setString(12, serviceVO.getCustomerName());
			preparedStatement.setString(13, serviceVO.getContactNo());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : newComplaint");
	}
	
	public void updateComplaint(ComplaintVO complaintVO)
	{
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE SERVICE SET complaint=?,complaint_date=? where id=?");
			preparedStatement.setString(1, complaintVO.getComplaint());
			preparedStatement.setString(2, complaintVO.getDateOfComplaint());
			preparedStatement.setInt(3, complaintVO.getId());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
	public ObservableList<ServiceVO> getComplaintList() throws Exception
	{
		LOG.info("Enter : getComplaintList");
		ObservableList<ServiceVO> listOfEnquries = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("select s.id,s.reference_no,s.complaint,s.complaint_date,s.complaint_id,s.product_name,s.customer_id, e.service_count from quotation.service s, quotation.enquiry e where e.ref_number=s.reference_no and s.date=?");
			preparedStatement.setString(1, CommonConstants.NA);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				ServiceVO serviceVO = new ServiceVO();
				serviceVO.setId(resultSet.getInt(1));
				serviceVO.setReferenceNo(resultSet.getString(2));
				serviceVO.setComplaint(resultSet.getString(3));
				serviceVO.setComplaintDate(resultSet.getString(4));
				serviceVO.setComplaintId(resultSet.getString(5));
				serviceVO.setProductName(resultSet.getString(6));
				serviceVO.setCustomerId(resultSet.getInt(7));
				serviceVO.setServiceCount(resultSet.getInt(8));
				
				listOfEnquries.add(serviceVO);
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
		LOG.info("Exit : getComplaintList");
		return listOfEnquries;
	}
	
	public void deleteComplaint(ComplaintVO complaintVO) throws Exception
	{
		LOG.info("Enter : deleteComplaint");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("DELETE FROM Service WHERE ID=?");
			preparedStatement.setInt(1, complaintVO.getId());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : deleteComplaint");
	}
	
	public void newService(ServiceVO serviceVO,String complaintId)
	{
		LOG.info("Enter : newService");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE SERVICE SET engineer_name=?,complaint=?,date=?,rating=?,charge=?,feedback=? where complaint_id=?");
			preparedStatement.setString(1, serviceVO.getEngineerName());
			preparedStatement.setString(2, serviceVO.getComplaint());
			preparedStatement.setString(3, serviceVO.getDate());
			preparedStatement.setString(4, serviceVO.getRating());
			preparedStatement.setDouble(5, serviceVO.getCharge());
			preparedStatement.setString(6, serviceVO.getFeedback());
			preparedStatement.setString(7, complaintId);
			
			preparedStatement.execute();
			
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE ENQUIRY SET service_count = service_count + 1 where REF_NUMBER=?");
			preparedStatement.setString(1, serviceVO.getReferenceNo());
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : newService");
	}
	
	public ObservableList<ServiceVO> getServiceList(String startDate, String endDate) throws Exception
	{
		LOG.info("Enter : getComplaintList");
		ObservableList<ServiceVO> listOfEnquries = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("select reference_no,complaint,complaint_date,complaint_id,product_name,customer_id from quotation.service where date!=? and "+
							"STR_TO_DATE(`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and "+
							"STR_TO_DATE(`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')");
			preparedStatement.setString(1, CommonConstants.NA);
			preparedStatement.setString(2, startDate);
			preparedStatement.setString(3, endDate);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				ServiceVO serviceVO = new ServiceVO();
				serviceVO.setReferenceNo(resultSet.getString(1));
				serviceVO.setComplaint(resultSet.getString(2));
				serviceVO.setComplaintDate(resultSet.getString(3));
				serviceVO.setComplaintId(resultSet.getString(4));
				serviceVO.setProductName(resultSet.getString(5));
				serviceVO.setCustomerId(resultSet.getInt(6));
				
				listOfEnquries.add(serviceVO);
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
		LOG.info("Exit : getComplaintList");
		return listOfEnquries;
	}
	
	public ObservableList<ServiceVO> getServiceDetails() throws Exception
	{
		LOG.info("Enter : getServiceDetails");
		ObservableList<ServiceVO> listOfServices = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("select * from quotation.SERVICE where date!=?");
			preparedStatement.setString(1, CommonConstants.NA);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				ServiceVO serviceVO = new ServiceVO();
				serviceVO.setId(resultSet.getInt(1));
				serviceVO.setReferenceNo(resultSet.getString(2));
				serviceVO.setEngineerName(resultSet.getString(3));
				serviceVO.setComplaint(resultSet.getString(4));
				serviceVO.setDate(resultSet.getString(5));
				serviceVO.setRating(resultSet.getString(6));
				serviceVO.setCharge(resultSet.getDouble(7));
				serviceVO.setComplaintDate(resultSet.getString(8));
				serviceVO.setFeedback(resultSet.getString(9));
				serviceVO.setComplaintId(resultSet.getString(10));
				
				listOfServices.add(serviceVO);
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
		LOG.info("Exit : getServiceDetails");
		return listOfServices;
	}
	
	public ObservableList<EnquiryViewVO> getServiceHistory(String startDate , String endDate) throws Exception
	{
		LOG.info("Enter : getServiceHistory");
		ObservableList<EnquiryViewVO> listOfServices = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("SELECT s.reference_no , s.product_name, s.complaint_date, s.date, c.customername, c.companyname, c.state , e.referedby FROM quotation.SERVICE s, "+
			"quotation.Customers c, quotation.enquiry e where e.ref_number=s.reference_no and e.cust_id=c.id and s.date!=? and "+
			"STR_TO_DATE(s.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and "+
			"STR_TO_DATE(s.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')");
			preparedStatement.setString(1, CommonConstants.NA);
			preparedStatement.setString(2, startDate);
			preparedStatement.setString(3, endDate);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				EnquiryViewVO viewVO = new EnquiryViewVO();
				viewVO.setReferenceNo(resultSet.getString(1));
				viewVO.setProductName(resultSet.getString(2));
				viewVO.setComplaintDate(resultSet.getString(3));
				viewVO.setServiceDate(resultSet.getString(4));
				viewVO.setCustomerName(resultSet.getString(5));
				viewVO.setCompanyName(resultSet.getString(6));
				viewVO.setState(resultSet.getString(7));
				viewVO.setReferedBy(resultSet.getString(8));
				
				listOfServices.add(viewVO);
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
		LOG.info("Exit : getServiceHistory");
		return listOfServices;
	}
	
	public void deleteService(ServiceVO serviceVO)
	{
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE SERVICE SET engineer_name=?,date=?,rating=?,charge=?,feedback=? where complaint_id=?");
			preparedStatement.setString(1, serviceVO.getEngineerName());
			preparedStatement.setString(2, serviceVO.getDate());
			preparedStatement.setString(3, serviceVO.getRating());
			preparedStatement.setDouble(4, serviceVO.getCharge());
			preparedStatement.setString(5, serviceVO.getFeedback());
			preparedStatement.setString(6, serviceVO.getComplaintId());
			
			preparedStatement.execute();
			
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE ENQUIRY SET service_count = service_count + -1 where REF_NUMBER=?");
			preparedStatement.setString(1, serviceVO.getReferenceNo());
			preparedStatement.execute();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
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
	
	public Map<String, String> getServiceOptionDefaultValues()
	{
		LOG.info("Enter : getServiceOptionDefaultValues");
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT `KEY`, VALUE FROM STATIC_UTIL");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				if(resultSet.getString(1).equals(CommonConstants.KEY_SERVICE_EMAIL))
				{
					map.put(CommonConstants.KEY_SERVICE_EMAIL,resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_SERVICE_MESSAGE))
				{
					map.put(CommonConstants.KEY_SERVICE_MESSAGE, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_SERVICE_PASSWORD))
				{
					map.put(CommonConstants.KEY_SERVICE_PASSWORD, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_SERVICE_USERNAME))
				{
					map.put(CommonConstants.KEY_SERVICE_USERNAME, resultSet.getString(2));
				}
			}
			LOG.info("Exit : getServiceOptionDefaultValues");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return map;
	}
	
}
