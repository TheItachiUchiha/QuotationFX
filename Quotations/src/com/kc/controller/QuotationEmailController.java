package com.kc.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.QuotationDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.Validation;

public class QuotationEmailController implements Initializable  {

	private static final Logger LOG = LogManager.getLogger(QuotationEmailController.class);
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	Validation validation;
	QuotationDAO quotationDAO;
	public QuotationEmailController()
	{
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		validation = new Validation();
		quotationDAO = new QuotationDAO();
	}
	
	@FXML
	private ComboBox<String> monthCombo;
	@FXML
	private ComboBox<String> yearCombo;
	@FXML
	private ComboBox<String> referenceCombo;
	@FXML
    private TextField estimatedPrice;

    @FXML
    private TextField ecity;

    @FXML
    private TextField ecompanyName;

    @FXML
    private TextField ecustomerName;

    @FXML
    private TextArea ecustomerRequirements;

    @FXML
    private TextField ecustomerType;

    @FXML
    private TextField edateOfEnquiry;

    @FXML
    private TextField epriceEstimation;

    @FXML
    private TextField eproductName;

    @FXML
    private TextField epurchasePeriod;

    @FXML
    private TextField equotationPreparation;

    @FXML
    private TextField ereferedBy;

    @FXML
    private TextField ereferenceNo;

    @FXML
    private TextField ecustomerDocument;
    
    @FXML
    private Button search;
    
    @FXML
    private HBox referenceHBox;
    
    @FXML
    private GridPane emailGrid;
    
    @FXML
    private GridPane enquiryGrid;
    
    @FXML
    private  Button enquiryDetails;
    
    @FXML
    private  Button prepareMail;
    
    @FXML
    private TextField customerName;

    @FXML
    private TextArea message;

    @FXML
    private Label messageEmail;

    @FXML
    private Label messageSent;
    
    @FXML
    private TextField priceEstimation;

    @FXML
    private TextField productName;

    @FXML
    private TextField quotationPreparation;

    @FXML
    private TextField receiver;

    @FXML
    private TextField referenceNo;
    
    @FXML
    private TextField sender;

    @FXML
    private TextField subject;
    @FXML
    private Button openQuotation;

    int flag=0;
	
	private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	private Map<String, String> defaultValues = new HashMap<String, String>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = fillEnquiryViewListFromEnquiryList(enquiryList);
			search.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					try
					{
						refList.clear();
						if(monthCombo.getSelectionModel().getSelectedIndex()==-1|| yearCombo.getSelectionModel().getSelectedIndex()==-1)
						{
							Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
						}
						else
						{
							for(EnquiryViewVO enquiryVO : enquiryViewList)
							{
								if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem())&&(enquiryVO.getPriceEstimation().equalsIgnoreCase("Y")&&(enquiryVO.getQuotationPreparation().equalsIgnoreCase("Y") && enquiryVO.getEmailSent().equalsIgnoreCase("N"))))
								{
									refList.add(enquiryVO.getReferenceNo());
								}
							}
							if(refList.isEmpty())
							{
								Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
							}
							else
							{
								referenceCombo.setItems(refList);
								referenceHBox.setVisible(true);	
								flag=0;
							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
			enquiryDetails.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
					{
						Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_REFERENCE);
					}
					else
					{
						enquiryGrid.setVisible(true);
						for(EnquiryViewVO enquiryViewVO: enquiryViewList)
						{
							if(referenceCombo.getSelectionModel().getSelectedItem().equals(enquiryViewVO.getReferenceNo()))
							{
								ereferenceNo.setText(enquiryViewVO.getReferenceNo());
								eproductName.setText(enquiryViewVO.getProductName());
								ecustomerType.setText(enquiryViewVO.getCustomerType());
								ecustomerName.setText(enquiryViewVO.getCustomerName());
								ecompanyName.setText(enquiryViewVO.getCompanyName());
								ecustomerRequirements.setText(enquiryViewVO.getCustomerRequirement());
								ereferedBy.setText(enquiryViewVO.getReferedBy());
								edateOfEnquiry.setText(enquiryViewVO.getDateOfEnquiry());
								epriceEstimation.setText(enquiryViewVO.getPriceEstimation());
								ecity.setText(enquiryViewVO.getCity());
								equotationPreparation.setText(enquiryViewVO.getQuotationPreparation());
								ecustomerDocument.setText(enquiryViewVO.getCustomerFile());
								epurchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
							}
						}
						flag=1;
					}
				}
			});
			prepareMail.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					if(flag==1)
					{
						emailGrid.setVisible(true);
						for(EnquiryViewVO enquiryViewVO :enquiryViewList)
						{
							if(referenceCombo.getSelectionModel().getSelectedItem().equals(enquiryViewVO.getReferenceNo()))
							{
								referenceNo.setText(enquiryViewVO.getReferenceNo());
								customerName.setText(enquiryViewVO.getCustomerName());
								productName.setText(enquiryViewVO.getProductName());
								priceEstimation.setText(enquiryViewVO.getPeDate());
								estimatedPrice.setText(String.valueOf(enquiryViewVO.getMargin()));
								quotationPreparation.setText(enquiryViewVO.getQpDate());
							}
						}
					}
					else
					{
						Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.VIEW_ENQUIRY);
					}
				}
			});
			monthCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String paramT1, String paramT2) {
					referenceHBox.setVisible(false);
					emailGrid.setVisible(false);
					enquiryGrid.setVisible(false);
					
				}
			});
			yearCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String paramT1, String paramT2) {
					referenceHBox.setVisible(false);
					emailGrid.setVisible(false);
					enquiryGrid.setVisible(false);
					
				}
			});
			referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String paramT1, String paramT2) {
					emailGrid.setVisible(false);
					enquiryGrid.setVisible(false);
					
				}
			});
			openQuotation.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					
					try
					{
						defaultValues = quotationDAO.getCustomDefaultValues();
						Desktop.getDesktop().open(new File(defaultValues.get(CommonConstants.KEY_QUOTATION_WORD_PATH)+"\\"+referenceNo.getText()+"_"+productName.getText()+".docx"));
					}
					catch (IOException e)
					{
						Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
					}
					
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public ObservableList<EnquiryViewVO> fillEnquiryViewListFromEnquiryList(ObservableList<EnquiryVO> enquiryList)
	{
		ObservableList<EnquiryViewVO> tempList = FXCollections.observableArrayList();
		
		
		for(EnquiryVO enquiryVO : enquiryList)
		{
			EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
			enquiryViewVO.setId(enquiryVO.getId());
			enquiryViewVO.setDateOfEnquiry(enquiryVO.getDate());
			enquiryViewVO.setProductName(enquiryVO.getProductName());
			enquiryViewVO.setPurchasePeriod(enquiryVO.getPurchasePeriod());
			enquiryViewVO.setReferedBy(enquiryVO.getReferedBy());
			enquiryViewVO.setReferenceNo(enquiryVO.getRefNumber());
			enquiryViewVO.setCustomerRequirement(enquiryVO.getCustomerrequirements());
			enquiryViewVO.setCustomerFile(enquiryVO.getCustomerDocument());
			enquiryViewVO.setPriceEstimation(enquiryVO.getPriceEstimation());
			enquiryViewVO.setMargin(enquiryVO.getMargin());
			enquiryViewVO.setPeDate(enquiryVO.getPeDate());
			enquiryViewVO.setDateOfEnquiry(enquiryVO.getDate());
			enquiryViewVO.setQuotationPreparation(enquiryVO.getQuotationPreparation());
			enquiryViewVO.setEmailSent(enquiryVO.getEmailSent());
			enquiryViewVO.setSales(enquiryVO.getSales());
			enquiryViewVO.setQpDate(enquiryVO.getQpDate());
			if(enquiryVO.getFlag().equalsIgnoreCase("C"))
			{
				enquiryViewVO.setEnquiryType("Custom");
			}
			else if(enquiryVO.getFlag().equalsIgnoreCase("S"))
			{
				enquiryViewVO.setEnquiryType("Standard");
			}
			
			for(CustomersVO customersVO : customerList)
			{
				if(customersVO.getId() == enquiryVO.getCustomerId())
				{
					enquiryViewVO.setCustomerName(customersVO.getCustomerName());
					enquiryViewVO.setCity(customersVO.getCity());
					enquiryViewVO.setCompanyName(customersVO.getCompanyName());
					enquiryViewVO.setState(customersVO.getState());
					enquiryViewVO.setAddress(customersVO.getAddress());
					enquiryViewVO.setEmailId(customersVO.getEmailId());
					enquiryViewVO.setTinNumber(customersVO.getTinNumber());
					enquiryViewVO.setContactNumber(customersVO.getContactNumber());
					enquiryViewVO.setCustomerId(customersVO.getId());
					if(customersVO.getCustomerType().equalsIgnoreCase("Dealer"))
					{
						enquiryViewVO.setCustomerType("Dealer");
					}
					else if(customersVO.getCustomerType().equalsIgnoreCase("End User"))
					{
						enquiryViewVO.setCustomerType("End User");
					}
					enquiryViewVO.setProductId(enquiryVO.getProductId());
				}
			}
			tempList.add(enquiryViewVO);
		}
		return tempList;
	}
}
