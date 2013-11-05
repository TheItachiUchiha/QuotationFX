package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class UsersController {

	private static final Logger LOG = LogManager.getLogger(UsersController.class);
	
	public void newUser()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("../view/users-create.fxml"));
		GridPane userCreate = (GridPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Create User");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(userCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void modifyUser()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("../view/users-modify.fxml"));
		BorderPane userCreate = (BorderPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Modify User");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(userCreate);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void viewUser()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("../view/users-view.fxml"));
		BorderPane userCreate = (BorderPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("View Users");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(userCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
