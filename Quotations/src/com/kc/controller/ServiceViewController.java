package com.kc.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ServiceDAO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ServiceVO;

public class ServiceViewController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceViewController.class);
	
	 	@FXML
	    private TableColumn action;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> companyName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> customerName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> engineerName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> location;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> productPurchased;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> rating;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referedBy;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referenceNo;

	    @FXML
	    private TableView<EnquiryViewVO> salesOrderTable;
	    
	    @FXML
	    private TableView<ServiceVO> serviceTable;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> serviceCharge;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> thisYearService;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> totalService;

	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    private ObservableList<String> monthList = FXCollections.observableArrayList();
	   	private ObservableList<String> yearList = FXCollections.observableArrayList();
	   	
	   	ServiceDAO serviceDAO;
	   	
	   	public ServiceViewController() {
	   		serviceDAO = new ServiceDAO();
		}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			yearCombo.setItems(yearList);
			monthCombo.setItems(monthList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void viewDetails()
	{
		
	}

}
