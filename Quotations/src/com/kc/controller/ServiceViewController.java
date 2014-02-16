package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ServiceDAO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ServiceVO;
import com.kc.util.QuotationUtil;

import eu.schudt.javafx.controls.calendar.DatePicker;

public class ServiceViewController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceViewController.class);
	
	 	@FXML
	    private TableColumn action;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> companyName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> customerName;

	    @FXML
	    private TableColumn<ServiceVO, String> engineerName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> location;

	    @FXML
	    private ComboBox<String> monthCombo;
	    
	    @FXML
	    private ComboBox<String> keyCombo;
	    
	    @FXML
	    private ComboBox<String> searchCombo;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> productPurchased;

	    @FXML
	    private TableColumn<ServiceVO, String> rating;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referedBy;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referenceNo;

	    @FXML
	    private TableView<EnquiryViewVO> salesOrderTable;
	    
	    @FXML
	    private TableView<ServiceVO> serviceTable;

	    @FXML
	    private TableColumn<ServiceVO, String> serviceCharge;

	    @FXML
	    private TableColumn<ServiceVO, String> thisYearService;

	    @FXML
	    private TableColumn<ServiceVO, String> totalService;
	    
	    @FXML
	    private TableColumn<EnquiryViewVO, String> serviceDate;

	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    @FXML
	    private HBox calanderBox;
	    
	    @FXML
	    private HBox keyHBox;
	    
	    @FXML
	    private HBox searchBox;
	    
	    @FXML
	    private VBox keyVBox;
	    
	    private ObservableList<String> monthList = FXCollections.observableArrayList();
	   	private ObservableList<String> yearList = FXCollections.observableArrayList();
		private ObservableList<String> keyList = FXCollections.observableArrayList();
	   	private ObservableList<EnquiryViewVO> listOfServices = FXCollections.observableArrayList();
	   	private ObservableList<EnquiryViewVO> finalListOfServices = FXCollections.observableArrayList();
	   	
	   	String startDate;
	   	String endDate;
	   	ServiceDAO serviceDAO;
	   	private DatePicker calendar;
	   	
	   	public ServiceViewController() {
	   		serviceDAO = new ServiceDAO();
		}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			yearCombo.setItems(yearList);
			monthCombo.setItems(monthList);
			
			keyVBox.getChildren().removeAll(keyHBox,calanderBox);
			
			calendar = new DatePicker(Locale.ENGLISH);
    		calendar.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    		calendar.getCalendarView().todayButtonTextProperty().set("Today");
    		calendar.getCalendarView().setShowWeeks(false);
    		calendar.getStylesheets().add("com/kc/style/DatePicker.css");
    		((TextField)calendar.getChildren().get(0)).setEditable(false);
    		((TextField)calendar.getChildren().get(0)).setPrefWidth(200);
    		calanderBox.getChildren().add(calendar);
			
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
	        
	        searchCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
						if(newValue.equalsIgnoreCase("Complaint By Calender"))
						{
							keyVBox.getChildren().clear();
							keyVBox.getChildren().add(calanderBox);
						}
						else
						{
							keyVBox.getChildren().clear();
							keyVBox.getChildren().add(keyHBox);
						}
						salesOrderTable.setVisible(false);
						serviceTable.setVisible(false);
						keyVBox.setVisible(false);
				}
			});
	        
	        keyCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
						salesOrderTable.setVisible(false);
						serviceTable.setVisible(false);
					
				}
			});
	        monthCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					salesOrderTable.setVisible(false);
					serviceTable.setVisible(false);
					keyVBox.setVisible(false);
					searchBox.setVisible(false);
					
				}
			});
	        yearCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					salesOrderTable.setVisible(false);
					serviceTable.setVisible(false);
					keyVBox.setVisible(false);
					searchBox.setVisible(false);
					
				}
			});
	        ((TextField)calendar.getChildren().get(0)).textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					finalListOfServices.clear();
					for(EnquiryViewVO enquiryViewVO : listOfServices)
					{
						if(((TextField)calendar.getChildren().get(0)).getText().equals(enquiryViewVO.getComplaintDate()))
						{
							finalListOfServices.add(enquiryViewVO);
						}
					}
					if(finalListOfServices.isEmpty())
					{
						Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_ENQUIRY);
					}
					else
					{
						fillTable(finalListOfServices);
						salesOrderTable.setVisible(true);
					}
					
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void viewDetails()
	{
		if(monthCombo.getSelectionModel().getSelectedIndex()==-1||yearCombo.getSelectionModel().getSelectedIndex()==-1)
		{
			Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
		}
		else
		{
			try
			{
				serviceTable.getItems().clear();
				listOfServices.clear();
				startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				listOfServices=serviceDAO.getServiceHistory(startDate, endDate);
				if(listOfServices.isEmpty())
				{
					Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_ENQUIRY);
				}
				else
				{
					searchBox.setVisible(true);
				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void go()
	{
		try
		{
			if(searchCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_SEARCH_TYPE);
			}
			else
			{
				keyList.clear();
				for(EnquiryViewVO enquiryViewVO : listOfServices)
				{
					if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Reference Number"))
					{
						if(!keyList.contains(enquiryViewVO.getReferenceNo()))
						{
							keyList.add(enquiryViewVO.getReferenceNo());
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Product name"))
					{
						if(!keyList.contains(enquiryViewVO.getProductName()))
						{
							keyList.add(enquiryViewVO.getProductName());
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Customer Name"))
					{
						if(!keyList.contains(enquiryViewVO.getCustomerName()))
						{
							keyList.add(enquiryViewVO.getCustomerName());
						}
					}
				}
				keyCombo.setItems(keyList);
				keyVBox.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void search()
	{
		try
		{
			if(keyCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_KEYWORD);
			}
			else
			{
				finalListOfServices.clear();
				for(EnquiryViewVO enquiryViewVO : listOfServices)
				{
					if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Reference Number"))
					{
						if(keyCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(enquiryViewVO.getReferenceNo()))
						{
							finalListOfServices.add(enquiryViewVO);
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Product name"))
					{
						if(keyCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(enquiryViewVO.getProductName()))
						{
							finalListOfServices.add(enquiryViewVO);
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Customer Name"))
					{
						if(keyCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(enquiryViewVO.getCustomerName()))
						{
							finalListOfServices.add(enquiryViewVO);
						}
					}
				}
				fillTable(finalListOfServices);
				salesOrderTable.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void fillTable(ObservableList<EnquiryViewVO> tempList)
	{
		try
		{
			ObservableList<EnquiryViewVO> finalTemplist = FXCollections.observableArrayList();
			for(EnquiryViewVO enquiryViewVO : tempList)
			{
				if(!finalTemplist.contains(enquiryViewVO))
				{
					finalTemplist.add(enquiryViewVO);
				}
			}
			salesOrderTable.setItems(finalTemplist);
			referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
			productPurchased.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
			serviceDate.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("serviceDate"));
			location.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("state"));
			customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
			companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
			referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateServiceTable(String reference)
	{
		try
		{
			ObservableList<ServiceVO> listOfServicesForReference = FXCollections.observableArrayList();
			listOfServicesForReference = serviceDAO.getServicesForReference(reference);
			serviceTable.setItems(listOfServicesForReference);
			engineerName.setCellValueFactory(new PropertyValueFactory<ServiceVO, String>("engineerName"));
			totalService.setCellValueFactory(new PropertyValueFactory<ServiceVO, String>("serviceCount"));
			serviceCharge.setCellValueFactory(new PropertyValueFactory<ServiceVO, String>("charge"));
			rating.setCellValueFactory(new PropertyValueFactory<ServiceVO, String>("rating"));
			//thisYearService.setCellValueFactory(new PropertyValueFactory<ServiceVO, String>("thisYearService"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class ButtonCell extends TableCell<EnquiryViewVO, Boolean> {
	       
		Image buttonViewImage = new Image(getClass().getResourceAsStream("/com/kc/style/view.png"));
		final Button cellViewButton = new Button("", new ImageView(buttonViewImage));
		
        ButtonCell(){
            
        	cellViewButton.getStyleClass().add("editDeleteButton");
        	cellViewButton.setTooltip(new Tooltip("View"));
        	
        	cellViewButton.setOnAction(new EventHandler<ActionEvent>(){
 
                @Override
                public void handle(ActionEvent t) {
                    try {
                    		updateServiceTable(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()).getReferenceNo());
                    		serviceTable.setVisible(true);
                    	
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            });
        	
        }
 
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
            	HBox box = new HBox();
            	box.getChildren().addAll(cellViewButton);
                setGraphic(box);
            }
        }
    }

}
