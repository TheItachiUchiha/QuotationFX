package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ServiceDAO;
import com.kc.dao.StatusReminderDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ReminderVO;
import com.kc.util.QuotationUtil;
import com.kc.util.Validation;

public class ReminderController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ReminderController.class);
	
		EnquiryDAO enquiryDAO;
		CustomersDAO customersDAO;
		ServiceDAO serviceDAO;
		Validation validate;
		StatusReminderDAO statusReminderDAO;
		String startDate;
		String endDate;
	
		public ReminderController() {
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		serviceDAO = new ServiceDAO();
		validate = new Validation();
		statusReminderDAO = new StatusReminderDAO();
		}
	
		@FXML
	    private ComboBox<String> referenceCombo;

	    @FXML
	    private Button createReminder;

	    @FXML
	    private ComboBox<Integer> frequencyCombo;

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
	    private ComboBox<Integer> reminderCombo;

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
	    
	    @FXML
	    private ComboBox<String> actionCombo;

	    private ObservableList<String> monthList = FXCollections.observableArrayList();
		private ObservableList<String> yearList = FXCollections.observableArrayList();
		private ObservableList<ReminderVO> reminderList = FXCollections.observableArrayList();
		private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
		private ObservableList<String> refList = FXCollections.observableArrayList();
		SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
		private EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
		
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			search.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					
				}
			});
			
			actionCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					try
					{
				
						if(actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Create Reminder"))
						{
							refList = statusReminderDAO.getCreateReminders(startDate,endDate);
						}
						else if (actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Modify Reminder"))
						{
							refList = statusReminderDAO.getModifyReminders(startDate,endDate);
						}
						referenceCombo.setItems(refList);
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
	}
	public void createModifyReminder()
	{
		if(actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Create Reminder"))
		{
			ReminderVO reminderVO = new ReminderVO();
			reminderVO.setFrequency(frequencyCombo.getSelectionModel().getSelectedItem());
			reminderVO.setTotalReminder(reminderCombo.getSelectionModel().getSelectedItem());
		}
		else if (actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Modify Reminder"))
		{
			
		}
	}

}
