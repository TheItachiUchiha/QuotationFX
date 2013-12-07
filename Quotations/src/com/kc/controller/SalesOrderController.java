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

public class SalesOrderController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(SalesOrderController.class);
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
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/salesOrder-new.fxml"));
			BorderPane newSalesOrder = (BorderPane) loader.load();
			newTab.setContent(newSalesOrder);
			FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("../view/salesOrder-modify.fxml"));
			BorderPane modifySalesOrder = (BorderPane) loader2.load();
			modifyTab.setContent(modifySalesOrder);
			FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("../view/salesOrder-view.fxml"));
			BorderPane viewSalesOrder = (BorderPane) loader3.load();
			viewTab.setContent(viewSalesOrder);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : initialize");
	}
}
