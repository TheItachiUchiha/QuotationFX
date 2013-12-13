package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ServiceDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ServiceVO;
import com.kc.util.QuotationUtil;
import com.kc.util.Validation;

import eu.schudt.javafx.controls.calendar.DatePicker;

public class ServiceRegistyModifyController implements Initializable {
	
	
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	ServiceDAO serviceDAO;
	Validation validate;
	String startDate;
	String endDate;
	
	public ServiceRegistyModifyController() {
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		serviceDAO = new ServiceDAO();
		validate = new Validation();
	}
	
	@FXML
    private TextArea complaint;

    @FXML
    private Label message;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private TextField name;

    @FXML
    private ComboBox<String> referenceCombo;

    @FXML
    private Button search;

    @FXML
    private TextField serviceCharge;

    @FXML
    private ComboBox<String> ratingCombo;

    @FXML
    private GridPane serviceGrid;

    @FXML
    private ComboBox<String> yearCombo;
    
    @FXML
    private HBox referenceHBox;

    private DatePicker calendar;
	private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private ObservableList<ServiceVO> serviceList = FXCollections.observableArrayList();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
			
			calendar = new DatePicker(Locale.ENGLISH);
    		calendar.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    		calendar.getCalendarView().todayButtonTextProperty().set("Today");
    		calendar.getCalendarView().setShowWeeks(false);
    		calendar.getStylesheets().add("com/kc/style/DatePicker.css");
    		((TextField)calendar.getChildren().get(0)).setEditable(false);
    		serviceGrid.add(calendar, 1, 2);
    		
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			
			customerList = customersDAO.getCustomers();
			
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		search.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try
				{
					startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					enquiryList = serviceDAO.getServiceEnquires(startDate, endDate);
					refList.clear();
					for(EnquiryVO enquiryVO : enquiryList)
					{
						refList.add(enquiryVO.getRefNumber());
					}
					if(monthCombo.getSelectionModel().getSelectedIndex()==-1|| yearCombo.getSelectionModel().getSelectedIndex()==-1)
					{
						Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
					}
					else
					{
						if(refList.isEmpty())
						{
							Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
						}
						else
						{
							referenceCombo.setItems(refList);	
							referenceHBox.setVisible(true);
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		monthCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				
				referenceHBox.setVisible(false);
				serviceGrid.setVisible(false);
				
			}
		});
		yearCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				
				referenceHBox.setVisible(false);
				serviceGrid.setVisible(false);
				
			}
		});
		referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				
				serviceGrid.setVisible(false);
				
			}
		});
	}
	public void viewDetails()
	{
		try
		{
			if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_REFERENCE);
			}
			else
			{
				serviceList = serviceDAO.getServices(referenceCombo.getSelectionModel().getSelectedItem());
				for(ServiceVO serviceVO : serviceList)
				{
					if(referenceCombo.getSelectionModel().getSelectedItem().equals(serviceVO.getReferenceNo()))
					{
						name.setText(serviceVO.getEngineerName());
						complaint.setText(serviceVO.getComplaint());
						((TextField)calendar.getChildren().get(0)).setText(serviceVO.getDate());
						serviceCharge.setText(serviceVO.getCharge());
						if(serviceVO.getRating().equalsIgnoreCase("Excelent"))
						{
							ratingCombo.getSelectionModel().select(0);
						}
						else if(serviceVO.getRating().equalsIgnoreCase("Good"))
						{
							ratingCombo.getSelectionModel().select(1);
						}
						else if(serviceVO.getRating().equalsIgnoreCase("Average"))
						{
							ratingCombo.getSelectionModel().select(2);
						}
						else if(serviceVO.getRating().equalsIgnoreCase("Bad"))
						{
							ratingCombo.getSelectionModel().select(3);
						}
					}
				}
				serviceGrid.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void UpdateServiceEntry()
	{
		if(validate.isEmpty(name,serviceCharge))
		{
			message.setText(CommonConstants.MANDATORY_FIELDS);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			message.setVisible(true);
		}
		else if (((TextField)calendar.getChildren().get(0)).getText().length()==0||complaint.getText().length()==0)
		{
			message.setText(CommonConstants.MANDATORY_FIELDS);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			message.setVisible(true);
		}
		else if (ratingCombo.getSelectionModel().getSelectedIndex()==-1)
		{
			message.setText(CommonConstants.MANDATORY_FIELDS);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			message.setVisible(true);
		}
		else
		{
		ServiceVO serviceVO = new ServiceVO();
		serviceVO.setCharge(serviceCharge.getText());
		serviceVO.setComplaint(complaint.getText());
		serviceVO.setDate(((TextField)calendar.getChildren().get(0)).getText());
		serviceVO.setEngineerName(name.getText());
		serviceVO.setRating(ratingCombo.getSelectionModel().getSelectedItem());
		serviceVO.setReferenceNo(referenceCombo.getSelectionModel().getSelectedItem());
		serviceDAO.updateService(serviceVO);
		
		message.setText(CommonConstants.SERVICE_UPDATED);
		message.getStyleClass().remove("failure");
		message.getStyleClass().add("success");
		message.setVisible(true);
		}
		
	}
	public void deleteEntry()
	{
		try
		{
			serviceDAO.deleteService(referenceCombo.getSelectionModel().getSelectedItem());
			message.setText(CommonConstants.SERVICE_DELETED);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
			message.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
