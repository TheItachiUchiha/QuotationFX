package com.kc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.util.ExportDB;
import com.kc.util.Validation;

public class BackupImportController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(BackupImportController.class);
	Validation validation;
	ExportDB export;
	@FXML
    private Button browse;

    @FXML
    private TextField importDB;

    @FXML
    private Label messageImport;
    
    public BackupImportController() {
    	export = new ExportDB();
    	validation = new Validation();
	}

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		
		AdminHomeController.currentPage.setText("IMPORT");
		
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
	
	public void importToDatabase()
	{

    	try
    	  {
    		if(validation.isEmpty(importDB))
    		{
    			Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.BROWSE_PATH);
    		}
    		else
    		{
    	       File filedst = new File(importDB.getText());
    	       export.setDataToDatabase(CommonConstants.DB_HOST, CommonConstants.DB_PORT, CommonConstants.DB_USER, CommonConstants.DB_PASSWORD, "abc", filedst.getAbsolutePath());

	   			messageImport.setText(CommonConstants.DB_EXPORT_SUCCESS);
	   			messageImport.getStyleClass().remove("failure");
	   			messageImport.getStyleClass().add("success");
	   			messageImport.setVisible(true);
    		}
    	   }catch (Exception ex){
    	    ex.printStackTrace();
    	  }
	}

}
