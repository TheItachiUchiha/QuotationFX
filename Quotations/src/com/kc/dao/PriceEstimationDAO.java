package com.kc.dao;

import java.sql.Connection;

import com.kc.model.PriceEstimationVO;
import com.kc.util.DBConnector;

public class PriceEstimationDAO {
	public void savePriceEstimation(PriceEstimationVO priceEstimationVO)
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

