package com.kc.dao;

import java.sql.Connection;

import com.kc.model.CustomersVO;
import com.kc.util.DBConnector;

public class CustomersDAO {
	public void saveCustomer(CustomersVO customersVO)
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
