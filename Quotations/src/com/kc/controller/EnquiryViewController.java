package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
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
			enquiryViewList = fillEnquiryViewListFromEnquiryList(enquiryList);
			productList = productsDAO.getProducts();
			
			
			monthCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					try{
						enquiryListForTable.clear();
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
				}
			});
			go.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					enquiryTable.getItems().clear();
					fillTableFromData();
				}
			});
			action.setSortable(false);
	         
	        action.setCellValueFactory(
	                new Callback<TableColumn.CellDataFeatures<EnquiryViewVO, Boolean>,
	                ObservableValue<Boolean>>() {
	 
	            @Override
	            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<EnquiryViewVO, Boolean> p) {
	                return new SimpleBooleanProperty(p.getValue() != null);
	            }
	        });
	 
	        action.setCellFactory(
	                new Callback<TableColumn<EnquiryViewVO, Boolean>, TableCell<EnquiryViewVO, Boolean>>() {
	 
	            @Override
	            public TableCell<EnquiryViewVO, Boolean> call(TableColumn<EnquiryViewVO, Boolean> p) {
	                return new ButtonCell();
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
		tempList.clear();
		enquiryViewList.clear();
		enquiryViewList = fillEnquiryViewListFromEnquiryList(enquiryList);
		
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
			else if(searchCombo.getSelectionModel().getSelectedItem().equals("Ref_No"))
			{
				for(EnquiryViewVO enquiryViewVO : enquiryViewList)
				{
					if(enquiryViewVO.getReferenceNo().equalsIgnoreCase(tempString))
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
	public void deleteEnquiry(EnquiryViewVO enquryViewVO) throws Exception
	{
		LOG.info("Enter : deleteEnquiry");
		try{
				DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
					    "Do you want to delete selected Enquiry(s)", "Confirm", "Delete Enquiry", DialogOptions.OK_CANCEL);
				if(response.equals(DialogResponse.OK))
				{
					enquiryDAO.deleteEnquiry(enquryViewVO);
					/*message.setText(CommonConstants.COMPONENT_DELETE_SUCCESS);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);*/
					fillAutoCompleteFromComboBox(searchCombo.getSelectionModel().getSelectedItem());
					fillTableFromData();
					
				}
				LOG.info("Exit : deleteEnquiry");
		}
		catch(SQLException s)
		{
			s.printStackTrace();
		}
	}
	
	public ObservableList<EnquiryViewVO> fillEnquiryViewListFromEnquiryList(ObservableList<EnquiryVO> enquiryList)
	{
		ObservableList<EnquiryViewVO> tempList = FXCollections.observableArrayList();
		
		
		for(EnquiryVO enquiryVO : enquiryList)
		{
			EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
			enquiryViewVO.setId(enquiryVO.getId());
			enquiryViewVO.setDateOfEnquiry(enquiryVO.getDate());
			enquiryViewVO.setProductName(enquiryVO.getProductName());
			enquiryViewVO.setPurchasePeriod(enquiryVO.getPurchasePeriod());
			enquiryViewVO.setReferedBy(enquiryVO.getReferedBy());
			enquiryViewVO.setReferenceNo(enquiryVO.getRefNumber());
			enquiryViewVO.setCustomerRequirement(enquiryVO.getCustomerrequirements());
			enquiryViewVO.setCustomerFile(enquiryVO.getCustomerDocument());
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
					enquiryViewVO.setAddress(customersVO.getAddress());
					enquiryViewVO.setEmailId(customersVO.getEmailId());
					enquiryViewVO.setTinNumber(customersVO.getTinNumber());
					enquiryViewVO.setContactNumber(customersVO.getContactNumber());
					enquiryViewVO.setCustomerId(customersVO.getId());
					if(customersVO.getCustomerType().equalsIgnoreCase("Dealer"))
					{
						enquiryViewVO.setCustomerType("Dealer");
					}
					else if(customersVO.getCustomerType().equalsIgnoreCase("End User"))
					{
						enquiryViewVO.setCustomerType("End User");
					}
					enquiryViewVO.setProductId(enquiryVO.getProductId());
				}
			}
			tempList.add(enquiryViewVO);
		}
		return tempList;
	}
	
	
	private class ButtonCell extends TableCell<EnquiryViewVO, Boolean> {
	       
		Image buttonDeleteImage = new Image(getClass().getResourceAsStream("../style/delete.png"));
		Image buttonViewImage = new Image(getClass().getResourceAsStream("../style/view.png"));
		Image buttonEditImage = new Image(getClass().getResourceAsStream("../style/edit.png"));
		final Button cellViewButton = new Button("", new ImageView(buttonViewImage));
		final Button cellDeleteButton = new Button("", new ImageView(buttonDeleteImage));
		final Button cellEditButton = new Button("", new ImageView(buttonEditImage));
		
       
         
        ButtonCell(){
            
        	
        	cellDeleteButton.getStyleClass().add("editDeleteButton");
        	cellDeleteButton.setTooltip(new Tooltip("Delete"));
        	cellEditButton.getStyleClass().add("editDeleteButton");
        	cellEditButton.setTooltip(new Tooltip("Edit"));
        	cellViewButton.getStyleClass().add("editDeleteButton");
        	cellViewButton.setTooltip(new Tooltip("View"));
        	
        	cellEditButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent paramT) {
					try {
						FXMLLoader menuLoader = new FXMLLoader(this.getClass()
							.getResource("../view/enquiry-modify.fxml"));
					BorderPane enquiryModify;
					enquiryModify = (BorderPane) menuLoader.load();
					Stage modifyStage = new Stage();
					Scene modifyScene = new Scene(enquiryModify);
					modifyStage.setResizable(false);
					modifyStage.setHeight(650);
					modifyStage.setWidth(600);
					modifyStage.initModality(Modality.WINDOW_MODAL);
					modifyStage.initOwner(LoginController.primaryStage);
					modifyStage.setScene(modifyScene);
					modifyStage.show();
					((EnquiryModifyPopUpController) menuLoader.getController())
					.fillTextFieldValues(ButtonCell.this
							.getTableView().getItems()
							.get(ButtonCell.this.getIndex()));

					
				}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
        	cellViewButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent paramT) {
					try {
						FXMLLoader menuLoader = new FXMLLoader(this.getClass()
							.getResource("../view/enquiry-view-popup.fxml"));
					BorderPane enquiryView;
					enquiryView = (BorderPane) menuLoader.load();
					Stage viewStage = new Stage();
					Scene viewScene = new Scene(enquiryView);
					viewStage.setResizable(false);
					viewStage.setHeight(650);
					viewStage.setWidth(600);
					viewStage.initModality(Modality.WINDOW_MODAL);
					viewStage.initOwner(LoginController.primaryStage);
					viewStage.setScene(viewScene);
					viewStage.show();
					
					((EnquiryViewPopUpController) menuLoader.getController())
					.fillTextFieldValues(ButtonCell.this
							.getTableView().getItems()
							.get(ButtonCell.this.getIndex()));
					
				}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
        	cellDeleteButton.setOnAction(new EventHandler<ActionEvent>(){
 
                @Override
                public void handle(ActionEvent t) {
                    try {
						deleteEnquiry(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            });
        	
        	/*cellEditButton.setOnAction(new EventHandler<ActionEvent>(){
        		 
                @Override
                public void handle(ActionEvent t) {
                	LOG.info("Enter : handle");
                	try {FXMLLoader menuLoader = new FXMLLoader(this.getClass()
								.getResource("../view/components-modify.fxml"));
						BorderPane componentModify;
						componentModify = (BorderPane) menuLoader.load();
						componentModify.setTop(new HBox());
						componentModify.getCenter().setVisible(true);
						Stage modifyStage = new Stage();
						Scene modifyScene = new Scene(componentModify);
						modifyStage.setResizable(false);
						modifyStage.setHeight(500);
						modifyStage.setWidth(600);
						modifyStage.initModality(Modality.WINDOW_MODAL);
						modifyStage.initOwner(LoginController.primaryStage);
						modifyStage.setScene(modifyScene);
						modifyStage.show();
						
						((ComponentsModifyController) menuLoader.getController())
								.fillTextFieldValues(ButtonCell.this
										.getTableView().getItems()
										.get(ButtonCell.this.getIndex()));
						modifyStage
								.setOnCloseRequest(new EventHandler<WindowEvent>() {

									@Override
									public void handle(WindowEvent paramT) {
										fillAutoCompleteFromComboBox(combo.getSelectionModel().getSelectedItem());
										for (EnquryViewVO componentsVO : componentsList) {
											if (componentsVO.getId() == ButtonCell.this.getTableView()
													.getItems()
													.get(ButtonCell.this.getIndex())
													.getId()) {
												updateAutoField(componentsVO, combo.getSelectionModel().getSelectedItem());
											}
										}
										fillTableFromData();
									}
								});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LOG.error(e.getMessage());
					}
                	LOG.info("Exit : handle");
                }
            });*/
        }
 
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
            	HBox box = new HBox();
            	box.getChildren().addAll(cellViewButton,cellEditButton, cellDeleteButton);
                setGraphic(box);
            }
        }
    }
	/*private void updateAutoField(EnquryViewVO componentsVO, String t1) {
		if(t1.equals("Component Category"))
        {
        	keyword.setText(componentsVO.getComponentCategory());
        }
        else if(t1.equals("Sub Category"))
        {
        	keyword.setText(componentsVO.getSubCategory());
        }
        else if(t1.equals("Component Name"))
        {
        	keyword.setText(componentsVO.getComponentName());
        }
        else if(t1.equals("Vendor"))
        {
        	keyword.setText(componentsVO.getVendor());
        }
        else if(t1.equals("Model"))
        {
        	keyword.setText(componentsVO.getModel());
        }
        else if(t1.equals("Type"))
        {
        	keyword.setText(componentsVO.getType());
        }
        else if(t1.equals("Size"))
        {
        	keyword.setText(componentsVO.getSize());
        }
	}*/
}
