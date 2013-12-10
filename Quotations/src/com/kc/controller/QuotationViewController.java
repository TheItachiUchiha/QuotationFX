package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.Validation;

public class QuotationViewController implements Initializable  {

	private static final Logger LOG = LogManager.getLogger(QuotationViewController.class);
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	Validation validation;
	public QuotationViewController()
	{
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		validation = new Validation();
	}
	
	    @FXML
	    private TableColumn<EnquiryViewVO, String> companyName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> customerName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> dateOfEnquiry;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> dateOfPe;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> dateOfQp;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> dateOfEmailSent;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> productName;

	    @FXML
	    private TableView<EnquiryViewVO> quotationTable;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referedBy;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referenceNo;

	    @FXML
	    private Button search;

	    @FXML
	    private ComboBox<String> yearCombo;
	
	private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	 SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			yearCombo.setItems(yearList);
			monthCombo.setItems(monthList);
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = fillEnquiryViewListFromEnquiryList(enquiryList);
			search.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					
					if(monthCombo.getSelectionModel().getSelectedIndex()==-1|| yearCombo.getSelectionModel().getSelectedIndex()==-1)
					{
						Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
					}
					else
					{
						quotationTable.setVisible(true);
						fillTable();
					}
					
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public void fillTable()
	{

		try
		{
			enquiryList = enquiryDAO.getEnquries();
			enquiryViewList = fillEnquiryViewListFromEnquiryList(enquiryList);
			refList.clear();
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem()) && new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem()) && (enquiryVO.getPriceEstimation()).equalsIgnoreCase("Y") && (enquiryVO.getQuotationPreparation()).equalsIgnoreCase("Y")&& (enquiryVO.getEmailSent()).equalsIgnoreCase("Y"))
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
				quotationTable.setItems(enquiryViewList);
				referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
				productName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
				companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
				customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
				referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
				dateOfEnquiry.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("dateOfEnquiry"));
				dateOfPe.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("peDate"));
				dateOfQp.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("qpDate"));
				dateOfEmailSent.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("mailSentDate"));
			}
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
			enquiryViewVO.setDateOfEnquiry(enquiryVO.getDate());
			enquiryViewVO.setQuotationPreparation(enquiryVO.getQuotationPreparation());
			enquiryViewVO.setEmailSent(enquiryVO.getEmailSent());
			enquiryViewVO.setSales(enquiryVO.getSales());
			enquiryViewVO.setQpDate(enquiryVO.getQpDate());
			enquiryViewVO.setPeDate(enquiryVO.getPeDate());
			enquiryViewVO.setMailSentDate(enquiryVO.getMailSentDate());
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
