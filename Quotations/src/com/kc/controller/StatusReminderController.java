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

public class StatusReminderController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(StatusReminderController.class);
    @FXML
    private Tab optionTab;

    @FXML
    private Tab reminderTab;

    @FXML
    private Tab salesStatusTab;
    
    @FXML
    private TabPane tabPane;
    
    HelpDAO helpDAO;
	private Map<String, String> theme = new HashMap<String, String>();
	
	public StatusReminderController() {
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
				reminderTab.getStyleClass().add("pink-tab");
				salesStatusTab.getStyleClass().add("pink-tab");
				optionTab.getStyleClass().add("pink-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-blue"))
			{
				reminderTab.getStyleClass().add("blue-tab");
				salesStatusTab.getStyleClass().add("blue-tab");
				optionTab.getStyleClass().add("blue-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-cyan"))
			{
				reminderTab.getStyleClass().add("cyan-tab");
				salesStatusTab.getStyleClass().add("cyan-tab");
				optionTab.getStyleClass().add("cyan-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-green"))
			{
				reminderTab.getStyleClass().add("green-tab");
				salesStatusTab.getStyleClass().add("green-tab");
				optionTab.getStyleClass().add("green-tab");
			}
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/reminder.fxml"));
			BorderPane newreminder = (BorderPane) loader.load();
			reminderTab.setContent(newreminder);
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

				@Override
				public void changed(ObservableValue<? extends Tab> observable,
						Tab oldValue, Tab newValue) {
					
					try{
							if(newValue.equals(reminderTab))
							{
								FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/reminder.fxml"));
								BorderPane newreminder = (BorderPane) loader.load();
								reminderTab.setContent(newreminder);
							}
							else if(newValue.equals(salesStatusTab))
							{
								FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesStatus.fxml"));
								BorderPane SalesStatus = (BorderPane) loader2.load();
								salesStatusTab.setContent(SalesStatus);
							}
							else if(newValue.equals(optionTab))
							{
								FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/statusReminder-option.fxml"));
								BorderPane option = (BorderPane) loader3.load();
								optionTab.setContent(option);
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
