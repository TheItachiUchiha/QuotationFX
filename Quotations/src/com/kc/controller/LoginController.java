package com.kc.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class LoginController extends Application {

	private static Stage primaryStage;
	private AnchorPane login;
	private BorderPane home;
	
	@FXML
	private TextField userName;
	@FXML
	private TextField password;
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		
		
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Quotation");
			// Load the root layout from the fxml file
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/Root.fxml"));
			login = (AnchorPane) loader.load();
			Scene scene = new Scene(this.login);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	
	@FXML
	private void initialize() {
		
	}
	
	@SuppressWarnings("static-access")
	public void doLogin()
	{
		try{
			if(userName.getText().equals("admin") &&
					password.getText().equals("admin"))
			{
				FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/Home.fxml"));
				this.home = (BorderPane) loader.load();
				Scene scene = new Scene(this.home);
				this.primaryStage.setScene(scene);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
