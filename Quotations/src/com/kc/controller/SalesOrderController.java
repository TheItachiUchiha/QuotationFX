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

public class SalesOrderController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(SalesOrderController.class);
	@FXML
    private Tab modifyTab;

    @FXML
    private Tab newTab;

    @FXML
    private Tab viewTab;
    
    @FXML
	private TabPane tabPane;
    
    HelpDAO helpDAO;
	private Map<String, String> theme = new HashMap<String, String>();
	
	public SalesOrderController() {
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
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-blue"))
			{
				newTab.getStyleClass().add("blue-tab");
				viewTab.getStyleClass().add("blue-tab");
				modifyTab.getStyleClass().add("blue-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-cyan"))
			{
				newTab.getStyleClass().add("cyan-tab");
				viewTab.getStyleClass().add("cyan-tab");
				modifyTab.getStyleClass().add("cyan-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-green"))
			{
				newTab.getStyleClass().add("green-tab");
				viewTab.getStyleClass().add("green-tab");
				modifyTab.getStyleClass().add("green-tab");
			}
			AdminHomeController.currentPage.setText("NEW SALES LEAD");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesOrder-new.fxml"));
			BorderPane newSalesOrder = (BorderPane) loader.load();
			newTab.setContent(newSalesOrder);
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				
		        @Override
		        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					try{
					if(t1.equals(newTab))
					{
						AdminHomeController.currentPage.setText("NEW SALES LEAD");
						FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesOrder-new.fxml"));
						BorderPane newSalesOrder = (BorderPane) loader.load();
						newTab.setContent(newSalesOrder);
					}
					else if(t1.equals(viewTab))
					{
						AdminHomeController.currentPage.setText("VIEW SALES LEAD");
						FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesOrder-view.fxml"));
						BorderPane viewSalesOrder = (BorderPane) loader3.load();
						viewTab.setContent(viewSalesOrder);
					}
					else
					{
						AdminHomeController.currentPage.setText("MODIFY SALES LEAD");
						FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesOrder-modify.fxml"));
						BorderPane modifySalesOrder = (BorderPane) loader2.load();
						modifyTab.setContent(modifySalesOrder);
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				}				
			});

			
			
			FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesOrder-modify.fxml"));
			BorderPane modifySalesOrder = (BorderPane) loader2.load();
			modifyTab.setContent(modifySalesOrder);
			FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesOrder-view.fxml"));
			BorderPane viewSalesOrder = (BorderPane) loader3.load();
			viewTab.setContent(viewSalesOrder);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : initialize");
	}
}
