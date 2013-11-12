package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class TopMenuController {

	private static final Logger LOG = LogManager.getLogger(TopMenuController.class);
	public void adminHome()
	{
		LOG.info("Enter : adminHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/home-admin.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : adminHome");
	}
	public void productsHome()
	{
		LOG.info("Enter : productsHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/products-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : productsHome");
	}
	public void componentsHome()
	{
		LOG.info("Enter : productsHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/components-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : productsHome");
	}
	public void usersHome()
	{
		LOG.info("Enter : usersHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/users-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : usersHome");
	}
	public void customersHome()
	{
		LOG.info("Enter : customersHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/customers-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : customersHome");
	}
	public void helpHome()
	{
		LOG.info("Enter : helpHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/help-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : helpHome");
	}
	public void logout()
	{
	}
}
