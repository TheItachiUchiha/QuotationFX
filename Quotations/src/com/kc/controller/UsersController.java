package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.UsersVO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

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
	public void newUser()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/users.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((BorderPane)((BorderPane)LoginController.home.getCenter()).getCenter()).setCenter(componentCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void modifyUser()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/users.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((BorderPane)((BorderPane)LoginController.home.getCenter()).getCenter()).setCenter(componentCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
