package com.kc.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class AdminHomeController {

	public void priceEstimation()
	{
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/priceEstimation.fxml"));
			BorderPane price = (BorderPane) loader.load();
			((BorderPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setCenter(price);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void quotationPreparation()
	{
		
	}
	public void salesOrder()
	{
		
	}
	public void status()
	{
		
	}
	public void reports()
	{
		
	}
}
