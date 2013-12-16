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

import com.kc.model.ComponentsVO;
import com.kc.model.EnquiryVO;
import com.kc.util.DBConnector;

public class ReportsDAO 
{
	private static final Logger LOG = LogManager.getLogger(ReportsDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	
	public ObservableList<EnquiryVO> salesReport(String startDate, String endDate) throws Exception {
		LOG.info("Enter : salesReportCategory");
		ObservableList<EnquiryVO> listOfEnquiries = FXCollections.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("select ref_number,salesdone,prod_id,cust_id from quotation.enquiry e where STR_TO_DATE(e.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and "+
							"STR_TO_DATE(e.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')");
			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				EnquiryVO enquiryVO = new EnquiryVO();
				enquiryVO.setRefNumber(resultSet.getString(1));
				enquiryVO.setSales(resultSet.getString(2));
				enquiryVO.setProductId(resultSet.getInt(3));
				enquiryVO.setCustomerId(resultSet.getInt(4));
				listOfEnquiries.add(enquiryVO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return listOfEnquiries;
	}
	
	public int getNumberOfService(String referenceNumber) throws SQLException {
		LOG.info("Enter : getNumberOfService");
		int number=0;
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT count(*) FROM SERVICE where reference_no=?");
			preparedStatement.setString(1, referenceNumber);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				number = resultSet.getInt(1);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getNumberOfService");
		return number;
	}
}
