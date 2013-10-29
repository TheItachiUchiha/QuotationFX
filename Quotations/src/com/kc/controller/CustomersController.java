package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.CustomersVO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

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
	private RadioButton customerType;
	@FXML
	private Label title;
	public void newCustomer()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/customers-create.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((BorderPane)((BorderPane)LoginController.home.getCenter()).getCenter()).setCenter(componentCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void modifyCustomer()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/customers-create.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((BorderPane)((BorderPane)LoginController.home.getCenter()).getCenter()).setCenter(componentCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
