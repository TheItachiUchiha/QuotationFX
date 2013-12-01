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
	private Button clear;
	@FXML
	private Label messageOption;
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
	    folderPath.setDisable(true);
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
		 LOG.info("Exit : initialize");
	}
	public void saveDefaultLocation() throws Exception
	{
		try
		{
			if(!folderPath.getText().equals(""))
			{
				enquiryDAO.saveEnquirylocation(folderPath.getText(), simpleDateFormat.format(new Date()));
				messageOption.setText(CommonConstants.DEFAULT_PATH);
				messageOption.getStyleClass().remove("failure");
				messageOption.getStyleClass().add("success");
				messageOption.setVisible(true);

			}
			else
			{
				messageOption.setText(CommonConstants.PATH_ERROR);
				messageOption.getStyleClass().remove("success");
				messageOption.getStyleClass().add("failure");
				messageOption.setVisible(true);
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
}
