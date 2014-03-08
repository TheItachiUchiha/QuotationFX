package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.SQLConstants;
import com.kc.model.ComponentsVO;
import com.kc.model.ProductsVO;
import com.kc.util.DBConnector;

public class ProductsDAO {
	private static final Logger LOG = LogManager.getLogger(ProductsDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	public int saveProducts(ProductsVO productsVO) throws Exception
	{
		LOG.info("Enter : saveProducts");
		int id=0;
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement(SQLConstants.SAVE_PRODUCT /* ,Statement.RETURN_GENERATED_KEYS*/);
	
			preparedStatement.setString(1, productsVO.getProductName());
			preparedStatement.setString(2, productsVO.getProductCategory());
			preparedStatement.setString(3, productsVO.getProductSubCategory());
			preparedStatement.setString(4, productsVO.getProductCode());
			preparedStatement.setString(5, productsVO.getPathSet());
			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();
			while(resultSet.next())
			{
				id=resultSet.getInt(1);
			}
			
			statement = conn.createStatement();
			for(ComponentsVO component : productsVO.getList())
			{
				statement.addBatch(SQLConstants.SAVE_PRODUCT_SUB+id+","+component.getId()+","+component.getQuantity()+")");
			}
			statement.executeBatch();
			
		}catch (Exception e) {
			//e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
			
		}
		LOG.info("Exit : saveProducts");
		return id;
	}
	
	public void modifyProducts(ProductsVO productsVO) throws Exception
	{
		LOG.info("Enter : modifyProduct");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement(SQLConstants.UPDATE_PRODUCT);
			preparedStatement.setInt(1, productsVO.getId());
			preparedStatement.execute();
			
			preparedStatement = conn
					.prepareStatement(SQLConstants.UPDATE_PRODUCT_SUB);
			preparedStatement.setString(1, productsVO.getProductName());
			preparedStatement.setString(2, productsVO.getProductCategory());
			preparedStatement.setString(3, productsVO.getProductSubCategory());
			preparedStatement.setString(4, productsVO.getProductCode());
			preparedStatement.setInt(5, productsVO.getId());
			preparedStatement.execute();
			
			statement = conn.createStatement();
			for(ComponentsVO component : productsVO.getList())
			{
				statement.addBatch(SQLConstants.UPDATE_PRODUCT_SUB2+productsVO.getId()+","+component.getId()+","+component.getQuantity()+")");
			}
			statement.executeBatch();
			
	}catch (Exception e) {
		//e.printStackTrace();
		LOG.error(e.getMessage());
		throw e;
		
	}
	LOG.info("Exit : modifyProduct");
	}
	
	
	
	public ObservableList<ProductsVO> getProducts() throws SQLException {
		LOG.info("Enter : getProducts");
		ObservableList<ProductsVO> listOfProducts = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQLConstants.GET_PRODUCTS);

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
			preparedStatement=conn.prepareStatement(SQLConstants.DELETE_PRODUCT);
			preparedStatement.setInt(1, productsVO.getId());
			preparedStatement.execute();
			preparedStatement = conn.prepareStatement(SQLConstants.DELETE_PRODUCT_SUB);
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
			preparedStatement = conn.prepareStatement(SQLConstants.GET_COMPONENTS_FOR_PRODUCT);
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
				componentsVO.setCostPrice(Double.parseDouble(resultSet.getString(9)));
				componentsVO.setEndUserPrice(Double.parseDouble(resultSet.getString(10)));
				componentsVO.setDealerPrice(Double.parseDouble(resultSet.getString(11)));
				componentsVO.setQuantity(resultSet.getInt(12));
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
	
	public ObservableList<Map<String, Integer>> getCategoriesForProduct() throws SQLException {
		LOG.info("Enter : getComponents");
		ObservableList<Map<String, Integer>> listOfCategories = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_CATEGORY_FOR_PRODUCT);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(resultSet.getString(2),resultSet.getInt(1));
				listOfCategories.add(map);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getComponents");
		return listOfCategories;
	}
	
	public ObservableList<Map<String, Integer>> getSubCategoriesForProduct() throws SQLException {
		LOG.info("Enter : getSubCategoriesForProduct");
		ObservableList<Map<String, Integer>> listOfCategories = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_SUBCATEGORY_FOR_PRODUCT);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(resultSet.getString(2),resultSet.getInt(1));
				listOfCategories.add(map);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getSubCategoriesForProduct");
		return listOfCategories;
	}
	
	public ObservableList<Map<String, Integer>> getCustomerTypeForProduct() throws SQLException {
		LOG.info("Enter : getSubCategoriesForProduct");
		ObservableList<Map<String, Integer>> listOfCustomerType = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_CUSTOMET_TYPE_FOR_PRODUCT);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(resultSet.getString(2),resultSet.getInt(1));
				listOfCustomerType.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getSubCategoriesForProduct");
		return listOfCustomerType;
	}
	public ObservableList<Map<String, Integer>> getCustomerStateForProduct() throws SQLException {
		LOG.info("Enter : getCustomerStateForProduct");
		ObservableList<Map<String, Integer>> listOfCustomerState = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_CUSTOMET_STATE_FOR_PRODUCT);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(resultSet.getString(2),resultSet.getInt(1));
				listOfCustomerState.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getCustomerStateForProduct");
		return listOfCustomerState;
	}
	
	public ObservableList<String> getProductCategoryList() throws SQLException {
		LOG.info("Enter : getProductCategoryList");
		ObservableList<String> listOfCategory = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_PRODUCT_CATEGORY_LIST);
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
		LOG.info("Exit : getProductCategoryList");
		return listOfCategory;
	}
	
	public ObservableList<String> getProductSubcategoryList() throws SQLException {
		LOG.info("Enter : getProductSubcategoryList");
		ObservableList<String> listOfCategory = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_PRODUCT_SUBCATEGORY_LIST);
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
		LOG.info("Exit : getProductSubcategoryList");
		return listOfCategory;
	}
	
	public ObservableList<String> getProductNameList() throws SQLException {
		LOG.info("Enter : getProductNameList");
		ObservableList<String> listOfCategory = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_PRODUCT_NAME_LIST);
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
		LOG.info("Exit : getProductNameList");
		return listOfCategory;
	}

	public ObservableList<String> getProductCodeList() throws SQLException {
		LOG.info("Enter : getProductCodeList");
		ObservableList<String> listOfCategory = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement(SQLConstants.GET_PRODUCT_CODE_LIST);
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
		LOG.info("Exit : getProductCodeList");
		return listOfCategory;
	}
}
