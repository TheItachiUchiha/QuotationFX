package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.SalesDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.QuotationUtil;

import eu.schudt.javafx.controls.calendar.DatePicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SalesModifyController {

	private EnquiryDAO enquiryDAO;
	private CustomersDAO customersDAO;
	private SalesDAO salesDAO;
	public SalesModifyController()
	{
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		salesDAO = new SalesDAO();
	}
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
    private ComboBox<String> monthCombo;

    @FXML
    private TextField productName;

    @FXML
    private ComboBox<String> referenceCombo;

    @FXML
    private ComboBox<String> yearCombo;
    
    @FXML
    private Label message;
    
    @FXML
    private GridPane salesGrid;
    private DatePicker calendar;
    private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	private EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	
    @FXML
    void initialize() {
    	try{
    		
    		calendar = new DatePicker(Locale.ENGLISH);
    		calendar.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    		calendar.getCalendarView().todayButtonTextProperty().set("Today");
    		calendar.getCalendarView().setShowWeeks(false);
    		calendar.getStylesheets().add("com/kc/style/DatePicker.css");
    		((TextField)calendar.getChildren().get(0)).setEditable(false);
    		salesGrid.add(calendar, 1, 6);
    		
    		
	    	monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			referenceCombo.setItems(refList);
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
			
			monthCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String paramT1, String paramT2) {
					try{
						if(null != paramT2 && (null==paramT1 || !paramT1.equals(paramT2)) && yearCombo.getSelectionModel().getSelectedIndex() != -1){
							for(EnquiryViewVO enquiryVO : enquiryViewList)
							{
								if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem())&&(enquiryVO.getPriceEstimation()).equalsIgnoreCase("Y")&&(enquiryVO.getQuotationPreparation()).equalsIgnoreCase("Y")&&(enquiryVO.getSales()).equalsIgnoreCase("Y"))
								{
									refList.add(enquiryVO.getReferenceNo());
								}
							}
							if(refList.isEmpty())
							{
								Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			yearCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String paramT1, String paramT2) {
					try{
						if(null != paramT2 && (null==paramT1 || !paramT1.equals(paramT2)) && monthCombo.getSelectionModel().getSelectedIndex() != -1){
							for(EnquiryViewVO enquiryVO : enquiryViewList)
							{
								if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem())&&(enquiryVO.getPriceEstimation()).equalsIgnoreCase("Y")&&(enquiryVO.getQuotationPreparation()).equalsIgnoreCase("Y")&&(enquiryVO.getSales()).equalsIgnoreCase("Y"))
								{
									refList.add(enquiryVO.getReferenceNo());
								}
							}
							if(refList.isEmpty())
							{
								Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String paramT1, String paramT2) {
					if(null==paramT1 || !paramT2.equals(paramT1))
					{
						for(EnquiryViewVO enquiryVO : enquiryViewList)
						{
							if(paramT2.equals(enquiryVO.getReferenceNo()))
							{
								enquiryViewVO = enquiryVO;
								break;
							}
						}
					}
					
				}
			});
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public void viewDetails()
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
	    		calendar.setSelectedDate(formatter.parse(enquiryViewVO.getSalesDate()));
	    		salesGrid.setVisible(true);
			}
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    
    public void updateSalesData()
    {
    	try
    	{
    		salesDAO.updateSalesDate(enquiryViewVO.getId(), formatter.format(calendar.getSelectedDate()));
    		message.setText(CommonConstants.SALES_DATE_MODIFIED);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
			message.setVisible(true);
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
}