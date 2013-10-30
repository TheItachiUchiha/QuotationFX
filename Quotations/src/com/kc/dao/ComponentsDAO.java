package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	
	public void updateComponent(ComponentsVO componentsVO)
	{
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
			preparedStatement.setDouble(8, componentsVO.getCostPrice());
			preparedStatement.setDouble(9, componentsVO.getDealerPrice());
			preparedStatement.setDouble(10, componentsVO.getEndUserPrice());
			preparedStatement.setInt(11, componentsVO.getId());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ObservableList<ComponentsVO> getComponents() throws SQLException
	{		
		ObservableList<ComponentsVO> listOfComponents = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM COMPONENTS");
			
			while(resultSet.next())
			{
				ComponentsVO componentsVO = new ComponentsVO();
				componentsVO.setId(resultSet.getInt(1));
				componentsVO.setComponentName(resultSet.getString(2));
				componentsVO.setComponentCategory(resultSet.getString(3));
				componentsVO.setSubCategory(resultSet.getString(4));
				componentsVO.setVendor(resultSet.getString(5));
				componentsVO.setModel(resultSet.getString(6));
				componentsVO.setType(resultSet.getString(7));
				componentsVO.setSize(resultSet.getString(8));
				componentsVO.setCostPrice(resultSet.getInt(9));
				componentsVO.setEndUserPrice(resultSet.getInt(10));
				componentsVO.setDealerPrice(resultSet.getInt(11));
				listOfComponents.add(componentsVO);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			if(conn !=null)
			{
				conn.close();
			}
		}
		return listOfComponents;
	}
}
