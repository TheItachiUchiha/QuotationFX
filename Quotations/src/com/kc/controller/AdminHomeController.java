package com.kc.controller;



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class AdminHomeController {

	private static final Logger LOG = LogManager.getLogger(AdminHomeController.class);

	public void priceEstimationNew()
	{
		LOG.info("Enter : priceEstimationNew");
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/priceEstimation-new.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : priceEstimationNew");
	}
	public void priceEstimationModify()
	{
		LOG.info("Enter : priceEstimationModify");
		
				
		LOG.info("Exit : priceEstimationModify");
	}
	public void priceEstimationView()
	{
		LOG.info("Enter : priceEstimationView");
		
		
		LOG.info("Exit : priceEstimationView");
	}
	public void quotationPreparation()
	{
		LOG.info("Enter : quotationPreparation");
		
		
		LOG.info("Exit : quotationPreparation");
	}
	public void salesOrder()
	{
		LOG.info("Enter : salesOrder");
		
		
		LOG.info("Exit : salesOrder");
	}
	public void status()
	{
		LOG.info("Enter : status");
		
		
		LOG.info("Exit : status");
	}
	public void reports()
	{
		LOG.info("Enter : reports");
		
		
		LOG.info("Exit : reports");
	}
	public void createProduct()
	{
		LOG.info("Enter : createProduct");
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/product-create.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : createProduct");
	}
	public void modifyProduct()
	{
		LOG.info("Enter : modifyProduct");
		
		
		LOG.info("Exit : modifyProduct");
	}
	public void viewProduct()
	{
		LOG.info("Enter : viewProduct");
		
		
		LOG.info("Exit : viewProduct");
	}
	public void createComponent()
	{
		LOG.info("Enter : createComponent");
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/components-create.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : createComponent");
	}
	public void modifyComponent()
	{
		LOG.info("Enter : modifyComponent");
		
		
		LOG.info("Exit : modifyComponent");
	}
	public void viewComponent()
	{
		LOG.info("Enter : viewComponent");
		
		
		LOG.info("Exit : viewComponent");
	}
	public void createCustomer()
	{
		LOG.info("Enter : createCustomer");
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/customers-create.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : createCustomer");
	}
	public void modifyCustomer()
	{
		LOG.info("Enter : modifyCustomer");
		
		
		LOG.info("Exit : modifyCustomer");
	}
	public void viewCustomer()
	{
		LOG.info("Enter : viewCustomer");
		
		
		LOG.info("Exit : viewCustomer");
	}
	public void createUser()
	{
		LOG.info("Enter : createUser");
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/users.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : createUser");
	}
	public void modifyUser()
	{
		LOG.info("Enter : modifyUser");
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/users.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : modifyUser");
	}
	public void viewUser()
	{
		LOG.info("Enter : viewUser");
		
		
		LOG.info("Exit : viewUser");	
	}
	public void companyDetails()
	{
		LOG.info("Enter : companyDetails");
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/help-companyDetails.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : companyDetails");
	}
	public void about()
	{
		LOG.info("Enter : about");
		
		
		LOG.info("Exit : about");	
	}	
}
