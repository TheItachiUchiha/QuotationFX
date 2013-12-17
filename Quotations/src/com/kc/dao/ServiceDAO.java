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

import com.kc.constant.CommonConstants;
import com.kc.model.EnquiryVO;
import com.kc.model.ServiceVO;
import com.kc.util.DBConnector;

public class ServiceDAO {
	private static final Logger LOG = LogManager.getLogger(ServiceDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void newService(ServiceVO serviceVO)
	{
		LOG.info("Enter : newService");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO service(reference_no,engineer_name,complaint,date,rating,charge) VALUES(?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, serviceVO.getReferenceNo());
			preparedStatement.setString(2, serviceVO.getEngineerName());
			preparedStatement.setString(3, serviceVO.getComplaint());
			preparedStatement.setString(4, serviceVO.getDate());
			preparedStatement.setString(5, serviceVO.getRating());
			preparedStatement.setString(6, serviceVO.getCharge());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : newService");
	}
	public void updateService(ServiceVO serviceVO)
	{

		LOG.info("Enter : updateService");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE service SET engineer_name=?,complaint=?,date=?,rating=?,charge=? where reference_no=?");
			
			preparedStatement.setString(1, serviceVO.getEngineerName());
			preparedStatement.setString(2, serviceVO.getComplaint());
			preparedStatement.setString(3, serviceVO.getDate());
			preparedStatement.setString(4, serviceVO.getRating());
			preparedStatement.setString(5, serviceVO.getCharge());
			preparedStatement.setString(6, serviceVO.getReferenceNo());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : updateService");
	
	}
	
	public ObservableList<ServiceVO> getServices() throws SQLException
	{
		LOG.info("Enter : getServices");
		ObservableList<ServiceVO> listOfServices = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			
			preparedStatement = conn.prepareStatement("SELECT * FROM SERVICE");
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
				serviceVO.setCharge(resultSet.getString(7));
				
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
		LOG.info("Exit : getServices");
		return listOfServices;
	}
	public ObservableList<ServiceVO> getServices(String reference_no) throws SQLException
	{
		LOG.info("Enter : getServices");
		ObservableList<ServiceVO> listOfServices = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			
			preparedStatement = conn.prepareStatement("SELECT * FROM SERVICE WHERE reference_no = ?");
			preparedStatement.setString(1, reference_no);
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
				serviceVO.setCharge(resultSet.getString(7));
				
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
		LOG.info("Exit : getServices");
		return listOfServices;
	}
	public void deleteService(String string)
	{
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("DELETE FROM SERVICE WHERE reference_no=?");
			
			preparedStatement.setString(1, string);
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ObservableList<EnquiryVO> getServiceEnquires() throws Exception
	{
		LOG.info("Enter : getServiceEnquires");
		ObservableList<EnquiryVO> listOfEnquries = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("SELECT e.* FROM ENQUIRY e, SERVICE s WHERE e.ref_number = s.reference_no");
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
		LOG.info("Exit : getServiceEnquires");
		return listOfEnquries;
	}
	
	public ObservableList<EnquiryVO> getServiceEnquires(String startDate, String endDate) throws Exception
	{
		LOG.info("Enter : getServiceEnquires");
		ObservableList<EnquiryVO> listOfEnquries = FXCollections.observableArrayList();
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("select e.* from quotation.enquiry e where e.ref_number in (select distinct s.reference_no from quotation.service s where "+
							"STR_TO_DATE(s.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and "+ 
							"STR_TO_DATE(s.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y'))");
			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);
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
		LOG.info("Exit : getServiceEnquires");
		return listOfEnquries;
	}
}
