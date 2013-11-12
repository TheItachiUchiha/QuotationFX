package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.kc.constant.CommonConstants;
import com.kc.dao.UsersDAO;
import com.kc.model.UsersVO;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class UsersModifyController implements Initializable{
	private static final Logger LOG = LogManager.getLogger(UsersModifyController.class);
	private ObservableList<UsersVO> usersList;
	private UsersDAO usersDAO;
	private UsersVO usersVO;
	Validation validation;
	public UsersModifyController(){
		usersDAO = new UsersDAO();
		usersVO = new UsersVO();
		validation = new Validation();
	}
 	
	@FXML
	private AutoCompleteTextField <UsersVO> userNameAutoFill;
	@FXML
	private HBox modifyHbox; 
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
    private CheckBox report;
    @FXML
    private CheckBox salesOrderManagement;
    @FXML
    private CheckBox statusReminder;
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
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		LOG.info("Enter : initialize");
		
		try{
			validation.allowAsPhoneNumber(mobileNumber);
			userType.valueProperty().addListener(new ChangeListener<String>() {
	            
				@Override public void changed(ObservableValue ov, String t, String t1) {                
					if(t1.equalsIgnoreCase("ADMIN"))
					{
						quotation.setSelected(true);
						quotation.setDisable(true);
						priceEstimation.setSelected(true);
						priceEstimation.setDisable(true);
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
			usersList = usersDAO.getUsers();
			
			userNameAutoFill.setItems(usersList);
			
			
			userNameAutoFill.setPromptText("Name Of User");
			
			
			userNameAutoFill.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
				
					for(UsersVO usersVO: usersList)
					{
						if(usersVO.getName().equals(userNameAutoFill.getText()))
						{
							modifyHbox.setVisible(true);
							userNameAutoFill.setDisable(true);
							fillTextFieldValues(usersVO);
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
	public void fillTextFieldValues(UsersVO usersVO)
	{
		LOG.info("Enter : fillTextFieldValues");
		UsersModifyController.this.usersVO.setId(usersVO.getId());
		name.setText(usersVO.getName());
		designation.setText(usersVO.getDesignation());
		mobileNumber.setText(usersVO.getMobileNumber());
		username.setText(usersVO.getUsername());
		password.setText(usersVO.getPassword());

		if(usersVO.getQuotation().equalsIgnoreCase("Y"))
		{
			quotation.setSelected(true);
		}

		if(usersVO.getPriceEstimation().equalsIgnoreCase("Y"))
		{
			priceEstimation.setSelected(true);
		}
		if(usersVO.getReport().equalsIgnoreCase("Y"))
		{
			report.setSelected(true);
		}

		if(usersVO.getSalesOrderManagement().equalsIgnoreCase("Y"))
		{
			salesOrderManagement.setSelected(true);
		}
		if(usersVO.getStatusReminder().equalsIgnoreCase("Y"))
		{
			statusReminder.setSelected(true);
		}

		if(usersVO.getView().equalsIgnoreCase("Y"))
		{
			view.setSelected(true);
		}
		if(usersVO.getEdit().equalsIgnoreCase("Y"))
		{
			edit.setSelected(true);
		}

		if(usersVO.getDelete().equalsIgnoreCase("Y"))
		{
			delete.setSelected(true);
		}
		if(usersVO.getUserType().equalsIgnoreCase("ADMIN"))
		{
			userType.getSelectionModel().selectFirst();
		}
		else
		{
			userType.getSelectionModel().selectLast();
		}
		LOG.info("Exit : fillTextFieldValues");
	}
	public void modifyUser()
	{
		LOG.info("Enter : modifyUser");
		try
		{
			if(validation.isEmpty(name, designation, username, password, mobileNumber))
			{
				message.setText(CommonConstants.MANDATORY_FIELDS);
				message.setVisible(true);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
			}
			else if(userType.getSelectionModel().getSelectedIndex()<0)
			{
				message.setText(CommonConstants.USER_SELECT_USERTYPE);
				message.setVisible(true);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
			}
			else if(!(quotation.isSelected()||priceEstimation.isSelected()||report.isSelected()||statusReminder.isSelected()||salesOrderManagement.isSelected()))
			{
				message.setText(CommonConstants.USER_SELECT_MODULE);
				message.setVisible(true);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
			}
			else if(!(view.isSelected()||edit.isSelected()||delete.isSelected()))
			{
				message.setText(CommonConstants.USER_SELECT_PERMISSION);
				message.setVisible(true);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
			}
			else
			{
			UsersVO usersVO = new UsersVO();
			
			usersVO.setName(name.getText());
			usersVO.setDesignation(designation.getText());
			usersVO.setMobileNumber(mobileNumber.getText());
			usersVO.setUsername(username.getText());
			usersVO.setPassword(password.getText());
			
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
			if(report.isSelected())
			{
				usersVO.setReport("Y");
			}
			else
			{
				usersVO.setReport("N");
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
			if(userType.getSelectionModel().getSelectedItem().equalsIgnoreCase("Admin"))
			{
				usersVO.setUserType("ADMIN");
			}
			else
			{
				usersVO.setUserType("NORMAL");
			}
			usersVO.setId(this.usersVO.getId());
			usersDAO.updateUser(usersVO);
			message.setText(CommonConstants.USER_MODIFY_SUCCESS);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
			message.setVisible(true);
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : modifyUser");
	}
}
