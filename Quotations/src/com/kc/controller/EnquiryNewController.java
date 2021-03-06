package com.kc.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ProductsDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.ProductsVO;
import com.kc.util.Email;
import com.kc.util.Validation;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

@SuppressWarnings("rawtypes")
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
	private HBox productHbox;
	@FXML
	private ComboBox<String> categoryCombo;
	@FXML
	private ComboBox<String> subcategoryCombo;
	@FXML
	private ComboBox<ProductsVO> nameCombo;
	@FXML
	private TextField productName;
	@FXML
	private Label standardRef;
	@FXML
	private Label customRef;
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
	private TextField cc;
	@FXML
	private TextField subject;
	@FXML
	private Label messageSendMail;
	@FXML
	private Label messageNewEnquiry;
	private TextField filePath;
	private String typeFlag="S";
	private int productId=0;
	
	private ObservableList<ProductsVO> productsList;
	private ProductsDAO productsDAO;
	private ObservableList<CustomersVO> customersList;
	private CustomersDAO customersDAO;
	private EnquiryDAO enquiryDAO;
	private CustomersVO customersVO;
	private Validation validation;
	private char flag='N';
	private String productCode ="";
	private String date;
	private String month;
	private String year;
	private String enquiryNumber;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	private Map<String, String> defaultValues = new HashMap<String, String>();
	
	public EnquiryNewController() {
		customersDAO = new CustomersDAO();
		customersVO = new CustomersVO();
		productsDAO = new ProductsDAO();
		enquiryDAO = new EnquiryDAO();
		validation = new Validation();
		date = simpleDateFormat.format(new Date()); 
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	try {
		LOG.info("Enter : initialize");
		
        customersList = customersDAO.getCustomers();
		productsList = productsDAO.getProducts();
		
		month = date.substring(3,5);
		year = date.substring(8,10);
		
		ObservableList<String> tempTinList = FXCollections.observableArrayList();
		ObservableList<String> tempEmailList = FXCollections.observableArrayList();
		ObservableList<String> tempCategoryList = FXCollections.observableArrayList();
		final ObservableList<String> tempSubCategoryList = FXCollections.observableArrayList();
		final ObservableList<ProductsVO> tempProductList = FXCollections.observableArrayList();
		defaultValues = enquiryDAO.getEnquiryOptionDefaultValues();
		filePath = new TextField();
		filePath.setEditable(false);
		filePath.setText("");
		filePath.setPrefWidth(300);
		Button browse = new Button();
        browse.setText("Browse");
        browse.getStyleClass().add("button-style");
        browse.setPrefWidth(65);
        final HBox hBox =new HBox(5);
        hBox.getChildren().addAll(filePath,browse);
        hBox.setAlignment(Pos.CENTER_LEFT);
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
        productType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0,
					Toggle oldValue, Toggle newValue) {
				enquiryGrid.setVisible(false);
				if(standard.isSelected())
				{
						productHbox.setVisible(false);
						productVbox.setVisible(true);
						productName.setText("");
						cc.setText("");
						typeFlag="S";
						customRef.setVisible(false);
				}
				else if(custom.isSelected())
				{
					typeFlag="C";
					productVbox.setVisible(false);
					productHbox.setVisible(true);
					categoryCombo.getSelectionModel().clearSelection();
					enquiryGrid.setVisible(true);
					cc.setText("");
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
					emailMessage.setText(defaultValues.get(CommonConstants.KEY_ENQUIRY_MESSAGE));
					messageNewEnquiry.setText("");
					standardRef.setVisible(false);
					productCode = CommonConstants.CUSTOM_PRODUCT_CODE;
					enquiryNumber = enquiryDAO.getLatestEnquiryNumber();
					customRef.setText("Ref No. : "+defaultValues.get(CommonConstants.KEY_ENQUIRY_BRANCH_CODE)+defaultValues.get(CommonConstants.KEY_ENQUIRY_DEFAULT_CODE)+productCode + month + year + enquiryNumber);
					
				}
			}
		});
        
        productName.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
					
				customRef.setText("Ref No. : "+defaultValues.get(CommonConstants.KEY_ENQUIRY_BRANCH_CODE)+defaultValues.get(CommonConstants.KEY_ENQUIRY_DEFAULT_CODE)+productCode + month + year + enquiryNumber);
				customRef.setVisible(true);
			}
		});
			
			final ObservableList<ProductsVO> tempProductsList = FXCollections
					.observableArrayList();

			
			subcategoryCombo.setItems(tempSubCategoryList);
			nameCombo.setItems(tempProductList);

			for (ProductsVO productsVO : productsList) {
				if (!tempCategoryList.contains(productsVO
						.getProductCategory())) {
					tempCategoryList.add(productsVO.getProductCategory());
				}
			}
			FXCollections.sort(tempCategoryList);

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
							messageNewEnquiry.setText("");
							standardRef.setText("");
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
								FXCollections.sort(tempSubCategoryList);
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
							standardRef.setText("");

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
							if(null!=t1)
							{
								enquiryGrid.setVisible(true);
								productId = t1.getId();
								emailMessage.setText(defaultValues.get(CommonConstants.KEY_ENQUIRY_MESSAGE));
								productCode=t1.getProductCode().substring(0, 2);
								enquiryNumber = enquiryDAO.getLatestEnquiryNumber();
								standardRef.setText("Ref No. : "+defaultValues.get(CommonConstants.KEY_ENQUIRY_BRANCH_CODE)+defaultValues.get(CommonConstants.KEY_ENQUIRY_DEFAULT_CODE)+productCode + month + year + enquiryNumber);
								standardRef.setVisible(true);
							}
						}
					});
			tinNumber.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					for(CustomersVO customersVO: customersList)
					{
						if(customersVO.getTinNumber().equals(tinNumber.getText())){
							fillTextFieldValues(customersVO);
							customerRequirements.requestFocus();
						}
					}
				}
			});
			emailId.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					for(CustomersVO customersVO: customersList)
					{
						if(customersVO.getEmailId().equals(emailId.getText())){
							fillTextFieldValues(customersVO);
							customerRequirements.requestFocus();
						}
					}
				}
			});
			customersList = customersDAO.getCustomers();
			for(CustomersVO customersVO : customersList)
			{
				if(!tempTinList.contains(customersVO.getTinNumber()))
					tempTinList.add(customersVO.getTinNumber());
				if(!tempEmailList.contains(customersVO.getEmailId()))
				{
					tempEmailList.add(customersVO.getEmailId());
				}
			}
			tinNumber.setItems(tempTinList);
			emailId.setItems(tempEmailList);
			referedBy.setItems(customersList);
			
			
			validation.allowAsPhoneNumber(contactNumber);
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
        LOG.info("Exit : initialize");
	}
	public void fillTextFieldValues(CustomersVO customersVO)
	{
		LOG.info("Enter : fillTextFieldValues");
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
		LOG.info("Exit : fillTextFieldValues");
	}
	
	public CustomersVO fillDataFromTextFields()
	{
		LOG.info("Enter : fillDataFromTextFields");
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
		LOG.info("Exit : fillDataFromTextFields");
		return customersVO;
	}
	
	public void saveEnquiries()
	{
		LOG.info("Enter : saveEnquiries");
		int customerId = 0;
		String productName = "";
		
		try
		{
			if(validation.isEmpty(customerName, companyName, city, state,purchasePeriod))
			{
				messageNewEnquiry.setText(CommonConstants.MANDATORY_FIELDS);
				messageNewEnquiry.getStyleClass().remove("success");
				messageNewEnquiry.getStyleClass().add("failure");
				messageNewEnquiry.setVisible(true);
			}
			else if(customerRequirements.getText().length()==0||address.getText().length()==0)
			{
				messageNewEnquiry.setText(CommonConstants.MANDATORY_FIELDS);
				messageNewEnquiry.getStyleClass().remove("success");
				messageNewEnquiry.getStyleClass().add("failure");
				messageNewEnquiry.setVisible(true);
			}
			else if(emailId.getText().length()!=0)
			{
				if(!validation.isEmail(emailId.getText()))
				{
					messageNewEnquiry.setText(CommonConstants.INCORRECT_EMAIL);
					messageNewEnquiry.getStyleClass().remove("success");
					messageNewEnquiry.getStyleClass().add("failure");
					messageNewEnquiry.setVisible(true);
				}
			
			else
				{
					if(typeFlag.equals("S"))
					{
						for (ProductsVO productsVO : productsList) {
							if (nameCombo.getSelectionModel().getSelectedItem().getId() == productsVO.getId()) {
								productName=productsVO.getProductName();
								//productCode=productsVO.getProductCode().substring(0, 2);
							}
						}
					}
					else
					{
						productName = this.productName.getText();
						//productCode = CommonConstants.CUSTOM_PRODUCT_CODE;
					}
					
					if(flag=='N')
					{
						customerId = customersDAO.saveCustomer(fillDataFromTextFields());
					}
					else
					{
						customerId = this.customersVO.getId();
					}
					enquiryNumber = enquiryDAO.getLatestEnquiryNumber();
					//String month = date.substring(3,5);
					//String year = date.substring(8,10);
					EnquiryVO enquiryVO=new EnquiryVO();
					enquiryVO.setCustomerId(customerId);
					enquiryVO.setRefNumber(defaultValues.get(CommonConstants.KEY_ENQUIRY_BRANCH_CODE)+defaultValues.get(CommonConstants.KEY_ENQUIRY_DEFAULT_CODE)+productCode + month + year + enquiryNumber);
					enquiryVO.setProductName(productName);
					enquiryVO.setReferedBy(referedBy.getText());
					enquiryVO.setCustomerrequirements(customerRequirements.getText());
					enquiryVO.setPurchasePeriod(purchasePeriod.getText());
					enquiryVO.setCustomerDocument(filePath.getText());
					enquiryVO.setEmailMessage(emailMessage.getText());
					enquiryVO.setPriceEstimation("N");
					enquiryVO.setQuotationPreparation("N");
					enquiryVO.setEmailSent("N");
					enquiryVO.setSales("N");
					enquiryVO.setDispatchDone("N");
					enquiryVO.setDate(date);
					enquiryVO.setFlag(typeFlag);
					enquiryVO.setProductId(productId);
					enquiryDAO.saveEnquiry(enquiryVO);
					enquiryDAO.increaseEnquiryNumber(generateNewEnuiryNumber(enquiryNumber), date);
					messageNewEnquiry.setText(CommonConstants.ENQUIRY_ADD_SUCCESS);
					messageNewEnquiry.getStyleClass().remove("failure");
					messageNewEnquiry.getStyleClass().add("success");
					messageNewEnquiry.setVisible(true);
				}
			}
			}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : saveEnquiries");
	}
	
	private String generateNewEnuiryNumber(String oldEnquiryNumber)
	{
		String newEnquiryNumber="";
		try{
			int oldEnquiry = Integer.parseInt(oldEnquiryNumber);
			if(oldEnquiry<10)
			{
				newEnquiryNumber = "00" + (oldEnquiry+1);
			}
			else if(oldEnquiry>=10&&oldEnquiry<100)
			{
				newEnquiryNumber = "0" + (oldEnquiry + 1);
			}
			else
			{
				newEnquiryNumber = String.valueOf(oldEnquiry + 1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return newEnquiryNumber;
	}
	
	public void sendEmail()
	{
		Map<String, String> map = new HashMap<String, String>();
		Map<String,String> emailMap = new HashMap<String,String>();
		emailMap = enquiryDAO.getEnquiryOptionDefaultValues();
		if(validation.isEmail(emailId.getText()))
		{
				if(!emailMessage.getText().equals(""))
				{
					map.put(CommonConstants.EMAIL_TO, emailId.getText());
					map.put(CommonConstants.EMAIL_CC, cc.getText());
					map.put(CommonConstants.EMAIL_SUBJECT, subject.getText());
					map.put(CommonConstants.EMAIL_ATTACHMENT, filePath.getText());
					map.put(CommonConstants.EMAIL_BODY, emailMessage.getText());
					map.put(CommonConstants.EMAIL_USERNAME, emailMap.get(CommonConstants.KEY_ENQUIRY_EMAIL_USERNAME));
					map.put(CommonConstants.EMAIL_PASSWORD, emailMap.get(CommonConstants.KEY_ENQUIRY_EMAIL_PASSWORD));
					Email email = new Email(map);
					new Thread(email).start();
					messageSendMail.getStyleClass().remove("failure");
					messageSendMail.getStyleClass().add("success");
					messageSendMail.setText("Email send successfully");
					messageSendMail.setVisible(true);
				}
				else
				{
					messageSendMail.getStyleClass().remove("success");
					messageSendMail.getStyleClass().add("failure");
					messageSendMail.setText("Empty Email Body");
					messageSendMail.setVisible(true);
				}
		}
		else
		{
			messageSendMail.getStyleClass().remove("success");
			messageSendMail.getStyleClass().add("failure");
			messageSendMail.setText("Empty To Address");
			messageSendMail.setVisible(true);
		}
	}
}
