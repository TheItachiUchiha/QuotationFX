package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.kc.model.ComponentsVO;
import com.kc.model.EnquiryVO;
import com.kc.util.DBConnector;

public class PriceEstimationDAO {
	private Connection conn = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement=null;
	public void savePriceEstimation(EnquiryVO enquiryVO)
	{
		try
		{
			conn = DBConnector.getConnection();
			for(ComponentsVO component : enquiryVO.getList())
			{
				preparedStatement = conn.prepareStatement("INSERT into enquiry_component(enquiry_id, component_id, quantity) values("+enquiryVO.getId()+","+component.getId()+","+component.getQuantity()+")");
				preparedStatement.execute();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}

