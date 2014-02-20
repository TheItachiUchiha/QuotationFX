package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import com.kc.dao.DispatchDAO;
import com.kc.model.DispatchVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.Email;
import com.kc.util.QuotationUtil;
import com.kc.util.Validation;

import eu.schudt.javafx.controls.calendar.DatePicker;

public class ProductDispatchCreateController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ProductDispatchCreateController.class);

	@FXML
    private TextField FreightAmount;

    @FXML
    private TextField Freightmode;

    @FXML
    private TextField billingName;

    @FXML
    private TextArea body;

    @FXML
    private TextField cc;

    @FXML
    private TextField companyName;

    @FXML
    private GridPane dispatchGrid;

    @FXML
    private TextField invoiceNo;
    
    @FXML
    private Label message;

    @FXML
    private Label messageMail;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private TextField numberOfItems;

    @FXML
    private ComboBox<String> referenceCombo;

    @FXML
    private HBox referenceHBox;

    @FXML
    private TextField receiver;

    @FXML
    private TextField shippingTo;

    @FXML
    private TextField trackingNo;

    @FXML
    private TextField transporter;
   
    @FXML
    private ComboBox<String> yearCombo;
    
    private ObservableList<String> monthList = FXCollections.observableArrayList();
   	private ObservableList<String> yearList = FXCollections.observableArrayList();
   	private ObservableList<EnquiryViewVO> mainList = FXCollections.observableArrayList();
   	private ObservableList<String> reflist = FXCollections.observableArrayList();
   	Map<String, String> emailData = new HashMap<String, String>();
   	Map<String,String> emailMap = new HashMap<String,String>();
   	
   	String emailId="";
   	String startDate;
   	String endDate;
   	DispatchDAO dispatchDAO;
   	private DatePicker calendar;
   	private DatePicker calendar2;
   	Validation validation;
   	
   	public ProductDispatchCreateController() {
		dispatchDAO = new DispatchDAO();
		validation = new Validation();
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			validation.allowAsAmount(FreightAmount);
			validation.allowDigit(numberOfItems);
			
			emailMap = dispatchDAO.getDispatchOptionDefaultValues();
			body.setText(emailMap.get(CommonConstants.KEY_DISPATCH_MESSAGE));
			
			calendar = new DatePicker(Locale.ENGLISH);
    		calendar.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    		calendar.getCalendarView().todayButtonTextProperty().set("Today");
    		calendar.getCalendarView().setShowWeeks(false);
    		calendar.getStylesheets().add("com/kc/style/DatePicker.css");
    		((TextField)calendar.getChildren().get(0)).setEditable(false);
    		((TextField)calendar.getChildren().get(0)).setPrefWidth(200);
    		
    		calendar2 = new DatePicker(Locale.ENGLISH);
    		calendar2.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    		calendar2.getCalendarView().todayButtonTextProperty().set("Today");
    		calendar2.getCalendarView().setShowWeeks(false);
    		calendar2.getStylesheets().add("com/kc/style/DatePicker.css");
    		((TextField)calendar2.getChildren().get(0)).setEditable(false);
    		((TextField)calendar2.getChildren().get(0)).setPrefWidth(200);
    		dispatchGrid.add(calendar, 1, 1);
    		dispatchGrid.add(calendar2, 1, 6);
    		
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			yearCombo.setItems(yearList);
			monthCombo.setItems(monthList);
			
			monthCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					referenceHBox.setVisible(false);
					//mainBox.setVisible(false);
					dispatchGrid.setVisible(false);
					
				}
			});
			
			yearCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					referenceHBox.setVisible(false);
					//mainBox.setVisible(false);
					dispatchGrid.setVisible(false);
					
				}
			});
			referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					for(EnquiryViewVO enquiryViewVO : mainList)
					{
						if(newValue.equals(enquiryViewVO.getReferenceNo()))
						{
							receiver.setText(enquiryViewVO.getEmailId());
							emailId = enquiryViewVO.getEmailId();
						}
					}
					dispatchGrid.setVisible(false);
					
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void search()
	{
		try
		{
			if(monthCombo.getSelectionModel().getSelectedIndex()==-1 || yearCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
			}
			else
			{
				startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				mainList=dispatchDAO.getDispatchEnquiryList(startDate, endDate);
				if(mainList.isEmpty())
				{
					Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_ENQUIRY);
				}
				else
				{
					for(EnquiryViewVO enquiryViewVO : mainList)
					{
						reflist.add(enquiryViewVO.getReferenceNo());
					}
					referenceCombo.setItems(reflist);
					referenceHBox.setVisible(true);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void go()
	{
		if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
		{
			Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_REFERENCE);
		}
		else
		{
			dispatchGrid.setVisible(true);
		}
	}
	
	//create a product Dispatch Entry
	public void saveDispatch()
	{
		DispatchVO dispatchVO = new DispatchVO();
		dispatchVO.setReferenceNo(referenceCombo.getSelectionModel().getSelectedItem());
		dispatchVO.setBillingName(billingName.getText());
		dispatchVO.setCompanyName(companyName.getText());
		dispatchVO.setDispatchDate(((TextField)calendar2.getChildren().get(0)).getText());
		dispatchVO.setInvoiceDate(((TextField)calendar.getChildren().get(0)).getText());
		dispatchVO.setFreightAmount(Double.parseDouble(FreightAmount.getText()));
		dispatchVO.setNoOfItems(Integer.parseInt(numberOfItems.getText()));
		dispatchVO.setFreightMode(Freightmode.getText());
		dispatchVO.setInvoiceNo(invoiceNo.getText());
		dispatchVO.setShippingTo(shippingTo.getText());
		dispatchVO.setTrackingNo(trackingNo.getText());
		dispatchVO.setTransporter(transporter.getText());
		dispatchVO.setCustomerEmail(emailId);
		dispatchDAO.newDispatch(dispatchVO);
		message.setText(CommonConstants.DISPATCH_DONE);
		message.getStyleClass().remove("failure");
		message.getStyleClass().add("success");
		message.setVisible(true);
	}
	
	//Send a Mail
	public void sendMail()
	{
		try{
			
			emailData.put(CommonConstants.EMAIL_TO, receiver.getText());
			emailData.put(CommonConstants.EMAIL_BODY, body.getText());
			emailData.put(CommonConstants.EMAIL_SUBJECT, "Dispatch Details");
			emailData.put(CommonConstants.EMAIL_USERNAME, emailMap.get(CommonConstants.KEY_DISPATCH_EMAIL));
			emailData.put(CommonConstants.EMAIL_PASSWORD, emailMap.get(CommonConstants.KEY_DISPATCH_PASSWORD));
			Email email = new Email(emailData);
			new Thread(email).start();
			messageMail.getStyleClass().remove("failure");
			messageMail.getStyleClass().add("success");
			messageMail.setText("Email send successfully");
			messageMail.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
}
