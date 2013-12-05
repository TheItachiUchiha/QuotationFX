package com.kc.controller;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.EnquiryDAO;
import com.kc.util.Encryption;

public class EnquiryOptionsController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(EnquiryNewController.class);
	
	@FXML
	private GridPane optionsGrid;
	@FXML
	private TextField branchCode;
	@FXML
	private TextField defaultCode;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button clear;
	@FXML
	private Label message;
	/*@FXML
	private Label messageUsername;
	@FXML
	private Label messagePassword;
	@FXML
	private Label messageBranchCode;
	@FXML
	private Label messageDefaultCode;*/
	@FXML
	private TextField folderPath;
	@FXML
	private Button browse;
	private EnquiryDAO enquiryDAO;
	private Encryption encription;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	
	public EnquiryOptionsController() {
		enquiryDAO=new EnquiryDAO();
		encription = new Encryption();
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		LOG.info("Enter : initialize");
		
		map.put(commonConstants.KEY_ENQUIRY_PATH,"");
		map.put(commonConstants.KEY_ENQUIRY_BRANCH_CODE,"");
		map.put(commonConstants.KEY_ENQUIRY_DEFAULT_CODE,"");
		map.put(commonConstants.KEY_ENQUIRY_USERNAME,"");
		
	    browse.setOnAction(new EventHandler<ActionEvent>() {
	     @Override
	     public void handle(ActionEvent event) {
	        DirectoryChooser directoryChooser = new DirectoryChooser();
	        File tempFile = directoryChooser.showDialog(null);
	        if(tempFile!=null){
	        	folderPath.setText(tempFile.getPath());
	        }
	     }
	});
	    clear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent paramT) {
				//folderPath.setText("");
				defaultCode.setText("");
				branchCode.setText("");
				message.setText("");
				username.setText("");
				password.setText("");
			}
		});
	    folderPath.setText(enquiryDAO.getDefault("enquiry_path"));
	    branchCode.setText(enquiryDAO.getDefault("branch_code"));
	    defaultCode.setText(enquiryDAO.getDefault("default_code"));
	    
		 LOG.info("Exit : initialize");
	}
	Map<String, String> getEnquiryOptionValues()
	{
		for()
		
	}
	public void saveConfigurations() throws Exception
	{
		try
		{
			if(!folderPath.getText().equals("")||!branchCode.getText().equals("")||!defaultCode.getText().equals("")||!username.getText().equals("")||!password.getText().equals(""))
			{
				if(!folderPath.getText().equals(""))
				{
					enquiryDAO.saveEnquirylocation(folderPath.getText(), simpleDateFormat.format(new Date()));
					message.setText(CommonConstants.NEW_CONFIGURATION);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
	
				}
				if(!branchCode.getText().equals(""))
				{
					enquiryDAO.saveBranchCode(branchCode.getText(), simpleDateFormat.format(new Date()));
					message.setText(CommonConstants.NEW_CONFIGURATION);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
	
				}
				if(!defaultCode.getText().equals(""))
				{
					enquiryDAO.saveDefaultCode(defaultCode.getText(), simpleDateFormat.format(new Date()));
					message.setText(CommonConstants.NEW_CONFIGURATION);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
	
				}
				if(!username.getText().equals(""))
				{
					enquiryDAO.saveEmailUsername(username.getText(), simpleDateFormat.format(new Date()));
					message.setText(CommonConstants.NEW_CONFIGURATION);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
	
				}
				if(!password.getText().equals(""))
				{
					enquiryDAO.saveEmailPassword(encription.encrypt(password.getText()), simpleDateFormat.format(new Date()));
					message.setText(CommonConstants.NEW_CONFIGURATION);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
	
				}
			}
			else
			{
				message.setText(CommonConstants.OPTION_ERROR);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
}
