package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.DispatchDAO;
import com.kc.model.DispatchVO;
import com.kc.util.Email;
import com.kc.util.Validation;

import eu.schudt.javafx.controls.calendar.DatePicker;

public class ProductDispatchModifyController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ProductDispatchModifyController.class);
	
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
    private TextField numberOfItems;

    @FXML
    private TextField receiver;

    @FXML
    private TextField shippingTo;

    @FXML
    private TextField trackingNo;

    @FXML
    private TextField transporter;
    
    DispatchDAO dispatchDAO;
   	private DatePicker calendar;
   	private DatePicker calendar2;
    Validation validation;
    Map<String, String> emailData = new HashMap<String, String>();
   	private Map<String, String> emailDetails = new HashMap<String, String>();
   	private DispatchVO dispatchVO = new DispatchVO();
    
    
    public ProductDispatchModifyController() {
    	
    	dispatchDAO = new DispatchDAO();
		validation = new Validation();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
		
			validation.allowAsAmount(FreightAmount);
			validation.allowDigit(numberOfItems);
			
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//fill the form for selected Dispatch table row
	
	public void fillTextFieldValues(DispatchVO dispatchVO)
	{
		try
		{
			billingName.setText(dispatchVO.getBillingName());
			companyName.setText(dispatchVO.getCompanyName());
			((TextField)calendar2.getChildren().get(0)).setText(dispatchVO.getDispatchDate());
			((TextField)calendar.getChildren().get(0)).setText(dispatchVO.getInvoiceDate());
			FreightAmount.setText(String.valueOf(dispatchVO.getFreightAmount()));
			numberOfItems.setText(String.valueOf(dispatchVO.getNoOfItems()));
			Freightmode.setText(dispatchVO.getFreightMode());
			invoiceNo.setText(dispatchVO.getInvoiceNo());
			shippingTo.setText(dispatchVO.getShippingTo());
			trackingNo.setText(dispatchVO.getTrackingNo());
			transporter.setText(dispatchVO.getTransporter());
			
			ProductDispatchModifyController.this.dispatchVO=dispatchVO;
			
			emailDetails = dispatchDAO.getDispatchOptionDefaultValues();
			body.setText(emailDetails.get(CommonConstants.KEY_DISPATCH_MESSAGE));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// update the product dispatch Entry 
	public void updateDispatch()
	{
		try
		{
			DispatchVO dispatchVO = new DispatchVO();
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
			dispatchVO.setId(ProductDispatchModifyController.this.dispatchVO.getId());
			dispatchDAO.updateDispatch(dispatchVO);
			message.setText(CommonConstants.DISPATCH_UPDATE);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
			message.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
    }

	//Send a Mail
    public void sendMail()
    {
		try{
			emailData.put(CommonConstants.EMAIL_TO, receiver.getText());
			emailData.put(CommonConstants.EMAIL_BODY, body.getText());
			emailData.put(CommonConstants.EMAIL_SUBJECT, "Dispatch Details");
			emailData.put(CommonConstants.EMAIL_USERNAME, emailDetails.get(CommonConstants.KEY_DISPATCH_EMAIL));
			emailData.put(CommonConstants.EMAIL_PASSWORD, emailDetails.get(CommonConstants.KEY_DISPATCH_PASSWORD));
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
