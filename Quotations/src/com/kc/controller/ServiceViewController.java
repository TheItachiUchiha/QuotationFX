package com.kc.controller;

import java.net.URL;
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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ServiceDAO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ServiceVO;
import com.kc.util.QuotationUtil;

public class ServiceViewController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceViewController.class);
	
	 	@FXML
	    private TableColumn action;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> companyName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> customerName;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> engineerName;

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
	    private TableColumn<EnquiryViewVO, String> rating;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referedBy;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> referenceNo;

	    @FXML
	    private TableView<EnquiryViewVO> salesOrderTable;
	    
	    @FXML
	    private TableView<ServiceVO> serviceTable;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> serviceCharge;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> thisYearService;

	    @FXML
	    private TableColumn<EnquiryViewVO, String> totalService;

	    @FXML
	    private ComboBox<String> yearCombo;
	    
	    private ObservableList<String> monthList = FXCollections.observableArrayList();
	   	private ObservableList<String> yearList = FXCollections.observableArrayList();
	   	private ObservableList<EnquiryViewVO> listOfServices = FXCollections.observableArrayList();
	   	
	   	String startDate;
	   	String endDate;
	   	ServiceDAO serviceDAO;
	   	
	   	
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
	
	public void go()
	{
		
	}
	
	public void search()
	{
		
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
				salesOrderTable.setItems(listOfServices);
				referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
				productPurchased.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
				location.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("state"));
				customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
				companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
				referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
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
