package com.kc.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
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

import javax.imageio.ImageIO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.model.ComponentsVO;
import com.kc.model.CustomersVO;
import com.kc.model.EmployeeVO;
import com.kc.model.HelpVO;
import com.kc.util.DBConnector;

public class HelpDAO {
	private static final Logger LOG = LogManager.getLogger(CustomersDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void updateCompanyDetails(HelpVO helpVO)
	{

		LOG.info("Enter : updateCompanyDetails");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE company_details SET name=?,description=?,address=?,contact_details=?,company_logo=?,home_logo=? where ID=?");
			
			FileInputStream fis = null;
			FileInputStream fis2 = null;
			
		    fis = new FileInputStream(helpVO.getCompanyLogo());
		    fis2 = new FileInputStream(helpVO.getHomeLogo());
		    
			preparedStatement.setString(1, helpVO.getName());
			preparedStatement.setString(2, helpVO.getDescription());
			preparedStatement.setString(3, helpVO.getAddress());
			preparedStatement.setString(4, helpVO.getContact());
			preparedStatement.setBinaryStream(5, fis, (int) helpVO.getCompanyLogo().length());
			preparedStatement.setBinaryStream(6, fis2, (int) helpVO.getHomeLogo().length());
			preparedStatement.setInt(7, 1);
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : updateCompanyDetails");
	
	}
	public HelpVO getCompanyDetails()
	{
		LOG.info("Enter : getCompanyDetails");
		HelpVO helpVO = new HelpVO();
		try {
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM company_details");

			while (resultSet.next()) {
				helpVO.setId(resultSet.getInt(1));
				helpVO.setName(resultSet.getString(2));
				helpVO.setDescription(resultSet.getString(3));
				helpVO.setAddress(resultSet.getString(4));
				helpVO.setContact(resultSet.getString(5));
				
				Blob companyBlob = resultSet.getBlob(6);
				int blobLength = (int) companyBlob.length();
				byte[] blobAsBytes = companyBlob.getBytes(1, blobLength);
				BufferedImage image = ImageIO.read( new ByteArrayInputStream( blobAsBytes ) );
				ImageIO.write(image, "png", new File("E:\\companylogo.png"));
				helpVO.setCompanyLogo(new File("E:\\companylogo.png"));
				
				Blob homeBlob = resultSet.getBlob(7);
				int blobLengthHome = (int) homeBlob.length();
				byte[] blobAsBytesHome = homeBlob.getBytes(1, blobLengthHome);
				BufferedImage imageHome = ImageIO.read( new ByteArrayInputStream( blobAsBytesHome ) );
				ImageIO.write(imageHome, "png", new File("E:\\homelogo.png"));
				helpVO.setHomeLogo(new File("E:\\homelogo.png"));
			}
		} 
		catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		LOG.info("Exit : getCompanyDetails");
		return helpVO;
	}
	public void saveEmployee(EmployeeVO employeeVO) throws Exception
	{
		LOG.info("Enter : saveEmployee");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO employee(name,designation,mobileno,address,rating) VALUES(?, ?, ?, ?, ?)");
			preparedStatement.setString(1, employeeVO.getName());
			preparedStatement.setString(2, employeeVO.getDesignation());
			preparedStatement.setString(3, employeeVO.getMobileNo());
			preparedStatement.setString(4, employeeVO.getAddress());
			preparedStatement.setString(5, employeeVO.getServiceRating());
						
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : saveEmployee");
	}
	public ObservableList<EmployeeVO> getEmployees() throws SQLException
	{
		LOG.info("Enter : getEMployees");
		ObservableList<EmployeeVO> listOfCustomers = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM employee");
			
			while(resultSet.next())
			{
				EmployeeVO employeeVO = new EmployeeVO();
				employeeVO.setId(resultSet.getInt(1));
				employeeVO.setName(resultSet.getString(2));
				employeeVO.setDesignation(resultSet.getString(3));
				employeeVO.setMobileNo(resultSet.getString(4));
				employeeVO.setAddress(resultSet.getString(5));
				employeeVO.setServiceRating(resultSet.getString(6));
						
				listOfCustomers.add(employeeVO);
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
		LOG.info("Exit : getEMployees");
		return listOfCustomers;
	}
	public void updateEmployee(EmployeeVO employeeVO)
	{
		LOG.info("Enter : updateEmployee");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE employee SET name=?,designation=?,mobileno=?,address=?,rating=? where ID=?");
			
			preparedStatement.setString(1, employeeVO.getName());
			preparedStatement.setString(2, employeeVO.getDesignation());
			preparedStatement.setString(3, employeeVO.getMobileNo());
			preparedStatement.setString(4, employeeVO.getAddress());
			preparedStatement.setString(5, employeeVO.getServiceRating());
			preparedStatement.setInt(6, employeeVO.getId());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : updateEmployee");
	}
	public void deleteEmployees(EmployeeVO employeeVO) throws Exception {
		LOG.info("Enter : deleteEmployees");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("DELETE FROM employee WHERE ID=?");
			preparedStatement.setInt(1, employeeVO.getId());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : deleteEmployees");
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
	
	
	public Map<String, String> getBackground()
	{
		LOG.info("Enter : getBackground");
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT `KEY`, VALUE FROM STATIC_UTIL");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				if(resultSet.getString(1).equals(CommonConstants.KEY_BACKGROUND))
				{
					map.put(CommonConstants.KEY_BACKGROUND,resultSet.getString(2));
					break;
				}
			}
			LOG.info("Exit : getBackground");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return map;
	}
	
}
