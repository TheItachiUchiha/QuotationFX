package com.kc.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CustomersController {
	
	private static final Logger LOG = LogManager.getLogger(CustomersController.class);
	public void newCustomer()
	{

		LOG.info("Enter : newCustomer");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/customers-create.fxml"));
		GridPane customerCreate = (GridPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Create Customer");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(customerCreate);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : newCustomer");
	}
	public void modifyCustomer()
	{
		LOG.info("Enter : modifyCustomer");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/customers-modify.fxml"));
		BorderPane customerCreate = (BorderPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Modify Customer");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(customerCreate);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : modifyCustomer");
	}
	public void viewCustomers()
	{
		LOG.info("Enter : viewCustomers");
		try{
			FXMLLoader menuLoader = new FXMLLoader(
					this.getClass()
							.getResource("/com/kc/view/customers-view.fxml"));
			BorderPane customerCreate = (BorderPane) menuLoader.load();
			((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("View Customers");
			((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
			((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(customerCreate);
			}
			catch (Exception e) {
				LOG.error(e.getMessage());
			}
		LOG.info("Exit : viewCustomers");
	}
}
