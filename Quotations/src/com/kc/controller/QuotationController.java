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
	@FXML
	private TabPane tabPane;
	
	HelpDAO helpDAO;
	private Map<String, String> theme = new HashMap<String, String>();
	
	public QuotationController() {
		helpDAO = new HelpDAO();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try{
			
			theme = helpDAO.getBackground();
			//newTab.getStyleClass().remove(1);
			if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-pink"))
			{
				newTab.getStyleClass().add("pink-tab");
				viewTab.getStyleClass().add("pink-tab");
				modifyTab.getStyleClass().add("pink-tab");
				emailTab.getStyleClass().add("pink-tab");
				optionTab.getStyleClass().add("pink-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-blue"))
			{
				newTab.getStyleClass().add("blue-tab");
				viewTab.getStyleClass().add("blue-tab");
				modifyTab.getStyleClass().add("blue-tab");
				emailTab.getStyleClass().add("blue-tab");
				optionTab.getStyleClass().add("blue-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-cyan"))
			{
				newTab.getStyleClass().add("cyan-tab");
				viewTab.getStyleClass().add("cyan-tab");
				modifyTab.getStyleClass().add("cyan-tab");
				emailTab.getStyleClass().add("cyan-tab");
				optionTab.getStyleClass().add("cyan-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-green"))
			{
				newTab.getStyleClass().add("green-tab");
				viewTab.getStyleClass().add("green-tab");
				modifyTab.getStyleClass().add("green-tab");
				emailTab.getStyleClass().add("green-tab");
				optionTab.getStyleClass().add("green-tab");
			}
			FXMLLoader loadernew = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-new.fxml"));
			BorderPane newQuotation = (BorderPane) loadernew.load();
			newTab.setContent(newQuotation);
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				
		        @Override
		        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					try{
					if(t1.equals(newTab))
					{
						FXMLLoader loadernew = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-new.fxml"));
						BorderPane newQuotation = (BorderPane) loadernew.load();
						newTab.setContent(newQuotation);
					}
					else if(t1.equals(viewTab))
					{
						FXMLLoader loaderview = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-view.fxml"));
						BorderPane viewQuotation = (BorderPane) loaderview.load();
						viewTab.setContent(viewQuotation);
					}
					else if(t1.equals(modifyTab))
					{
						FXMLLoader loadermodify = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-modify.fxml"));
						BorderPane modifyQuotation = (BorderPane) loadermodify.load();
						modifyTab.setContent(modifyQuotation);
					}
					else if(t1.equals(emailTab))
					{
						FXMLLoader loaderemail = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-email.fxml"));
						BorderPane emailQuotation = (BorderPane) loaderemail.load();
						emailTab.setContent(emailQuotation);
					}
					else
					{
						FXMLLoader loaderoption = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation-option.fxml"));
						BorderPane optionQuotation = (BorderPane) loaderoption.load();
						optionTab.setContent(optionQuotation);
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
		
	}

}
