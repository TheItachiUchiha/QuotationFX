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

public class QuotationEmailController implements Initializable  {

	
	@FXML
	private Tab sendQuotTab;
	@FXML
	private Tab resendQuotTab;
	@FXML
	private  TabPane tabPane;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
		FXMLLoader loadernew = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-email-send.fxml"));
		BorderPane send = (BorderPane) loadernew.load();
		sendQuotTab.setContent(send);
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable,
					Tab oldValue, Tab newValue) {
				try{
					if(newValue.equals(sendQuotTab))
					{
						FXMLLoader loadernew = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-email-send.fxml"));
						BorderPane send = (BorderPane) loadernew.load();
						sendQuotTab.setContent(send);
					}
					else if(newValue.equals(resendQuotTab))
					{
						FXMLLoader loadernew = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-email-resend.fxml"));
						BorderPane resend = (BorderPane) loadernew.load();
						resendQuotTab.setContent(resend);
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
		
	}
	
}
