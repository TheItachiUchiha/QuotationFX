package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.ComponentsVO;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

	
	public void saveComponent()
	{
		ComponentsVO componentsVO = new ComponentsVO();
		
	}
	
}
