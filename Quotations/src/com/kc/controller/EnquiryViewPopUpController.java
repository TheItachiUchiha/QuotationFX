package com.kc.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.mysql.jdbc.log.Log;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class EnquiryViewPopUpController {

    @FXML
    private TextArea address;

    @FXML
    private TextField city;

    @FXML
    private TextField companyName;

    @FXML
    private TextField contactNumber;

    @FXML
    private TextField customerFile;

    @FXML
    private TextField customerName;

    @FXML
    private TextArea customerRequirements;

    @FXML
    private TextField customerType;

    @FXML
    private AutoCompleteTextField<?> emailId;

    @FXML
    private GridPane enquiryGrid;

    @FXML
    private TextField enquiryType;

    @FXML
    private Label messageNewEnquiry;

    @FXML
    private TextField purchasePeriod;

    @FXML
    private AutoCompleteTextField<?> referedBy;

    @FXML
    private TextField state;

    @FXML
    private AutoCompleteTextField<?> tinNumber;

    @FXML
    private Button viewFile;
    
    @FXML
    private TextField productName;
    
    @FXML
    private TextField date;


    @FXML
    void initialize() {
    	address.setDisable(true);
    	city.setDisable(true);
    	companyName.setDisable(true);
    	contactNumber.setDisable(true);
    	customerFile.setDisable(true);
    	customerName.setDisable(true);
    	customerRequirements.setDisable(true);
    	customerType.setDisable(true);
    	emailId.setDisable(true);
    	enquiryGrid.setDisable(true);
    	enquiryType.setDisable(true);
    	messageNewEnquiry.setDisable(true);
    	purchasePeriod.setDisable(true);
    	referedBy.setDisable(true);
    	state.setDisable(true);
    	tinNumber.setDisable(true);
    	productName.setDisable(true);
    	date.setDisable(true);
    	
    	viewFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent paramT) {
				try {
					Desktop.getDesktop().open(new File(customerFile.getText()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
    }

}

