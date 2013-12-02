package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PriceEstimationNewController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(PriceEstimationNewController.class);
	@FXML
	private ComboBox<String> monthCombo;
	@FXML
	private ComboBox<String> yearCombo;
	@FXML
	private ComboBox<String> referenceCombo;
	@FXML
	private TextArea requirements;
	@FXML
	private TextArea address;
	@FXML
	private TextField enquiryType;
	@FXML
	private TextField productName;
	@FXML
	private TextField customerName;
	@FXML
	private TextField companyName;
	@FXML
	private TextField tinNumber;
	@FXML
	private TextField emailId;
	@FXML
	private TextField referedBy;
	@FXML
	private TextField customerType;
	@FXML
	private TextField state;
	@FXML
	private TextField city;
	@FXML
	private TextField contactNumber;
	@FXML
	private TextField purchasePeriod;
	@FXML
	private TextField customerFile;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
