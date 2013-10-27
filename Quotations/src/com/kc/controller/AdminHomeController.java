package com.kc.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class AdminHomeController {

	public void priceEstimationNew()
	{
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/priceEstimation-new.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void priceEstimationModify()
	{
		
	}
	public void priceEstimationView()
	{
		
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
	public void createProduct()
	{
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/product-create.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void modifyProduct()
	{
		
	}
	public void viewProduct()
	{
		
	}
	public void createComponent()
	{
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/components-create.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void modifyComponent()
	{
		
	}
	public void viewComponent()
	{
		
	}
	public void createCustomer()
	{
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/customers-create.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void modifyCustomer()
	{
		
	}
	public void viewCustomer()
	{
		
	}
	public void createUser()
	{
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/users.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void modifyUser()
	{
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/users.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void viewUser()
	{
		
	}
	public void companyDetails()
	{
		try{
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../view/help-companyDetails.fxml"));
			GridPane gridpane = (GridPane) loader.load();
			LoginController.home.setCenter(gridpane);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void about()
	{
		
	}	
}
