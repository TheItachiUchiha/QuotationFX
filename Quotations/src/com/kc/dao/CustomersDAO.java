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

import com.kc.model.CustomersVO;
import com.kc.util.DBConnector;

public class CustomersDAO {
	private static final Logger LOG = LogManager.getLogger(CustomersDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public int saveCustomer(CustomersVO customersVO) throws Exception
	{
		LOG.info("Enter : saveCustomer");
		int id=0;
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO customers(customername,companyname,address,city,state,email,contactnumber,type,tinnumber,telephone,website) VALUES(?, ?, ?,?,?, ?, ?, ?, ?, ?, ?)",  Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, customersVO.getCustomerName());
			preparedStatement.setString(2, customersVO.getCompanyName());
			preparedStatement.setString(3, customersVO.getAddress());
			preparedStatement.setString(4, customersVO.getCity());
			preparedStatement.setString(5, customersVO.getState());
			preparedStatement.setString(6, customersVO.getEmailId());
			preparedStatement.setString(7, customersVO.getContactNumber());
			preparedStatement.setString(8, customersVO.getCustomerType());
			preparedStatement.setString(9, customersVO.getTinNumber());
			preparedStatement.setString(10, customersVO.getTelephone());
			preparedStatement.setString(11, customersVO.getWebsite());
			
			preparedStatement.execute();
			
			resultSet = preparedStatement.getGeneratedKeys();
			while(resultSet.next())
			{
				id=resultSet.getInt(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : saveCustomer");
		return id;
	}
	
	
	public void updateCustomer(CustomersVO customersVO)
	{
		LOG.info("Enter : updateCustomer");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE customers SET customername=?,companyname=?,address=?,city=?,state=?,email=?,contactnumber=?,type=?,tinnumber=?,telephone=?,website=? where ID=?");
			
			preparedStatement.setString(1, customersVO.getCustomerName());
			preparedStatement.setString(2, customersVO.getCompanyName());
			preparedStatement.setString(3, customersVO.getAddress());
			preparedStatement.setString(4, customersVO.getCity());
			preparedStatement.setString(5, customersVO.getState());
			preparedStatement.setString(6, customersVO.getEmailId());
			preparedStatement.setString(7, customersVO.getContactNumber());
			preparedStatement.setString(8, customersVO.getCustomerType());
			preparedStatement.setString(9, customersVO.getTinNumber());
			preparedStatement.setString(10, customersVO.getTelephone());
			preparedStatement.setString(11, customersVO.getWebsite());
			preparedStatement.setInt(12, customersVO.getId());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : updateCustomer");
	}
	
	public ObservableList<CustomersVO> getCustomers() throws SQLException
	{
		LOG.info("Enter : getCustomers");
		ObservableList<CustomersVO> listOfCustomers = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM CUSTOMERS");
			
			while(resultSet.next())
			{
				CustomersVO customersVO = new CustomersVO();
				customersVO.setId(resultSet.getInt(1));
				customersVO.setCustomerName(resultSet.getString(2));
				customersVO.setCompanyName(resultSet.getString(3));
				customersVO.setAddress(resultSet.getString(4));
				customersVO.setCity(resultSet.getString(5));
				customersVO.setState(resultSet.getString(6));
				customersVO.setEmailId(resultSet.getString(7));
				customersVO.setContactNumber(resultSet.getString(8));
				if("D".equalsIgnoreCase(resultSet.getString(9)))
				{
					customersVO.setCustomerType("Dealer");
				}
				else
				{
					customersVO.setCustomerType("End User");
				}
				customersVO.setTinNumber(resultSet.getString(10));
				customersVO.setTelephone(resultSet.getString(11));
				customersVO.setWebsite(resultSet.getString(12));
				
				listOfCustomers.add(customersVO);
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
		LOG.info("Exit : getCustomers");
		return listOfCustomers;
	}
	public void deleteCustomers(CustomersVO customersVO) throws Exception {
		LOG.info("Enter : deleteCustomers");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("DELETE FROM CUSTOMERS WHERE ID=?");
			preparedStatement.setInt(1, customersVO.getId());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : deleteCustomers");
	}
	public ObservableList<String> getCustomerNameList() throws SQLException {
		LOG.info("Enter : getCustomerNameList");
		ObservableList<String> listOfCategory = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT distinct customername FROM quotation.customers");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				listOfCategory.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getCustomerNameList");
		return listOfCategory;
	}
	public ObservableList<String> getCustomerCompanyList() throws SQLException {
		LOG.info("Enter : getCustomerCompanyList");
		ObservableList<String> listOfCategory = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT distinct companyname FROM quotation.customers");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				listOfCategory.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getCustomerCompanyList");
		return listOfCategory;
	}
	public ObservableList<String> getCustomerCityList() throws SQLException {
		LOG.info("Enter : getCustomerCityList");
		ObservableList<String> listOfCategory = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT distinct city FROM quotation.customers");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				listOfCategory.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getCustomerCityList");
		return listOfCategory;
	}
	public ObservableList<String> getCustomerStateList() throws SQLException {
		LOG.info("Enter : getCustomerStateList");
		ObservableList<String> listOfCategory = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT distinct state FROM quotation.customers");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				listOfCategory.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getCustomerStateList");
		return listOfCategory;
	}
}
