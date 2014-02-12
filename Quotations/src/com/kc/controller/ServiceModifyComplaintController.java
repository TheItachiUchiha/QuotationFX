package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.ComplaintVO;

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


	@Override
	public void initialize(URL location, ResourceBundle resources) {
			
		
	}
	public void fillTextFieldValues(ComplaintVO complaintVO)
	{
		
	}
	
	public void UpdateComplaint()
	{
		
	}

	public void sendMail()
	{
		
	}

}
