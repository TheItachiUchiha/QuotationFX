package com.kc.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;

public class PriceEstimationController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(PriceEstimationController.class);
	@FXML
	private Tab newTab;
	@FXML
	private Tab modifyTab;
	@FXML
	private Tab viewTab;
	@FXML
	private Tab enquiryStatus;
	@FXML
	private TabPane tabPane;
	
	HelpDAO helpDAO;
	private Map<String, String> theme = new HashMap<String, String>();
	
	public PriceEstimationController() {
		helpDAO = new HelpDAO();
	}
	
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		
		LOG.info("Enter : initialize");
		try{
			
			theme = helpDAO.getBackground();
			//newTab.getStyleClass().remove(1);
			if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-pink"))
			{
				newTab.getStyleClass().add("pink-tab");
				viewTab.getStyleClass().add("pink-tab");
				modifyTab.getStyleClass().add("pink-tab");
				enquiryStatus.getStyleClass().add("pink-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-blue"))
			{
				newTab.getStyleClass().add("blue-tab");
				viewTab.getStyleClass().add("blue-tab");
				modifyTab.getStyleClass().add("blue-tab");
				enquiryStatus.getStyleClass().add("blue-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-cyan"))
			{
				newTab.getStyleClass().add("cyan-tab");
				viewTab.getStyleClass().add("cyan-tab");
				modifyTab.getStyleClass().add("cyan-tab");
				enquiryStatus.getStyleClass().add("cyan-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-green"))
			{
				newTab.getStyleClass().add("green-tab");
				viewTab.getStyleClass().add("green-tab");
				modifyTab.getStyleClass().add("green-tab");
				enquiryStatus.getStyleClass().add("green-tab");
			}
			AdminHomeController.currentPage.setText("NEW PRICE ESTIMATION");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation-new.fxml"));
			final BorderPane newEstimation = (BorderPane) loader.load();
			newTab.setContent(newEstimation);
			
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				
		        @Override
		        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					try{
					if(t1.equals(newTab))
					{
						AdminHomeController.currentPage.setText("NEW PRICE ESTIMATION");
						FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation-new.fxml"));
						BorderPane newEstimation = (BorderPane) loader.load();
						newTab.setContent(newEstimation);
					}
					else if(t1.equals(viewTab))
					{
						AdminHomeController.currentPage.setText("VIEW PRICE ESTIMATION");
						FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation-view.fxml"));
						BorderPane viewEstimation = (BorderPane) loader2.load();
						viewTab.setContent(viewEstimation);
					}
					else if(t1.equals(modifyTab))
					{
						AdminHomeController.currentPage.setText("MODIFY PRICE ESTIMATION");
						FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation-modify.fxml"));
						BorderPane modifyEstimation = (BorderPane) loader3.load();
						modifyTab.setContent(modifyEstimation);
					}
					else
					{
						AdminHomeController.currentPage.setText("ENQUIRY STATUS");
						FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/enquiry-status.fxml"));
						BorderPane status = (BorderPane) loader3.load();
						enquiryStatus.setContent(status);
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
