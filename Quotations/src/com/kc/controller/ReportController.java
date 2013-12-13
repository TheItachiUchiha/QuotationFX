package com.kc.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.kc.constant.CommonConstants;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

public class ReportController implements Initializable {

	 @FXML
	    private ComboBox<String> typeCombo;

	    @FXML
	    private ListView<String> periodList;

	    @FXML
	    private ComboBox<String> reportTypeCombo;
	    
	    @FXML
	    private GridPane reportGrid;
	    
	    private ObservableList<String> reportTypeList = FXCollections.observableArrayList();;
	    private ObservableList<String> salesList = FXCollections.observableArrayList();;
	    private ObservableList<String> serviceList = FXCollections.observableArrayList();;
	    private ObservableList<String> monthList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
		reportTypeList.add("Sales Report");
		reportTypeList.add("Service Report");
		reportTypeCombo.setItems(reportTypeList);
		
		salesList.add("Product Category");
		salesList.add("Product Subcategory");
		salesList.add("Customer Type");
		salesList.add("Customer State");
		
		serviceList.add("Reference No");
		serviceList.add("Service Engineer Name");
		serviceList.add("Customer name");
		
		monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
		periodList.setItems(monthList);
		periodList.getSelectionModel().selectionModeProperty().set(SelectionMode.MULTIPLE);
		
		reportTypeCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				reportGrid.setVisible(true);
				periodList.getSelectionModel().clearSelection();
				
				if(reportTypeCombo.getSelectionModel().getSelectedItem().equals("Sales Report"))
				{
					typeCombo.setItems(salesList);
					periodList.getSelectionModel().clearSelection();
				}
				else if (reportTypeCombo.getSelectionModel().getSelectedItem().equals("Service Report"))
				{
					typeCombo.setItems(serviceList);
					periodList.getSelectionModel().clearSelection();
				}
				
			}
		});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
