package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.HelpVO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

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
	public void companyDetails()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/help-companyDetails.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((BorderPane)((BorderPane)LoginController.home.getCenter()).getCenter()).setCenter(componentCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
