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

}
