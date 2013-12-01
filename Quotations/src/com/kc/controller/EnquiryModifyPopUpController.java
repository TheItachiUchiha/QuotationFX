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
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ProductsVO;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class EnquiryModifyPopUpController {
	private static final Logger LOG = LogManager
			.getLogger(EnquiryModifyPopUpController.class);

	private EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
	private ObservableList<ProductsVO> productList = FXCollections
			.observableArrayList();
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
    		filePath.setDisable(true);
    		filePath.setPrefWidth(300);
    		Button browse = new Button();
            browse.setText("Browse");
            browse.setPrefWidth(65);
            final HBox hBox =new HBox(5);
            hBox.getChildren().addAll(filePath,browse);
            enquiryGrid.add(hBox,3,0);

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
	    	tempProductList.addAll(productList);
	        //get a enquiryViewVo object from the table
	    	for(ProductsVO productsVO : productList)
	    	{
	    		tempCategoryList.add(productsVO.getProductCategory());
	    		tempSubCategoryList.add(productsVO.getProductSubCategory());
	    	}
	    	
	    	categoryCombo.setItems(tempCategoryList);
	    	subcategoryCombo.setItems(tempSubCategoryList);
	    	nameCombo.setItems(tempProductList);
	    	
	    	categoryCombo.valueProperty().addListener(
					new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {
							tinNumber.setText("");
							emailId.setText("");
							referedBy.setText("");
							customerName.setText("");
							customerTypeCombo.getSelectionModel().clearSelection();
							companyName.setText("");
							address.setText("");
							city.setText("");
							state.setText("");
							contactNumber.setText("");
							customerRequirements.setText("");
							purchasePeriod.setText("");
							//filePath.setText("");
							//emailMessage.setText("");
							enquiryGrid.setVisible(false);
							try {
								productList.clear();

								productList = productsDAO.getProducts();

								tempSubCategoryList.clear();
								tempProductList.clear();
		
								for (ProductsVO productsVO : productList) {
									if (productsVO.getProductCategory()
											.equals(t1)) {
										if (!tempSubCategoryList
												.contains(productsVO
														.getProductSubCategory())) {
											tempSubCategoryList
													.add(productsVO
															.getProductSubCategory());
											tempProductList
													.add(productsVO);
										}
									}
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					});

			subcategoryCombo.valueProperty().addListener(
					new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {

							tempProductList.clear();
							enquiryGrid.setVisible(false);

							for (ProductsVO productsVO : tempProductList) {
								if (productsVO.getProductSubCategory().equals(t1)) {
									tempProductList.add(productsVO);
								}
							}
						}
					});
			nameCombo.valueProperty().addListener(
					new ChangeListener<ProductsVO>() {

						@Override
						public void changed(ObservableValue ov, ProductsVO t,
								ProductsVO t1) {
							enquiryGrid.setVisible(true);
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
		if(enquiryViewVO.getEnquiryType().equalsIgnoreCase("Standard"))
    	{
    		standard.selectedProperty().set(true);
    		productVbox.setVisible(true);
    		for(ProductsVO productsVO : productList)
    		{
    			if(enquiryViewVO.getProductId() == productsVO.getId())
    			{
    				categoryCombo.getSelectionModel().selectNext();
        			subcategoryCombo.getSelectionModel().selectNext();
        			nameCombo.getSelectionModel().selectNext();
    				break;
    			}
    			categoryCombo.getSelectionModel().selectNext();
    			subcategoryCombo.getSelectionModel().selectNext();
    			nameCombo.getSelectionModel().selectNext();
    		}
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
    	//customerFile.setText(enquiryViewVO.getCustomerFile());
    	customerRequirements.setText(enquiryViewVO.getCustomerRequirement());
    	emailId.setText(enquiryViewVO.getEmailId());
    	tinNumber.setText(enquiryViewVO.getTinNumber());
    }
	
	public void modifyEnquiry()
	{
		LOG.info("Enter : saveEnquiries");
		int customerId = 0;
		
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
			enquiryVO.setCustomerId(customerId);
			enquiryVO.setProductName(productNameText);
			enquiryVO.setReferedBy(referedBy.getText());
			enquiryVO.setCustomerRequirement(customerRequirements.getText());
			enquiryVO.setPurchasePeriod(purchasePeriod.getText());
			enquiryVO.setCustomerFile(filePath.getText());
			enquiryVO.setPriceEstimation(enquiryViewVO.getPriceEstimation());
			enquiryVO.setQuotationPreparation(enquiryViewVO.getQuotationPreparation());
			enquiryVO.setEmailSent(enquiryViewVO.getEmailSent());
			enquiryVO.setSales(enquiryViewVO.getSales());
			enquiryVO.setEnquiryType(enquiryViewVO.getEnquiryType().substring(0,1));
			enquiryVO.setProductId(productId);
			//enquiryDAO.updateEnquiry(enquiryVO);
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
