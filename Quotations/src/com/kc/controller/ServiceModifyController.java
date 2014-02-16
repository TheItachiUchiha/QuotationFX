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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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

public class ServiceModifyController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceModifyController.class);
	
	 @FXML
	    private TextField charge;

	    @FXML
	    private RadioButton customerName;

	    @FXML
	    private ToggleGroup customerToggle;

	    @FXML
	    private TextField engineerName;

	    @FXML
	    private TextArea feedback;

	    @FXML
	    private Label message;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private TextArea natureOfComplaint;

	    @FXML
	    private ComboBox<String> ratingCombo;

	    @FXML
	    private ComboBox<String> referenceCombo;
	    
	    @FXML
	    private ComboBox<String> complaintCombo;

	    @FXML
	    private RadioButton referenceNo;

	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    @FXML
	    private GridPane serviceGrid;
	    
	    @FXML
	    private HBox complaintHBox;
	    
	    @FXML
	    private HBox referenceHBox;
	    
	    Validation validation;
	    private DatePicker calendar;
	    ServiceDAO serviceDAO;
	    EnquiryDAO enquiryDAO;
	    CustomersDAO customersDAO;
	    String startDate;
	    String endDate;
	    
	   /* private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
		private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
		private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();*/
	    private ObservableList<ServiceVO> refList = FXCollections.observableArrayList();
	    private ObservableList<ServiceVO> serviceList = FXCollections.observableArrayList();
	    private ObservableList<String> uniqueRefList = FXCollections.observableArrayList();
	    private ObservableList<String> complaintList = FXCollections.observableArrayList();
	    private ObservableList<String> monthList = FXCollections.observableArrayList();
	   	private ObservableList<String> yearList = FXCollections.observableArrayList();
	    public ServiceModifyController() {
	    	validation = new Validation();
			serviceDAO = new ServiceDAO();
			enquiryDAO = new EnquiryDAO();
			customersDAO =new CustomersDAO();
		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			yearCombo.setItems(yearList);
			monthCombo.setItems(monthList);
			validation.allowAsAmount(charge);
			calendar = new DatePicker(Locale.ENGLISH);
    		calendar.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    		calendar.getCalendarView().todayButtonTextProperty().set("Today");
    		calendar.getCalendarView().setShowWeeks(false);
    		calendar.getStylesheets().add("com/kc/style/DatePicker.css");
    		((TextField)calendar.getChildren().get(0)).setEditable(false);
    		((TextField)calendar.getChildren().get(0)).setPrefWidth(100);
    		serviceGrid.add(calendar, 1, 3);
    		
    		/*enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);*/
    		monthCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					
						serviceGrid.setVisible(false);
						complaintHBox.setVisible(false);
						referenceHBox.setVisible(false);
						message.setText("");
				}
			});
    		yearCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					
						serviceGrid.setVisible(false);
						complaintHBox.setVisible(false);
						referenceHBox.setVisible(false);
						message.setText("");
				}
			});
    		referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					
						serviceGrid.setVisible(false);
						complaintHBox.setVisible(false);
						message.setText("");
				}
			});
    		complaintCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					
						serviceGrid.setVisible(false);
						message.setText("");
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//search reference No for selected Month and Year
	public void search()
	{
		if(monthCombo.getSelectionModel().getSelectedIndex()==-1 || yearCombo.getSelectionModel().getSelectedIndex()==-1)
		{
			Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
		}
		else
		{
		
			try
			{
				refList.clear();
				uniqueRefList.clear();
				startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				refList=serviceDAO.getServiceList(startDate, endDate);
				if(refList.isEmpty())
				{
					Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_ENQUIRY);
				}
				else
				{
					serviceList=serviceDAO.getServiceDetails();
					
					for(ServiceVO serviceVO : refList)
					{
						if(!uniqueRefList.contains(serviceVO.getReferenceNo()))
						{
							uniqueRefList.add(serviceVO.getReferenceNo());
						}
					}
					referenceCombo.setItems(uniqueRefList);
					referenceHBox.setVisible(true);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// Fill the ComplaintCombo for a Reference Number
	public void viewComplaints()
	{
		try
		{
			complaintCombo.getSelectionModel().clearSelection();
			complaintList.clear();
			for(ServiceVO serviceVO : refList)
			{
				if(serviceVO.getReferenceNo().equals(referenceCombo.getSelectionModel().getSelectedItem()))
				{
					complaintList.add(serviceVO.getComplaintId());
				}
			}
			complaintCombo.setItems(complaintList);
			complaintHBox.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//fill the Service Form according to the complaint id  

   public void viewDetails()
   {
	   try
	   {
		   	if(complaintCombo.getSelectionModel().getSelectedIndex()==-1)
	   		{
	   			Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_COMPLAINT);
	   		}
   		else
   		{
   			for(ServiceVO serviceVO : serviceList)
   			{
   				
   				if(complaintCombo.getSelectionModel().getSelectedItem().equals(serviceVO.getComplaintId()))
   				{
   					fillTextFieldValues(serviceVO);
   					break;
   				}
   				
   			}
   			serviceGrid.setVisible(true);
   		}
	   }
	   catch (Exception e) {
		e.printStackTrace();
	}
   }
   
   public void fillTextFieldValues(ServiceVO serviceVO)
   {
   		engineerName.setText(serviceVO.getEngineerName());
		natureOfComplaint.setText(serviceVO.getComplaint());
		feedback.setText(serviceVO.getFeedback());
		((TextField)calendar.getChildren().get(0)).setText(serviceVO.getDate());
		charge.setText(String.valueOf(serviceVO.getCharge()));
		if(serviceVO.getRating().equalsIgnoreCase("Excelent"))
		{
			ratingCombo.getSelectionModel().select(0);
		}
		else if(serviceVO.getRating().equalsIgnoreCase("Average"))
		{
			ratingCombo.getSelectionModel().select(1);
		}
		else if(serviceVO.getRating().equalsIgnoreCase("Good"))
		{
			ratingCombo.getSelectionModel().select(2);
		}
		else if(serviceVO.getRating().equalsIgnoreCase("Bad"))
		{
			ratingCombo.getSelectionModel().select(3);
		}
   }
	 // Register Service
   public void save() 
   {
	   	ServiceVO serviceVO = new ServiceVO();
		serviceVO.setCharge(Double.parseDouble(charge.getText()));
		serviceVO.setComplaint(natureOfComplaint.getText());
		serviceVO.setDate(((TextField)calendar.getChildren().get(0)).getText());
		serviceVO.setEngineerName(engineerName.getText());
		serviceVO.setFeedback(feedback.getText());
		serviceVO.setRating(ratingCombo.getSelectionModel().getSelectedItem());
		serviceDAO.newService(serviceVO,complaintCombo.getSelectionModel().getSelectedItem());
		message.setText(CommonConstants.SERVICE_UPDATED);
		message.getStyleClass().remove("failure");
		message.getStyleClass().add("success");
		message.setVisible(true);
   }
   
   // delete a service and RollBack its status as Complaint
   public void deleteEntry()
   {
	   try
	   {
			if(complaintCombo.getSelectionModel().getSelectedIndex()==-1)
	   		{
	   			Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_COMPLAINT);
	   		}
			else
			{
				DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
					    "Do you want to delete selected Service", "Confirm", "Delete Service", DialogOptions.OK_CANCEL);
				if(response.equals(DialogResponse.OK))
				{
					ServiceVO serviceVO = new ServiceVO();
					serviceVO.setCharge(0);
					serviceVO.setDate(CommonConstants.NA);
					serviceVO.setEngineerName(CommonConstants.NA);
					serviceVO.setFeedback(CommonConstants.NA);
					serviceVO.setRating(CommonConstants.NA);
					serviceVO.setComplaintId(complaintCombo.getSelectionModel().getSelectedItem());
					serviceVO.setReferenceNo(referenceCombo.getSelectionModel().getSelectedItem());
					serviceDAO.deleteService(serviceVO);
					clearFields();
					message.setText(CommonConstants.SERVICE_DELETED);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
				}
			}
	   }
	   catch (Exception e) {
		e.printStackTrace();
	}
   }
   //clear all the fields After deleting a Service
   public void clearFields()
   {
	   //referenceCombo.getSelectionModel().clearSelection();
	   engineerName.setText("");
	   natureOfComplaint.setText("");
	   charge.setText("");
	   //complaintCombo.getSelectionModel().clearSelection();
	   feedback.setText("");
	   ratingCombo.getSelectionModel().clearSelection();
	   ((TextField)calendar.getChildren().get(0)).setText("");
   }

}
