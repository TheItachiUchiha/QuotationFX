package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.util.DBConnector;

public class DispatchDAO {
	
	private static final Logger LOG = LogManager.getLogger(DispatchDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private Map<String, String> map;
	
	public void saveConfiguration(Map<String, String> map, String date)
	{
		try{
			Set<String> keys = map.keySet();
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			for(String key : keys)
			{
				String query = "UPDATE STATIC_UTIL SET `value`='";
				query=query+map.get(key);
				query = query + "' ,`last_updated`='";
				query = query + date;
				query = query + "' where `key`='" + key + "'";
				statement.addBatch(query);
			}
			statement.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
	
	public Map<String, String> getDispatchOptionDefaultValues()
	{
		LOG.info("Enter : getDispatchOptionDefaultValues");
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("SELECT `KEY`, VALUE FROM STATIC_UTIL");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				if(resultSet.getString(1).equals(CommonConstants.KEY_DISPATCH_EMAIL))
				{
					map.put(CommonConstants.KEY_DISPATCH_EMAIL,resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_DISPATCH_MESSAGE))
				{
					map.put(CommonConstants.KEY_DISPATCH_MESSAGE, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_DISPATCH_PASSWORD))
				{
					map.put(CommonConstants.KEY_DISPATCH_PASSWORD, resultSet.getString(2));
				}
				else if(resultSet.getString(1).equals(CommonConstants.KEY_DISPATCH_USERNAME))
				{
					map.put(CommonConstants.KEY_DISPATCH_USERNAME, resultSet.getString(2));
				}
			}
			LOG.info("Exit : getDispatchOptionDefaultValues");
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return map;
	}

}
