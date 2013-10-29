package com.kc.dao;

import java.sql.Connection;

import com.kc.model.UsersVO;
import com.kc.util.DBConnector;

public class UsersDAO {
	public void saveUser(UsersVO usersVO)
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
