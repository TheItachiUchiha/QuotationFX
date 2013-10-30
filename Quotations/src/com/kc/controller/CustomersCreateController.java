package com.kc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.kc.model.CustomersVO;

public class CustomersCreateController {
private static final Logger LOG = LogManager.getLogger(CustomersCreateController.class);
	
	@FXML
	private TextField customerName;
	@FXML
	private TextField companyName;
	@FXML
	private TextArea address;
	@FXML
	private TextField city;
	@FXML
	private TextField state;
	@FXML
	private TextField emailId;
	@FXML
	private TextField contactNumber;
	@FXML
	private TextField tinNumber;
	@FXML
	private RadioButton customerType;
	public void saveCustomers()
	{
		CustomersVO customersVO=new CustomersVO();
		customersVO.setCustomerName(customerName.getText());
		customersVO.setCompanyName(companyName.getText());
		customersVO.setAddress(address.getText());
		customersVO.setCity(city.getText());
		customersVO.setState(state.getText());
		customersVO.setEmailId(emailId.getText());
		customersVO.setContactNumber(contactNumber.getText());
		customersVO.setTinNumber(tinNumber.getText());
		customersVO.setCustomerType(customerType.getText());
	}

}