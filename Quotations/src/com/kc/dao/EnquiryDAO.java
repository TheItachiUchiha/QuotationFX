package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.DBConnector;

public class EnquiryDAO {
	private static final Logger LOG = LogManager.getLogger(EnquiryDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private CustomersDAO customersDAO;
	private Map<String, String> map;
	private CommonConstants commonConstants;
	
	public EnquiryDAO()
	{
		customersDAO = new CustomersDAO();
		map= new HashMap<>();
		commonConstants = new CommonConstants();
	}
	
	public void saveEnquiry(EnquiryVO enquiryVO) throws Exception
	{
		LOG.info("Enter : saveEnquiry");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO enquiry(cust_id,referedby,cust_req,purchase_period,cust_doc,priceestimation,quotationpreparation,emailsent,date,prod_name,salesdone,type, ref_number, prod_id) VALUES(?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)");
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
			preparedStatement.setString(13, enquiryVO.getRefNumber());
			preparedStatement.setInt(14, enquiryVO.getProductId());
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
				enquiryVO.setRefNumber(resultSet.getString(14));
				enquiryVO.setProductId(resultSet.getInt(15));
				
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
	public void saveEnquirylocation(String location, String date) throws Exception
	{
		LOG.info("Enter : saveEnquirylocation");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE STATIC_UTIL SET value=?, LAST_UPDATED=? where `key`=?");
			preparedStatement.setString(1, location);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, "enquiry");
			preparedStatement.execute();
			
			LOG.info("Exit : saveEnquirylocation");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	public String getLatestEnquiryNumber()
	{
		String number = "";
		LOG.info("Enter : getLatestEnquiryNumber");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT value from STATIC_UTIL where `key`=?");
			preparedStatement.setString(1, CommonConstants.ENQUIRY_MONTHLY_NUMBER);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			number = resultSet.getString(1);
			LOG.info("Exit : getLatestEnquiryNumber");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return number;
	}
	public String getPassword()
	{
		String pass = "";
		LOG.info("Enter : getPassword");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT value from STATIC_UTIL where `key`=?");
			preparedStatement.setString(1, "email_password");
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			pass = resultSet.getString(1);
			LOG.info("Exit : getPassword");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return pass;
	}
	public void increaseEnquiryNumber(String enquiryNumber, String date)
	{
		LOG.info("Enter : increaseEnquiryNumber");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE STATIC_UTIL SET VALUE =?, LAST_UPDATED=? where `key` = ?");
			preparedStatement.setString(1, enquiryNumber);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, CommonConstants.ENQUIRY_MONTHLY_NUMBER);
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
	public void checkAndUpdateEnquiryNumber()
	{
		String number = "";
		String oldDate = "";
		String newDate = new SimpleDateFormat(CommonConstants.DATE_FORMAT).format(new Date());
		String newMonth = newDate.substring(3,5);
		String newYear = newDate.substring(8,10);
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT LAST_UPDATED from STATIC_UTIL where `key`=?");
			preparedStatement.setString(1, CommonConstants.ENQUIRY_MONTHLY_NUMBER);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			oldDate = resultSet.getString(1);
			String oldMonth = oldDate.substring(3,5);
			String oldYear = oldDate.substring(6,10);
			if(Integer.valueOf(oldYear)< Integer.valueOf(newYear))
			{
				number = "001";
				increaseEnquiryNumber(number, newDate);
			}
			else if(Integer.valueOf(oldMonth)<Integer.valueOf(newMonth))
			{
				number = "001";
				increaseEnquiryNumber(number, newDate);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
			LOG.info("Exit : getLatestEnquiryNumber");	
	}
	public void deleteEnquiry(EnquiryViewVO enquiryViewVO) throws Exception {
		LOG.info("Enter : deleteEnquiry");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("DELETE FROM ENQUIRY WHERE ID=?");
			preparedStatement.setInt(1, enquiryViewVO.getId());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : deleteEnquiry");
	}
	public void updateEnquiry(EnquiryViewVO enquiryViewVO, CustomersVO customersVO)
	{
		LOG.info("Enter : updateEnquiry");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE ENQUIRY SET cust_id=?,referedby=?,cust_req=?,purchase_period=?,cust_doc=?,priceestimation=?,quotationpreparation=?,emailsent=?,date=?,prod_name=?,salesdone=?,type=?, ref_number=?, prod_id=? where ID=?");
			
			preparedStatement.setInt(1, enquiryViewVO.getCustomerId());
			preparedStatement.setString(2, enquiryViewVO.getReferedBy());
			preparedStatement.setString(3, enquiryViewVO.getCustomerRequirement());
			preparedStatement.setString(4, enquiryViewVO.getPurchasePeriod());
			preparedStatement.setString(5, enquiryViewVO.getCustomerFile());
			preparedStatement.setString(6, enquiryViewVO.getPriceEstimation());
			preparedStatement.setString(7, enquiryViewVO.getQuotationPreparation());
			preparedStatement.setString(8, enquiryViewVO.getEmailSent());
			preparedStatement.setString(9, enquiryViewVO.getDateOfEnquiry());
			preparedStatement.setString(10, enquiryViewVO.getProductName());
			preparedStatement.setString(11, enquiryViewVO.getSales());
			preparedStatement.setString(12, enquiryViewVO.getEnquiryType());
			preparedStatement.setString(13, enquiryViewVO.getReferenceNo());
			preparedStatement.setInt(14, enquiryViewVO.getProductId());
			preparedStatement.setInt(15, enquiryViewVO.getId());
			
			preparedStatement.execute();
			
			customersDAO.updateCustomer(customersVO);
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : updateEnquiry");
	}
	public String getDefault(String string )
	{
		String defaultPath = "";
		LOG.info("Enter : getDefault");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT value from STATIC_UTIL where `key`=?");
			preparedStatement.setString(1, string);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			defaultPath = resultSet.getString(1);
			LOG.info("Exit : getDefault");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return defaultPath;
	}
	
	public void saveBranchCode(String code, String date) throws Exception
	{
		LOG.info("Enter : saveBranchCode");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE STATIC_UTIL SET value=?, LAST_UPDATED=? where `key`=?");
			preparedStatement.setString(1, code);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, "branch_code");
			preparedStatement.execute();
			
			LOG.info("Exit : saveBranchCode");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	public void saveDefaultCode(String code, String date) throws Exception
	{
		LOG.info("Enter : saveDefaultCode");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE STATIC_UTIL SET value=?, LAST_UPDATED=? where `key`=?");
			preparedStatement.setString(1, code);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, "default_code");
			preparedStatement.execute();
			
			LOG.info("Exit : saveDefaultCode");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	public void saveEmailUsername(String name, String date) throws Exception
	{
		LOG.info("Enter : saveEmailUsername");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE STATIC_UTIL SET value=?, LAST_UPDATED=? where `key`=?");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, "email_username");
			preparedStatement.execute();
			
			LOG.info("Exit : saveEmailUsername");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	public void saveEmailPassword(String pwd, String date) throws Exception
	{
		LOG.info("Enter : saveEmailPassword");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE STATIC_UTIL SET value=?, LAST_UPDATED=? where `key`=?");
			preparedStatement.setString(1, pwd);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, "email_password");
			preparedStatement.execute();
			
			LOG.info("Exit : saveEmailPassword");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
	public String estimationConfirm(int id)
	{
		String flag = "";
		LOG.info("Enter : estimationConfirm");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT priceestimation from ENQUIRY where id=?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			flag = resultSet.getString(1);
			LOG.info("Exit : estimationConfirm");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return flag;
	}
	public String quotationConfirm(int id)
	{
		String flag = "";
		LOG.info("Enter : quotationConfirm");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT quotationpreparation from ENQUIRY where id=?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			flag = resultSet.getString(1);
			LOG.info("Exit : quotationConfirm");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return flag;
	}
	public String emailConfirm(int id)
	{
		String flag = "";
		LOG.info("Enter : emailConfirm");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT emailsent from ENQUIRY where id=?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			flag = resultSet.getString(1);
			LOG.info("Exit : emailConfirm");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return flag;
	}
}
