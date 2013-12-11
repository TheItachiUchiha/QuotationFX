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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try{
			FXMLLoader loadernew = new FXMLLoader(this.getClass().getResource("../view/quotation-new.fxml"));
			BorderPane newQuotation = (BorderPane) loadernew.load();
			newTab.setContent(newQuotation);
			
tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				
		        @Override
		        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					try{
					if(t1.equals(newTab))
					{
						FXMLLoader loadernew = new FXMLLoader(this.getClass().getResource("../view/quotation-new.fxml"));
						BorderPane newQuotation = (BorderPane) loadernew.load();
						newTab.setContent(newQuotation);
					}
					else if(t1.equals(viewTab))
					{
						FXMLLoader loaderview = new FXMLLoader(this.getClass().getResource("../view/quotation-view.fxml"));
						BorderPane viewQuotation = (BorderPane) loaderview.load();
						viewTab.setContent(viewQuotation);
					}
					else if(t1.equals(modifyTab))
					{
						FXMLLoader loadermodify = new FXMLLoader(this.getClass().getResource("../view/quotation-modify.fxml"));
						BorderPane modifyQuotation = (BorderPane) loadermodify.load();
						modifyTab.setContent(modifyQuotation);
					}
					else if(t1.equals(emailTab))
					{
						FXMLLoader loaderemail = new FXMLLoader(this.getClass().getResource("../view/quotation-email.fxml"));
						BorderPane emailQuotation = (BorderPane) loaderemail.load();
						emailTab.setContent(emailQuotation);
					}
					else
					{
						FXMLLoader loaderoption = new FXMLLoader(this.getClass().getResource("../view/quotation-option.fxml"));
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
