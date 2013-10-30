package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.kc.model.ComponentsVO;
import com.kc.util.DBConnector;


public class ComponentsDAO {

	public void saveComponent(ComponentsVO componentsVO)
	{
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO components(name,category,subcategory,vendor,model,type,size,costprice,dealerprice,enduserprice) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			preparedStatement.setString(1, componentsVO.getComponentName());
			preparedStatement.setString(2, componentsVO.getComponentCategory());
			preparedStatement.setString(3, componentsVO.getSubCategory());
			preparedStatement.setString(4, componentsVO.getVender());
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
}
