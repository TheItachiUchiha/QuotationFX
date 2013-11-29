package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.omg.CORBA.Environment;

import com.kc.constant.CommonConstants;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ProductsDAO;
import com.kc.model.EnquiryVO;
import com.kc.model.ProductsVO;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EnquiryViewController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(EnquiryViewController.class);
	ProductsDAO productsDAO;
	EnquiryDAO enquiryDAO;
	Validation validation;
	
	public EnquiryViewController()
	{
		productsDAO = new ProductsDAO();
		enquiryDAO = new EnquiryDAO();
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
    private TableView<EnquiryVO> enquiryTable;
	@FXML private TableColumn<EnquiryVO, String> referenceNo;
    @FXML private TableColumn<EnquiryVO, String> productName;
    @FXML private TableColumn<EnquiryVO, String> customerName;
    @FXML private TableColumn<EnquiryVO, String> companyName;
    @FXML private TableColumn<EnquiryVO, String> city;
    @FXML private TableColumn<EnquiryVO, String> state;
    @FXML private TableColumn<EnquiryVO, String> referedBy;
    @FXML private TableColumn<EnquiryVO, String> purchaseperiod;
    @FXML private TableColumn<EnquiryVO, String> dateOfEnquiry;
    @FXML private TableColumn action;
    @FXML private Label message;
    
    private ObservableList<String> searchList = FXCollections.observableArrayList();
    private ObservableList<String> monthList = FXCollections.observableArrayList();
    private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
    private ObservableList<ProductsVO> productList = FXCollections.observableArrayList();
    private ObservableList<EnquiryVO> enquiryListForTable = FXCollections.observableArrayList();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    
    
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
			
			enquiryList = enquiryDAO.getEnquries();
			productList = productsDAO.getProducts();
			
			
			monthCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					try{
						if(newValue!=null && !newValue.equals(oldValue))
						{
							for(EnquiryVO enquiryVO : enquiryList)
							{
								if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDate())).equalsIgnoreCase(newValue))
								{
									enquiryListForTable.add(enquiryVO);
								}
							}
							enquiryTable.setItems(enquiryListForTable);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
						LOG.error(e.getMessage());
					}
					
				}
			});
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}

}
