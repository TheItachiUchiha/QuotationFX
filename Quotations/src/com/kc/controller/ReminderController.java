package com.kc.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;

public class ReminderController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(ReminderController.class);
	
	 @FXML
	    private ComboBox<String> createCombo;

	    @FXML
	    private Button createReminder;

	    @FXML
	    private ComboBox<String> frequencyCombo;

	    @FXML
	    private TextArea message;

	    @FXML
	    private ComboBox<String> modifyCombo;

	    @FXML
	    private Button modifyReminder;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private TextField receiver;

	    @FXML
	    private ComboBox<String> reminderCombo;

	    @FXML
	    private Button search;

	    @FXML
	    private TextField sender;

	    @FXML
	    private Label sentReminder;

	    @FXML
	    private TextField subject;

	    @FXML
	    private ComboBox<String> autoReminderCombo;

	    @FXML
	    private ComboBox<String> yearCombo;

	    private ObservableList<String> monthList = FXCollections.observableArrayList();
		private ObservableList<String> yearList = FXCollections.observableArrayList();
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
		yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
		monthCombo.setItems(monthList);
		yearCombo.setItems(yearList);
		
	}

}
