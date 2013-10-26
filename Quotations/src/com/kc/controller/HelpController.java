package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelpController {
	
	private static final Logger LOG = LogManager.getLogger(HelpController.class);
	
	@FXML
	private TextField companyName;
	@FXML
	private TextField contactDetails;
	@FXML
	private TextArea description;
	@FXML
	private TextArea address;
	@FXML
	private Label title;

}
