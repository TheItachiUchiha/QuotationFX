package com.kc.dao;

import java.sql.Connection;

import com.kc.model.ProductsVO;
import com.kc.util.DBConnector;

public class ProductsDAO {
	public void saveProduct(ProductsVO productsVO)
	{
		Connection conn = null;
		try
		{
			conn = DBConnector.getConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

}
