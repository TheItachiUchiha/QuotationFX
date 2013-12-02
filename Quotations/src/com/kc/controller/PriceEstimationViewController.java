package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PriceEstimationViewController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(PriceEstimationViewController.class);
	@FXML
	private ComboBox<String> monthCombo;
	@FXML
	private ComboBox<String> yearCombo;
	@FXML
	private ComboBox<String> referenceCombo;
	@FXML
 	private TableView<?> priceEstimationTable;
	@FXML private TableColumn<?, String> referenceNo;
    @FXML private TableColumn<?, String> productName;
    @FXML private TableColumn<?, String> customerName;
    @FXML private TableColumn<?, String> companyName;
    @FXML private TableColumn<?, String> referedBy;
    @FXML private TableColumn<?, String> dateOfEnquiry;
    @FXML private TableColumn<?, String> dateOfPe;
    @FXML private TableColumn action;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
