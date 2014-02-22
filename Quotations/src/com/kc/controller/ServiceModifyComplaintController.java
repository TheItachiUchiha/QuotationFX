package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.kc.dao.ServiceDAO;
import com.kc.model.ComplaintVO;
import com.kc.util.Email;

public class ServiceModifyComplaintController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceModifyComplaintController.class);
	
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
	    private GridPane referenceGrid;

	    @FXML
	    private Label registerConfirm;

	    @FXML
	    private Label registerConfirmInName;

	    @FXML
	    private Label totalService;

	    @FXML
	    private Label totalServiceInName;
	    
	    private ComplaintVO complaintVO = new ComplaintVO();
	    SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	    Map<String, String> emailData = new HashMap<String, String>();
	    private Map<String, String> emailDetails = new HashMap<String, String>();
	    ServiceDAO serviceDAO;
	    
	    public ServiceModifyComplaintController() {
			serviceDAO = new ServiceDAO();
		}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		emailDetails = serviceDAO.getServiceOptionDefaultValues();
		
	}
	public void fillTextFieldValues(ComplaintVO complaintVO)
	{
		productName.setText(complaintVO.getProductName());
		natureOfComplaint.setText(complaintVO.getComplaint());
		totalService.setText(String.valueOf(complaintVO.getServiceCount()));
		receiver.setText(complaintVO.getEmailId());
		message.setText(emailDetails.get(CommonConstants.KEY_SERVICE_MESSAGE));
		ServiceModifyComplaintController.this.complaintVO=complaintVO;
	}
	
	public void UpdateComplaint()
	{
		ComplaintVO complaintVO = new ComplaintVO();
		complaintVO.setComplaint(natureOfComplaint.getText());
		complaintVO.setDateOfComplaint(formatter.format(new Date()));
		complaintVO.setId(ServiceModifyComplaintController.this.complaintVO.getId());
		serviceDAO.updateComplaint(complaintVO);
		registerConfirm.setText(CommonConstants.COMPLAINT_UPDATE);
		registerConfirm.getStyleClass().remove("failure");
		registerConfirm.getStyleClass().add("success");
		registerConfirm.setVisible(true);
		
	}

	public void sendMail()
	{
		try{
			Map<String,String> emailMap = new HashMap<String,String>();
			emailMap = serviceDAO.getServiceOptionDefaultValues();
			emailData.put(CommonConstants.EMAIL_TO, receiver.getText());
			emailData.put(CommonConstants.EMAIL_CC, cc.getText());
			emailData.put(CommonConstants.EMAIL_BODY, message.getText());
			emailData.put(CommonConstants.EMAIL_SUBJECT, "New Complaint");
			emailData.put(CommonConstants.EMAIL_USERNAME, emailMap.get(CommonConstants.KEY_SERVICE_EMAIL));
			emailData.put(CommonConstants.EMAIL_PASSWORD, emailMap.get(CommonConstants.KEY_SERVICE_PASSWORD));
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
