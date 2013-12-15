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
import com.kc.util.FileUtils;
import com.kc.util.QuotationUtil;
import com.kc.util.Validation;

public class QuotationModifyController implements Initializable  {
	private static final Logger LOG = LogManager.getLogger(PriceEstimationNewController.class);
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	Validation validation;
	QuotationDAO quotationDAO;;
	public QuotationModifyController()
	{
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		validation = new Validation();
		quotationDAO= new QuotationDAO();
	}
	
	@FXML
	private ComboBox<String> monthCombo;
	@FXML
	private ComboBox<String> yearCombo;
	@FXML
	private ComboBox<String> referenceCombo;
	@FXML
	private Button enquiryDetails;
	 @FXML
	    private TextField customerName;

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
	    private TextField eproductName;

	    @FXML
	    private TextField epurchasePeriod;

	    @FXML
	    private TextField ereferedBy;

	    @FXML
	    private TextField ereferenceNo;

	    @FXML
	    private Label message;
	    
	    @FXML
	    private TextField priceEstimationDate;

	    @FXML
	    private TextField productName;

	    @FXML
	    private TextField referenceNo;
	    @FXML
	    private GridPane enquiryGrid;
	    @FXML
	    private TextField ecustomerFile;
	    @FXML
	    private Button viewFile;
	    @FXML
	    private Button search;
	    @FXML
	    private GridPane quotationGrid;
	    @FXML
	    private HBox referenceHBox;
	    
	    @FXML
	    private Button modifyQuotation;
	    
	    @FXML
	    private Button openQuotation;
	    
	    @FXML
	    private Button  deleteQuotation;
	    
	    @FXML
	    private TextField quotationPreparationDate;
	    
	    int flag=0;
	
	private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	private Map<String, String> defaultValues = new HashMap<String, String>();
	private EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
	        viewFile.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					try {
						Desktop.getDesktop().open(new File(ecustomerFile.getText()));
					} catch (IOException e) {
							Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
					}
				}
			});
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
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
								if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem())&&(enquiryVO.getPriceEstimation()).equalsIgnoreCase("Y")&&(enquiryVO.getQuotationPreparation()).equalsIgnoreCase("Y"))
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
								ecity.setText(enquiryViewVO.getCity());
								ecustomerFile.setText(enquiryViewVO.getCustomerFile());
								epurchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
							}
						}
						flag=1;
					}
				}
			});
			modifyQuotation.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					if(flag==1)
					{
						for(EnquiryViewVO enquiryViewVO :enquiryViewList)
						{
							if(referenceCombo.getSelectionModel().getSelectedItem().equals(enquiryViewVO.getReferenceNo()))
							{
								referenceNo.setText(enquiryViewVO.getReferenceNo());
								customerName.setText(enquiryViewVO.getCustomerName());
								productName.setText(enquiryViewVO.getProductName());
								priceEstimationDate.setText(enquiryViewVO.getPeDate());
								estimatedPrice.setText(String.valueOf(enquiryViewVO.getTotalRevenue()));
								quotationPreparationDate.setText(enquiryViewVO.getQpDate());
								QuotationModifyController.this.enquiryViewVO=enquiryViewVO;
								quotationGrid.setVisible(true);
							}
						}
						if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("Standard"))
						{
							defaultValues = quotationDAO.getStandardProductPath(enquiryViewVO.getProductId());
						}
						else if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("Custom"))
						{
							defaultValues=quotationDAO.getCustomDefaultValues();
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
					quotationGrid.setVisible(false);
					enquiryGrid.setVisible(false);
					
				}
			});
			yearCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String paramT1, String paramT2) {
					referenceHBox.setVisible(false);
					quotationGrid.setVisible(false);
					enquiryGrid.setVisible(false);
					
				}
			});
			referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String paramT1, String paramT2) {
					quotationGrid.setVisible(false);
					enquiryGrid.setVisible(false);
				}
			});
			openQuotation.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					
					try
					{
						Desktop.getDesktop().open(new File(defaultValues.get(CommonConstants.KEY_QUOTATION_WORD_PATH)+"\\"+referenceNo.getText()+"_"+customerName.getText()+".docx"));
					}
					catch (Exception e)
					{
						Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
					}
					
				}
			});
			deleteQuotation.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					File file = new File(defaultValues.get(CommonConstants.KEY_QUOTATION_WORD_PATH)+"\\"+referenceNo.getText()+"_"+customerName.getText()+".docx");
					if(file.exists())
					{
						FileUtils.deleteFile(file);
						quotationDAO.UpdateEnquiry(enquiryViewVO.getId(),"N",CommonConstants.NA);
						message.setText(CommonConstants.QUOTATION_DELETED);
						message.getStyleClass().remove("success");
						message.getStyleClass().add("failure");
						message.setVisible(true);
					}
					else
					{
						Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
					}
					
				};
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
