package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.Validation;

public class PriceEstimationModifyController implements Initializable{

	private static final Logger LOG = LogManager.getLogger(PriceEstimationModifyController.class);
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	Validation validation;
	public PriceEstimationModifyController()
	{
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		validation = new Validation();
	}
	 @FXML
	    private Label costPriceTotal;

	    @FXML
	    private Label dealerPriceTotal;

	    @FXML
	    private TextArea eaddress;

	    @FXML
	    private TextField ecity;

	    @FXML
	    private TextField ecompanyName;

	    @FXML
	    private TextField econtactNumber;

	    @FXML
	    private TextField ecustomerFile;

	    @FXML
	    private TextField ecustomerName;

	    @FXML
	    private TextField ecustomerType;

	    @FXML
	    private TextField eemailId;

	    @FXML
	    private TextField eenquiryType;

	    @FXML
	    private Label endUserPrice;

	    @FXML
	    private Button enquiryDetails;

	    @FXML
	    private TextField eproductName;

	    @FXML
	    private TextField epurchasePeriod;

	    @FXML
	    private TextField ereferedBy;

	    @FXML
	    private TextArea erequirements;

	    @FXML
	    private TextField estate;

	    @FXML
	    private TextField etinNumber;

	    @FXML
	    private TextField marginValue;

	    @FXML
	    private Button modifyPriceEstimation;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private TextField productName;

	    @FXML
	    private ComboBox<String> referenceCombo;

	    @FXML
	    private TextField referenceNo;

	    @FXML
	    private Button search;

	    @FXML
	    private Label totalProfit;

	    @FXML
	    private ComboBox<String> yearCombo;
	
	private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	 SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			referenceNo.setEditable(false);
			productName.setEditable(false);
			eenquiryType.setEditable(false);
			eproductName.setEditable(false);
			erequirements.setEditable(false);
			ecustomerFile.setEditable(false);
			ecustomerType.setEditable(false);
			ecustomerName.setEditable(false);
			etinNumber.setEditable(false);
			eemailId.setEditable(false);
			ereferedBy.setEditable(false);
			eaddress.setEditable(false);
			estate.setEditable(false);
			ecity.setEditable(false);
			econtactNumber.setEditable(false);
			epurchasePeriod.setEditable(false);
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			monthCombo.setItems(monthList);
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = fillEnquiryViewListFromEnquiryList(enquiryList);
			monthCombo.valueProperty().addListener(new ChangeListener<String>() {
			
				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					try{
						refList.clear();
						if(newValue!=null && !newValue.equals(oldValue))
						{
							for(EnquiryViewVO enquiryVO : enquiryViewList)
							{
								if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(newValue)&&(enquiryDAO.estimationConfirm(enquiryVO.getId())).equalsIgnoreCase("Y"))
								{
									refList.add(enquiryVO.getReferenceNo());
								}
							}
						}
						referenceCombo.setItems(refList);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						LOG.error(e.getMessage());
					}
				}
			});
			enquiryDetails.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					for(EnquiryViewVO enquiryViewVO: enquiryViewList)
					{
						if(referenceCombo.getSelectionModel().getSelectedItem().equals(enquiryViewVO.getReferenceNo()))
						{
							referenceNo.setText(enquiryViewVO.getReferenceNo());
							productName.setText(enquiryViewVO.getProductName());
							eenquiryType.setText(enquiryViewVO.getEnquiryType());
							eproductName.setText(enquiryViewVO.getProductName());
							erequirements.setText(enquiryViewVO.getCustomerRequirement());
							ecustomerName.setText(enquiryViewVO.getCustomerName());
							ecompanyName.setText(enquiryViewVO.getCompanyName());
							etinNumber.setText(enquiryViewVO.getTinNumber());
							eemailId.setText(enquiryViewVO.getEmailId());
							ereferedBy.setText(enquiryViewVO.getReferedBy());
							ecustomerType.setText(enquiryViewVO.getCustomerType());
							eaddress.setText(enquiryViewVO.getAddress());
							estate.setText(enquiryViewVO.getState());
							ecity.setText(enquiryViewVO.getCity());
							econtactNumber.setText(enquiryViewVO.getContactNumber());
							ecustomerFile.setText(enquiryViewVO.getCustomerFile());
							epurchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
						}
					}
					
				}
			});

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
