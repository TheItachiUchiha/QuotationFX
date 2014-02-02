package com.kc.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.QuotationDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.Email;
import com.kc.util.FileUtils;
import com.kc.util.QuotationUtil;
import com.kc.util.Validation;

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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class QuotationEmailResendController implements Initializable {


	private static final Logger LOG = LogManager.getLogger(QuotationEmailResendController.class);
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	Validation validation;
	QuotationDAO quotationDAO;
	public QuotationEmailResendController()
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
    private TextField eproductName;

    @FXML
    private TextField epurchasePeriod;

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
    private  ToggleButton enquiryDetails;
    
    @FXML
	 private ToggleGroup buttonToggle;
    
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
    private TextField cc;

    @FXML
    private TextField subject;
    @FXML
    private Button openQuotation;
    @FXML
    private Label attachmentLabel;

    //int flag=0;
    
    String newFileName = "";
	
	private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	private Map<String, String> defaultValues = new HashMap<String, String>();
	private EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
	Map<String, String> emailData = new HashMap<String, String>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			
			customerList = customersDAO.getCustomers();
			
			search.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					try
					{
						enquiryList = enquiryDAO.getEnquries();
						enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
						refList.clear();
						if(monthCombo.getSelectionModel().getSelectedIndex()==-1|| yearCombo.getSelectionModel().getSelectedIndex()==-1)
						{
							Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
						}
						else
						{
							for(EnquiryViewVO enquiryVO : enquiryViewList)
							{
								if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem())&&(enquiryVO.getPriceEstimation().equalsIgnoreCase("Y")&&(enquiryVO.getQuotationPreparation().equalsIgnoreCase("Y") && enquiryVO.getEmailSent().equalsIgnoreCase("Y"))))
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
								//flag=0;
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
					for(EnquiryViewVO enquiryViewVO: enquiryViewList)
					{
						if(referenceCombo.getSelectionModel().getSelectedItem().equals(enquiryViewVO.getReferenceNo()))
						{
							QuotationEmailResendController.this.enquiryViewVO = enquiryViewVO;
						}
					}
					
				}
			});
			/*enquiryDetails.setOnAction(new EventHandler<ActionEvent>() {
				
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
								ecustomerDocument.setText(enquiryViewVO.getCustomerFile());
								epurchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
								QuotationEmailResendController.this.enquiryViewVO = enquiryViewVO;
							}
						}
						flag=1;
					}
				}
			});*/
			buttonToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			    public void changed(ObservableValue<? extends Toggle> ov,
				        Toggle old_toggle, Toggle new_toggle) {
				        if (old_toggle !=null)
				        {
				        	enquiryGrid.setVisible(false);
				        }
				        else if (new_toggle !=null) {
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
										ecustomerDocument.setText(enquiryViewVO.getCustomerFile());
										epurchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
										//QuotationEmailResendController.this.enquiryViewVO = enquiryViewVO;
									}
								}
								//flag=1;
							}
						}
				    }
				});
			prepareMail.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					/*if(flag==1)
					{*/
					if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
					{
						Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_REFERENCE);
					}
					else
					{
						receiver.setText(enquiryViewVO.getEmailId());
						if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("STANDARD"))
						{
								referenceNo.setText(enquiryViewVO.getReferenceNo());
								customerName.setText(enquiryViewVO.getCustomerName());
								productName.setText(enquiryViewVO.getProductName());
								priceEstimation.setText(enquiryViewVO.getPeDate());
								estimatedPrice.setText(String.valueOf(enquiryViewVO.getTotalRevenue()));
								quotationPreparation.setText(enquiryViewVO.getQpDate());
								defaultValues = quotationDAO.getStandardProductPath(enquiryViewVO.getProductId());
								emailGrid.setVisible(true);
						}
						else
						{	
							referenceNo.setText(enquiryViewVO.getReferenceNo());
							customerName.setText(enquiryViewVO.getCustomerName());
							productName.setText(enquiryViewVO.getProductName());
							priceEstimation.setText(enquiryViewVO.getPeDate());
							estimatedPrice.setText(String.valueOf(enquiryViewVO.getTotalRevenue()));
							quotationPreparation.setText(enquiryViewVO.getQpDate());
							defaultValues = quotationDAO.getCustomDefaultValues();
							emailGrid.setVisible(true);
						}
					}
					/*}
					else
					{
						Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.VIEW_ENQUIRY);
					}*/
				}
			});
			
			openQuotation.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					
					try
					{
						File newFile = new File(defaultValues.get(CommonConstants.KEY_QUOTATION_WORD_PATH)+"\\"+referenceNo.getText()+"_"+customerName.getText()+".docx");
						if(newFile.exists())
						{
							Desktop.getDesktop().open(newFile);
						}
						else
						{
							Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
						}
					}
					catch (Exception e)
					{
						Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
						e.printStackTrace();
					}
					
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void createPDF()
	{
		try{
			if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("STANDARD"))
			{
				defaultValues = quotationDAO.getStandardProductPath(enquiryViewVO.getProductId());
			}
			else if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("CUSTOM"))
			{
				defaultValues = quotationDAO.getCustomDefaultValues();
			}
			
			File file = new File(defaultValues.get(CommonConstants.KEY_QUOTATION_WORD_PATH)+"\\"+referenceNo.getText()+"_"+customerName.getText()+".docx");
			if(file.exists())
			{
				try
				{
					newFileName = FileUtils.createPDF(file, defaultValues.get(CommonConstants.KEY_QUOTATION_PDF_PATH));
				}
				catch (Throwable e)
				{
					Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.PDF_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
				}
				emailData.put(CommonConstants.EMAIL_ATTACHMENT, newFileName );
				attachmentLabel.setText(newFileName);
				messageEmail.getStyleClass().remove("failure");
				messageEmail.getStyleClass().add("success");
				messageEmail.setText("PDF created successfully");
				messageEmail.setVisible(true);
			}
			else
			{
				Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
	public void openPdf()
	{
		try{
				Desktop.getDesktop().open(new File(newFileName));
		}
		catch (Exception e) {
			Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
			LOG.error(e.getMessage());
		}
	}
	
	public void deletePDF()
	{
		try{
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
					"Do you want to delete this Pdf", "Confirm",
					"Delete Pdf", DialogOptions.OK_CANCEL);
			if (response.equals(DialogResponse.OK))
			{
				FileUtils.deleteFile(new File(newFileName));
				messageEmail.getStyleClass().remove("failure");
				messageEmail.getStyleClass().add("success");
				messageEmail.setText("PDF deleted successfully");
				messageEmail.setVisible(true);
				attachmentLabel.setText("");
			}
		}
		catch (Exception e) {
			Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
			LOG.error(e.getMessage());
		}
	}
	
	public void sendEmail()
	{
		try{
			Map<String,String> emailMap = new HashMap<String,String>();
			emailMap = quotationDAO.getEmailDetails();
			emailData.put(CommonConstants.EMAIL_TO, receiver.getText());
			emailData.put(CommonConstants.EMAIL_BODY, message.getText());
			emailData.put(CommonConstants.EMAIL_SUBJECT, subject.getText());
			emailData.put(CommonConstants.EMAIL_USERNAME, emailMap.get(CommonConstants.KEY_QUOTATION_EMAIL));
			emailData.put(CommonConstants.EMAIL_PASSWORD, emailMap.get(CommonConstants.KEY_QUOTATION_PASSWORD));
			Email email = new Email(emailData);
			new Thread(email).start();
			quotationDAO.updateEnquiryEmailSentStatus(enquiryViewVO.getId(), formatter.format(new Date()));
			messageSent.getStyleClass().remove("failure");
			messageSent.getStyleClass().add("success");
			messageSent.setText("Email send successfully");
			messageSent.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	public void viewFile()
	{
		try
		{
			File newFile = new File(ecustomerDocument.getText());
			if(newFile.exists())
			{
				Desktop.getDesktop().open(newFile);
			}
			else
			{
				Dialogs.showErrorDialog(LoginController.primaryStage,CommonConstants.FILE_ACCESS_FAILED_MSG,CommonConstants.FILE_ACCESS_FAILED);
			}
		} catch (IOException e) {
			Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
		}
	}

}
