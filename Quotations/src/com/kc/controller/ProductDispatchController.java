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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;

public class ProductDispatchController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ProductDispatchController.class);
	
	@FXML
    private RadioButton createRadio;

    @FXML
    private Tab dispatchTab;

    @FXML
    private ToggleGroup dispatchToggle;

    @FXML
    private Tab optionTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private RadioButton viewRadio;
    
    HelpDAO helpDAO;
	private Map<String, String> theme = new HashMap<String, String>();
	
	public ProductDispatchController() {
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
				dispatchTab.getStyleClass().add("pink-tab");
				optionTab.getStyleClass().add("pink-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-blue"))
			{
				dispatchTab.getStyleClass().add("blue-tab");
				optionTab.getStyleClass().add("blue-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-cyan"))
			{
				dispatchTab.getStyleClass().add("cyan-tab");
				optionTab.getStyleClass().add("cyan-tab");
			}
			else if(theme.get(CommonConstants.KEY_BACKGROUND).equals("background-green"))
			{
				dispatchTab.getStyleClass().add("green-tab");
				optionTab.getStyleClass().add("green-tab");
			}
			createRadio.setSelected(true);
			AdminHomeController.currentPage.setText("NEW PRODUCT DISPATCH");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/productDispatch-new.fxml"));
			final BorderPane productDispatch = (BorderPane) loader.load();
			((BorderPane)dispatchTab.getContent()).setLeft(productDispatch);
			
			dispatchToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

				@Override
				public void changed(ObservableValue<? extends Toggle> arg0,
						Toggle arg1, Toggle arg2) {
					try
					{
						if(createRadio.isSelected())
						{
							AdminHomeController.currentPage.setText("NEW PRODUCT DISPATCH");
							FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/productDispatch-new.fxml"));
							final BorderPane productDispatch = (BorderPane) loader.load();
							((BorderPane)dispatchTab.getContent()).setLeft(productDispatch);
						}
						else if(viewRadio.isSelected())
						{
							AdminHomeController.currentPage.setText("VIEW PRODUCT DISPATCH");
							FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/productDispatch-view.fxml"));
							final BorderPane productDispatch = (BorderPane) loader.load();
							((BorderPane)dispatchTab.getContent()).setLeft(productDispatch);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
			
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				
		        @Override
		        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					try
					{
						if(t1.equals(optionTab))
						{
							AdminHomeController.currentPage.setText("OPTION");
							FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/productDispatch-option.fxml"));
							BorderPane option = (BorderPane) loader2.load();
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
