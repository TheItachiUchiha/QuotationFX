package com.kc.dao;

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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.model.ProductsVO;
import com.kc.model.QuotationVO;
import com.kc.util.DBConnector;

public class QuotationDAO {
	private static final Logger LOG = LogManager.getLogger(QuotationDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	
	public ObservableList<ProductsVO> getQuotationProducts() throws SQLException
	{
		ObservableList<ProductsVO> listOfProducts = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM PRODUCTS where path_set='N'");
			
			while (resultSet.next()) {
				ProductsVO productsVO = new ProductsVO();
				productsVO.setId(resultSet.getInt(1));
				productsVO.setProductName(resultSet.getString(2));
				productsVO.setProductCategory(resultSet.getString(3));
				productsVO.setProductSubCategory(resultSet.getString(4));
				productsVO.setProductCode(resultSet.getString(5));
				productsVO.setPathSet(resultSet.getString(6));
				listOfProducts.addAll(productsVO);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return listOfProducts;
		
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
	public Map<String, String> getCustomDefaultValues()
	{
		LOG.info("Enter : getCustomDefaultValues");
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT `KEY`, VALUE FROM STATIC_UTIL");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				if(resultSet.getString(1).equals(CommonConstants.KEY_QUOTATION_PATH))
				{
					map.put(CommonConstants.KEY_QUOTATION_PATH,resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_QUOTATION_WORD_PATH))
				{
					map.put(CommonConstants.KEY_QUOTATION_WORD_PATH, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_QUOTATION_PDF_PATH))
				{
					map.put(CommonConstants.KEY_QUOTATION_PDF_PATH, resultSet.getString(2));
				}
			}
			LOG.info("Exit : getCustomDefaultValues");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return map;
	}
	public void saveStandardProductPath(QuotationVO quotationVO)
	{
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO quotation(product_id,default_file,word_file_path,pdf_file_path) VALUES(?, ?, ?, ?)");
			preparedStatement.setInt(1, quotationVO.getProductId());
			preparedStatement.setString(2, quotationVO.getQuotationPath());
			preparedStatement.setString(3, quotationVO.getWordPath());
			preparedStatement.setString(4, quotationVO.getPdfPath());
			preparedStatement.execute();
			
			preparedStatement = conn.prepareStatement("UPDATE PRODUCTS SET path_set=? WHERE ID=?");
			preparedStatement.setString(1, "Y");
			preparedStatement.setInt(2, quotationVO.getProductId());
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void UpdateEnquiry(int id, String status, String date )
	{
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE ENQUIRY SET quotationpreparation=?, QP_DATE=? where ID=?");
			
			preparedStatement.setString(1, status);
			preparedStatement.setString(2, date);
			preparedStatement.setInt(3, id);
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Map<String, String> getStandardProductPath(int id)
	{
		LOG.info("Enter : getStandardProductPath");
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT * FROM quotation where product_id=?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				map.put(CommonConstants.KEY_QUOTATION_PATH, resultSet.getString(3));
				map.put(CommonConstants.KEY_QUOTATION_WORD_PATH,resultSet.getString(4));
				map.put(CommonConstants.KEY_QUOTATION_PDF_PATH,resultSet.getString(5));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return map;
	}
	
	public String isProductPathSet(int productId)
	{
		String pathSet ="N";
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT PATH_SET FROM PRODUCTS where id=?");
			preparedStatement.setInt(1, productId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				pathSet = resultSet.getString(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return pathSet;
	}
}
