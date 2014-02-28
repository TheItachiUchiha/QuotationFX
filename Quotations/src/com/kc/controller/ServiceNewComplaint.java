package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ProductsDAO;
import com.kc.dao.ServiceDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ServiceVO;
import com.kc.util.Email;
import com.kc.util.QuotationUtil;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class ServiceNewComplaint implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceNewComplaint.class);

	@FXML
    private TextField cc;

    @FXML
    private TextField ccInName;

    @FXML
    private ToggleGroup customerToggle;

    @FXML
    private Label emailSent;

    @FXML
    private Label emailSentInName;

    @FXML
    private TextArea message;

    @FXML
    private TextArea messageAreaInName;

    @FXML
    private GridPane nameGrid;

    @FXML
    private TextArea natureOfComplaint;

    @FXML
    private TextArea natureOfComplaintInName;

    @FXML
    private TextField productName;

    @FXML
    private TextField receiver;

    @FXML
    private TextField receiverInName;
    
    @FXML
    private RadioButton nameRadio;
    
    @FXML
    private RadioButton referenceRadio;

    @FXML
    private AutoCompleteTextField<String> referenceAutoFill;

    @FXML
    private GridPane referenceGrid;

    @FXML
    private HBox referenceHBox;
    
    @FXML
    private HBox nameHBox;

    @FXML
    private VBox mainVBox;
    
    @FXML
    private VBox containtVBox;
    
    @FXML
    private Label registerConfirm;

    @FXML
    private Label registerConfirmInName;

    @FXML
    private Label totalService;

    @FXML
    private Label totalServiceInName;
    
    @FXML
    private ComboBox<String> customerCombo;
    
    @FXML
    private ComboBox<String> contactCombo;
    
    @FXML
    private ComboBox<String> productCombo;
    
    ServiceDAO serviceDAO;
    EnquiryDAO enquiryDAO;
    CustomersDAO customersDAO;
    ProductsDAO productsDAO;
    private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private ObservableList<String> productsList = FXCollections.observableArrayList();
	private ObservableList<String> nameList = FXCollections.observableArrayList();
	private Map<String, String> emailDetails = new HashMap<String, String>();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	Map<String, String> emailData = new HashMap<String, String>();
	private EnquiryViewVO viewVO = new EnquiryViewVO();
	private CustomersVO custVO = new CustomersVO();
    
    
    public ServiceNewComplaint() {

    	serviceDAO = new ServiceDAO();
    	enquiryDAO = new EnquiryDAO();
    	customersDAO = new CustomersDAO();
    	productsDAO = new ProductsDAO();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			
			mainVBox.getChildren().clear();
			nameGrid.setVisible(false);
			referenceGrid.setVisible(false);
			containtVBox.getChildren().clear();
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			productsList = productsDAO.getProductNameList();
			emailDetails = serviceDAO.getServiceOptionDefaultValues();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);
			productCombo.setItems(productsList);
			
			for(CustomersVO customersVO : customerList)
			{
				if(!nameList.contains(customersVO.getCustomerName()))
				{
					nameList.add(customersVO.getCustomerName());
				}
			}
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if(enquiryVO.getSales().equalsIgnoreCase("Y") )
				{
					refList.add(enquiryVO.getReferenceNo());
				}
			}
			referenceAutoFill.setItems(refList);
			customerCombo.setItems(nameList);
			
			referenceAutoFill.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					referenceGrid.setVisible(false);
				}
			});
			
			 customerToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

				@Override
				public void changed(
						ObservableValue<? extends Toggle> observable,
						Toggle oldValue, Toggle newValue) {
						
					if(referenceRadio.isSelected())
					{
						nameGrid.setVisible(false);
						containtVBox.getChildren().clear();
						customerCombo.getSelectionModel().clearSelection();
						mainVBox.getChildren().clear();
						mainVBox.getChildren().add(referenceHBox);
					}
					else if(nameRadio.isSelected())
					{
						referenceGrid.setVisible(false);
						containtVBox.getChildren().clear();
						referenceAutoFill.setText("");
						mainVBox.getChildren().clear();
						mainVBox.getChildren().add(nameHBox);
						messageAreaInName.setText(emailDetails.get(CommonConstants.KEY_SERVICE_MESSAGE));
					}
					
				}
			});
			 contactCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
						
						for(CustomersVO customersVO : customerList)
						{
							if(newValue!=null)
							{
								if(contactCombo.getSelectionModel().getSelectedItem().equals(customersVO.getContactNumber()))
								{
									ServiceNewComplaint.this.custVO = customersVO;
									receiverInName.setText(customersVO.getEmailId());
									totalServiceInName.setText(String.valueOf(customersVO.getServiceCount()));
									break;
								}
							}
						}
					
				}
			});
			 customerCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
			    	productCombo.getSelectionModel().clearSelection();
			    	nameGrid.setVisible(false);
					
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void createComplaint()
	{
		if(referenceAutoFill.getText()=="")
		{
			Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_REFERENCE);
		}
		else
		{
			natureOfComplaint.setText("");
	    	registerConfirm.setText("");
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if(enquiryVO.getReferenceNo().equals(referenceAutoFill.getText()) )
				{
					totalService.setText(String.valueOf(enquiryVO.getServiceCount()));
					productName.setText(enquiryVO.getProductName());
					receiver.setText(enquiryVO.getEmailId());
					message.setText(emailDetails.get(CommonConstants.KEY_SERVICE_MESSAGE));
					ServiceNewComplaint.this.viewVO=enquiryVO;
					break;
				}
			}
			referenceGrid.setVisible(true);
			containtVBox.getChildren().add(referenceGrid);
		}
    }
	
	
	// displaying customer information in a Pop up for the selected reference No.
    public void customerInfo()
    {
    	try
    	{
    		if(referenceAutoFill.getText()=="")
    		{
    			Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_REFERENCE);
    		}
    		else
    		{
	    		for(EnquiryViewVO enquiryVO : enquiryViewList)
				{
					if(enquiryVO.getReferenceNo().equals(referenceAutoFill.getText()) )
					{
						ServiceNewComplaint.this.viewVO=enquiryVO;
						break;
					}
				}
	    	
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
				.fillTextFieldValues(ServiceNewComplaint.this.viewVO);
    		}
			
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // resister a Complaint for a Reference No.
    public void registerComplaint() 
    {
    	try
    	{
	    	ServiceVO serviceVO = new ServiceVO();
			serviceVO.setCharge(0);
			serviceVO.setComplaint(natureOfComplaint.getText());
			serviceVO.setComplaintDate(formatter.format(new Date()));
			serviceVO.setProductName(productName.getText());
			serviceVO.setDate(CommonConstants.NA);
			serviceVO.setEngineerName(CommonConstants.NA);
			serviceVO.setFeedback(CommonConstants.NA);
			serviceVO.setRating(CommonConstants.NA);
			serviceVO.setReferenceNo(referenceAutoFill.getText());
			serviceVO.setCustomerId(viewVO.getCustomerId());
			serviceVO.setCustomerName(viewVO.getCustomerName());
			serviceVO.setContactNo(viewVO.getContactNumber());
			serviceDAO.newComplaint(serviceVO);
			registerConfirm.setText(CommonConstants.COMPLAINT_REGISTERED);
			registerConfirm.getStyleClass().remove("failure");
			registerConfirm.getStyleClass().add("success");
			registerConfirm.setVisible(true);
    	}
    	catch (Exception e) {
				e.printStackTrace();
		}
	}
	

    public void sendMail()
    {
		try{
			emailData.put(CommonConstants.EMAIL_TO, receiver.getText());
			emailData.put(CommonConstants.EMAIL_CC, cc.getText());
			emailData.put(CommonConstants.EMAIL_BODY, message.getText());
			emailData.put(CommonConstants.EMAIL_SUBJECT, "New Complaint");
			emailData.put(CommonConstants.EMAIL_USERNAME, emailDetails.get(CommonConstants.KEY_SERVICE_EMAIL));
			emailData.put(CommonConstants.EMAIL_PASSWORD, emailDetails.get(CommonConstants.KEY_SERVICE_PASSWORD));
			Email email = new Email(emailData);
			new Thread(email).start();
			emailSent.getStyleClass().remove("failure");
			emailSent.getStyleClass().add("success");
			emailSent.setText("Email send successfully");
			emailSent.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
    
    public void createComplaintInName()
	{
    	if(customerCombo.getSelectionModel().getSelectedIndex()==-1)
		{
			Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_CUST);
		}
		else
		{
			natureOfComplaintInName.setText("");
	    	registerConfirmInName.setText("");
			ObservableList<String> contactsList = FXCollections.observableArrayList();
			contactCombo.getSelectionModel().clearSelection();
			for(CustomersVO customersVO : customerList)
			{
				if(customersVO.getCustomerName().equals(customerCombo.getSelectionModel().getSelectedItem()) )
				{
					contactsList.add(customersVO.getContactNumber());
				}
			}
			contactCombo.setItems(contactsList);
			nameGrid.setVisible(true);
			containtVBox.getChildren().add(nameGrid);
		}
	}
	
	 public void customerInfoInName()
    {
		 try
		 {
			 if(contactCombo.getSelectionModel().getSelectedIndex()==-1)
			 {
				 Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_CONTACT);
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
					.fillTextFieldValuesForCust(ServiceNewComplaint.this.custVO);
			 }
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		}
    }
	 
	 public void registerComplaintInName() 
	    {
	    	try
	    	{
		    	ServiceVO serviceVO = new ServiceVO();
				serviceVO.setCharge(0);
				serviceVO.setComplaint(natureOfComplaintInName.getText());
				serviceVO.setComplaintDate(formatter.format(new Date()));
				serviceVO.setProductName(productCombo.getSelectionModel().getSelectedItem());
				serviceVO.setDate(CommonConstants.NA);
				serviceVO.setEngineerName(CommonConstants.NA);
				serviceVO.setFeedback(CommonConstants.NA);
				serviceVO.setRating(CommonConstants.NA);
				serviceVO.setCustomerId(custVO.getId());
				serviceVO.setCustomerName(custVO.getCustomerName());
				serviceVO.setContactNo(contactCombo.getSelectionModel().getSelectedItem());
				serviceDAO.newComplaint(serviceVO);
				registerConfirmInName.setText(CommonConstants.COMPLAINT_REGISTERED);
				registerConfirmInName.getStyleClass().remove("failure");
				registerConfirmInName.getStyleClass().add("success");
				registerConfirmInName.setVisible(true);
	    	}
	    	catch (Exception e) {
					e.printStackTrace();
			}
		}
	 
	 	public void sendMailInName()
	    {
			try{
				emailData.put(CommonConstants.EMAIL_TO, receiverInName.getText());
				emailData.put(CommonConstants.EMAIL_CC, ccInName.getText());
				emailData.put(CommonConstants.EMAIL_BODY, messageAreaInName.getText());
				emailData.put(CommonConstants.EMAIL_SUBJECT, "New Complaint");
				emailData.put(CommonConstants.EMAIL_USERNAME, emailDetails.get(CommonConstants.KEY_SERVICE_EMAIL));
				emailData.put(CommonConstants.EMAIL_PASSWORD, emailDetails.get(CommonConstants.KEY_SERVICE_PASSWORD));
				Email email = new Email(emailData);
				new Thread(email).start();
				emailSentInName.getStyleClass().remove("failure");
				emailSentInName.getStyleClass().add("success");
				emailSentInName.setText("Email send successfully");
				emailSentInName.setVisible(true);
			}
			catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage());
			}
		}
}
