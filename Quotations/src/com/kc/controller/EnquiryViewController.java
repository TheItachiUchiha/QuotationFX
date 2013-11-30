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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ProductsDAO;
import com.kc.model.ComponentsVO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ProductsVO;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class EnquiryViewController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(EnquiryViewController.class);
	ProductsDAO productsDAO;
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	Validation validation;
	
	public EnquiryViewController()
	{
		productsDAO = new ProductsDAO();
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		validation = new Validation();
	}
	
	@FXML
	private ComboBox<String> searchCombo;
	@FXML
	private ComboBox<String> monthCombo;
	@FXML
	private AutoCompleteTextField<String> keyword;
	@FXML
	private Button go;
	@FXML
    private TableView<EnquiryViewVO> enquiryTable;
	@FXML private TableColumn<EnquiryViewVO, String> referenceNo;
    @FXML private TableColumn<EnquiryViewVO, String> productName;
    @FXML private TableColumn<EnquiryViewVO, String> customerName;
    @FXML private TableColumn<EnquiryViewVO, String> companyName;
    @FXML private TableColumn<EnquiryViewVO, String> city;
    @FXML private TableColumn<EnquiryViewVO, String> state;
    @FXML private TableColumn<EnquiryViewVO, String> referedBy;
    @FXML private TableColumn<EnquiryViewVO, String> purchasePeriod;
    @FXML private TableColumn<EnquiryViewVO, String> enquiryType;
    @FXML private TableColumn<EnquiryViewVO, String> dateOfEnquiry;
    @FXML private TableColumn action;
    @FXML private Label message;
    
    private ObservableList<String> searchList = FXCollections.observableArrayList();
    private ObservableList<String> monthList = FXCollections.observableArrayList();
    private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
    private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
    private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
    private ObservableList<ProductsVO> productList = FXCollections.observableArrayList();
    private ObservableList<EnquiryViewVO> enquiryListForTable = FXCollections.observableArrayList();
    ObservableList<EnquiryViewVO> tempList;
    ObservableList<String> tempList2;
    SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);

    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try{
			searchList.add("Ref_No");
			searchList.add("Product Name");
			searchList.add("Product Type");
			searchList.add("Customer Name");
			searchList.add("Company Name");
			searchList.add("City");
			searchList.add("State");
			searchList.add("Referred By");
			searchList.add("Estimate Purchase Period");
			searchList.add("Date of Enquiry created");
			searchCombo.setItems(searchList);
			
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			monthCombo.setItems(monthList);
			
			customerList = customersDAO.getCustomers();
			enquiryList = enquiryDAO.getEnquries();
			for(EnquiryVO enquiryVO : enquiryList)
			{
				EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
				enquiryViewVO.setId(enquiryVO.getId());
				enquiryViewVO.setDateOfEnquiry(enquiryVO.getDate());
				enquiryViewVO.setProductName(enquiryVO.getProductName());
				enquiryViewVO.setPurchasePeriod(enquiryVO.getPurchasePeriod());
				enquiryViewVO.setReferedBy(enquiryVO.getReferedBy());
				enquiryViewVO.setReferenceNo(enquiryVO.getRefNumber());
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
						
					}
				}
				enquiryViewList.add(enquiryViewVO);
			}
			productList = productsDAO.getProducts();
			
			
			monthCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					try{
						if(newValue!=null && !newValue.equals(oldValue))
						{
							for(EnquiryViewVO enquiryVO : enquiryViewList)
							{
								if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(newValue))
								{
									enquiryListForTable.add(enquiryVO);
								}
							}
							enquiryTable.setItems(enquiryListForTable);
						}
						referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
						enquiryType.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("enquiryType"));
						productName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
						companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
						customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
						city.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("city"));
						state.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("state"));
						referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
						purchasePeriod.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("purchasePeriod"));
						dateOfEnquiry.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("dateOfEnquiry"));
					}
					catch(Exception e)
					{
						e.printStackTrace();
						LOG.error(e.getMessage());
					}
				}
			});
			searchCombo.valueProperty().addListener(new ChangeListener<String>() {
	            
				@Override
				public void changed(ObservableValue ov, String t, String t1) {                
				 	fillAutoCompleteFromComboBox(t1);
				 	keyword.setText("");
				 	tempList = FXCollections.observableArrayList();
				 	tempList.clear();
				 	
	            }
	        });
			keyword.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					enquiryTable.getItems().clear();
					fillTableFromData();
					tempList.clear();
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fillAutoCompleteFromComboBox(String t1)
	{
		LOG.info("Enter : fillAutoCompleteFromComboBox");
		try{
			enquiryList = enquiryDAO.getEnquries();
			productList = productsDAO.getProducts();
			customerList = customersDAO.getCustomers();
			tempList2 = FXCollections.observableArrayList(); 
			if(t1.equals("Product Name"))
	        {
	        	for(EnquiryVO enquiryVO : enquiryList)
	        	{
	        		if(!tempList2.contains(enquiryVO.getProductName()))
	        		{
	        			tempList2.add(enquiryVO.getProductName());
	        		}
	        	}
	        }
			else if(t1.equals("Ref_No"))
	        {
	        	for(EnquiryVO enquiryVO : enquiryList)
	        	{
	        		if(!tempList2.contains(enquiryVO.getRefNumber()))
	        		{
	        			tempList2.add(enquiryVO.getRefNumber());
	        		}
	        	}
	        }
	        else if(t1.equals("Product Type"))
	        {
	        	for(ProductsVO productsVO : productList)
	        	{
	        		if(!tempList2.contains(productsVO.getProductCategory()))
	        		{
	        			tempList2.add(productsVO.getProductCategory());
	        		}
	        	}
	        }
	        else if(t1.equals("Customer Name"))
	        {
	        	for(CustomersVO customersVO : customerList)
	        	{
	        		if(!tempList2.contains(customersVO.getCustomerName()))
	        		{
	        			tempList2.add(customersVO.getCustomerName());
	        		}
	        	}
	        }
	        else if(t1.equals("Company Name"))
	        {
	        	for(CustomersVO customersVO : customerList)
	        	{
	        		if(!tempList2.contains(customersVO.getCompanyName()))
	        		{
	        			tempList2.add(customersVO.getCompanyName());
	        		}
	        	}
	        }
	        else if(t1.equals("City"))
	        {
	        	for(CustomersVO customersVO : customerList)
	        	{
	        		if(!tempList2.contains(customersVO.getCity()))
	        		{
	        			tempList2.add(customersVO.getCity());
	        		}
	        	}
	        }
	        else if(t1.equals("State"))
	        {
	        	for(CustomersVO customersVO : customerList)
	        	{
	        		if(!tempList2.contains(customersVO.getState()))
	        		{
	        			tempList2.add(customersVO.getState());
	        		}
	        	}
	        }
	        else if(t1.equals("Referred By"))
	        {
	        	for(EnquiryVO enquiryVO : enquiryList)
	        	{
	        		if(!tempList2.contains(enquiryVO.getReferedBy()))
	        		{
	        			tempList2.add(enquiryVO.getReferedBy());
	        		}
	        	}
	        }
	        else if(t1.equals("Estimate Purchase Period"))
	        {
	        	for(EnquiryVO enquiryVO : enquiryList)
	        	{
	        		if(!tempList2.contains(enquiryVO.getPurchasePeriod()))
	        		{
	        			tempList2.add(enquiryVO.getPurchasePeriod());
	        		}
	        	}
	        }
	        else if(t1.equals("Date of Enquiry created"))
	        {
	        	for(EnquiryVO enquiryVO : enquiryList)
	        	{
	        		if(!tempList2.contains(enquiryVO.getDate()))
	        		{
	        			tempList2.add(enquiryVO.getDate());
	        		}
	        	}
	        }
			keyword.setItems(tempList2);
			
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : fillAutoCompleteFromComboBox");
	}
	private void fillTableFromData()
	{
		LOG.info("Enter : fillTableFromData");
		try{
			
			String tempString = keyword.getText();
			if(searchCombo.getSelectionModel().getSelectedItem().equals("Product Name"))
			{
				for(EnquiryViewVO enquiryViewVO : enquiryViewList)
				{
					if(enquiryViewVO.getProductName().equalsIgnoreCase(tempString))
					{
						tempList.add(enquiryViewVO);
					}
				}
			}
			else if(searchCombo.getSelectionModel().getSelectedItem().equals("Product Type"))
			{
				for(ProductsVO productsVO : productList)
				{
					for(EnquiryViewVO enquiryViewVO : enquiryViewList)
					{
						if(productsVO.getProductName().equals(enquiryViewVO.getProductName()))
						{
							tempList.add(enquiryViewVO);
						}
					}
				}
			}
			else if(searchCombo.getSelectionModel().getSelectedItem().equals("Customer Name"))
			{
				for(EnquiryViewVO enquiryViewVO : enquiryViewList)
				{
					if(enquiryViewVO.getCustomerName().equalsIgnoreCase(tempString))
					{
						tempList.add(enquiryViewVO);
					}
				}
			}
			else if(searchCombo.getSelectionModel().getSelectedItem().equals("Company Name"))
			{
				for(EnquiryViewVO enquiryViewVO : enquiryViewList)
				{
					if(enquiryViewVO.getCompanyName().equalsIgnoreCase(tempString))
					{
						tempList.add(enquiryViewVO);
					}
				}
			}
			else if(searchCombo.getSelectionModel().getSelectedItem().equals("City"))
			{
				for(EnquiryViewVO enquiryViewVO : enquiryViewList)
				{
					if(enquiryViewVO.getCity().equalsIgnoreCase(tempString))
					{
						tempList.add(enquiryViewVO);
					}
				}
			}
			else if(searchCombo.getSelectionModel().getSelectedItem().equals("State"))
			{
				for(EnquiryViewVO enquiryViewVO : enquiryViewList)
				{
					if(enquiryViewVO.getState().equalsIgnoreCase(tempString))
					{
						tempList.add(enquiryViewVO);
					}
				}
			}
			else if(searchCombo.getSelectionModel().getSelectedItem().equals("Referred By"))
			{
				for(EnquiryViewVO enquiryViewVO : enquiryViewList)
				{
					if(enquiryViewVO.getReferedBy().equalsIgnoreCase(tempString))
					{
						tempList.add(enquiryViewVO);
					}
				}
			}
			else if(searchCombo.getSelectionModel().getSelectedItem().equals("Estimate Purchase Period"))
			{
				for(EnquiryViewVO enquiryViewVO : enquiryViewList)
				{
					if(enquiryViewVO.getPurchasePeriod().equalsIgnoreCase(tempString))
					{
						tempList.add(enquiryViewVO);
					}
				}
			}
			else if(searchCombo.getSelectionModel().getSelectedItem().equals("Date of Enquiry created"))
			{
				for(EnquiryViewVO enquiryViewVO : enquiryViewList)
				{
					if(enquiryViewVO.getDateOfEnquiry().equalsIgnoreCase(tempString))
					{
						tempList.add(enquiryViewVO);
					}
				}
			}
			
			referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
			enquiryType.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("enquiryType"));
			productName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
			companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
			customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
			city.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("city"));
			state.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("state"));
			referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
			purchasePeriod.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("purchasePeriod"));
			dateOfEnquiry.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("dateOfEnquiry"));
			enquiryTable.setItems(tempList);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : fillTableFromData");
	}

}
