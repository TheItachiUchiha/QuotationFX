package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
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

public class ServiceRegisterController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceRegisterController.class);
	

    @FXML
    private TextField charge;

    @FXML
    private RadioButton customerName;

    @FXML
    private ToggleGroup customerToggle;

    @FXML
    private TextField dateOfService;

    @FXML
    private TextField engineerName;

    @FXML
    private TextArea feedback;

    @FXML
    private Label message;

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
    private GridPane serviceGrid;
    
    @FXML
    private HBox complaintHBox;
    
    Validation validation;
    private DatePicker calendar;
    ServiceDAO serviceDAO;
    EnquiryDAO enquiryDAO;
    CustomersDAO customersDAO;
    private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
    private ObservableList<ServiceVO> refList = FXCollections.observableArrayList();
    private ObservableList<String> uniqueRefList = FXCollections.observableArrayList();
    private ObservableList<String> complaintList = FXCollections.observableArrayList();
    private EnquiryViewVO viewVO = new EnquiryViewVO();
    
	public ServiceRegisterController() {
		validation = new Validation();
		serviceDAO = new ServiceDAO();
		enquiryDAO = new EnquiryDAO();
    	customersDAO = new CustomersDAO();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			validation.allowAsAmount(charge);
			calendar = new DatePicker(Locale.ENGLISH);
    		calendar.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    		calendar.getCalendarView().todayButtonTextProperty().set("Today");
    		calendar.getCalendarView().setShowWeeks(false);
    		calendar.getStylesheets().add("com/kc/style/DatePicker.css");
    		((TextField)calendar.getChildren().get(0)).setEditable(false);
    		((TextField)calendar.getChildren().get(0)).setPrefWidth(200);
    		serviceGrid.add(calendar, 1, 3);
    		
    		enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);
    		refList=serviceDAO.getComplaintList();
    		for(ServiceVO serviceVO : refList)
    		{
    			if(!uniqueRefList.contains(serviceVO.getReferenceNo()))
    			{
    				uniqueRefList.add(serviceVO.getReferenceNo());
    			}
    		}
    		referenceCombo.setItems(uniqueRefList);
    		
    		referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					
						serviceGrid.setVisible(false);
						complaintHBox.setVisible(false);
					
					
				}
			});
    		complaintCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					
						serviceGrid.setVisible(false);
					
					
				}
			});
    		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// fill the ComplaintCombo for a  reference number
	public void showComplaints()
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
		
		for(EnquiryViewVO enquiryVO : enquiryViewList)
		{
			if(enquiryVO.getReferenceNo().equals(referenceCombo.getSelectionModel().getSelectedItem()) )
			{
				ServiceRegisterController.this.viewVO=enquiryVO;
				break;
			}
		}
		complaintHBox.setVisible(true);
	}
	
	// display the Customer Info in a Pop Up for selected reference No.
	public void customerInfo()
	{
    	try
    	{
    		if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
    		{
    			Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_REFERENCE);
    		}
    		else
    		{
				FXMLLoader menuLoader = new FXMLLoader(this.getClass()
						.getResource("/com/kc/view/service-customer-popup.fxml"));
				GridPane customerInf;
				customerInf = (GridPane) menuLoader.load();
				Stage viewStage = new Stage();
				Scene viewScene = new Scene(customerInf);
				viewStage.setResizable(false);
				viewStage.setHeight(500);
				viewStage.setWidth(600);
				viewStage.initModality(Modality.WINDOW_MODAL);
				viewStage.initOwner(LoginController.primaryStage);
				viewStage.setScene(viewScene);
				viewStage.show();
				
				((ServiceCustomerInfoController) menuLoader.getController())
				.fillTextFieldValues(ServiceRegisterController.this.viewVO);
    		}
			
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	// Fill  the Nature of Complaint Field According to the Complaint Id in ComplaintCombo and Display the Service Grid Form
	public void createService()
	{
		try
		{
			for(ServiceVO serviceVO : refList)
			{
				if(complaintCombo.getSelectionModel().getSelectedItem().equals(serviceVO.getComplaintId()))
				{
					natureOfComplaint.setText(serviceVO.getComplaint());
					break;
				}
			}
			serviceGrid.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Resister Service for a Complaint
	public void registerService()
	{
		ServiceVO serviceVO = new ServiceVO();
		serviceVO.setReferenceNo(referenceCombo.getSelectionModel().getSelectedItem());
		serviceVO.setCharge(Double.parseDouble(charge.getText()));
		serviceVO.setComplaint(natureOfComplaint.getText());
		serviceVO.setDate(((TextField)calendar.getChildren().get(0)).getText());
		serviceVO.setEngineerName(engineerName.getText());
		serviceVO.setFeedback(feedback.getText());
		serviceVO.setRating(ratingCombo.getSelectionModel().getSelectedItem());
		serviceDAO.newService(serviceVO,complaintCombo.getSelectionModel().getSelectedItem());
		message.setText(CommonConstants.SERVICE_REGISTERED);
		message.getStyleClass().remove("failure");
		message.getStyleClass().add("success");
		message.setVisible(true);
	}

}
