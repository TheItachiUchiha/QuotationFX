package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ServiceRegistryController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceRegistryController.class);

	@FXML
    private Tab modifyTab;

    @FXML
    private Tab newTab;

    @FXML
    private Tab viewTab;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		LOG.info("Enter : initialize");
		try{
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/serviceRegisty-new.fxml"));
			BorderPane newService = (BorderPane) loader.load();
			newTab.setContent(newService);
			FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("../view/serviceRegistry-view.fxml"));
			BorderPane viewService = (BorderPane) loader2.load();
			viewTab.setContent(viewService);
			FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("../view/serviceRegistry-modify.fxml"));
			BorderPane modifyService = (BorderPane) loader3.load();
			modifyTab.setContent(modifyService);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : initialize");
	}

}
