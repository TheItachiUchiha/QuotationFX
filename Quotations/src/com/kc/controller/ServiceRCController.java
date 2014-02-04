package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class ServiceRCController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceRegistryController.class);

	 	@FXML
	    private Tab newTab;

	    @FXML
	    private Tab optionTab;

	    @FXML
	    private TabPane tabPane;

	    @FXML
	    private Tab viewTab;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		LOG.info("Enter : initialize");
		try{
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-newComplaint.fxml"));
			BorderPane newService = (BorderPane) loader.load();
			newTab.setContent(newService);
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

				@Override
				public void changed(ObservableValue<? extends Tab> observable,
						Tab oldValue, Tab newValue) {
					
					try{
						if(newValue.equals(newTab))
						{
							FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-newComplaint.fxml"));
							BorderPane newService = (BorderPane) loader.load();
							newTab.setContent(newService);
						}
						else if(newValue.equals(optionTab))
						{
							FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-option.fxml"));
							BorderPane modifyService = (BorderPane) loader3.load();
							optionTab.setContent(modifyService);
						}
						else if(newValue.equals(viewTab))
						{
							FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-viewComplaint.fxml"));
							BorderPane viewService = (BorderPane) loader2.load();
							viewTab.setContent(viewService);
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
