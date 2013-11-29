package com.kc.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ProductsDAO;
import com.kc.model.EnquiryVO;
import com.kc.model.CustomersVO;
import com.kc.model.ProductsVO;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class EnquiryNewController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(EnquiryNewController.class);
	@FXML
	private ToggleGroup productType;
	@FXML
	private RadioButton standard;
	@FXML
	private RadioButton custom;
	@FXML
	private VBox productVbox;
	@FXML
	private ComboBox<String> categoryCombo;
	@FXML
	private ComboBox<String> subcategoryCombo;
	@FXML
	private ComboBox<ProductsVO> nameCombo;
	@FXML
	private TextField productName;
	@FXML
	private GridPane enquiryGrid;
	@FXML
	private AutoCompleteTextField<String> tinNumber;
	@FXML
	private AutoCompleteTextField<String> emailId;
	@FXML
	private TextArea customerRequirements;
	@FXML
	private TextArea emailMessage;
	@FXML
	private AutoCompleteTextField<CustomersVO> referedBy;
	@FXML
	private ComboBox<String> customerTypeCombo;
	@FXML
	private TextField customerName;
	@FXML
	private TextField companyName;
	@FXML
	private TextArea address;
	@FXML
	private TextField city;
	@FXML
	private TextField state;
	@FXML
	private TextField contactNumber;
	@FXML
	private TextField purchasePeriod;
	@FXML
	private Label messageSendMail;
	@FXML
	private Label messageNewEnquiry;
	private TextField filePath;
	
	private ObservableList<ProductsVO> productsList;
	private ProductsDAO productsDAO;
	private ProductsVO productsVO;
	private ObservableList<CustomersVO> customersList;
	private CustomersDAO customersDAO;
	private EnquiryDAO enquiryDAO;
	private CustomersVO customersVO;
	private Validation validation;
	private char flag='N';
	
	public EnquiryNewController() {
		customersDAO = new CustomersDAO();
		customersVO = new CustomersVO();
		productsDAO = new ProductsDAO();
		productsVO = new ProductsVO();
		enquiryDAO = new EnquiryDAO();
		validation = new Validation();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		filePath = new TextField();
		Button browse = new Button();
        browse.setText("Browse");
        final HBox hBox =new HBox(5);
        hBox.getChildren().addAll(filePath,browse);
        enquiryGrid.add(hBox,3,1);

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
        productType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0,
					Toggle arg1, Toggle arg2) {
				enquiryGrid.setVisible(false);
				if(standard.isSelected())
				{
					productName.setVisible(false);
					productVbox.setVisible(true);
					productName.setText("");
				}
				else if(custom.isSelected())
				{
					productVbox.setVisible(false);
					productName.setVisible(true);
					categoryCombo.getSelectionModel().clearSelection();
					enquiryGrid.setVisible(true);
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
					filePath.setText("");
					emailMessage.setText("");
				}
			}
		});
        try {
        	
        	customersList = customersDAO.getCustomers();
			productsList = productsDAO.getProducts();
			
			final ObservableList<ProductsVO> tempProductsList = FXCollections
					.observableArrayList();

			ObservableList<String> tempCategoryList = FXCollections
					.observableArrayList();
			final ObservableList<String> tempSubCategoryList = FXCollections
					.observableArrayList();
			final ObservableList<ProductsVO> tempProductList = FXCollections
					.observableArrayList();
			subcategoryCombo.setItems(tempSubCategoryList);
			nameCombo.setItems(tempProductList);

			for (ProductsVO productsVO : productsList) {
				if (!tempCategoryList.contains(productsVO
						.getProductCategory())) {
					tempCategoryList.add(productsVO.getProductCategory());
				}
			}

			categoryCombo.setItems(tempCategoryList);

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
							filePath.setText("");
							emailMessage.setText("");
							enquiryGrid.setVisible(false);
							try {
								productsList.clear();

								productsList = productsDAO.getProducts();

								tempSubCategoryList.clear();

								tempProductsList.clear();
								for (ProductsVO productsVO : productsList) {
									if (productsVO.getProductCategory()
											.equals(t1)) {
										if (!tempSubCategoryList
												.contains(productsVO
														.getProductSubCategory())) {
											tempSubCategoryList
													.add(productsVO
															.getProductSubCategory());
											tempProductsList
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

							for (ProductsVO productsVO : tempProductsList) {
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
						}
					});
			tinNumber.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					for(CustomersVO customersVO: customersList)
					{
						if(customersVO.getTinNumber().equals(tinNumber.getText())){
							fillTextFieldValues(customersVO);
						}
					}
				}
			});
			customersList = customersDAO.getCustomers();
			referedBy.setItems(customersList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void fillTextFieldValues(CustomersVO customersVO)
	{
		customerName.setText(customersVO.getCustomerName());
		companyName.setText(customersVO.getCompanyName());
		address.setText(customersVO.getAddress());
		city.setText(customersVO.getCity());
		state.setText(customersVO.getState());
		emailId.setText(customersVO.getEmailId());
		contactNumber.setText(customersVO.getContactNumber());
		if((customersVO.getCustomerType().equalsIgnoreCase("D")))
		{
			customerTypeCombo.getSelectionModel().selectFirst();
		}
		else
		{
			customerTypeCombo.getSelectionModel().selectLast();
		}
		flag = 'Y';
		this.customersVO = customersVO;
	}
	
	public CustomersVO fillDataFromTextFields()
	{
		CustomersVO customersVO = null;
		try{
			customersVO = new CustomersVO();
			customersVO.setCustomerName(customerName.getText());
			customersVO.setCompanyName(companyName.getText());
			customersVO.setTinNumber(tinNumber.getText());
			if(customerTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Dealer"))
			{
				customersVO.setCustomerType("D");
			}
			else if(customerTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("End User"))
			{
				customersVO.setCustomerType("E");
			}
			customersVO.setAddress(address.getText());
			customersVO.setCity(city.getText());
			customersVO.setState(state.getText());
			customersVO.setEmailId(emailId.getText());
			customersVO.setContactNumber(contactNumber.getText());
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return customersVO;
	}
	
	public void saveEnquiries()
	{
		int customerId = 0;
		int productId = 0;
		for (ProductsVO productsVO : productsList) {
			if (nameCombo.getSelectionModel().getSelectedItem().getId() == productsVO.getId()) {
				productId=productsVO.getId();
			}
		}
		try
		{
			if(flag=='N')
			{
				customerId = customersDAO.saveCustomer(fillDataFromTextFields());
			}
			else
			{
				customerId = this.customersVO.getId();
			}
			EnquiryVO enquiryVO=new EnquiryVO();
			enquiryVO.setCustomerId(customerId);
			enquiryVO.setProductId(productId);
			enquiryVO.setReferedBy(referedBy.getText());
			enquiryVO.setCustomerrequirements(customerRequirements.getText());
			enquiryVO.setPurchasePeriod(purchasePeriod.getText());
			enquiryVO.setCustomerDocument(filePath.getText());
			enquiryVO.setEmailMessage(emailMessage.getText());
			enquiryVO.setPriceEstimation("N");
			enquiryVO.setQuotationPreparation("N");
			enquiryVO.setEmailSent("N");
			enquiryVO.setSales("N");
			enquiryVO.setDate((new Date().toString()));
			enquiryDAO.saveEnquiry(enquiryVO);
			messageNewEnquiry.setText(CommonConstants.ENQUIRY_ADD_SUCCESS);
			messageNewEnquiry.getStyleClass().remove("failure");
			messageNewEnquiry.getStyleClass().add("success");
			messageNewEnquiry.setVisible(true);
			}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
}
