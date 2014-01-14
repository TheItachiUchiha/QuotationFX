package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.SalesDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.QuotationUtil;
import com.sun.glass.events.KeyEvent;

import eu.schudt.javafx.controls.calendar.DatePicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class SalesNewController {

	private SalesDAO salesDAO;
	private CustomersDAO customersDAO;
	
	public SalesNewController()
	{
		salesDAO = new SalesDAO();
		customersDAO = new CustomersDAO();
	}
	
	@FXML
    private  ToggleButton viewEnquiry;
    
    @FXML
	 private ToggleGroup buttonToggle;
    
    @FXML
    private TextField city;

    @FXML
    private TextField companyName;

    @FXML
    private TextField customerName;

    @FXML
    private TextField dateOfEnquiry;

    @FXML
    private TextField dateOfQuotation;

    @FXML
    private TextField dateOfSalesOrder;

    @FXML
    private TextArea eaddress;

    @FXML
    private TextField ecity;

    @FXML
    private TextField ecomapanyName;

    @FXML
    private TextField econatctNumber;

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
    private Label message;

    @FXML
    private TextField productName;

    @FXML
    private ComboBox<String> referenceCombo;
    
    @FXML
    private GridPane enquiryGrid;
    
    @FXML
    private GridPane salesGrid;
    
	private DatePicker calendar;
    private EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
    private ObservableList<String> refList = FXCollections.observableArrayList();
    private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);

	
	
    @FXML
    void initialize() {
    	try{
    		enquiryList = salesDAO.getListOfEnquiresForSales();
    		customerList = customersDAO.getCustomers();
    		enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
    		referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String paramT1, String paramT2) {
					
					for(EnquiryViewVO enquiryViewVO: enquiryViewList)
					{
						if(referenceCombo.getSelectionModel().getSelectedItem().equals(enquiryViewVO.getReferenceNo()))
						{
							SalesNewController.this.enquiryViewVO = enquiryViewVO;
							break;
						}
					}
					
				}
			});
    		
    		for(EnquiryViewVO enquiryViewVO : enquiryViewList)
    		{
    			refList.add(enquiryViewVO.getReferenceNo());
    		}
    		
    		referenceCombo.setItems(refList);
    		calendar = new DatePicker(Locale.ENGLISH);
    		calendar.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    		calendar.getCalendarView().todayButtonTextProperty().set("Today");
    		calendar.getCalendarView().setShowWeeks(false);
    		calendar.getStylesheets().add("com/kc/style/DatePicker.css");
    		((TextField)calendar.getChildren().get(0)).setEditable(false);
    		salesGrid.add(calendar, 1, 6);
    		
    		buttonToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			    public void changed(ObservableValue<? extends Toggle> ov,
				        Toggle old_toggle, Toggle new_toggle) {
				        if (old_toggle !=null)
				        {
				        	enquiryGrid.setVisible(false);
				        }
				        else if (new_toggle !=null) {
							if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
							{
								Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_REFERENCE);
							}
							else
							{
								for(EnquiryViewVO enquiryViewVO: enquiryViewList)
								{
									if(referenceCombo.getSelectionModel().getSelectedItem().equals(enquiryViewVO.getReferenceNo()))
									{
										eproductName.setText(enquiryViewVO.getProductName());
										ecustomerType.setText(enquiryViewVO.getCustomerType());
										ecustomerName.setText(enquiryViewVO.getCustomerName());
										ecomapanyName.setText(enquiryViewVO.getCompanyName());
										erequirements.setText(enquiryViewVO.getCustomerRequirement());
										eaddress.setText(enquiryViewVO.getAddress());
										eemailId.setText(enquiryViewVO.getEmailId());
										ereferedBy.setText(enquiryViewVO.getReferedBy());
										eenquiryType.setText(enquiryViewVO.getEnquiryType());
										estate.setText(enquiryViewVO.getState());
										econatctNumber.setText(enquiryViewVO.getContactNumber());
										etinNumber.setText(enquiryViewVO.getTinNumber());
										ecity.setText(enquiryViewVO.getCity());
										ecustomerFile.setText(enquiryViewVO.getCustomerFile());
										epurchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
										enquiryGrid.setVisible(true);
										//SalesNewController.this.enquiryViewVO = enquiryViewVO;
										break;
									}
								}
							}
				    }
				    }
				});	
    	}
    	catch (Exception e) {
    		e.printStackTrace();
		}
    }
    
    /*public void viewEnquiry()
    {
			if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_REFERENCE);
			}
			else
			{
				for(EnquiryViewVO enquiryViewVO: enquiryViewList)
				{
					if(referenceCombo.getSelectionModel().getSelectedItem().equals(enquiryViewVO.getReferenceNo()))
					{
						eproductName.setText(enquiryViewVO.getProductName());
						ecustomerType.setText(enquiryViewVO.getCustomerType());
						ecustomerName.setText(enquiryViewVO.getCustomerName());
						ecomapanyName.setText(enquiryViewVO.getCompanyName());
						erequirements.setText(enquiryViewVO.getCustomerRequirement());
						eaddress.setText(enquiryViewVO.getAddress());
						eemailId.setText(enquiryViewVO.getEmailId());
						ereferedBy.setText(enquiryViewVO.getReferedBy());
						eenquiryType.setText(enquiryViewVO.getEnquiryType());
						estate.setText(enquiryViewVO.getState());
						econatctNumber.setText(enquiryViewVO.getContactNumber());
						etinNumber.setText(enquiryViewVO.getTinNumber());
						ecity.setText(enquiryViewVO.getCity());
						ecustomerFile.setText(enquiryViewVO.getCustomerFile());
						epurchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
						enquiryGrid.setVisible(true);
						this.enquiryViewVO = enquiryViewVO;
						break;
					}
				}
			}
    }*/
    
    public void createSalesOrder()
    {
    	try
    	{
    		if(referenceCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_REFERENCE);
			}
			else
			{
	    		productName.setText(enquiryViewVO.getProductName());
	    		customerName.setText(enquiryViewVO.getCustomerName());
	    		companyName.setText(enquiryViewVO.getCompanyName());
	    		city.setText(enquiryViewVO.getCity());
	    		dateOfEnquiry.setText(enquiryViewVO.getDateOfEnquiry());
	    		dateOfQuotation.setText(enquiryViewVO.getQpDate());
	    		salesGrid.setVisible(true);
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		
    	}
    }
    
    public void createSaleEntry()
    {
    	try{
    		salesDAO.saveSalesDate(enquiryViewVO.getId(), formatter.format(calendar.getSelectedDate()));
    		message.setText(CommonConstants.SALES_DATE_CREATED);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
			message.setVisible(true);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		
    	}
    }
    
}

    