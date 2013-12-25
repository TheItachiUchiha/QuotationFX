package com.kc.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.util.ExportDB;

public class BackupExportController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(BackupExportController.class);
	
	ExportDB export;
	@FXML
    private Button browse;

    @FXML
    private TextField exportDB;

    @FXML
    private Label messageExport;
    
    public BackupExportController() {
    	export = new ExportDB();
	}

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		
		browse.setOnAction(new EventHandler<ActionEvent>() {
		     @Override
		     public void handle(ActionEvent event) {
		        DirectoryChooser directoryChooser = new DirectoryChooser();
		        File tempFile = directoryChooser.showDialog(null);
		        if(tempFile!=null){
		        	exportDB.setText(tempFile.getPath());
		        }
		     }
		});
		
	}
	public void exportToDirectory()
	{

    	try
    	  {
    	       byte[] data = export.getData(CommonConstants.DB_HOST, CommonConstants.DB_PORT, CommonConstants.DB_USER, CommonConstants.DB_PASSWORD, CommonConstants.DB_NAME).getBytes();
    	       File filedst = new File(exportDB.getText()+"\\Database"+".sql");
    	       FileOutputStream dest = new FileOutputStream(filedst);
    	       
	    	    // if file doesnt exists, then create it
	   			if (!filedst.exists()) {
	   				filedst.createNewFile();
	   			}
	    
	   			dest.write(data);
	   			dest.flush();
	   			dest.close();

    	       	messageExport.setText(CommonConstants.DB_EXPORT_SUCCESS);
				messageExport.getStyleClass().remove("failure");
				messageExport.getStyleClass().add("success");
				messageExport.setVisible(true);
    	   }catch (Exception ex){
    	    ex.printStackTrace();
    	  }
    
	}
}
