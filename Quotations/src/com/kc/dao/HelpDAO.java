package com.kc.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
				/*Blob companyBlob = resultSet.getBlob(6);
				helpVO.setCompanyLogo(companyBlob.getBinaryStream(0, companyBlob.length()));
				Blob homeBlob = resultSet.getBlob(7);
				helpVO.setHomeLogo(homeBlob.getBinaryStream(0, homeBlob.length()))d;*/
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

}
