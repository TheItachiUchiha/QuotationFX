package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.kc.model.UsersVO;
import com.kc.util.DBConnector;

public class UsersDAO {
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	

	public void saveUser(UsersVO usersVO) throws Exception
	{
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO users(name,designation,mobilenumber,username,password,quotation,priceestimation,report,salesorder,statusreminder,view,`edit`,`delete`,usertype) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");			
			preparedStatement.setString(1, usersVO.getName());
			preparedStatement.setString(2, usersVO.getDesignation());
			preparedStatement.setString(3, usersVO.getMobileNumber());
			preparedStatement.setString(4, usersVO.getUsername());
			preparedStatement.setString(5, usersVO.getPassword());
			preparedStatement.setString(6, usersVO.getQuotation());
			preparedStatement.setString(7, usersVO.getPriceEstimation());
			preparedStatement.setString(8, usersVO.getReport());
			preparedStatement.setString(9, usersVO.getSalesOrderManagement());
			preparedStatement.setString(10, usersVO.getStatusReminder());
			preparedStatement.setString(11, usersVO.getView());
			preparedStatement.setString(12, usersVO.getEdit());
			preparedStatement.setString(13, usersVO.getDelete());
			preparedStatement.setString(14, usersVO.getUserType());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public void updateUser(UsersVO usersVO)
	{
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE users SET name=?,designation=?,mobilenumber=?,username=?,password=?,quotation=?,priceestimation=?,report=?,salesorder=?,statusreminder=?,view=?,`edit`=?,`delete`=?,usertype=? where ID=?");
			
			preparedStatement.setString(1, usersVO.getName());
			preparedStatement.setString(2, usersVO.getDesignation());
			preparedStatement.setString(3, usersVO.getMobileNumber());
			preparedStatement.setString(4, usersVO.getUsername());
			preparedStatement.setString(5, usersVO.getPassword());
			preparedStatement.setString(6, usersVO.getQuotation());
			preparedStatement.setString(7, usersVO.getPriceEstimation());
			preparedStatement.setString(8, usersVO.getReport());
			preparedStatement.setString(9, usersVO.getSalesOrderManagement());
			preparedStatement.setString(10, usersVO.getStatusReminder());
			preparedStatement.setString(11, usersVO.getView());
			preparedStatement.setString(12, usersVO.getEdit());
			preparedStatement.setString(13, usersVO.getDelete());
			preparedStatement.setString(14, usersVO.getUserType());
			preparedStatement.setInt(15, usersVO.getId());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public ObservableList<UsersVO> getUsers() throws SQLException
	{		
		ObservableList<UsersVO> listOfUsers = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM USERS");
			
			while(resultSet.next())
			{
				UsersVO usersVO = new UsersVO();
				usersVO.setId(resultSet.getInt(1));
				usersVO.setName(resultSet.getString(2));
				usersVO.setDesignation(resultSet.getString(3));
				usersVO.setMobileNumber(resultSet.getString(4));
				usersVO.setUsername(resultSet.getString(5));
				usersVO.setPassword(resultSet.getString(6));
				usersVO.setQuotation(resultSet.getString(7));
				usersVO.setPriceEstimation(resultSet.getString(8));
				usersVO.setReport(resultSet.getString(9));
				usersVO.setSalesOrderManagement(resultSet.getString(10));
				usersVO.setStatusReminder(resultSet.getString(11));
				usersVO.setView(resultSet.getString(12));
				usersVO.setEdit(resultSet.getString(13));
				usersVO.setDelete(resultSet.getString(14));
				usersVO.setUserType(resultSet.getString(15));
				
				listOfUsers.add(usersVO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(conn !=null)
			{
				conn.close();
			}
		}
		return listOfUsers;
	}
	public ObservableList<UsersVO> getModules() throws SQLException
	{		
		ObservableList<UsersVO> listOfModules = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM USERS");
			
			while(resultSet.next())
			{
				String modules="";
				UsersVO usersVO = new UsersVO();
				usersVO.setId(resultSet.getInt(1));
				usersVO.setName(resultSet.getString(2));
				usersVO.setDesignation(resultSet.getString(3));
				usersVO.setMobileNumber(resultSet.getString(4));
				usersVO.setUsername(resultSet.getString(5));
				usersVO.setPassword(resultSet.getString(6));
				if("Y".equalsIgnoreCase(resultSet.getString(7)))
				{
					modules = modules.concat("Quatation\n");
				}
				if("Y".equalsIgnoreCase(resultSet.getString(8)))
				{
					modules = modules.concat("PriceEstimation\n");
				}
				if("Y".equalsIgnoreCase(resultSet.getString(9)))
				{
					modules = modules.concat("Report\n");
				}
				if("Y".equalsIgnoreCase(resultSet.getString(10)))
				{
					modules = modules.concat("SalesOrderManagement\n");
				}
				if("Y".equalsIgnoreCase(resultSet.getString(11)))
				{
					modules = modules.concat("Status / Reminder\n");
				}
				usersVO.setQuotation(modules);
				usersVO.setView(resultSet.getString(12));
				usersVO.setEdit(resultSet.getString(13));
				usersVO.setDelete(resultSet.getString(14));
				usersVO.setUserType(resultSet.getString(15));
				
				listOfModules.add(usersVO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(conn !=null)
			{
				conn.close();
			}
		}
		return listOfModules;
	}
	public void deleteUsers(List<UsersVO> listUsers) throws Exception {
		try {
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			for (UsersVO usersVO : listUsers) {
				statement.addBatch("DELETE FROM USERS WHERE ID="
						+ usersVO.getId());
			}
			statement.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
