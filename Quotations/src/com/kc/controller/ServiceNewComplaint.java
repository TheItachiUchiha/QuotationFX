package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
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
    private TextField companyName;

    @FXML
    private TextField contactNo;

    @FXML
    private TextField customerLocation;

    @FXML
    private TextField customerName;

    @FXML
    private ToggleGroup customerToggle;

    @FXML
    private TextField emailId;

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
    private TextField productNameInName;

    @FXML
    private TextField receiver;

    @FXML
    private TextField receiverInName;

    @FXML
    private AutoCompleteTextField<String> referenceAutoFill;

    @FXML
    private GridPane referenceGrid;

    @FXML
    private HBox referenceHBox;

    @FXML
    private Label registerConfirm;

    @FXML
    private Label registerConfirmInName;

    @FXML
    private Label totalService;

    @FXML
    private Label totalServiceInName;
    
    ServiceDAO serviceDAO;
    EnquiryDAO enquiryDAO;
    CustomersDAO customersDAO;
    private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private Map<String, String> emailDetails = new HashMap<String, String>();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	Map<String, String> emailData = new HashMap<String, String>();
	private EnquiryViewVO viewVO = new EnquiryViewVO();
    
    
    public ServiceNewComplaint() {

    	serviceDAO = new ServiceDAO();
    	enquiryDAO = new EnquiryDAO();
    	customersDAO = new CustomersDAO();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			emailDetails = serviceDAO.getServiceOptionDefaultValues();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);
			
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if(enquiryVO.getSales().equalsIgnoreCase("Y") )
				{
					refList.add(enquiryVO.getReferenceNo());
				}
			}
			referenceAutoFill.setItems(refList);
			
			referenceAutoFill.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					referenceGrid.setVisible(false);
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
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if(enquiryVO.getReferenceNo().equals(referenceAutoFill.getText()) )
				{
					productName.setText(enquiryVO.getProductName());
					receiver.setText(enquiryVO.getEmailId());
					message.setText(emailDetails.get(CommonConstants.KEY_SERVICE_MESSAGE));
					ServiceNewComplaint.this.viewVO=enquiryVO;
				}
			}
			referenceGrid.setVisible(true);
		}
    }
	// displaying customer information in a Pop up for the selected reference No.
    public void customerInfo()
    {
    	try
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
			.fillTextFieldValues(ServiceNewComplaint.this.viewVO);
			
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
    // resister a Complaint for a Reference No.
    public void registerComplaint() 
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
		for(EnquiryViewVO enquiryVO : enquiryViewList)
		{
			if(enquiryVO.getReferenceNo().equals(referenceAutoFill.getText()) )
			{
				serviceVO.setCustomerId(enquiryVO.getCustomerId());
				serviceVO.setCustomerName(enquiryVO.getCustomerName());
				serviceVO.setContactNo(enquiryVO.getContactNumber());
				break;
			}
		}
		serviceDAO.newComplaint(serviceVO);
		registerConfirm.setText(CommonConstants.COMPLAINT_REGISTERED);
		registerConfirm.getStyleClass().remove("failure");
		registerConfirm.getStyleClass().add("success");
		registerConfirm.setVisible(true);
	
    }

    public void sendMail()
    {
		try{
			emailData.put(CommonConstants.EMAIL_TO, receiver.getText());
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
}
