package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import com.kc.dao.ProductsDAO;
import com.kc.model.ProductsVO;

public class EnquiryController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(EnquiryNewController.class);
	@FXML
	private Tab newTab;
	@FXML
	private Tab viewTab;
	@FXML
	private Tab optionTab;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("Enter : initialize");
		try{
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/enquiry-new.fxml"));
			BorderPane newEnquiry = (BorderPane) loader.load();
			newTab.setContent(newEnquiry);
			FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("../view/enquiry-view.fxml"));
			BorderPane viewEnquiry = (BorderPane) loader2.load();
			viewTab.setContent(viewEnquiry);
			FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("../view/enquiry-options.fxml"));
			BorderPane optionsEnquiry = (BorderPane) loader3.load();
			optionTab.setContent(optionsEnquiry);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		LOG.info("Exit : initialize");
	}
}