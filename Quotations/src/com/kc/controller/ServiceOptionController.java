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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ServiceDAO;
import com.kc.util.Encryption;
import com.kc.util.Validation;

public class ServiceOptionController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(ServiceOptionController.class);
	
		@FXML
	    private TextArea defaultMessage;

	    @FXML
	    private TextField emailId;

	    @FXML
	    private Label message;

	    @FXML
	    private GridPane optionsGrid;

	    @FXML
	    private PasswordField password;

	    @FXML
	    private TextField username;

	 	private Encryption encryption;
		private Validation validation;
		private ServiceDAO serviceDAO;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
		private Map<String, String> defaultValues = new HashMap<String, String>();

		public ServiceOptionController() {
			serviceDAO = new ServiceDAO();
			validation = new Validation();
			encryption = new Encryption("");
		}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
		
		 	defaultValues = serviceDAO.getServiceOptionDefaultValues();
		    defaultMessage.setText(defaultValues.get(CommonConstants.KEY_SERVICE_MESSAGE));
		    emailId.setText(defaultValues.get(CommonConstants.KEY_SERVICE_EMAIL));
		    username.setText(defaultValues.get(CommonConstants.KEY_SERVICE_USERNAME));
		    password.setText(encryption.decrypt(defaultValues.get(CommonConstants.KEY_SERVICE_PASSWORD)));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	 public void saveCofiguration()
	 {
			try
			{
				if(validation.isEmpty(emailId,username,password))
				{
					message.setText(CommonConstants.MANDATORY_FIELDS);
					message.getStyleClass().remove("success");
					message.getStyleClass().add("failure");
					message.setVisible(true);
				}
				else
				{
					defaultValues.put(CommonConstants.KEY_SERVICE_EMAIL, emailId.getText());
					defaultValues.put(CommonConstants.KEY_SERVICE_MESSAGE, defaultMessage.getText());
					defaultValues.put(CommonConstants.KEY_SERVICE_USERNAME, username.getText());
					defaultValues.put(CommonConstants.KEY_SERVICE_PASSWORD, encryption.encrypt(password.getText()));
					serviceDAO.saveConfiguration(defaultValues, simpleDateFormat.format(new Date()));
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
