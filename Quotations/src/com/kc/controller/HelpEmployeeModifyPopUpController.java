package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;
import com.kc.model.EmployeeVO;
import com.kc.util.Validation;

public class HelpEmployeeModifyPopUpController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(HelpEmployeeModifyPopUpController.class);
	
	 	@FXML
	    private TextArea address;

	    @FXML
	    private TextField designation;

	    @FXML
	    private Label message;

	    @FXML
	    private TextField mobileNo;

	    @FXML
	    private TextField name;

	    @FXML
	    private ComboBox<String> serviceRating;
	
	 	Validation validation;
	    HelpDAO helpDAO;
	    EmployeeVO employeeVO;
	    
	    public HelpEmployeeModifyPopUpController() {
			validation = new Validation();
			helpDAO = new HelpDAO();
		}

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		
		validation.allowAsPhoneNumber(mobileNo);
		
	}
	public void updateEmployee( )
	{

		try
		{
			if(validation.isEmpty(name,mobileNo))
			{
				message.setText(CommonConstants.MANDATORY_FIELDS);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else if(serviceRating.getSelectionModel().getSelectedIndex()==-1)
			{
				message.setText(CommonConstants.RATING_MANDATORY);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else
			{
				employeeVO.setName(name.getText());
				employeeVO.setDesignation(designation.getText());
				employeeVO.setAddress(address.getText());
				employeeVO.setServiceRating(serviceRating.getSelectionModel().getSelectedItem());
				employeeVO.setMobileNo(mobileNo.getText());
				helpDAO.updateEmployee(employeeVO);
				message.setText(CommonConstants.EMPLOYEE_UPDATE_SUCCESS);
				message.getStyleClass().remove("failure");
				message.getStyleClass().add("success");
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
	
	}
	public void fillTextfieldValues(EmployeeVO employeeVO)
	{
		
		this.employeeVO = employeeVO;
		name.setText(employeeVO.getName());
		designation.setText(employeeVO.getDesignation());
		address.setText(employeeVO.getAddress());
		mobileNo.setText(employeeVO.getMobileNo());
		if(employeeVO.getServiceRating().equalsIgnoreCase("Excelent"))
		{
			serviceRating.getSelectionModel().select(0);
		}
		else if(employeeVO.getServiceRating().equalsIgnoreCase("Good"))
		{
			serviceRating.getSelectionModel().select(1);
		}
		else if(employeeVO.getServiceRating().equalsIgnoreCase("Average"))
		{
			serviceRating.getSelectionModel().select(2);
		}
		else if(employeeVO.getServiceRating().equalsIgnoreCase("Bad"))
		{
			serviceRating.getSelectionModel().select(3);
		}
	}
}
