package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.model.CustomersVO;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CustomersModifyController implements Initializable{
	private static final Logger LOG = LogManager.getLogger(CustomersModifyController.class);
	private ObservableList<CustomersVO> customersList;
	private CustomersDAO customersDAO;
	private CustomersVO customersVO;
	private Validation validation;
	
	public CustomersModifyController(){
		customersDAO = new CustomersDAO();
		customersVO = new CustomersVO();
		validation = new Validation();
	}
 	
	@FXML
	private AutoCompleteTextField <CustomersVO> customerNameAutoFill;
	@FXML
	private HBox modifyHbox; 
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
    private RadioButton dealer;
    @FXML
    private RadioButton endUser;
    @FXML
	private Label message;
    
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		LOG.info("Enter : initialize");
		try{
			validation.allowAsPhoneNumber(contactNumber);
			customersList = customersDAO.getCustomers();
			
			customerNameAutoFill.setItems(customersList);

			customerNameAutoFill.setPromptText("Name Of Customer");

			customerNameAutoFill.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					for(CustomersVO customersVO: customersList)
					{
						if(customersVO.getCustomerName().equals(customerNameAutoFill.getText())){
							modifyHbox.setVisible(true);
							customerNameAutoFill.setDisable(true);
							fillTextFieldValues(customersVO);
						}
					}
				}
			});
			}
			catch (Exception e) {
				LOG.error(e.getMessage());
			}
		LOG.info("Exit : initialize");
	}
	public void fillTextFieldValues(CustomersVO customersVO)
	{
		LOG.info("Enter : fillTextFieldValues");
		CustomersModifyController.this.customersVO.setId(customersVO.getId());
		customerName.setText(customersVO.getCustomerName());
		companyName.setText(customersVO.getCompanyName());
		address.setText(customersVO.getAddress());
		city.setText(customersVO.getCity());
		state.setText(customersVO.getState());
		emailId.setText(customersVO.getEmailId());
		contactNumber.setText(customersVO.getContactNumber());
		tinNumber.setText(customersVO.getTinNumber());
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
	public void modifyCustomer()
	{
		LOG.info("Enter : modifyCustomer");
		try
		{
			if(validation.isEmpty(customerName, companyName, contactNumber, tinNumber))
			{
				message.setText(CommonConstants.MANDATORY_FIELDS);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else if(!validation.isEmail(emailId.getText()))
			{
				message.setText(CommonConstants.INCORRECT_EMAIL);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else
			{
				CustomersVO customersVO = new CustomersVO();
				customersVO.setCustomerName(customerName.getText());
				customersVO.setCompanyName(companyName.getText());
				customersVO.setAddress(address.getText());
				customersVO.setCity(city.getText());
				customersVO.setState(state.getText());
				customersVO.setEmailId(emailId.getText());
				customersVO.setContactNumber(contactNumber.getText());
				customersVO.setTinNumber(tinNumber.getText());
				if(dealer.isSelected())
				{
					customersVO.setCustomerType("D");
				}
				else
				{
					customersVO.setCustomerType("E");
				}
				customersVO.setId(this.customersVO.getId());
				customersDAO.updateCustomer(customersVO);
				message.setText(CommonConstants.CUSTOMER_MODIFY_SUCCESS);
				message.getStyleClass().remove("failure");
				message.getStyleClass().add("success");
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : modifyCustomer");
	}
}