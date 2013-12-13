package com.kc.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.kc.constant.CommonConstants;

public class SaleStatusController implements Initializable {
	
	 @FXML
	    private TableColumn<?, ?> companyName;

	    @FXML
	    private TableColumn<?, ?> customerName;

	    @FXML
	    private TableColumn<?, ?> dateOfEnquiry;

	    @FXML
	    private TableColumn<?, ?> dateOfQuotation;

	    @FXML
	    private TableColumn<?, ?> dateOfSalesOrder;

	    @FXML
	    private TableColumn<?, ?> location;

	    @FXML
	    private TableColumn<?, ?> productName;

	    @FXML
	    private TableColumn<?, ?> referedBy;

	    @FXML
	    private TableColumn<?, ?> referenceNo;

	    @FXML
	    private TableView<?> successTable;

	    @FXML
	    private TableColumn<?, ?> ucompanyName;

	    @FXML
	    private TableColumn<?, ?> ucustomerName;

	    @FXML
	    private TableColumn<?, ?> udateOfEnquiry;

	    @FXML
	    private TableColumn<?, ?> udateOfQuotation;

	    @FXML
	    private TableColumn<?, ?> utotalReminderSent;

	    @FXML
	    private TableView<?> unsuccessTable;

	    @FXML
	    private TableColumn<?, ?> uproductName;

	    @FXML
	    private TableColumn<?, ?> ureferedBy;

	    @FXML
	    private TableColumn<?, ?> ureferenceNo;

	    @FXML
	    private ComboBox<String> monthCombo;
	    
	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    @FXML
	    private Button unsuccess;
	    
	    @FXML
	    private Button success;

	private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
		yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
		monthCombo.setItems(monthList);
		yearCombo.setItems(yearList);
		
	}

}
