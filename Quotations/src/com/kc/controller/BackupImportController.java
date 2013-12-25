package com.kc.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BackupImportController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(BackupImportController.class);
	
	@FXML
    private Button browse;

    @FXML
    private TextField importDB;

    @FXML
    private Label messageImport;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		
		browse.setOnAction(new EventHandler<ActionEvent>() {
		     @Override
		     public void handle(ActionEvent event) {
		     	   Stage stage=new Stage();
		           FileChooser fileChooser = new FileChooser();
		           File tempFile = fileChooser.showOpenDialog(stage);
		           if(tempFile!=null){
		                   importDB.setText(tempFile.getPath());
		           }
		       }
		});
	}

}
