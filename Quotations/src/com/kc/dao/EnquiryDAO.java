package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.EnquiryVO;
import com.kc.util.DBConnector;

public class EnquiryDAO {
	private static final Logger LOG = LogManager.getLogger(EnquiryDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	
	public void saveEnquiry(EnquiryVO enquiryVO) throws Exception
	{
		LOG.info("Enter : saveEnquiry");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO enquiry(cust_id,referedby,cust_req,purchase_period,cust_doc,priceestimation,quotationpreparation,emailsent,date,p_id) VALUES(?,?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, enquiryVO.getCustomerId());
			preparedStatement.setString(2, enquiryVO.getReferedBy());
			preparedStatement.setString(3, enquiryVO.getCustomerrequirements());
			preparedStatement.setString(4, enquiryVO.getPurchasePeriod());
			preparedStatement.setString(5, enquiryVO.getCustomerDocument());
			preparedStatement.setString(6, enquiryVO.getPriceEstimation());
			preparedStatement.setString(7, enquiryVO.getQuotationPreparation());
			preparedStatement.setString(8, enquiryVO.getEmailSent());
			preparedStatement.setString(9, enquiryVO.getDate());
			preparedStatement.setInt(10, enquiryVO.getProductId());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : saveEnquiry");
	}
}
