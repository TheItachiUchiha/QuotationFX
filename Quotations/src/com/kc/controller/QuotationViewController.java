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
import com.kc.util.QuotationUtil;
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
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
			search.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					try{
					
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
					catch (Exception e) {
						e.printStackTrace();
						LOG.error(e.getMessage());
					}
					
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	private void fillTable()
	{
		ObservableList<EnquiryViewVO> tempEnquiryList = FXCollections.observableArrayList();
		try
		{
			enquiryList = enquiryDAO.getEnquries();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
			tempEnquiryList.clear();
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem())&&(enquiryVO.getPriceEstimation().equalsIgnoreCase("Y")&&(enquiryVO.getQuotationPreparation().equalsIgnoreCase("Y") && enquiryVO.getEmailSent().equalsIgnoreCase("Y"))))
				{
					tempEnquiryList.add(enquiryVO);
				}
			}
			if(tempEnquiryList.isEmpty())
			{
				Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
			}
			else
			{
				quotationTable.setItems(tempEnquiryList);
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
}
