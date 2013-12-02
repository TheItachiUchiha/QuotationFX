package com.kc.controller;

import java.io.File;
import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ProductsDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ProductsVO;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class EnquiryModifyPopUpController {
	private static final Logger LOG = LogManager
			.getLogger(EnquiryModifyPopUpController.class);

	private EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
	private ObservableList<ProductsVO> productList = FXCollections.observableArrayList();
	private ObservableList<ProductsVO> productCatList = FXCollections.observableArrayList();
	private ObservableList<ProductsVO> productSubCatList = FXCollections.observableArrayList();
	final ObservableList<String> tempCategoryList = FXCollections
			.observableArrayList();
	final ObservableList<String> tempSubCategoryList = FXCollections
			.observableArrayList();
	ObservableList<ProductsVO> tempProductList = FXCollections
			.observableArrayList();
	private CustomersDAO customersDAO;
	private EnquiryDAO enquiryDAO;
	private CustomersVO customersVO;
	private Validation validation;
	private ProductsDAO productsDAO;
	private int productId=0;
	String productNameText = "";
	String category ="";
	String subCategory = "";
	
	
	
	

	public EnquiryModifyPopUpController() {
		customersDAO = new CustomersDAO();
		customersVO = new CustomersVO();
		productsDAO = new ProductsDAO();
		enquiryDAO = new EnquiryDAO();
		validation = new Validation();
	}

	@FXML
	private TextArea address;

	@FXML
	private ComboBox<String> categoryCombo;

	@FXML
	private TextField city;

	@FXML
	private TextField companyName;

	@FXML
	private TextField contactNumber;

	@FXML
	private RadioButton custom;

	@FXML
	private TextField customerName;

	@FXML
	private TextArea customerRequirements;

	@FXML
	private ComboBox<String> customerTypeCombo;

	@FXML
	private AutoCompleteTextField<?> emailId;

	@FXML
	private GridPane enquiryGrid;

	@FXML
	private Label messageNewEnquiry;

	@FXML
	private ComboBox<ProductsVO> nameCombo;

	@FXML
	private HBox productHbox;

	@FXML
	private TextField productName;

	@FXML
	private ToggleGroup productType;

	@FXML
	private VBox productVbox;

	@FXML
	private TextField purchasePeriod;

	@FXML
	private AutoCompleteTextField<?> referedBy;

	@FXML
	private RadioButton standard;

	@FXML
	private TextField state;

	@FXML
	private ComboBox<String> subcategoryCombo;

	@FXML
	private AutoCompleteTextField<?> tinNumber;

	private TextField filePath;

	@FXML
    void initialize() {
    	
    	try{
    		
    		filePath = new TextField();
    		filePath.setEditable(false);
    		filePath.setPrefWidth(209.0);
    		Button browse = new Button();
            browse.setText("Browse");
            browse.setPrefWidth(65);
            final HBox hBox =new HBox(5);
            hBox.getChildren().addAll(filePath,browse);
            enquiryGrid.add(hBox,1,11);

            browse.setOnAction(new EventHandler<ActionEvent>() {

	          @Override
	          public void handle(ActionEvent event) {
	       	   Stage stage=new Stage();
	             FileChooser fileChooser = new FileChooser();
	             File tempFile = fileChooser.showOpenDialog(stage);
	             if(tempFile!=null){
	                     filePath.setText(tempFile.getPath());
	             }
	            
	          }
            });
	    	
            standard.setDisable(true);
            custom.setDisable(true);
            
            
            productList = productsDAO.getProducts();
	    	
	        //get a enquiryViewVo object from the table
	    	
	    	
	    	categoryCombo.setItems(tempCategoryList);
	    	subcategoryCombo.setItems(tempSubCategoryList);
	    	nameCombo.setItems(productSubCatList);
	    	
	    	categoryCombo.valueProperty().addListener(
					new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {
							
								tempSubCategoryList.clear();
								tempProductList.clear();
								productCatList.clear();
								category = t1;
		
								for (ProductsVO productsVO : productList) {
									if (productsVO.getProductCategory()
											.equals(t1)) {
										if (!tempSubCategoryList
												.contains(productsVO
														.getProductSubCategory())) {
											tempSubCategoryList
													.add(productsVO
															.getProductSubCategory());
										}
										if(productsVO.getProductCategory().equals(category))
							    		{
							    			productCatList.add(productsVO);
							    		}
									}
								}
						}
					});

			subcategoryCombo.valueProperty().addListener(
					new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {

							tempProductList.clear();
							productSubCatList.clear();
							for (ProductsVO productsVO : productCatList) {
								if (productsVO.getProductSubCategory().equals(t1)) {
									tempProductList.add(productsVO);
								}
								if(productsVO.getProductSubCategory().equals(subCategory))
					    		{
					    			productSubCatList.add(productsVO);
					    		}
							}
						}
					});
			nameCombo.valueProperty().addListener(
					new ChangeListener<ProductsVO>() {

						@Override
						public void changed(ObservableValue ov, ProductsVO t,
								ProductsVO t1) {
									productId = t1.getId();
						}
					});
    	}
    	catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
    }
	
	public void fillTextFieldValues(EnquiryViewVO enquiryViewVO)
    {
		this.enquiryViewVO = enquiryViewVO;
		
		for(ProductsVO productsVO : productList)
		{
			if(productsVO.getId() == enquiryViewVO.getProductId())
			{
				category = productsVO.getProductCategory();
				subCategory = productsVO.getProductSubCategory();
				break;
			}
		}
		if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("Standard"))
    	{
    		standard.selectedProperty().set(true);
    		productVbox.setVisible(true);
    		for(ProductsVO productsVO : productList)
	    	{
	    		if(!tempCategoryList.contains(productsVO.getProductCategory()))
	    		{
	    			tempCategoryList.add(productsVO.getProductCategory());
	    		}
	    		if(productsVO.getProductCategory().equals(category))
	    		{
	    			productCatList.add(productsVO);
	    		}
	    	}
	    	for(ProductsVO productsVO : productCatList)
	    	{
	    		if(!tempSubCategoryList.contains(productsVO.getProductSubCategory()))
	    		{
	    			tempSubCategoryList.add(productsVO.getProductSubCategory());
	    		}
	    		if(productsVO.getProductSubCategory().equals(subCategory))
	    		{
	    			productSubCatList.add(productsVO);
	    		}
	    	}
	    	tempProductList.addAll(productSubCatList);
    		
    		
    		int categoryChoice=0,subCategoryChoice = 0,productChoice=0;
    		for(ProductsVO productsVO : productList)
    		{
    			if(productsVO.getId()==enquiryViewVO.getProductId())
        		{
    	    		for(String category1 : tempCategoryList)
    	    		{
    	    			if(category1.equals(productsVO.getProductCategory()))
    	    			{
    	    				break;
    	    			}
    	    			categoryChoice++;
    	    		}
    	    		for(String subCategory1 : tempSubCategoryList)
    	    		{
    	    			if(subCategory1.equals(productsVO.getProductSubCategory()))
    	    			{
    	    				break;
    	    			}
    	    			subCategoryChoice++;
    	    		}
    	    		for(ProductsVO productsVO2 : productSubCatList)
    	    		{
    	    			if(productsVO2.getId() == productsVO.getId())
    	    			{
    	    				break;
    	    			}
    	    			productChoice++;
    	    		}
        		}
    		}
			categoryCombo.getSelectionModel().select(categoryChoice);
			subcategoryCombo.getSelectionModel().select(subCategoryChoice);
			nameCombo.getSelectionModel().select(productChoice);
    	}
    	else if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("Custom"))
    	{
    		custom.selectedProperty().set(true);
    		productHbox.setVisible(true);
    	}
		
		if(enquiryViewVO.getCustomerType().equalsIgnoreCase("Dealer"))
		{
			customerTypeCombo.getSelectionModel().selectFirst();
		}
		else
		{
			customerTypeCombo.getSelectionModel().selectLast();
		}
		
    	city.setText(enquiryViewVO.getCity());
    	companyName.setText(enquiryViewVO.getCompanyName());
    	customerName.setText(enquiryViewVO.getCustomerName());
    	//enquiryType.setText(enquiryViewVO.getEnquiryType());
    	purchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
    	referedBy.setText(enquiryViewVO.getReferedBy());
    	state.setText(enquiryViewVO.getState());
    	productName.setText(enquiryViewVO.getProductName());
    	//date.setText(enquiryViewVO.getDateOfEnquiry());
    	address.setText(enquiryViewVO.getAddress());
    	contactNumber.setText(enquiryViewVO.getContactNumber());
    	filePath.setText(enquiryViewVO.getCustomerFile());
    	customerRequirements.setText(enquiryViewVO.getCustomerRequirement());
    	emailId.setText(enquiryViewVO.getEmailId());
    	tinNumber.setText(enquiryViewVO.getTinNumber());
    }
	
	public void modifyEnquiry()
	{
		LOG.info("Enter : saveEnquiries");
		try
		{
			if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("Standard"))
			{
				productNameText = nameCombo.getSelectionModel().getSelectedItem().getProductName();
			}
			else if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("Custom"))
			{
				productNameText = productName.getText();
			}
			
			EnquiryViewVO enquiryVO=new EnquiryViewVO();
			enquiryVO.setId(enquiryViewVO.getId());
			enquiryVO.setCustomerId(enquiryViewVO.getCustomerId());
			enquiryVO.setProductName(productNameText);
			enquiryVO.setReferedBy(referedBy.getText());
			enquiryVO.setCustomerRequirement(customerRequirements.getText());
			enquiryVO.setPurchasePeriod(purchasePeriod.getText());
			enquiryVO.setCustomerFile(filePath.getText());
			enquiryVO.setPriceEstimation(enquiryViewVO.getPriceEstimation());
			enquiryVO.setQuotationPreparation(enquiryViewVO.getQuotationPreparation());
			enquiryVO.setEmailSent(enquiryViewVO.getEmailSent());
			enquiryVO.setSales(enquiryViewVO.getSales());
			enquiryVO.setDateOfEnquiry(enquiryViewVO.getDateOfEnquiry());
			enquiryVO.setEnquiryType(enquiryViewVO.getEnquiryType().substring(0,1));
			enquiryVO.setReferenceNo(enquiryViewVO.getReferenceNo());
			enquiryVO.setProductId(productId);
			
			CustomersVO customersVO = new CustomersVO();
			customersVO.setCustomerName(customerName.getText());
			customersVO.setCompanyName(companyName.getText());
			customersVO.setAddress(address.getText());
			customersVO.setCity(city.getText());
			customersVO.setState(state.getText());
			customersVO.setEmailId(emailId.getText());
			customersVO.setContactNumber(contactNumber.getText());
			customersVO.setTinNumber(tinNumber.getText());
			customersVO.setId(enquiryViewVO.getCustomerId());
			
			
			enquiryDAO.updateEnquiry(enquiryVO, customersVO);
			messageNewEnquiry.setText(CommonConstants.ENQUIRY_ADD_SUCCESS);
			messageNewEnquiry.getStyleClass().remove("failure");
			messageNewEnquiry.getStyleClass().add("success");
			messageNewEnquiry.setVisible(true);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : saveEnquiries");
	}

}
