package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class UsersController {

	private static final Logger LOG = LogManager.getLogger(UsersController.class);
	
	public void newUser()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/users.fxml"));
		GridPane userCreate = (GridPane) menuLoader.load();
		((BorderPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setCenter(userCreate);
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
		GridPane userCreate = (GridPane) menuLoader.load();
		((BorderPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setCenter(userCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
