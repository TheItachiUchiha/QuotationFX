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

public class ServiceRegistryController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceRegistryController.class);

		@FXML
	    private Tab complaintTab;

	    @FXML
	    private Tab modifyTab;

	    @FXML
	    private Tab newTab;

	    @FXML
	    private TabPane tabPane;

	    @FXML
	    private Tab viewTab;
	    
	    HelpDAO helpDAO;
		private Map<String, String> theme = new HashMap<String, String>();
		
		public ServiceRegistryController() {
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
				modifyTab.getStyleClass().add("pink-tab");
				complaintTab.getStyleClass().add("pink-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-blue"))
			{
				newTab.getStyleClass().add("blue-tab");
				viewTab.getStyleClass().add("blue-tab");
				modifyTab.getStyleClass().add("blue-tab");
				complaintTab.getStyleClass().add("blue-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-cyan"))
			{
				newTab.getStyleClass().add("cyan-tab");
				viewTab.getStyleClass().add("cyan-tab");
				modifyTab.getStyleClass().add("cyan-tab");
				complaintTab.getStyleClass().add("cyan-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-green"))
			{
				newTab.getStyleClass().add("green-tab");
				viewTab.getStyleClass().add("green-tab");
				modifyTab.getStyleClass().add("green-tab");
				complaintTab.getStyleClass().add("green-tab");
			}
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-registerComplaint.fxml"));
			BorderPane newService = (BorderPane) loader.load();
			complaintTab.setContent(newService);
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

				@Override
				public void changed(ObservableValue<? extends Tab> observable,
						Tab oldValue, Tab newValue) {
					
					try{
						if(newValue.equals(complaintTab))
						{
							FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-registerComplaint.fxml"));
							BorderPane newService = (BorderPane) loader.load();
							complaintTab.setContent(newService);
						}
						else if(newValue.equals(newTab))
						{
							FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-serviceRegister.fxml"));
							BorderPane modifyService = (BorderPane) loader3.load();
							newTab.setContent(modifyService);
						}
						else if(newValue.equals(modifyTab))
						{
							FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-serviceModify.fxml"));
							BorderPane modifyService = (BorderPane) loader3.load();
							modifyTab.setContent(modifyService);
						}
						else if(newValue.equals(viewTab))
						{
							FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/service-serviceView.fxml"));
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
