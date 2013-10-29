package com.kc.dao;

import java.sql.Connection;

import com.kc.model.ComponentsVO;
import com.kc.util.DBConnector;

public class ComponentsDAO {

	public void saveComponent(ComponentsVO componentsVO)
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
