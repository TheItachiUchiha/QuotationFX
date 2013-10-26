package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UsersController {

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
	private TextField password;
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
	@FXML
	private Label title;
}
