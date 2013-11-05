package com.kc.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class AdminHomeController {

	public void priceEstimation()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/priceEstimation.fxml"));
			BorderPane price = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(price);
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
