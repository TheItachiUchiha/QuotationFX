package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.model.CustomersVO;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class CustomersCreateController implements Initializable {
private static final Logger LOG = LogManager.getLogger(CustomersCreateController.class);
	CustomersDAO customersDAO;
	Validation validation;
	
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
	
	public CustomersCreateController()
	{
		customersDAO = new CustomersDAO();
		validation = new Validation();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			AdminHomeController.currentPage.setText("CREATE CUSTOMER");
			validation.allowAsPhoneNumber(contactNumber);
			dealer.setSelected(true);
			cityList = customersDAO.getCustomerCityList();
			namelist = customersDAO.getCustomerNameList();
			companylist = customersDAO.getCustomerCompanyList();
			stateList = customersDAO.getCustomerStateList();
			companyName.setItems(companylist);
			customerName.setItems(namelist);
			city.setItems(cityList);
			state.setItems(stateList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void saveCustomers()
	{
		LOG.info("Enter : saveCustomers");
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
			else if(contactNumber.getText().length()<10)
			{
				message.setText(CommonConstants.INCORRECT_PHONE_NO);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else
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
				customersVO.setTelephone(telephone.getText());
				customersVO.setWebsite(website.getText());
				if(endUser.isSelected())
				{
					customersVO.setCustomerType("E");
				}
				else
				{
					customersVO.setCustomerType("D");
				}
				customersDAO.saveCustomer(customersVO);
				message.setText(CommonConstants.CUSTOMER_ADD_SUCCESS);
				message.getStyleClass().remove("failure");
				message.getStyleClass().add("success");
				message.setVisible(true);
			}
		}
		catch (SQLException s)
		{
			if (s.getErrorCode() == CommonConstants.UNIQUE_CONSTRAINT) {
				message.setText(CommonConstants.DUPLICATE_CUSTOMER);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			message.setVisible(true);
		}
		LOG.info("Exit : saveCustomers");
	}	
}