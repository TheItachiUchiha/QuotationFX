package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import com.kc.dao.ServiceDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.QuotationUtil;
import com.kc.util.Validation;

public class ReminderController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ReminderController.class);
	
		EnquiryDAO enquiryDAO;
		CustomersDAO customersDAO;
		ServiceDAO serviceDAO;
		Validation validate;
	
		public ReminderController() {
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		serviceDAO = new ServiceDAO();
		validate = new Validation();
		}
	
		@FXML
	    private ComboBox<String> createCombo;

	    @FXML
	    private Button createReminder;

	    @FXML
	    private ComboBox<String> frequencyCombo;

	    @FXML
	    private TextArea message;

	    @FXML
	    private ComboBox<String> modifyCombo;

	    @FXML
	    private Button modifyReminder;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private TextField receiver;

	    @FXML
	    private ComboBox<String> reminderCombo;

	    @FXML
	    private Button search;

	    @FXML
	    private TextField sender;

	    @FXML
	    private Label sentReminder;

	    @FXML
	    private TextField subject;

	    @FXML
	    private ComboBox<String> autoReminderCombo;

	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    @FXML
	    private ComboBox<String> actionCombo;

	    private ObservableList<String> monthList = FXCollections.observableArrayList();
		private ObservableList<String> yearList = FXCollections.observableArrayList();
		private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
		private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
		private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
		private ObservableList<String> refList = FXCollections.observableArrayList();
		SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
		private EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
		
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);
			
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if((new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem()) && new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem()) && enquiryVO.getSales().equalsIgnoreCase("Y")))
				{
					refList.add(enquiryVO.getReferenceNo());
				}
			}
			actionCombo.setItems(refList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void createReminder()
	{
		
	}

}
