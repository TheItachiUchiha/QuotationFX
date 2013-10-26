package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CustomersController {
	
	private static final Logger LOG = LogManager.getLogger(CustomersController.class);
	
	@FXML
	private TextField customerName;
	@FXML
	private TextField companyName;
	@FXML
	private TextArea address;
	@FXML
	private TextField city;
	@FXML
	private TextField state;
	@FXML
	private TextField emailId;
	@FXML
	private TextField contactNumber;
	@FXML
	private TextField tinNumber;
	@FXML
	private CheckBox dealer;
	@FXML
	private CheckBox endUser;
	@FXML
	private Label title;
}
