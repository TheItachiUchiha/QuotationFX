package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.kc.controller.LoginController;
import com.kc.util.DBConnector;

public class LoginDAO 
{
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public boolean verifyUser(String username, String password) throws SQLException
	{
		boolean status =false;
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT * FROM USERS WHERE USERNAME=? and PASSWORD=?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next())
			{
				LoginController.modulesVO.setPriceEstimation(resultSet.getString("priceestimation"));
				LoginController.modulesVO.setQuotation(resultSet.getString("quotation"));
				LoginController.modulesVO.setReport(resultSet.getString("report"));
				LoginController.modulesVO.setSalesOrderManagement(resultSet.getString("salesorder"));
				LoginController.modulesVO.setStatusReminder(resultSet.getString("statusReminder"));
				LoginController.modulesVO.setEnquiry(resultSet.getString("enquiry"));
				LoginController.modulesVO.setProductDispatch(resultSet.getString("product_dispatch"));
				LoginController.modulesVO.setService(resultSet.getString("service_registry"));
				LoginController.userType = resultSet.getString("usertype");
				LoginController.currentUser = resultSet.getString("name");
				status = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(conn!=null)
			{
				conn.close();
			}
		}
		return status;
	}
}
