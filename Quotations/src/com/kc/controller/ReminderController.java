package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ServiceDAO;
import com.kc.dao.StatusReminderDAO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ReminderVO;
import com.kc.util.DateUtil;
import com.kc.util.Email;
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
	    private ComboBox<Integer> frequencyCombo;

	    @FXML
	    private TextArea emailMessage;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private TextField receiver;

	    @FXML
	    private ComboBox<Integer> reminderCombo;

	    @FXML
	    private Button search;
	    
	    @FXML
	    private TextField subject;

	    @FXML
	    private ComboBox<String> autoReminderCombo;

	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    @FXML
	    private ComboBox<String> actionCombo;
	    
	    @FXML
	    private HBox autoReminderHBox;
	    
	    @FXML
	    private VBox autoReminderVBox;
	    
	    @FXML
	    private GridPane emailGrid;
	    
	    @FXML
	    private HBox referenceHBox;
	    
	    @FXML
	    private HBox actionHBox;
	    
	    @FXML
	    private  Button stopReminder;
	    
	    @FXML
	    private  Label messageSendMail;
	    
	    @FXML
	    private Label sentReminder;
	    
	    @FXML
	    private Label message;
	    
	    @FXML
	    private HBox sentHBox;

	    private ObservableList<String> monthList = FXCollections.observableArrayList();
		private ObservableList<String> yearList = FXCollections.observableArrayList();
		private ObservableList<ReminderVO> reminderList = FXCollections.observableArrayList();
		private ObservableList<EnquiryViewVO> refList = FXCollections.observableArrayList();
		private ObservableList<String> finalRefList = FXCollections.observableArrayList();
		private Map<String, String> defaultValues = new HashMap<String, String>();
		SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			LOG.info("Enter : initialize");
		
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			defaultValues = statusReminderDAO.getReminderMailDetails();
			search.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					if(monthCombo.getSelectionModel().getSelectedIndex()==-1||yearCombo.getSelectionModel().getSelectedIndex()==-1)
					{
						Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.SELECT_MONTH_YEAR);
					}
					else
					{
						startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
						endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
						actionHBox.setVisible(true);
					}
					
				}
			});
			
			actionCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					try
					{
						refList.clear();
						autoReminderCombo.getSelectionModel().clearSelection();
						reminderCombo.getSelectionModel().clearSelection();
						frequencyCombo.getSelectionModel().clearSelection();
						referenceCombo.getSelectionModel().clearSelection();
						emailGrid.setVisible(false);
						autoReminderHBox.setVisible(false);
						autoReminderVBox.setVisible(false);
						messageSendMail.setText("");
						refList.clear();
						finalRefList.clear();
						if(actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Create Reminder"))
						{
							refList = statusReminderDAO.getCreateReminders(startDate,endDate);
							emailMessage.setText(defaultValues.get(CommonConstants.KEY_REMINDER_MESSAGE));
							for(EnquiryViewVO enquiryViewVO : refList)
							{
								finalRefList.add(enquiryViewVO.getReferenceNo());
							}
							stopReminder.setVisible(false);
							sentHBox.setVisible(false);
						}
						else if (actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Modify Reminder"))
						{
							refList = statusReminderDAO.getModifyReminders(startDate,endDate);
							for(EnquiryViewVO enquiryViewVO : refList)
							{
								finalRefList.add(enquiryViewVO.getReferenceNo());
							}
							stopReminder.setVisible(true);
							sentHBox.setVisible(true);
						}
						if(refList.isEmpty())
						{
							Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_ENQUIRY);
						}
						else
						{
							referenceCombo.setItems(finalRefList);
							referenceHBox.setVisible(true);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			autoReminderCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					reminderCombo.getSelectionModel().clearSelection();
					frequencyCombo.getSelectionModel().clearSelection();
					if(null!=newValue)
					{
						if(newValue.equalsIgnoreCase("ON"))
						{
							autoReminderVBox.setVisible(true);
							emailGrid.setVisible(true);
						}
						else if(newValue.equalsIgnoreCase("OFF"))
						{
							autoReminderVBox.setVisible(false);
							emailGrid.setVisible(true);
						}
					}
				}
				
			});
			referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					autoReminderHBox.setVisible(true);
					messageSendMail.setText("");
					if(actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Create Reminder"))
					{
						for(EnquiryViewVO enquiryViewVO :refList)
						{
							if(null!=newValue)
							{
								if(newValue.equals(enquiryViewVO.getReferenceNo()))
								{
									receiver.setText(enquiryViewVO.getEmailId());
									break;
								}
							}
						}
					}
					else if(actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Modify Reminder"))
					{
						try
						{
							reminderList=statusReminderDAO.getReminderDetails();
							for(ReminderVO reminderVO :reminderList)
							{
								if(null!=newValue)
								{
									if(newValue.equals(reminderVO.getReferenceNo()))
									{
										fillDetails(reminderVO);
										break;
									}
								}
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			});
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("Exit : initialize");
	}
	
	public void saveReminder()
	{
		try
		{
			if(actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Create Reminder"))
			{
				ReminderVO reminderVO = new ReminderVO();
				reminderVO.setReferenceNo(referenceCombo.getSelectionModel().getSelectedItem());
				reminderVO.setLastSent(formatter.format(new Date()));
				reminderVO.setStatus(autoReminderCombo.getSelectionModel().getSelectedItem());
				reminderVO.setSubject(subject.getText());
				reminderVO.setEmailMessage(emailMessage.getText());
				reminderVO.setReciever(receiver.getText());
				if(autoReminderCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("ON"))
				{
					reminderVO.setFrequency(frequencyCombo.getSelectionModel().getSelectedItem());
					reminderVO.setTotalReminder(reminderCombo.getSelectionModel().getSelectedItem());
					reminderVO.setNextSend(formatter.format(DateUtil.addDays(new Date(), frequencyCombo.getSelectionModel().getSelectedItem())));
				}
				else if(autoReminderCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("OFF"))
				{
					reminderVO.setFrequency(0);
					reminderVO.setTotalReminder(1);
					reminderVO.setNextSend(CommonConstants.NA);
				}
				statusReminderDAO.createReminder(reminderVO);
				messageSendMail.getStyleClass().remove("failure");
				messageSendMail.getStyleClass().add("success");
				messageSendMail.setText("Reminder Created Successfully");
				messageSendMail.setVisible(true);
			}
			else if (actionCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Modify Reminder"))
			{
				ReminderVO reminderVO = new ReminderVO();
				reminderVO.setLastSent(formatter.format(new Date()));
				reminderVO.setStatus(autoReminderCombo.getSelectionModel().getSelectedItem());
				reminderVO.setSubject(subject.getText());
				reminderVO.setEmailMessage(emailMessage.getText());
				reminderVO.setReciever(receiver.getText());
				if(autoReminderCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("ON"))
				{
					reminderVO.setFrequency(frequencyCombo.getSelectionModel().getSelectedItem());
					reminderVO.setTotalReminder(reminderCombo.getSelectionModel().getSelectedItem());
					reminderVO.setNextSend(formatter.format(DateUtil.addDays(new Date(), frequencyCombo.getSelectionModel().getSelectedItem())));
				}
				else if(autoReminderCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("OFF"))
				{
					reminderVO.setFrequency(0);
					reminderVO.setTotalReminder(1);
					reminderVO.setNextSend(CommonConstants.NA);
				}
				statusReminderDAO.updateReminder(reminderVO,referenceCombo.getSelectionModel().getSelectedItem());
				messageSendMail.getStyleClass().remove("failure");
				messageSendMail.getStyleClass().add("success");
				messageSendMail.setText("Reminder Updated Successfully");
				messageSendMail.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMail()
	{
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> emailMap = statusReminderDAO.getReminderMailDetails();
		if(validate.isEmail(receiver.getText()))
		{
				if(!emailMessage.getText().equals(""))
				{
					map.put(CommonConstants.EMAIL_TO, receiver.getText());
					map.put(CommonConstants.EMAIL_BODY, emailMessage.getText());
					map.put(CommonConstants.EMAIL_SUBJECT, subject.getText());
					map.put(CommonConstants.EMAIL_USERNAME, emailMap.get(CommonConstants.KEY_REMINDER_EMAIL));
					map.put(CommonConstants.EMAIL_PASSWORD, emailMap.get(CommonConstants.KEY_REMINDER_PASSWORD));
					Email email = new Email(map);
					new Thread(email).start();
					messageSendMail.getStyleClass().remove("failure");
					messageSendMail.getStyleClass().add("success");
					messageSendMail.setText("Email send successfully");
					messageSendMail.setVisible(true);
				}
				else
				{
					messageSendMail.getStyleClass().remove("success");
					messageSendMail.getStyleClass().add("failure");
					messageSendMail.setText("Empty Email Body");
					messageSendMail.setVisible(true);
				}
		}
		else
		{
			messageSendMail.getStyleClass().remove("success");
			messageSendMail.getStyleClass().add("failure");
			messageSendMail.setText("Empty To Address");
			messageSendMail.setVisible(true);
		}
	}
	public void stopReminder()
	{
		try
		{
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
				    "Do you want to delete this Reminder", "Confirm", "Delete Reminder", DialogOptions.OK_CANCEL);
			if(response.equals(DialogResponse.OK))
			{
				statusReminderDAO.deleteReminder(referenceCombo.getSelectionModel().getSelectedItem());
				messageSendMail.getStyleClass().remove("failure");
				messageSendMail.getStyleClass().add("success");
				messageSendMail.setText(CommonConstants.REMINDER_DELETED);
				messageSendMail.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillDetails(ReminderVO reminderVO)
	{
		subject.setText(reminderVO.getSubject());
		receiver.setText(reminderVO.getReciever());
		emailMessage.setText(reminderVO.getEmailMessage());
		if(reminderVO.getStatus().equalsIgnoreCase("ON"))
		{
			autoReminderCombo.getSelectionModel().selectFirst();
			
			for(int i=1; i<=10;i++)
			{
				if(reminderVO.getTotalReminder()==i)
				{
					reminderCombo.getSelectionModel().select(i-1);
				}
				if(reminderVO.getFrequency()==i)
				{
					frequencyCombo.getSelectionModel().select(i-1);
				}
			}
			
			sentReminder.setText(String.valueOf(statusReminderDAO.getNoOfReminderSent(reminderVO.getReferenceNo())));
			
		}
		else if(reminderVO.getStatus().equalsIgnoreCase("OFF"))
		{
			autoReminderCombo.getSelectionModel().selectLast();
			reminderCombo.getSelectionModel().clearSelection();
			frequencyCombo.getSelectionModel().clearSelection();
		}
		
	}
}
