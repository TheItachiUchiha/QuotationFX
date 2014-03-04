package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.model.CustomersVO;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

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
	private AutoCompleteTextField<String> customerName;
	@FXML
	private AutoCompleteTextField<String> companyName;
	@FXML
	private TextArea address;
	@FXML
	private AutoCompleteTextField<String> city;
	@FXML
	private AutoCompleteTextField<String> state;
	@FXML
	private TextField emailId;
	@FXML
	private TextField contactNumber;
	@FXML
	private TextField tinNumber;
	@FXML
	private TextField telephone;
	@FXML
	private TextField website;
	@FXML
    private RadioButton dealer;
    @FXML
    private RadioButton endUser;
    @FXML
	private Label message;
    
    private ObservableList<String> cityList=FXCollections.observableArrayList();
	private ObservableList<String> stateList=FXCollections.observableArrayList();
	private ObservableList<String> companylist=FXCollections.observableArrayList();
	private ObservableList<String> namelist=FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		LOG.info("Enter : initialize");
		try{
			AdminHomeController.currentPage.setText("MODIFY CUSTOMER");
			validation.allowAsPhoneNumber(contactNumber);
			validation.allowAsPhoneNumber(telephone);
			customersList = customersDAO.getCustomers();
			
			cityList = customersDAO.getCustomerCityList();
			namelist = customersDAO.getCustomerNameList();
			companylist = customersDAO.getCustomerCompanyList();
			stateList = customersDAO.getCustomerStateList();
			companyName.setItems(companylist);
			customerName.setItems(namelist);
			city.setItems(cityList);
			state.setItems(stateList);
			
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
		telephone.setText(customersVO.getTelephone());
		website.setText(customersVO.getWebsite());
		if(customersVO.getCustomerType().equalsIgnoreCase("Dealer"))
		{
			dealer.setSelected(true);
		}

		if(customersVO.getCustomerType().equalsIgnoreCase("End User"))
		{
			endUser.setSelected(true);
		}
		modifyHbox.setVisible(true);
		LOG.info("Exit : fillTextFieldValues");
	}
	public void modifyCustomer()
	{
		LOG.info("Enter : modifyCustomer");
		try
		{
			if(validation.isEmpty(contactNumber, tinNumber))
			{
				message.setText(CommonConstants.MANDATORY_FIELDS);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else if(validation.isEmptyAutoComplte(customerName, companyName))
			{
				message.setText(CommonConstants.INCORRECT_EMAIL);
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
			else if(contactNumber.getText().length()<10)
			{
				message.setText(CommonConstants.INCORRECT_PHONE_NO);
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
				customersVO.setTelephone(telephone.getText());
				customersVO.setWebsite(website.getText());
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