package com.kc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.ReminderVO;
import com.kc.model.ServiceVO;
import com.kc.util.DBConnector;

public class StatusReminderDAO {
	
	private static final Logger LOG = LogManager.getLogger(StatusReminderDAO.class);
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void createReminder(ReminderVO reminderVO)
	{

		LOG.info("Enter : createReminder");
		try
		{
			conn = DBConnector.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO REMINDER(reference_no,total_reminder,frequency,last_sent,next_send) VALUES(?, ?, ?, ?, ?)");
			preparedStatement.setString(1, reminderVO.getReferenceNo());
			preparedStatement.setInt(2, reminderVO.getTotalReminder());
			preparedStatement.setInt(3, reminderVO.getFrequency());
			preparedStatement.setString(4, reminderVO.getLastSent());
			preparedStatement.setString(5, reminderVO.getNextSend());
			
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : createReminder");
	}
	
	public ObservableList<ReminderVO> getReminders() throws SQLException
	{
		LOG.info("Enter : getReminders");
		ObservableList<ReminderVO> listOfReminders = FXCollections.observableArrayList();
		try{
			conn = DBConnector.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT UNIQUE e.REF_NUMBER FROM ENQUIRY e ,REMINDER r WHERE e.REF_NUMBER = r.REFERENCE_NO");
			
			while(resultSet.next())
			{
				ReminderVO reminderVO = new ReminderVO();
				reminderVO.setId(resultSet.getInt(1));
				reminderVO.setReferenceNo(resultSet.getString(2));
				reminderVO.setTotalReminder(resultSet.getInt(3));
				reminderVO.setFrequency(resultSet.getInt(4));
				reminderVO.setLastSent(resultSet.getString(5));
				reminderVO.setNextSend(resultSet.getString(6));
				listOfReminders.add(reminderVO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		finally{
			if(conn !=null)
			{
				conn.close();
			}
		}
		LOG.info("Exit : getReminders");
		return listOfReminders;
	}

}
