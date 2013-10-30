package com.kc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.HelpVO;

public class HelpCompanyDetailsController {
private static final Logger LOG = LogManager.getLogger(HelpCompanyDetailsController.class);
	
	@FXML
	private TextField companyName;
	@FXML
	private TextField contactDetails;
	@FXML
	private TextArea description;
	@FXML
	private TextArea address;
	public void saveCompanyDetails()
	{
		HelpVO helpVO=new HelpVO();
		helpVO.setCompanyName(companyName.getText());
		helpVO.setContactDetails(contactDetails.getText());
		helpVO.setDescription(description.getText());
		helpVO.setAddress(address.getText());
	}

}
