package com.kc.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;

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
	
	HelpDAO helpDAO;
	private Map<String, String> theme = new HashMap<String, String>();
	
	public QuotationEmailController() {
		helpDAO = new HelpDAO();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
			theme = helpDAO.getBackground();
			//newTab.getStyleClass().remove(1);
			if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-pink"))
			{
				sendQuotTab.getStyleClass().add("pink-tab");
				resendQuotTab.getStyleClass().add("pink-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-blue"))
			{
				sendQuotTab.getStyleClass().add("blue-tab");
				resendQuotTab.getStyleClass().add("blue-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-cyan"))
			{
				sendQuotTab.getStyleClass().add("cyan-tab");
				resendQuotTab.getStyleClass().add("cyan-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-green"))
			{
				sendQuotTab.getStyleClass().add("green-tab");
				resendQuotTab.getStyleClass().add("green-tab");
			}
			AdminHomeController.currentPage.setText("SEND QUOTATION");
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
						AdminHomeController.currentPage.setText("SEND QUOTATION");
						FXMLLoader loadernew = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-email-send.fxml"));
						BorderPane send = (BorderPane) loadernew.load();
						sendQuotTab.setContent(send);
					}
					else if(newValue.equals(resendQuotTab))
					{
						AdminHomeController.currentPage.setText("RESEND QUOTATION");
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
