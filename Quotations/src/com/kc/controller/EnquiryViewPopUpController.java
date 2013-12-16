package com.kc.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import com.kc.constant.CommonConstants;
import com.kc.model.EnquiryViewVO;


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
    private TextField emailId;

    @FXML
    private GridPane enquiryGrid;

    @FXML
    private TextField enquiryType;

    @FXML
    private TextField purchasePeriod;

    @FXML
    private TextField referedBy;

    @FXML
    private TextField state;

    @FXML
    private TextField tinNumber;

    @FXML
    private Button viewFile;
    
    @FXML
    private TextField productName;
    
    @FXML
    private TextField date;


    @FXML
    void initialize() {
    	address.setEditable(false);
    	city.setEditable(false);
    	companyName.setEditable(false);
    	contactNumber.setEditable(false);
    	customerFile.setEditable(false);
    	customerName.setEditable(false);
    	customerRequirements.setEditable(false);
    	customerType.setEditable(false);
    	emailId.setEditable(false);
    	//enquiryGrid.setDisable(true);
    	enquiryType.setEditable(false);
    	purchasePeriod.setEditable(false);
    	referedBy.setEditable(false);
    	state.setEditable(false);
    	tinNumber.setEditable(false);
    	productName.setEditable(false);
    	date.setEditable(false);
    	
    	viewFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent paramT) {
				try
				{
					File newFile = new File(customerFile.getText());
					if(newFile.exists())
					{
						Desktop.getDesktop().open(newFile);
					}
					else
					{
						Dialogs.showErrorDialog(EnquiryViewController.viewStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
					}
				} catch (IOException e) {
					Dialogs.showErrorDialog(EnquiryViewController.viewStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
				}
			}
		});
    }
    
    public void fillTextFieldValues(EnquiryViewVO enquiryViewVO)
    {
    	city.setText(enquiryViewVO.getCity());
    	companyName.setText(enquiryViewVO.getCompanyName());
    	customerName.setText(enquiryViewVO.getCustomerName());
    	enquiryType.setText(enquiryViewVO.getEnquiryType());
    	purchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
    	referedBy.setText(enquiryViewVO.getReferedBy());
    	state.setText(enquiryViewVO.getState());
    	productName.setText(enquiryViewVO.getProductName());
    	date.setText(enquiryViewVO.getDateOfEnquiry());
    	address.setText(enquiryViewVO.getAddress());
    	contactNumber.setText(enquiryViewVO.getContactNumber());
    	customerFile.setText(enquiryViewVO.getCustomerFile());
    	customerRequirements.setText(enquiryViewVO.getCustomerRequirement());
    	customerType.setText(enquiryViewVO.getCustomerType());
    	emailId.setText(enquiryViewVO.getEmailId());
    	tinNumber.setText(enquiryViewVO.getTinNumber());
    }

}

