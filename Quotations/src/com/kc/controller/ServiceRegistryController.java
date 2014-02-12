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

public class ServiceRegistryController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceRegistryController.class);

		@FXML
	    private Tab complaintTab;

	    @FXML
	    private Tab modifyTab;

	    @FXML
	    private Tab newTab;

	    @FXML
	    private TabPane tabPane;

	    @FXML
	    private Tab viewTab;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		LOG.info("Enter : initialize");
		try{
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-registerComplaint.fxml"));
			BorderPane newService = (BorderPane) loader.load();
			complaintTab.setContent(newService);
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

				@Override
				public void changed(ObservableValue<? extends Tab> observable,
						Tab oldValue, Tab newValue) {
					
					try{
						if(newValue.equals(complaintTab))
						{
							FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-registerComplaint.fxml"));
							BorderPane newService = (BorderPane) loader.load();
							complaintTab.setContent(newService);
						}
						else if(newValue.equals(newTab))
						{
							FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-serviceRegister.fxml"));
							BorderPane modifyService = (BorderPane) loader3.load();
							newTab.setContent(modifyService);
						}
						else if(newValue.equals(modifyTab))
						{
							FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-serviceModify.fxml"));
							BorderPane modifyService = (BorderPane) loader3.load();
							modifyTab.setContent(modifyService);
						}
						else if(newValue.equals(viewTab))
						{
							FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-serviceView.fxml"));
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
