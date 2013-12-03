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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.EnquiryDAO;

public class EnquiryOptionsController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(EnquiryNewController.class);
	
	@FXML
	private GridPane optionsGrid;
	@FXML
	private TextField branchCode;
	@FXML
	private TextField defaultCode;
	@FXML
	private Button clear;
	@FXML
	private Label messageLocation;
	@FXML
	private Label messageBranchCode;
	@FXML
	private Label messageDefaultCode;
	
	private TextField folderPath;
	private EnquiryDAO enquiryDAO;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	
	public EnquiryOptionsController() {
		enquiryDAO=new EnquiryDAO();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		LOG.info("Enter : initialize");
		Button browse = new Button();
	    browse.setText("Browse Directory");
	    folderPath = new TextField();
	    folderPath.setPrefWidth(250);
	    folderPath.setText("");
	    folderPath.setEditable(false);
        final HBox hBox =new HBox(5);
        hBox.getChildren().addAll(folderPath,browse);
        optionsGrid.add(hBox,1,1);
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
				messageLocation.setText("");
			}
		});
	    folderPath.setText(enquiryDAO.getDefaultpath());
		 LOG.info("Exit : initialize");
	}
	public void saveDefaultLocation() throws Exception
	{
		try
		{
			
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
	public void saveConfigurations() throws Exception
	{
		try
		{
			if(!folderPath.getText().equals("")||!branchCode.getText().equals("")||!defaultCode.getText().equals(""))
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
