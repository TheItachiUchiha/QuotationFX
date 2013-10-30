package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.kc.model.ComponentsVO;
import com.kc.util.DBConnector;


public class ComponentsDAO {
	
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	

	public void saveComponent(ComponentsVO componentsVO)
	{
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO components(name,category,subcategory,vendor,model,type,size,costprice,dealerprice,enduserprice) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			preparedStatement.setString(1, componentsVO.getComponentName());
			preparedStatement.setString(2, componentsVO.getComponentCategory());
			preparedStatement.setString(3, componentsVO.getSubCategory());
			preparedStatement.setString(4, componentsVO.getVendor());
			preparedStatement.setString(5, componentsVO.getModel());
			preparedStatement.setString(6, componentsVO.getType());
			preparedStatement.setString(7, componentsVO.getSize());
			preparedStatement.setDouble(8, componentsVO.getCostPrice());
			preparedStatement.setDouble(9, componentsVO.getDealerPrice());
			preparedStatement.setDouble(10, componentsVO.getEndUserPrice());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public List<ComponentsVO> getComponents()
	{		
		ObservableList<ComponentsVO> listOfComponents = FXCollections.observableArrayList();
		try{
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM COMPONENTS");
			
			while(resultSet.next())
			{
				ComponentsVO componentsVO = new ComponentsVO();
				componentsVO.setId(resultSet.getInt(1));
				componentsVO.setComponentName(resultSet.getString(2));
				componentsVO.setComponentCategory(resultSet.getString(3));
				componentsVO.setSubCategory(resultSet.getString(3));
				componentsVO.setVendor(resultSet.getString(2));
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return listOfComponents;
	}
}
