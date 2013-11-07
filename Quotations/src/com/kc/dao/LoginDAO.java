package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
				status = true;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
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
