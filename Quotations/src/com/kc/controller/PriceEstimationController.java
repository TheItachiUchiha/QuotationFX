package com.kc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

public class PriceEstimationController {
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
}
