package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.ComponentsVO;
import com.kc.model.ProductsVO;
import com.kc.util.DBConnector;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductsDAO {
	private static final Logger LOG = LogManager.getLogger(ProductsDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	public void saveProducts(ProductsVO productsVO) throws Exception
	{
		LOG.info("Enter : saveProducts");
		try
		{
			int id=0;
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("INSERT INTO products(name,category,subcategory,code) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
	
			preparedStatement.setString(1, productsVO.getProductName());
			preparedStatement.setString(2, productsVO.getProductCategory());
			preparedStatement.setString(3, productsVO.getProductSubCategory());
			preparedStatement.setString(4, productsVO.getProductCode());
			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();
			while(resultSet.next())
			{
				id=resultSet.getInt(1);
			}
			
			statement = conn.createStatement();
			for(ComponentsVO component : productsVO.getList())
			{
				statement.addBatch("INSERT into product_component(product_id, component_id) values("+id+","+component.getId()+")");
			}
			statement.executeBatch();
			
	}catch (Exception e) {
		//e.printStackTrace();
		LOG.error(e.getMessage());
		throw e;
		
	}
	LOG.info("Exit : saveProducts");
	}
	
	public void modifyProducts(ProductsVO productsVO) throws Exception
	{
		LOG.info("Enter : saveProducts");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("DELETE FROM PRODUCT_COMPONENT WHERE PRODUCT_ID=?");
			preparedStatement.setInt(1, productsVO.getId());
			preparedStatement.execute();
			
			preparedStatement = conn
					.prepareStatement("UPDATE PRODUCTS SET NAME=?, CATEGORY=?, SUBCATEGORY=?, CODE=? WHERE ID=?");
			preparedStatement.setString(1, productsVO.getProductName());
			preparedStatement.setString(2, productsVO.getProductCategory());
			preparedStatement.setString(3, productsVO.getProductSubCategory());
			preparedStatement.setString(4, productsVO.getProductCode());
			preparedStatement.setInt(5, productsVO.getId());
			preparedStatement.execute();
			
			statement = conn.createStatement();
			for(ComponentsVO component : productsVO.getList())
			{
				statement.addBatch("INSERT into product_component(product_id, component_id) values("+productsVO.getId()+","+component.getId()+")");
			}
			statement.executeBatch();
			
	}catch (Exception e) {
		//e.printStackTrace();
		LOG.error(e.getMessage());
		throw e;
		
	}
	LOG.info("Exit : saveProducts");
	}
	
	
	
	public ObservableList<ProductsVO> getProducts() throws SQLException {
		LOG.info("Enter : getProducts");
		ObservableList<ProductsVO> listOfProducts = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM PRODUCTS");

			while (resultSet.next()) {
				ProductsVO productsVO = new ProductsVO();
				productsVO.setId(resultSet.getInt(1));
				productsVO.setProductName(resultSet.getString(2));
				productsVO.setProductCategory(resultSet.getString(3));
				productsVO.setProductSubCategory(resultSet.getString(4));
				productsVO.setProductCode(resultSet.getString(5));
				listOfProducts.add(productsVO);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getProducts");
		return listOfProducts;
	}
	public void deleteProducts(ProductsVO productsVO) throws Exception {
		LOG.info("Enter : deleteProducts");
		try {
			conn = DBConnector.getConnection();
			preparedStatement=conn.prepareStatement("DELETE FROM product_component WHERE PRODUCT_ID=?");
			preparedStatement.setInt(1, productsVO.getId());
			preparedStatement.execute();
			preparedStatement = conn.prepareStatement("DELETE FROM PRODUCTS WHERE ID=?");
			preparedStatement.setInt(1, productsVO.getId());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : deleteProducts");
	}
	
	public ObservableList<ComponentsVO> getComponentsForProduct(int productId) throws SQLException {
		LOG.info("Enter : getComponents");
		ObservableList<ComponentsVO> listOfComponents = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT * FROM COMPONENTS where id in (select component_id from product_component WHERE PRODUCT_ID=?)");
			preparedStatement.setInt(1, productId);
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
				componentsVO.setCostPrice(resultSet.getDouble(9));
				componentsVO.setEndUserPrice(resultSet.getDouble(10));
				componentsVO.setDealerPrice(resultSet.getDouble(11));
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
