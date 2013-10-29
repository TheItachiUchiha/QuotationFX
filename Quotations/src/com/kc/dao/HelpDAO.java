package com.kc.dao;

import java.sql.Connection;

import com.kc.model.HelpVO;
import com.kc.util.DBConnector;

public class HelpDAO {
	public void saveCompanyDetails(HelpVO helpVO)
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
