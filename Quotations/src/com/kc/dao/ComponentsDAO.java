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
import com.kc.util.DBConnector;

public class ComponentsDAO {
	private static final Logger LOG = LogManager.getLogger(ComponentsDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public void saveComponent(ComponentsVO componentsVO) throws Exception {
		LOG.info("Enter : saveComponent");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn
					.prepareStatement("INSERT INTO components(name,category,subcategory,vendor,model,type,size,costprice,dealerprice,enduserprice) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			preparedStatement.setString(1, componentsVO.getComponentName());
			preparedStatement.setString(2, componentsVO.getComponentCategory());
			preparedStatement.setString(3, componentsVO.getSubCategory());
			preparedStatement.setString(4, componentsVO.getVendor());
			preparedStatement.setString(5, componentsVO.getModel());
			preparedStatement.setString(6, componentsVO.getType());
			preparedStatement.setString(7, componentsVO.getSize());
			preparedStatement.setString(8, String.valueOf(componentsVO.getCostPrice()));
			preparedStatement.setString(9, String.valueOf(componentsVO.getDealerPrice()));
			preparedStatement.setString(10, String.valueOf(componentsVO.getEndUserPrice()));

			preparedStatement.execute();
		}  catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
			
		}
		LOG.info("Exit : saveComponent");
	}

	public void updateComponent(ComponentsVO componentsVO) throws Exception
	{
		LOG.info("Enter : updateComponent");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE components SET name=?,category=?,subcategory=?,vendor=?,model=?,type=?,size=?,costprice=?,dealerprice=?,enduserprice=? where ID=?");
			
			preparedStatement.setString(1, componentsVO.getComponentName());
			preparedStatement.setString(2, componentsVO.getComponentCategory());
			preparedStatement.setString(3, componentsVO.getSubCategory());
			preparedStatement.setString(4, componentsVO.getVendor());
			preparedStatement.setString(5, componentsVO.getModel());
			preparedStatement.setString(6, componentsVO.getType());
			preparedStatement.setString(7, componentsVO.getSize());
			preparedStatement.setString(8, String.valueOf(componentsVO.getCostPrice()));
			preparedStatement.setString(9, String.valueOf(componentsVO.getDealerPrice()));
			preparedStatement.setString(10, String.valueOf(componentsVO.getEndUserPrice()));
			preparedStatement.setInt(11, componentsVO.getId());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			throw e;	
		}
		LOG.info("Exit : updateComponent");
	}

	public ObservableList<ComponentsVO> getComponents() throws SQLException {
		LOG.info("Enter : getComponents");
		ObservableList<ComponentsVO> listOfComponents = FXCollections
				.observableArrayList();
		try {
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM COMPONENTS");

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
				listOfComponents.add(componentsVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		LOG.info("Exit : getComponents");
		return listOfComponents;
	}

	public void deleteComponents(ComponentsVO componentsVO) throws Exception {
		LOG.info("Enter : deleteComponents");
		try {
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("DELETE FROM COMPONENTS WHERE ID=?");
			preparedStatement.setInt(1, componentsVO.getId());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("Exit : deleteComponents");
	}
}
