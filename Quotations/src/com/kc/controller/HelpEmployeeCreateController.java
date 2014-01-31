package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;
import com.kc.model.EmployeeVO;
import com.kc.util.Validation;

@SuppressWarnings("unused")
public class HelpEmployeeCreateController implements Initializable {
	
		private static final Logger LOG = LogManager.getLogger(HelpEmployeeCreateController.class);
	
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
	    
	    public HelpEmployeeCreateController() {
			validation = new Validation();
			helpDAO = new HelpDAO();
		}
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {

			validation.allowAsPhoneNumber(mobileNo);
			
		}
		public void saveEmployee()
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
					EmployeeVO employeeVO = new EmployeeVO();
					employeeVO.setName(name.getText());
					employeeVO.setDesignation(designation.getText());
					employeeVO.setAddress(address.getText());
					employeeVO.setServiceRating(serviceRating.getSelectionModel().getSelectedItem());
					employeeVO.setMobileNo(mobileNo.getText());
					helpDAO.saveEmployee(employeeVO);
					message.setText(CommonConstants.EMPLOYEE_ADD_SUCCESS);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
				}
			}
			catch (Exception e) {
				e.printStackTrace();	
			}
		}

}
