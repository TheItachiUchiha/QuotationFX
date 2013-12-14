package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.StatusReminderDAO;
import com.kc.util.Encryption;
import com.kc.util.Validation;

public class ReminderOptionController implements Initializable {
		private static final Logger LOG = LogManager.getLogger(ReminderOptionController.class);
		Encryption encryption;
		StatusReminderDAO statusReminderDAO;
		Validation validate;
		public ReminderOptionController() {
	
			encryption = new Encryption("");
			validate = new Validation();
			statusReminderDAO = new StatusReminderDAO();
		}

		@FXML
	    private TextField emailid;

	    @FXML
	    private Label message;

	    @FXML
	    private PasswordField password;

	    @FXML
	    private TextField username;
	    
	    private Map<String, String> defaultValues = new HashMap<String, String>();
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		defaultValues = statusReminderDAO.getReminderMailDetails();
	    username.setText(defaultValues.get(CommonConstants.KEY_REMINDER_USERNAME));
	    emailid.setText(defaultValues.get(CommonConstants.KEY_REMINDER_EMAIL));
	    password.setText(encryption.decrypt(defaultValues.get(CommonConstants.KEY_REMINDER_PASSWORD)));
		
	}
	 
	 public void clearFields()
	 {
		 emailid.setText("");
		 password.setText("");
		 username.setText("");
		 message.setText("");
	 }

	 public void saveEmail()
	 {

			try
			{
				if(validate.isEmpty(emailid,username,password))
				{
					message.setText(CommonConstants.MANDATORY_FIELDS);
					message.getStyleClass().remove("success");
					message.getStyleClass().add("failure");
					message.setVisible(true);
				}
				else if(!validate.isEmail(emailid.getText()))
				{
					message.setText(CommonConstants.INCORRECT_EMAIL);
					message.getStyleClass().remove("success");
					message.getStyleClass().add("failure");
					message.setVisible(true);
				}
				else
				{
					defaultValues.put(CommonConstants.KEY_REMINDER_EMAIL, emailid.getText());
					defaultValues.put(CommonConstants.KEY_REMINDER_USERNAME, username.getText());
					defaultValues.put(CommonConstants.KEY_REMINDER_PASSWORD, encryption.encrypt(password.getText()));
					statusReminderDAO.saveConfiguration(defaultValues, simpleDateFormat.format(new Date()));
					message.setText(CommonConstants.CONF_SAVED);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
				}
			}
			catch (Exception e) {
				message.setText(CommonConstants.OPTION_ERROR);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
				LOG.error(e.getMessage());
			}
		
	 }
}
