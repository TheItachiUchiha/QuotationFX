package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class QuotationController implements Initializable {
	
	@FXML
	private Tab newTab;
	@FXML
	private Tab modifyTab;
	@FXML
	private Tab emailTab;
	@FXML
	private Tab viewTab;
	@FXML
	private Tab optionTab;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try{
			FXMLLoader loadernew = new FXMLLoader(this.getClass().getResource("../view/quotation-new.fxml"));
			BorderPane newQuotation = (BorderPane) loadernew.load();
			newTab.setContent(newQuotation);
			FXMLLoader loadermodify = new FXMLLoader(this.getClass().getResource("../view/quotation-modify.fxml"));
			BorderPane modifyQuotation = (BorderPane) loadermodify.load();
			modifyTab.setContent(modifyQuotation);
			FXMLLoader loaderemail = new FXMLLoader(this.getClass().getResource("../view/quotation-email.fxml"));
			BorderPane emailQuotation = (BorderPane) loaderemail.load();
			emailTab.setContent(emailQuotation);
			FXMLLoader loaderview = new FXMLLoader(this.getClass().getResource("../view/quotation-view.fxml"));
			BorderPane viewQuotation = (BorderPane) loaderview.load();
			viewTab.setContent(viewQuotation);
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
