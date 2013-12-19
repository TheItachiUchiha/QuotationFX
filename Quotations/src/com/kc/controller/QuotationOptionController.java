package com.kc.controller;

import java.io.File;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.ProductsDAO;
import com.kc.dao.QuotationDAO;
import com.kc.model.ProductsVO;
import com.kc.model.QuotationVO;
import com.kc.util.Encryption;
import com.kc.util.Validation;

public class QuotationOptionController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(QuotationOptionController.class);
	CustomersDAO customersDAO;
	ProductsDAO productsDAO;
	QuotationDAO quotationDAO;
	Validation validation;
	Encryption encryption;
	public QuotationOptionController() {
		customersDAO = new CustomersDAO();
		productsDAO = new ProductsDAO();
		quotationDAO = new QuotationDAO();
		validation = new Validation();
		encryption = new Encryption("");
	}
	
	@FXML
	private ComboBox<String> typeCombo;
	@FXML
	private ComboBox<String> categoryCombo;
	@FXML
	private ComboBox<String> subcategoryCombo;
	@FXML
	private ComboBox<ProductsVO> nameCombo;
	@FXML
	private TextArea productArea;
	@FXML
	private TextField defaultQuotationFile;
	@FXML
	private TextField wordFormat;
	@FXML
	private TextField pdfFormat;
	@FXML
	private TextField username;
	
	@FXML
	private TextField emailId;
	@FXML
	private PasswordField password;
	@FXML
	private Button defaultLocation;
	@FXML
	private Button wordLocation;
	@FXML
	private Button pdfLocation;
	@FXML
	private Label message;
	@FXML
	private Label messageSent;
	
	@FXML
	private Label messageEmail;
	@FXML
	private Button clear;
	
	@FXML
	private Button define;
	@FXML
	private GridPane standardGrid;
	
	@FXML
	private GridPane setPathGrid;
	
	int selectedProductId=0;
	
	private ObservableList<String> categoryList = FXCollections.observableArrayList();
	private ObservableList<String> subcategoryList = FXCollections.observableArrayList();
	private ObservableList<String> nameList = FXCollections.observableArrayList();
	private ObservableList<ProductsVO> productList = FXCollections.observableArrayList();
	ObservableList<ProductsVO> standardList = FXCollections.observableArrayList();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	private Map<String, String> defaultValues = new HashMap<String, String>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> tempProductCategoryList = FXCollections.observableArrayList();
		final ObservableList<String> tempProductSubCategoryList = FXCollections.observableArrayList();
		final ObservableList<ProductsVO> tempProductList = FXCollections.observableArrayList();
		try
		{
			categoryCombo.setItems(tempProductCategoryList);
			subcategoryCombo.setItems(tempProductSubCategoryList);
			nameCombo.setItems(tempProductList);
			standardList.addAll(quotationDAO.getQuotationProducts());
			for(ProductsVO productsVO: standardList)
			{
				if(!tempProductCategoryList.contains(productsVO.getProductCategory()))
				{
					tempProductCategoryList.add(productsVO.getProductCategory());
				}
			}
			
			categoryCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String oldValue, String newValue) {
					setPathGrid.setVisible(false);
					if(null!=newValue)
					{
						tempProductSubCategoryList.clear();
						for(ProductsVO productsVO: standardList)
						{
							if(productsVO.getProductCategory().equals(newValue))
							{
								if(!tempProductSubCategoryList.contains(productsVO.getProductSubCategory()))
								{
									tempProductSubCategoryList.add(productsVO.getProductSubCategory());
								}
							}
						}
					}
					
				}
			});
			subcategoryCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String oldValue, String newValue) {
					setPathGrid.setVisible(false);
					if(null!=newValue)
					{
						tempProductList.clear();
						for(ProductsVO productsVO: standardList)
						{
							if(productsVO.getProductSubCategory().equals(newValue))
							{
								if(!tempProductList.contains(productsVO))
								{
									tempProductList.add(productsVO);
								}
							}
						}
					}
					
				}
			});
			nameCombo.valueProperty().addListener(new ChangeListener<ProductsVO>() {

				@Override
				public void changed(ObservableValue<? extends ProductsVO> arg0,
						ProductsVO oldValue, ProductsVO newValue) {
					selectedProductId = newValue.getId();
				}
			});
			defaultLocation.setOnAction(new EventHandler<ActionEvent>() {
			     @Override
			     public void handle(ActionEvent event) {
			     	   Stage stage=new Stage();
			           FileChooser fileChooser = new FileChooser();
			           File tempFile = fileChooser.showOpenDialog(stage);
			           if(tempFile!=null){
			                   defaultQuotationFile.setText(tempFile.getPath());
			           }
			        	}
			});
			wordLocation.setOnAction(new EventHandler<ActionEvent>() {
			     @Override
			     public void handle(ActionEvent event) {
			        DirectoryChooser directoryChooser = new DirectoryChooser();
			        File tempFile = directoryChooser.showDialog(null);
			        if(tempFile!=null){
			        	wordFormat.setText(tempFile.getPath());
			        }
			     }
			});
			pdfLocation.setOnAction(new EventHandler<ActionEvent>() {
			     @Override
			     public void handle(ActionEvent event) {
			        DirectoryChooser directoryChooser = new DirectoryChooser();
			        File tempFile = directoryChooser.showDialog(null);
			        if(tempFile!=null){
			        	pdfFormat.setText(tempFile.getPath());
			        }
			     }
			});
		productArea.setEditable(false);
		typeCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				try
				{
				
				if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Standard"))
				{
					setPathGrid.setVisible(false);
					standardGrid.setVisible(true);
					for(ProductsVO productsVO : standardList)
					{
						if(!categoryList.contains(productsVO.getProductCategory()))
							categoryList.addAll(productsVO.getProductCategory());
						if(!subcategoryList.contains(productsVO.getProductSubCategory()))
							subcategoryList.addAll(productsVO.getProductSubCategory());
						if(!nameList.contains(productsVO.getProductName()))
							nameList.addAll(productsVO.getProductName());
					}
					String product="";
					if(nameList.size()!=0)
					{
						for(String string: nameList)
						{
								product = product + string + ", ";
						}
						product = product.substring(0, product.length()-2);
					}
					productArea.setText(product);
				}
				else if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Custom"))
				{
					setPathGrid.setVisible(true);
					standardGrid.setVisible(false);
					productArea.setText("");
					categoryCombo.getSelectionModel().clearSelection();
					subcategoryCombo.getSelectionModel().clearSelection();
					nameCombo.getSelectionModel().clearSelection();
					defaultValues = quotationDAO.getCustomDefaultValues();
				    defaultQuotationFile.setText(defaultValues.get(CommonConstants.KEY_QUOTATION_PATH));
				    wordFormat.setText(defaultValues.get(CommonConstants.KEY_QUOTATION_WORD_PATH));
				    pdfFormat.setText(defaultValues.get(CommonConstants.KEY_QUOTATION_PDF_PATH));
				}
			}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		define.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent paramT) {
				setPathGrid.setVisible(true);
				defaultQuotationFile.setText("");
				wordFormat.setText("");
				pdfFormat.setText("");
				
			}
		});
		
		defaultValues = quotationDAO.getEmailDetails();
	    username.setText(defaultValues.get(CommonConstants.KEY_QUOTATION_USERNAME));
	    emailId.setText(defaultValues.get(CommonConstants.KEY_QUOTATION_EMAIL));
	    password.setText(encryption.decrypt(defaultValues.get(CommonConstants.KEY_QUOTATION_PASSWORD)));
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void saveConfigurations() throws Exception
	{
		try
		{
			if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Custom"))
				{
				if(validation.isEmpty(defaultQuotationFile,wordFormat,pdfFormat))
				{
					message.setText(CommonConstants.MANDATORY_FIELDS);
					message.getStyleClass().remove("success");
					message.getStyleClass().add("failure");
					message.setVisible(true);
				}
				else
				{
					defaultValues.put(CommonConstants.KEY_QUOTATION_PATH, defaultQuotationFile.getText().replace("\\", "\\\\"));
					defaultValues.put(CommonConstants.KEY_QUOTATION_WORD_PATH, wordFormat.getText().replace("\\", "\\\\"));
					defaultValues.put(CommonConstants.KEY_QUOTATION_PDF_PATH, pdfFormat.getText().replace("\\", "\\\\"));
					quotationDAO.saveConfiguration(defaultValues, simpleDateFormat.format(new Date()));
					message.setText(CommonConstants.CONF_SAVED);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
			
				}
			}
			else if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Standard"))
			{
				if(validation.isEmpty(defaultQuotationFile,wordFormat,pdfFormat))
				{
					message.setText(CommonConstants.MANDATORY_FIELDS);
					message.getStyleClass().remove("success");
					message.getStyleClass().add("failure");
					message.setVisible(true);
				}
				else
				{
					QuotationVO quotationVO = new QuotationVO();
					quotationVO.setPdfPath(pdfFormat.getText());
					quotationVO.setWordPath(wordFormat.getText());
					quotationVO.setQuotationPath(defaultQuotationFile.getText());
					quotationVO.setProductId(selectedProductId);
					quotationDAO.saveStandardProductPath(quotationVO);
					message.setText(CommonConstants.CONF_SAVED);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
				}
			}
		}
		catch (Exception e) {
			message.setText(CommonConstants.OPTION_ERROR);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			message.setVisible(true);
			LOG.error(e.getMessage());
		}
	}
	public void saveEmail()
	{
		if(validation.isEmpty(emailId,username,password))
		{
			messageEmail.setText(CommonConstants.MANDATORY_FIELDS);
			messageEmail.getStyleClass().remove("success");
			messageEmail.getStyleClass().add("failure");
			messageEmail.setVisible(true);
		}
		else if(!validation.isEmail(emailId.getText()))
		{
			messageEmail.setText(CommonConstants.INCORRECT_EMAIL);
			messageEmail.getStyleClass().remove("success");
			messageEmail.getStyleClass().add("failure");
			messageEmail.setVisible(true);
		}
		else
		{
			defaultValues.put(CommonConstants.KEY_QUOTATION_USERNAME, username.getText());
			defaultValues.put(CommonConstants.KEY_QUOTATION_EMAIL, emailId.getText());
			defaultValues.put(CommonConstants.KEY_QUOTATION_PASSWORD, encryption.encrypt(password.getText()));
			quotationDAO.saveConfiguration(defaultValues, simpleDateFormat.format(new Date()));
			messageEmail.setText(CommonConstants.CONF_SAVED);
			messageEmail.getStyleClass().remove("failure");
			messageEmail.getStyleClass().add("success");
			messageEmail.setVisible(true);
		}
		
	}
	public void clearFields()
	{
		password.setText("");
		username.setText("");
		emailId.setText("");
		messageEmail.setText("");
	}
}
