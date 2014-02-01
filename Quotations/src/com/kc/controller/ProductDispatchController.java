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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ProductDispatchController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(PriceEstimationController.class);
	
		@FXML
	    private Tab dispatchTab;

	    @FXML
	    private Tab optionTab;

	    @FXML
	    private TabPane tabPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		LOG.info("Enter : initialize");
		try{
			
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/productDispatch-new-view.fxml"));
			final BorderPane productDispatch = (BorderPane) loader.load();
			dispatchTab.setContent(productDispatch);
			
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				
		        @Override
		        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					try
					{
							if(t1.equals(dispatchTab))
							{
								FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/productDispatch-new-view.fxml"));
								final BorderPane productDispatch = (BorderPane) loader.load();
								dispatchTab.setContent(productDispatch);
							}
							else if(t1.equals(optionTab))
							{
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
