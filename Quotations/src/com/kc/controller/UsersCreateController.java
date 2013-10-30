package com.kc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.UsersVO;

public class UsersCreateController {
private static final Logger LOG = LogManager.getLogger(UsersController.class);
	
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
	private CheckBox salesorderManagement;
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
	public void saveUsers()
	{
		UsersVO usersVO=new UsersVO();
		usersVO.setName(name.getText());
		usersVO.setDesignation(designation.getText());
		usersVO.setMobileNumber(mobileNumber.getText());
		usersVO.setUsername(username.getText());
		usersVO.setPassword(password.getText());
	}

}
