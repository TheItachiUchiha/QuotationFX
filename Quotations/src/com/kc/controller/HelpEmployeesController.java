package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;
import com.kc.model.EmployeeVO;
import com.kc.util.Validation;

@SuppressWarnings("unused")
public class HelpEmployeesController implements Initializable {
	
		private static final Logger LOG = LogManager.getLogger(HelpEmployeesController.class);
	
		@FXML
		private TabPane tabPane;
		@FXML
		private Tab newTab;
		@FXML
		private Tab viewTab;
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			LOG.info("Enter : initialize");
			try{
				
				FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/help-employees-new.fxml"));
				final GridPane newEmployee = (GridPane) loader.load();
				newTab.setContent(newEmployee);
				
				
				tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
					
			        @Override
			        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
						try{
								if(t1.equals(newTab))
								{
									FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/help-employees-new.fxml"));
									GridPane newEmployee = (GridPane) loader.load();
									newTab.setContent(newEmployee);
								}
								else
								{
									FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/com/kc/view/help-employees-view.fxml"));
									BorderPane viewEmployee = (BorderPane) loader2.load();
									viewTab.setContent(viewEmployee);
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
