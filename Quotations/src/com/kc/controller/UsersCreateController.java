package com.kc.controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.UsersDAO;
import com.kc.model.UsersVO;

public class UsersCreateController {
	private static final Logger LOG = LogManager.getLogger(UsersController.class);
	UsersDAO usersDAO=new UsersDAO();
	
	@FXML
	private TextField name;
	@FXML
	private TextField designation;
	@FXML
	private TextField mobileNumber;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private CheckBox quotation;
	@FXML
	private CheckBox priceEstimation;
	@FXML
	private CheckBox salesOrderManagement;
	@FXML
	private CheckBox statusReminder;
	@FXML
	private CheckBox report;
	@FXML
	private CheckBox view;
	@FXML
	private CheckBox edit;
	@FXML
	private CheckBox delete;
	@FXML
	private ComboBox userType;
	@FXML
	private Label message;
	public void saveUsers()
	{
		try
			{
			UsersVO usersVO=new UsersVO();
			usersVO.setName(name.getText());
			usersVO.setDesignation(designation.getText());
			usersVO.setMobileNumber(mobileNumber.getText());
			usersVO.setUsername(username.getText());
			usersVO.setPassword(password.getText());
			if(quotation.isSelected())
			{
				usersVO.setQuotation("Y");
			}
			else
			{
				usersVO.setQuotation("N");
			}
			if(priceEstimation.isSelected())
			{
				usersVO.setPriceEstimation("Y");
			}
			else
			{
				usersVO.setPriceEstimation("N");
			}
			if(salesOrderManagement.isSelected())
			{
				usersVO.setSalesOrderManagement("Y");
			}
			else
			{
				usersVO.setSalesOrderManagement("N");
			}
			if(statusReminder.isSelected())
			{
				usersVO.setStatusReminder("Y");
			}
			else
			{
				usersVO.setStatusReminder("N");
			}
			if(report.isSelected())
			{
				usersVO.setReport("Y");
			}
			else
			{
				usersVO.setReport("N");
			}
			if(view.isSelected())
			{
				usersVO.setView("Y");
			}
			else
			{
				usersVO.setView("N");
			}
			if(edit.isSelected())
			{
				usersVO.setEdit("Y");
			}
			else
			{
				usersVO.setEdit("N");
			}
			if(delete.isSelected())
			{
				usersVO.setDelete("Y");
			}
			else
			{
				usersVO.setDelete("N");
			}
			if(userType.getSelectionModel().getSelectedItem().equals("Admin"))
			{
			usersVO.setUserType("ADMIN");
			}
			else if(userType.getSelectionModel().getSelectedItem().equals("Normal"))
			{
				usersVO.setUserType("NORMAL");
			}	
			usersDAO.saveUser(usersVO);
			message.setText(CommonConstants.USER_ADD_SUCCESS);
			message.setVisible(true);
		}
			
		catch (SQLException s)
		{
			if (s.getErrorCode() == CommonConstants.UNIQUE_CONSTRAINT) {
				message.setText(CommonConstants.DUPLICATE_USER);
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}

}
