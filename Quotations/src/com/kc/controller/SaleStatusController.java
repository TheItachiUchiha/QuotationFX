package com.kc.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.StatusReminderDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.QuotationUtil;

public class SaleStatusController implements Initializable {
	
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	StatusReminderDAO statusReminderDAO;
	String startDate;
	String endDate;
	
	public SaleStatusController() {

		enquiryDAO = new EnquiryDAO();
		statusReminderDAO = new StatusReminderDAO();
		customersDAO = new CustomersDAO();
	}
	
		@FXML
	    private TableColumn<EnquiryViewVO,String> companyName;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> customerName;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> dateOfEnquiry;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> dateOfQuotation;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> dateOfSalesOrder;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> location;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> productName;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> referedBy;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> referenceNo;

	    @FXML
	    private TableView<EnquiryViewVO> successTable;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> ucompanyName;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> ucustomerName;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> udateOfEnquiry;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> udateOfQuotation;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> utotalReminderSent;

	    @FXML
	    private TableView<EnquiryViewVO> unsuccessTable;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> uproductName;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> ureferedBy;

	    @FXML
	    private TableColumn<EnquiryViewVO,String> ureferenceNo;

	    @FXML
	    private ComboBox<String> monthCombo;
	    
	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    @FXML
	    private VBox tableVBox;

	private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
		yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
		monthCombo.setItems(monthList);
		yearCombo.setItems(yearList);
		tableVBox.getChildren().removeAll(successTable,unsuccessTable);
		
	}

	public void viewSuccess()
	{
		try
		{
			if(monthCombo.getSelectionModel().getSelectedIndex()==-1||yearCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
			}
			else
			{
				startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				enquiryList = statusReminderDAO.getStatusEnquires(startDate, endDate, "Y");
				customerList = customersDAO.getCustomers();
				enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);
				tableVBox.getChildren().remove(unsuccessTable);
				if(enquiryViewList.isEmpty())
				{
					Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
				}
				else
				{
					successTable.setItems(enquiryViewList);
					referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
					dateOfEnquiry.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("dateOfEnquiry"));
					dateOfQuotation.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("qpDate"));
					dateOfSalesOrder.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("salesDate"));
					productName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
					customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
					companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
					location.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("city"));
					referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
					tableVBox.getChildren().clear();
					tableVBox.getChildren().add(successTable);
				}
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void viewUnsuccess()
	{
		try
		{
			if(monthCombo.getSelectionModel().getSelectedIndex()==-1||yearCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
			}
			else
			{
				startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				enquiryList = statusReminderDAO.getStatusEnquires(startDate, endDate, "N");
				customerList = customersDAO.getCustomers();
				enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);
				tableVBox.getChildren().remove(successTable);
				if(enquiryViewList.isEmpty())
				{
					Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
				}
				else
				{
					unsuccessTable.setItems(enquiryViewList);
					ureferenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
					udateOfEnquiry.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("dateOfEnquiry"));
					udateOfQuotation.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("qpDate"));
					uproductName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
					ucustomerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
					ucompanyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
					ureferedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
					utotalReminderSent.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("reminderSent"));
					tableVBox.getChildren().clear();
					tableVBox.getChildren().add(unsuccessTable);
				}
			}
		}
	
	catch (Exception e) {
		e.printStackTrace();
	}
	}
	
}
