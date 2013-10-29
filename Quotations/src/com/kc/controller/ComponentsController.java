package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.ComponentsVO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ComponentsController {
	
	private static final Logger LOG = LogManager.getLogger(ComponentsController.class);
	
	@FXML
	private TextField componentName;
	@FXML
	private TextField componentCategory;
	@FXML
	private TextField subCategory;
	@FXML
	private TextField vender;
	@FXML
	private TextField model;
	@FXML
	private TextField type;
	@FXML
	private TextField size;
	@FXML
	private TextField costPrice;
	@FXML
	private TextField dealerPrice;
	@FXML
	private TextField endUserPrice;
	@FXML
	private Label title;

	
	public void newComponent()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/components-create.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((BorderPane)((BorderPane)LoginController.home.getCenter()).getCenter()).setCenter(componentCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void modifyComponent()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/components-create.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((BorderPane)((BorderPane)LoginController.home.getCenter()).getCenter()).setCenter(componentCreate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
