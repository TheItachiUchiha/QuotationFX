package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.kc.model.EnquiryVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EnquiryViewController implements Initializable {
	
	@FXML
	private ComboBox<String> searchCombo;
	@FXML
	private ComboBox<String> monthCombo;
	@FXML
	private AutoCompleteTextField<String> keyword;
	@FXML
	private Button go;
	@FXML
    private TableView<EnquiryVO> enquiryTable;
	@FXML private TableColumn<EnquiryVO, String> referenceNo;
    @FXML private TableColumn<EnquiryVO, String> productName;
    @FXML private TableColumn<EnquiryVO, String> customerName;
    @FXML private TableColumn<EnquiryVO, String> companyName;
    @FXML private TableColumn<EnquiryVO, String> city;
    @FXML private TableColumn<EnquiryVO, String> state;
    @FXML private TableColumn<EnquiryVO, String> referedBy;
    @FXML private TableColumn<EnquiryVO, String> purchaseperiod;
    @FXML private TableColumn<EnquiryVO, String> dateOfEnquiry;
    @FXML private TableColumn action;
    @FXML private Label message;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
