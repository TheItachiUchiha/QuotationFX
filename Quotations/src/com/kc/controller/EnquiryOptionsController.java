package com.kc.controller;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.layout.HBox;
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
	private Label messageLocation;
	@FXML
	private Label messageUsername;
	@FXML
	private Label messagePassword;
	@FXML
	private Label messageBranchCode;
	@FXML
	private Label messageDefaultCode;
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
				messageBranchCode.setText("");
				messageDefaultCode.setText("");
				username.setText("");
				password.setText("");
				messagePassword.setText("");
				messageUsername.setText("");
				messageLocation.setText("");
			}
		});
	    folderPath.setText(enquiryDAO.getDefaultpath());
		 LOG.info("Exit : initialize");
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
					messageLocation.setText(CommonConstants.DEFAULT_PATH);
					messageLocation.getStyleClass().remove("failure");
					messageLocation.getStyleClass().add("success");
					messageLocation.setVisible(true);
	
				}
				/*else
				{
					messageLocation.setText(CommonConstants.PATH_ERROR);
					messageLocation.getStyleClass().remove("success");
					messageLocation.getStyleClass().add("failure");
					messageLocation.setVisible(true);
				}*/
				if(!branchCode.getText().equals(""))
				{
					enquiryDAO.saveBranchCode(branchCode.getText(), simpleDateFormat.format(new Date()));
					messageBranchCode.setText(CommonConstants.BRANCH_CODE);
					messageBranchCode.getStyleClass().remove("failure");
					messageBranchCode.getStyleClass().add("success");
					messageBranchCode.setVisible(true);
	
				}
				/*else
				{
					messageBranchCode.setText(CommonConstants.BRANCH_CODE_ERROR);
					messageBranchCode.getStyleClass().remove("success");
					messageBranchCode.getStyleClass().add("failure");
					messageBranchCode.setVisible(true);
				}*/
				if(!defaultCode.getText().equals(""))
				{
					enquiryDAO.saveDefaultCode(defaultCode.getText(), simpleDateFormat.format(new Date()));
					messageDefaultCode.setText(CommonConstants.DEFAULT_CODE);
					messageDefaultCode.getStyleClass().remove("failure");
					messageDefaultCode.getStyleClass().add("success");
					messageDefaultCode.setVisible(true);
	
				}
				if(!username.getText().equals(""))
				{
					enquiryDAO.saveEmailUsername(username.getText(), simpleDateFormat.format(new Date()));
					messageUsername.setText(CommonConstants.EMAIL_USERNAME);
					messageUsername.getStyleClass().remove("failure");
					messageUsername.getStyleClass().add("success");
					messageUsername.setVisible(true);
	
				}
				if(!password.getText().equals(""))
				{
					enquiryDAO.saveEmailPassword(encription.encrypt(password.getText()), simpleDateFormat.format(new Date()));
					messagePassword.setText(CommonConstants.EMAIL_PASSWORD);
					messagePassword.getStyleClass().remove("failure");
					messagePassword.getStyleClass().add("success");
					messagePassword.setVisible(true);
	
				}
			}
			else
			{
				messageBranchCode.setText(CommonConstants.OPTION_ERROR);
				messageBranchCode.getStyleClass().remove("success");
				messageBranchCode.getStyleClass().add("failure");
				messageBranchCode.setVisible(true);
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
}
