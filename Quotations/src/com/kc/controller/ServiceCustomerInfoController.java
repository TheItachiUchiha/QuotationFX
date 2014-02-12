package com.kc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.EnquiryViewVO;

public class ServiceCustomerInfoController {
	
	private static final Logger LOG = LogManager.getLogger(ServiceCustomerInfoController.class);
	
		@FXML
	    private TextArea address;

	    @FXML
	    private TextField city;

	    @FXML
	    private TextField companyName;

	    @FXML
	    private TextField contactNumber;

	    @FXML
	    private TextField customerName;

	    @FXML
	    private ToggleGroup customerType;

	    @FXML
	    private RadioButton dealer;

	    @FXML
	    private TextField emailId;

	    @FXML
	    private RadioButton endUser;

	    @FXML
	    private TextField state;

	    @FXML
	    private TextField telephone;

	    @FXML
	    private TextField tinNumber;

	    @FXML
	    private TextField website;
	    
	    public void fillTextFieldValues(EnquiryViewVO customersVO)
		{
			LOG.info("Enter : fillTextFieldValues");
			customerName.setText(customersVO.getCustomerName());
			companyName.setText(customersVO.getCompanyName());
			address.setText(customersVO.getAddress());
			city.setText(customersVO.getCity());
			state.setText(customersVO.getState());
			emailId.setText(customersVO.getEmailId());
			contactNumber.setText(customersVO.getContactNumber());
			tinNumber.setText(customersVO.getTinNumber());
			telephone.setText(customersVO.getContactNumber());
			//website.setText(customersVO.getWebsite());
			if(customersVO.getCustomerType().equalsIgnoreCase("Dealer"))
			{
				dealer.setSelected(true);
			}

			if(customersVO.getCustomerType().equalsIgnoreCase("End User"))
			{
				endUser.setSelected(true);
			}
			LOG.info("Exit : fillTextFieldValues");
		}

}
