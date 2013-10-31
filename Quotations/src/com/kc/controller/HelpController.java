package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
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
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Company Detalis");
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(companyDetails);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
