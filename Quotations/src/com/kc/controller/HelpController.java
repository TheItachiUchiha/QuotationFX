package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class HelpController {
	
	private static final Logger LOG = LogManager.getLogger(HelpController.class);
	
	public void companyDetails()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/help-companyDetails.fxml"));
		GridPane companyDetails = (GridPane) menuLoader.load();
		((BorderPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setCenter(companyDetails);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
