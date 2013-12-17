package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ServiceDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ServiceVO;
import com.kc.util.QuotationUtil;

public class ServiceRegistyViewController implements Initializable  {
	
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	ServiceDAO  serviceDAO;
	String startDate;
	String endDate;
	
	public ServiceRegistyViewController() {

		enquiryDAO = new EnquiryDAO();
		serviceDAO = new ServiceDAO();
		customersDAO = new CustomersDAO();
	}

	 	@FXML
	    private TableColumn action;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> companyName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> customerName;


	    @FXML
	    private TableColumn<EnquiryViewVO, String> location;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> productPurchased;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referedBy;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referenceNo;

	    @FXML
	    private TableView<EnquiryViewVO> salesOrderTable;

	    @FXML
	    private TableColumn<ServiceVO,String> serviceCharge;

	    @FXML
	    private TableColumn<ServiceVO,String> serviceRating;
	    
	    @FXML
	    private TableColumn<ServiceVO,String> name;

	    @FXML
	    private TableView<ServiceVO> serviceTable;


	    @FXML
	    private TableColumn<ServiceVO, String> dateOfService;

	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    private ObservableList<String> monthList = FXCollections.observableArrayList();
		private ObservableList<String> yearList = FXCollections.observableArrayList();
		private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
		private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
		private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
		private ObservableList<ServiceVO> serviceList = FXCollections.observableArrayList();
		SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
		
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
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
		}
	}
	public void viewDetails()
	{
		try
		{
			salesOrderTable.getItems().clear();
			if(monthCombo.getSelectionModel().getSelectedIndex()==-1||yearCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
			}
			else
			{
				startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + yearCombo.getSelectionModel().getSelectedItem();
				enquiryList = serviceDAO.getServiceEnquires(startDate, endDate);
				customerList = customersDAO.getCustomers();
				enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList,customerList);

				if(enquiryViewList.isEmpty())
				{
					salesOrderTable.setVisible(false);
					serviceTable.setVisible(false);
					Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
				}
				else
				{
					salesOrderTable.setItems(enquiryViewList);
					referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
					productPurchased.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
					customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
					companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
					referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
					location.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("city"));
					salesOrderTable.setVisible(true);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class ButtonCell extends TableCell<EnquiryViewVO, Boolean> {
	       
		Image buttonViewImage = new Image(getClass().getResourceAsStream("../style/view.png"));
		final Button cellViewButton = new Button("", new ImageView(buttonViewImage));
		
        ButtonCell(){
            
        	
        	cellViewButton.getStyleClass().add("editDeleteButton");
        	cellViewButton.setTooltip(new Tooltip("View"));
        	
        	
        	cellViewButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent paramT) {
					try {
						ServiceRegistyViewController.this.serviceTable.setVisible(true);
						fillServiceTable(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()).getReferenceNo());
				}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
        }
        	
        	
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
	
	public void fillServiceTable(String reference_no)
	{
		try
		{
			serviceList = serviceDAO.getServices(reference_no);
			serviceTable.setItems(serviceList);
			
			name.setCellValueFactory(new PropertyValueFactory<ServiceVO, String>("engineerName"));
			dateOfService.setCellValueFactory(new PropertyValueFactory<ServiceVO, String>("date"));
			serviceCharge.setCellValueFactory(new PropertyValueFactory<ServiceVO, String>("charge"));
			serviceRating.setCellValueFactory(new PropertyValueFactory<ServiceVO, String>("rating"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
