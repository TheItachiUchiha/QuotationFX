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

public class PriceEstimationController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(PriceEstimationController.class);
	@FXML
	private Tab newTab;
	@FXML
	private Tab modifyTab;
	@FXML
	private Tab viewTab;
	@FXML
	private Tab enquiryStatus;
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		
		LOG.info("Enter : initialize");
		try{
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/priceEstimation-new.fxml"));
			BorderPane newEstimation = (BorderPane) loader.load();
			newTab.setContent(newEstimation);
			FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("../view/priceEstimation-view.fxml"));
			BorderPane viewEstimation = (BorderPane) loader2.load();
			viewTab.setContent(viewEstimation);
			FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("../view/priceEstimation-modify.fxml"));
			BorderPane modifyEstimation = (BorderPane) loader3.load();
			modifyTab.setContent(modifyEstimation);
			FXMLLoader loader4 = new FXMLLoader(this.getClass().getResource("../view/enquiry-status.fxml"));
			BorderPane status = (BorderPane) loader4.load();
			enquiryStatus.setContent(status);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : initialize");
	}
	
}
