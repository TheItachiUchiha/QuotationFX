package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.ProductsVO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ProductsController {
	
	private static final Logger LOG = LogManager.getLogger(ProductsController.class);
	
	@FXML
	private TextField productName;
	@FXML
	private TextField productCategory;
	@FXML
	private TextField subCategory;
	@FXML
	private Label title;

	public void newProduct()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/products-create.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((BorderPane)((BorderPane)LoginController.home.getCenter()).getCenter()).setCenter(componentCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void modifyProduct()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/products-create.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((BorderPane)((BorderPane)LoginController.home.getCenter()).getCenter()).setCenter(componentCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
