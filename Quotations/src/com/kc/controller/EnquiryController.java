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

public class EnquiryController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(EnquiryNewController.class);
	@FXML
	private Tab newTab;
	@FXML
	private Tab viewTab;
	@FXML
	private Tab optionTab;
	@FXML
	private TabPane tabPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("Enter : initialize");
		try{			
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/enquiry-new.fxml"));
			BorderPane newEnquiry = (BorderPane) loader.load();
			newTab.setContent(newEnquiry);
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			
		        @Override
		        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					try{
					if(t1.equals(newTab))
					{
						FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/enquiry-new.fxml"));
						BorderPane newEnquiry = (BorderPane) loader.load();
						newTab.setContent(newEnquiry);
					}
					else if(t1.equals(viewTab))
					{
						FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/enquiry-view.fxml"));
						BorderPane viewEnquiry = (BorderPane) loader2.load();
						viewTab.setContent(viewEnquiry);
					}
					else
					{
						FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/enquiry-options.fxml"));
						BorderPane optionsEnquiry = (BorderPane) loader3.load();
						optionTab.setContent(optionsEnquiry);
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				}				
			});
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		LOG.info("Exit : initialize");
	}
}