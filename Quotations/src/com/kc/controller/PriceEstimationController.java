package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.PriceEstimationVO;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

public class PriceEstimationController {
	
	private static final Logger LOG = LogManager.getLogger(PriceEstimationController.class);
	
	@FXML
	private ComboBox productCategory;
	@FXML
	private ComboBox subCategory;
	@FXML
	private ComboBox productName;
	@FXML
	private Label referenceNo;
	@FXML
	private Label date;
	@FXML
	private CheckBox dealer;
	@FXML
	private CheckBox endUser;
	@FXML
	private TableColumn componentName;
	@FXML
	private TableColumn vender;
	@FXML
	private TableColumn model;
	@FXML
	private TableColumn type;
	@FXML
	private TableColumn size;
	@FXML
	private TableColumn quantity;
	@FXML
	private TableColumn costPrice;
	@FXML
	private TableColumn dealerEndUser;
	@FXML
	private Label title;
	
	public void savePriceEstimation()
	{
		PriceEstimationVO priceEstimationVO=new PriceEstimationVO();
	}
}
