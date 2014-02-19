package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.UsersDAO;
import com.kc.model.UsersVO;
import com.kc.util.Validation;

public class UsersCreateController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(UsersController.class);
	UsersDAO usersDAO;
	Validation validation;
	
	@FXML
	private TextField name;
	@FXML
	private TextField designation;
	@FXML
	private TextField mobileNumber;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private CheckBox quotation;
	@FXML
	private CheckBox priceEstimation;
	@FXML
	private CheckBox salesOrderManagement;
	@FXML
	private CheckBox statusReminder;
	@FXML
	private CheckBox report;
	@FXML
    private CheckBox enquiry;
    @FXML
    private CheckBox productDispatch;
    @FXML
	private CheckBox service;
	@FXML
	private CheckBox view;
	@FXML
	private CheckBox edit;
	@FXML
	private CheckBox delete;
	@FXML
	private ComboBox<String> userType;
	@FXML
	private Label message;
	
	public UsersCreateController()
	{
		usersDAO = new UsersDAO();
		validation = new Validation();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		validation.allowAsPhoneNumber(mobileNumber);
		userType.valueProperty().addListener(new ChangeListener<String>() {
            
			@Override public void changed(ObservableValue ov, String t, String t1) {                
				if(t1.equalsIgnoreCase("ADMIN"))
				{
					quotation.setSelected(true);
					quotation.setDisable(true);
					priceEstimation.setSelected(true);
					priceEstimation.setDisable(true);
					service.setDisable(true);;
					service.setSelected(true);
					productDispatch.setDisable(true);
					productDispatch.setSelected(true);
					enquiry.setDisable(true);
					enquiry.setSelected(true);
					report.setSelected(true);
					report.setDisable(true);
					statusReminder.setSelected(true);
					statusReminder.setDisable(true);
					salesOrderManagement.setSelected(true);
					salesOrderManagement.setDisable(true);
					view.setSelected(true);
					view.setDisable(true);
					edit.setSelected(true);
					edit.setDisable(true);
					delete.setSelected(true);
					delete.setDisable(true);
				}
				else if(t1.equalsIgnoreCase("NORMAL"))
				{
					quotation.setSelected(false);
					quotation.setDisable(false);
					priceEstimation.setSelected(false);
					priceEstimation.setDisable(false);
					service.setDisable(false);
					service.setSelected(false);
					productDispatch.setDisable(false);
					productDispatch.setSelected(false);
					enquiry.setDisable(false);
					enquiry.setSelected(false);
					report.setSelected(false);
					report.setDisable(false);
					statusReminder.setSelected(false);
					statusReminder.setDisable(false);
					salesOrderManagement.setSelected(false);
					salesOrderManagement.setDisable(false);
					view.setSelected(false);
					view.setDisable(false);
					edit.setSelected(false);
					edit.setDisable(false);
					delete.setSelected(false);
					delete.setDisable(false);
				}
			}
		});
	}
	public void saveUsers()
	{
		LOG.info("Enter : saveUsers");
		try
			{
			if(validation.isEmpty(name, designation, mobileNumber, username, password))
			{
				message.setText(CommonConstants.MANDATORY_FIELDS);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else if(mobileNumber.getText().length()<10)
			{
				message.setText(CommonConstants.INCORRECT_PHONE_NO);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else if(userType.getSelectionModel().getSelectedIndex()<0)
			{
				message.setText(CommonConstants.USER_SELECT_USERTYPE);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else if(!(quotation.isSelected()||priceEstimation.isSelected()||report.isSelected()||statusReminder.isSelected()||salesOrderManagement.isSelected()||service.isSelected()||enquiry.isSelected()||productDispatch.isSelected()))
			{
				message.setText(CommonConstants.USER_SELECT_MODULE);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else if(!(view.isSelected()||edit.isSelected()||delete.isSelected()))
			{
				message.setText(CommonConstants.USER_SELECT_PERMISSION);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else
			{
			UsersVO usersVO=new UsersVO();
			usersVO.setName(name.getText());
			usersVO.setDesignation(designation.getText());
			usersVO.setMobileNumber(mobileNumber.getText());
			usersVO.setUsername(username.getText());
			usersVO.setPassword(password.getText());
			if(enquiry.isSelected())
			{
				usersVO.setEnquiry("Y");
			}
			else
			{
				usersVO.setEnquiry("N");
			}
			if(service.isSelected())
			{
				usersVO.setService("Y");
			}
			else
			{
				usersVO.setService("N");
			}
			if(productDispatch.isSelected())
			{
				usersVO.setProductDispatch("Y");
			}
			else
			{
				usersVO.setProductDispatch("N");
			}
			if(quotation.isSelected())
			{
				usersVO.setQuotation("Y");
			}
			else
			{
				usersVO.setQuotation("N");
			}
			if(priceEstimation.isSelected())
			{
				usersVO.setPriceEstimation("Y");
			}
			else
			{
				usersVO.setPriceEstimation("N");
			}
			if(salesOrderManagement.isSelected())
			{
				usersVO.setSalesOrderManagement("Y");
			}
			else
			{
				usersVO.setSalesOrderManagement("N");
			}
			if(statusReminder.isSelected())
			{
				usersVO.setStatusReminder("Y");
			}
			else
			{
				usersVO.setStatusReminder("N");
			}
			if(report.isSelected())
			{
				usersVO.setReport("Y");
			}
			else
			{
				usersVO.setReport("N");
			}
			if(view.isSelected())
			{
				usersVO.setView("Y");
			}
			else
			{
				usersVO.setView("N");
			}
			if(edit.isSelected())
			{
				usersVO.setEdit("Y");
			}
			else
			{
				usersVO.setEdit("N");
			}
			if(delete.isSelected())
			{
				usersVO.setDelete("Y");
			}
			else
			{
				usersVO.setDelete("N");
			}
			if(userType.getSelectionModel().getSelectedItem().equals("Admin"))
			{
				usersVO.setUserType("ADMIN");
			}
			else if(userType.getSelectionModel().getSelectedItem().equals("Normal"))
			{
				usersVO.setUserType("NORMAL");
			}	
			usersDAO.saveUser(usersVO);
			message.setText(CommonConstants.USER_ADD_SUCCESS);
			message.getStyleClass().add("success");
			message.getStyleClass().remove("failure");
			message.setVisible(true);
			}
		}
			
		catch (SQLException s)
		{
			if (s.getErrorCode() == CommonConstants.UNIQUE_CONSTRAINT) {
				message.setText(CommonConstants.DUPLICATE_USER);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : saveUsers");
	}
}
