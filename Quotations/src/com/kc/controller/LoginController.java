package com.kc.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LoginController extends Application {

	private static final Logger LOG = LogManager.getLogger(LoginController.class);
	public static Stage primaryStage;
	public static BorderPane home;
	public static BorderPane login;

	@FXML
	private TextField username;
	@FXML
	private TextField password;

	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		LOG.info("Enter : start");
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Quotation");
			// Load the root layout from the fxml file
			FXMLLoader loader = new FXMLLoader(
					LoginController.class.getResource("../view/root.fxml"));
			login = (BorderPane) loader.load();
			Scene scene = new Scene(this.login);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : start");
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("static-access")
	public void doLogin() {
		LOG.info("Enter : doLogin");
		try {
			if (username.getText().equals("admin")
					&& password.getText().equals("admin")) {
				FXMLLoader loader = new FXMLLoader(
						LoginController.class
								.getResource("../view/admin.fxml"));
				FXMLLoader subMenuLoader = new FXMLLoader(
						LoginController.class
								.getResource("../view/home-admin.fxml"));
				this.home = (BorderPane) loader.load();
				BorderPane subMenu = (BorderPane) subMenuLoader.load();
				this.home.setCenter(subMenu);
				Scene scene = new Scene(this.home);
				this.primaryStage.setScene(scene);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : doLogin");
	}
}
