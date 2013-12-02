package com.kc.controller;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.EnquiryDAO;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EnquiryOptionsController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(EnquiryNewController.class);
	
	@FXML
	private GridPane optionsGrid;
	@FXML
	private TextField branchCode;
	@FXML
	private Button clear;
	@FXML
	private Label message;
	
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
				folderPath.setText("");
			}
		});
	    folderPath.setText(enquiryDAO.getDefaultpath());
		 LOG.info("Exit : initialize");
	}
	public void saveDefaultLocation() throws Exception
	{
		try
		{
			if(!folderPath.getText().equals(""))
			{
				enquiryDAO.saveEnquirylocation(folderPath.getText(), simpleDateFormat.format(new Date()));
				message.setText(CommonConstants.DEFAULT_PATH);
				message.getStyleClass().remove("failure");
				message.getStyleClass().add("success");
				message.setVisible(true);

			}
			else
			{
				message.setText(CommonConstants.PATH_ERROR);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
	public void saveBranchCode() throws Exception
	{
		try
		{
			if(!branchCode.getText().equals(""))
			{
				enquiryDAO.saveBranchCode(branchCode.getText(), simpleDateFormat.format(new Date()));
				message.setText(CommonConstants.BRANCH_CODE);
				message.getStyleClass().remove("failure");
				message.getStyleClass().add("success");
				message.setVisible(true);

			}
			else
			{
				message.setText(CommonConstants.BRANCH_CODE_ERROR);
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
