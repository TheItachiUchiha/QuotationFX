package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
	@FXML
	private TabPane tabPane;
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		
		LOG.info("Enter : initialize");
		try{
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation-new.fxml"));
			final BorderPane newEstimation = (BorderPane) loader.load();
			newTab.setContent(newEstimation);
			
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				
		        @Override
		        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					try{
					if(t1.equals(newTab))
					{
						FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation-new.fxml"));
						BorderPane newEstimation = (BorderPane) loader.load();
						newTab.setContent(newEstimation);
					}
					else if(t1.equals(viewTab))
					{
						FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation-view.fxml"));
						BorderPane viewEstimation = (BorderPane) loader2.load();
						viewTab.setContent(viewEstimation);
					}
					else if(t1.equals(modifyTab))
					{
						FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation-modify.fxml"));
						BorderPane modifyEstimation = (BorderPane) loader3.load();
						modifyTab.setContent(modifyEstimation);
					}
					else
					{
						FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/enquiry-status.fxml"));
						BorderPane status = (BorderPane) loader3.load();
						enquiryStatus.setContent(status);
					}
					
					}
					catch (Exception e) {
						e.printStackTrace();
					}
		        }
		     });
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : initialize");
	}
	
}
