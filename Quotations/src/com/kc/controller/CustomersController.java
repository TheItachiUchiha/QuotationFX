package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class CustomersController {
	
	private static final Logger LOG = LogManager.getLogger(CustomersController.class);
	public void newCustomer()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/customers-create.fxml"));
		GridPane customerCreate = (GridPane) menuLoader.load();
		((BorderPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setCenter(customerCreate);
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
		GridPane customerCreate = (GridPane) menuLoader.load();
		((BorderPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setCenter(customerCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
