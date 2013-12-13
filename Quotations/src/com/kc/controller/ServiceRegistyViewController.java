package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ServiceDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ServiceVO;
import com.kc.util.QuotationUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServiceRegistyViewController implements Initializable  {
	
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	ServiceDAO  serviceDAO;
	
	public ServiceRegistyViewController() {

		enquiryDAO = new EnquiryDAO();
		serviceDAO = new ServiceDAO();
		customersDAO = new CustomersDAO();
	}

	 @FXML
	    private TableColumn action;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> companyName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> customerName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> dateOfService;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> location;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private TableColumn<ServiceVO,String> name;

	    @FXML
	    private TableColumn<ServiceVO,String> noOfService;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> productPurchased;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referedBy;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referenceNo;

	    @FXML
	    private TableView<EnquiryViewVO> salesOrderTable;

	    @FXML
	    private TableColumn<ServiceVO,String> serviceCharge;

	    @FXML
	    private TableColumn<ServiceVO,String> serviceRating;

	    @FXML
	    private TableView<ServiceVO> serviceTable;

	    @FXML
	    private TableColumn<ServiceVO,String> totalServices;

	    @FXML
	    private Button view;

	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    private ObservableList<String> monthList = FXCollections.observableArrayList();
		private ObservableList<String> yearList = FXCollections.observableArrayList();
		private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
		private ObservableList<EnquiryViewVO> tableList = FXCollections.observableArrayList();
		private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
		private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
		private ObservableList<ServiceVO> serviceList = FXCollections.observableArrayList();
		SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
			
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			enquiryList = serviceDAO.getServiceEnquires();
			customerList = customersDAO.getCustomers();
			serviceList = serviceDAO.getServices();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void viewDetails()
	{
		try
		{
			for(EnquiryViewVO enquiryViewVO : enquiryViewList)
			{
				if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryViewVO.getServiceDate())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem()) && new SimpleDateFormat("yyyy").format(formatter.parse(enquiryViewVO.getServiceDate())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem()) && enquiryViewVO.getServiceDone().equalsIgnoreCase("Y"))
				{
					tableList.add(enquiryViewVO);
				}
			}
			if(tableList.isEmpty())
			{
				Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
			}
			else
			{
				salesOrderTable.setItems(tableList);
				referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
				dateOfService.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("serviceDate"));
				productPurchased.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
				customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
				companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
				referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
				location.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("city"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
