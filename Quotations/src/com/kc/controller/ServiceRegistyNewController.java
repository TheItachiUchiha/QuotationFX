package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
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

public class ServiceRegistyNewController implements Initializable {
	

		EnquiryDAO enquiryDAO;
		CustomersDAO customersDAO;
		ServiceDAO serviceDAO;
		Validation validate;
		
		public ServiceRegistyNewController() {
			enquiryDAO = new EnquiryDAO();
			customersDAO = new CustomersDAO();
			serviceDAO = new ServiceDAO();
			validate = new Validation();
		}
		
		@FXML
	    private TextArea complaint;

	    @FXML
	    private Button createService;

	    @FXML
	    private Button customerInfo;

	    @FXML
	    private TextField dateOfService;

	    @FXML
	    private Label message;

	    @FXML
	    private TextField name;

	    @FXML
	    private ComboBox<String> ratingCombo;

	    @FXML
	    private ComboBox<String> referenceCombo;

	    @FXML
	    private TextField serviceCharge;
	    
	    @FXML
	    private TextField customerName;
	    
	    @FXML
	    private TextField companyName;
	    
	    @FXML
	    private TextField customerType;

	    @FXML
	    private TextField emailId;
	    
	    @FXML
	    private TextField city;
	    
	    @FXML
	    private TextField state;
	    
	    @FXML
	    private TextField tinNumber;

	    @FXML
	    private GridPane serviceGrid;
	    
	    @FXML
	    private GridPane customerGrid;

	    private DatePicker calendar;
	    private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
		private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
		private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
		private ObservableList<String> refList = FXCollections.observableArrayList();
		private EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
		//SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	
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
    		
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);
		
			//refList.clear();
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if(enquiryVO.getSales().equalsIgnoreCase("Y") )
				{
					refList.add(enquiryVO.getReferenceNo());
				}
			}
			referenceCombo.setItems(refList);
			
			createService.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					if(refList.isEmpty())
					{
						Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
					}
					else if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
					{
						Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_REFERENCE);
					}
					else
					{
						serviceGrid.setVisible(true);
					}
					
				}
			});
			referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					for(EnquiryViewVO enquiryViewVO : enquiryViewList)
					{
						if(enquiryViewVO.getReferenceNo().equals(newValue))
						{
							ServiceRegistyNewController.this.enquiryViewVO=enquiryViewVO;
						}
					}
					serviceGrid.setVisible(false);
					customerGrid.setVisible(false);
					
				}
			});
			validate.allowAsAmount(serviceCharge);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void registerService()
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
			serviceDAO.newService(serviceVO);
			message.setText(CommonConstants.SERVICE_REGISTERED);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
			message.setVisible(true);
		}
	}
	public void viewCustomerInfo()
	{
		if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
		{
			Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_REFERENCE);
		}
		else
		{
			companyName.setText(enquiryViewVO.getCompanyName());
			customerName.setText(enquiryViewVO.getCustomerName());
			emailId.setText(enquiryViewVO.getEmailId());
			tinNumber.setText(enquiryViewVO.getTinNumber());
			city.setText(enquiryViewVO.getCity());
			state.setText(enquiryViewVO.getState());
			customerType.setText(enquiryViewVO.getCustomerType());
			customerGrid.setVisible(true);
		}
	}
	
}
