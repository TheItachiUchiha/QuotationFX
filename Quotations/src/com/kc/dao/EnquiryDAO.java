package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.model.ComponentsVO;
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
	
	public EnquiryDAO()
	{
		customersDAO = new CustomersDAO();
		map= new HashMap<>();
	}
	
	public void saveEnquiry(EnquiryVO enquiryVO) throws Exception
	{
		LOG.info("Enter : saveEnquiry");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO enquiry(cust_id,referedby,cust_req,purchase_period,cust_doc,priceestimation,quotationpreparation,emailsent,date,prod_name,salesdone,type, ref_number, prod_id, margin, pe_date,qp_date,mail_sent_date,sales_date,reminder_sent,total_revenue,customer_type,complaint_count) VALUES(?,?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			preparedStatement.setDouble(15, 0);
			preparedStatement.setString(16, CommonConstants.NA);
			preparedStatement.setString(17, CommonConstants.NA);
			preparedStatement.setString(18, CommonConstants.NA);
			preparedStatement.setString(19, CommonConstants.NA);
			preparedStatement.setInt(20, 0);
			preparedStatement.setDouble(21, 0);
			preparedStatement.setString(22, CommonConstants.NA);
			preparedStatement.setInt(23, 0);
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
				enquiryVO.setMargin(resultSet.getDouble(16));
				enquiryVO.setPeDate(resultSet.getString(17));
				enquiryVO.setQpDate(resultSet.getString(18));
				enquiryVO.setMailSentDate(resultSet.getString(19));
				enquiryVO.setSalesDate(resultSet.getString(20));
				enquiryVO.setReminderSent(resultSet.getInt(21));
				enquiryVO.setTotalRevenue(resultSet.getDouble(22));
				enquiryVO.setEnquiryCustomerType(resultSet.getString(23));
				enquiryVO.setComplaintCount(resultSet.getInt(24));
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
	
	
	public Map<String, String> getEnquiryOptionDefaultValues()
	{
		LOG.info("Enter : getEnquiryOptionDefaultValues");
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT `KEY`, VALUE FROM STATIC_UTIL");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				if(resultSet.getString(1).equals(CommonConstants.KEY_ENQUIRY_PATH))
				{
					map.put(CommonConstants.KEY_ENQUIRY_PATH,resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_ENQUIRY_BRANCH_CODE))
				{
					map.put(CommonConstants.KEY_ENQUIRY_BRANCH_CODE, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_ENQUIRY_DEFAULT_CODE))
				{
					map.put(CommonConstants.KEY_ENQUIRY_DEFAULT_CODE, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_ENQUIRY_EMAIL_USERNAME))
				{
					map.put(CommonConstants.KEY_ENQUIRY_EMAIL_USERNAME, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_ENQUIRY_EMAIL_PASSWORD))
				{
					map.put(CommonConstants.KEY_ENQUIRY_EMAIL_PASSWORD, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_ENQUIRY_MESSAGE))
				{
					map.put(CommonConstants.KEY_ENQUIRY_MESSAGE, resultSet.getString(2));
				}
			}
			LOG.info("Exit : getEnquiryOptionDefaultValues");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return map;
	}
	
	public ObservableList<ComponentsVO> getComponentsForEnquiry(int enquiryId) throws SQLException {
		LOG.info("Enter : getComponents");
		ObservableList<ComponentsVO> listOfComponents = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT c.id, `name`, category, subcategory, vendor, model, `type`, size, costprice, enduserprice, dealerprice, quantity  FROM COMPONENTS c inner join enquiry_component pc on (c.id = pc.component_id) where pc.ENQUIRY_ID=?");
			preparedStatement.setInt(1, enquiryId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ComponentsVO componentsVO = new ComponentsVO();
				componentsVO.setId(resultSet.getInt(1));
				componentsVO.setComponentName(resultSet.getString(2));
				componentsVO.setComponentCategory(resultSet.getString(3));
				componentsVO.setSubCategory(resultSet.getString(4));
				componentsVO.setVendor(resultSet.getString(5));
				componentsVO.setModel(resultSet.getString(6));
				componentsVO.setType(resultSet.getString(7));
				componentsVO.setSize(resultSet.getString(8));
				componentsVO.setCostPrice(Double.parseDouble(resultSet.getString(9)));
				componentsVO.setEndUserPrice(Double.parseDouble(resultSet.getString(10)));
				componentsVO.setDealerPrice(Double.parseDouble(resultSet.getString(11)));
				componentsVO.setQuantity(resultSet.getInt(12));
				listOfComponents.add(componentsVO);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getComponents");
		return listOfComponents;
	}
	
}
