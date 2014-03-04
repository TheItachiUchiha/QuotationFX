package com.kc.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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

public class ServiceRCController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceRegistryController.class);

	 	@FXML
	    private Tab newTab;

	    @FXML
	    private Tab optionTab;

	    @FXML
	    private TabPane tabPane;

	    @FXML
	    private Tab viewTab;
	    
	    HelpDAO helpDAO;
		private Map<String, String> theme = new HashMap<String, String>();
		
		public ServiceRCController() {
			helpDAO = new HelpDAO();
		}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		LOG.info("Enter : initialize");
		try{
			
			theme = helpDAO.getBackground();
			//newTab.getStyleClass().remove(1);
			if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-pink"))
			{
				newTab.getStyleClass().add("pink-tab");
				viewTab.getStyleClass().add("pink-tab");
				optionTab.getStyleClass().add("pink-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-blue"))
			{
				newTab.getStyleClass().add("blue-tab");
				viewTab.getStyleClass().add("blue-tab");
				optionTab.getStyleClass().add("blue-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-cyan"))
			{
				newTab.getStyleClass().add("cyan-tab");
				viewTab.getStyleClass().add("cyan-tab");
				optionTab.getStyleClass().add("cyan-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-green"))
			{
				newTab.getStyleClass().add("green-tab");
				viewTab.getStyleClass().add("green-tab");
				optionTab.getStyleClass().add("green-tab");
			}
			
			AdminHomeController.currentPage.setText("NEW COMPLAINT");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-newComplaint.fxml"));
			BorderPane newService = (BorderPane) loader.load();
			newTab.setContent(newService);
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

				@Override
				public void changed(ObservableValue<? extends Tab> observable,
						Tab oldValue, Tab newValue) {
					
					try{
						if(newValue.equals(newTab))
						{
							AdminHomeController.currentPage.setText("NEW COMPLAINT");
							FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-newComplaint.fxml"));
							BorderPane newService = (BorderPane) loader.load();
							newTab.setContent(newService);
						}
						else if(newValue.equals(optionTab))
						{
							AdminHomeController.currentPage.setText("OPTION");
							FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-option.fxml"));
							BorderPane modifyService = (BorderPane) loader3.load();
							optionTab.setContent(modifyService);
						}
						else if(newValue.equals(viewTab))
						{
							AdminHomeController.currentPage.setText("VIEW COMPLAINTS");
							FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-viewComplaint.fxml"));
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
