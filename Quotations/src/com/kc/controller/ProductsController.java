package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

}
