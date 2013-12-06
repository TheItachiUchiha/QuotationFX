package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class QuotationOptionController implements Initializable {

	@FXML
	private ComboBox<String> categoryCombo;
	@FXML
	private ComboBox<String> subcategoryCombo;
	@FXML
	private ComboBox<String> nameCombo;
	@FXML
	private TextArea productArea;
	@FXML
	private TextField defaultQuotationFile;
	@FXML
	private TextField wordFormat;
	@FXML
	private TextField pdfFormat;
	@FXML
	private TextField emailAddress;
	@FXML
	private Button defaultLocation;
	@FXML
	private Button wordLocation;
	@FXML
	private Button pdfLocation;
	@FXML
	private Label message;
	@FXML
	private Label messageSent;
	@FXML
	private Button clear;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}
