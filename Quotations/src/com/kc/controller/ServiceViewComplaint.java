package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ServiceDAO;
import com.kc.model.ComplaintVO;
import com.kc.model.CustomersVO;
import com.kc.model.ServiceVO;
import com.kc.util.QuotationUtil;

public class ServiceViewComplaint implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ServiceViewComplaint.class);
	

    @FXML
    private TableColumn action;

    @FXML
    private TableView<ComplaintVO> complaintTable;

    @FXML
    private TableColumn<ComplaintVO,String> customerCity;

    @FXML
    private TableColumn<ComplaintVO,String> customerName;

    @FXML
    private TableColumn<ComplaintVO,String> dateOfComplaint;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private TableColumn<ComplaintVO,String> productName;

    @FXML
    private TableColumn<ComplaintVO,String> referenceNo;

    @FXML
    private TableColumn<ComplaintVO,String> serviceCount;

    @FXML
    private ComboBox<String> yearCombo;
    
    private ObservableList<String> monthList = FXCollections.observableArrayList();
   	private ObservableList<String> yearList = FXCollections.observableArrayList();
   	private ObservableList<ServiceVO> tempList = FXCollections.observableArrayList();
   	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
   	private ObservableList<ComplaintVO> tempcomplaintList = FXCollections.observableArrayList();
   	private ObservableList<ComplaintVO> complaintList = FXCollections.observableArrayList();
   	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
   	SimpleDateFormat yearFormat = new SimpleDateFormat(CommonConstants.YEAR_FORMAT);
   	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	ServiceDAO serviceDAO;
	String startDate;
	String endDate;
	String currentYear="";
   	public ServiceViewComplaint() {
		
   		enquiryDAO = new EnquiryDAO();
   		customersDAO = new CustomersDAO();
   		serviceDAO = new ServiceDAO();
   		currentYear = yearFormat.format(new Date());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			yearCombo.setItems(yearList);
			monthCombo.setItems(monthList);
			tempList = serviceDAO.getComplaintList();
			customerList = customersDAO.getCustomers();
			tempcomplaintList = QuotationUtil.fillComplaintListFromService(tempList, customerList);
			
			for(ComplaintVO complaintVO : tempcomplaintList)
			{
				if(new SimpleDateFormat("yyyy").format(formatter.parse(complaintVO.getDateOfComplaint())).equalsIgnoreCase(currentYear))
				{
					complaintList.add(complaintVO);
				}
			}
			fillTable(complaintList);
			
			
			action.setSortable(false);
	        
	        action.setCellValueFactory(
	                new Callback<TableColumn.CellDataFeatures<ComplaintVO, Boolean>,
	                ObservableValue<Boolean>>() {
	 
	            @Override
	            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ComplaintVO, Boolean> p) {
	                return new SimpleBooleanProperty(p.getValue() != null);
	            }
	        });
	        action.setCellFactory(
	                new Callback<TableColumn<ComplaintVO, Boolean>, TableCell<ComplaintVO, Boolean>>() {
	 
	            @Override
	            public TableCell<ComplaintVO, Boolean> call(TableColumn<ComplaintVO, Boolean> p) {
	                return new ButtonCell();
	            }
	         
	        });
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		
		
	}
	
	public void search()
	{
		try
		{
			if(monthCombo.getSelectionModel().getSelectedIndex()==-1||yearCombo.getSelectionModel().getSelectedIndex()==-1)
			{
				Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.SELECT_MONTH_YEAR);
			}
			else
			{
				tempList = serviceDAO.getComplaintList();
				tempcomplaintList = QuotationUtil.fillComplaintListFromService(tempList, customerList);
				complaintTable.getItems().clear();
				complaintList.clear();
				for(ComplaintVO complaintVO : tempcomplaintList)
				{
					if(new SimpleDateFormat("MMM").format(formatter.parse(complaintVO.getDateOfComplaint())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(complaintVO.getDateOfComplaint())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem()))
					{
						complaintList.add(complaintVO);
					}
				}
				if(complaintList.isEmpty())
				{
					Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_COMPLAINT);
				}
				else
				{
					fillTable(complaintList);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void fillTable(ObservableList<ComplaintVO> tableList)
	{
		
		try
		{
			complaintTable.setItems(tableList);
			referenceNo.setCellValueFactory(new PropertyValueFactory<ComplaintVO, String>("referenceNo"));
			productName.setCellValueFactory(new PropertyValueFactory<ComplaintVO, String>("productName"));
			customerCity.setCellValueFactory(new PropertyValueFactory<ComplaintVO, String>("customerCity"));
			customerName.setCellValueFactory(new PropertyValueFactory<ComplaintVO, String>("customerName"));
			dateOfComplaint.setCellValueFactory(new PropertyValueFactory<ComplaintVO, String>("dateOfComplaint"));
			serviceCount.setCellValueFactory(new PropertyValueFactory<ComplaintVO, String>("serviceCount"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteComplaint(ComplaintVO complaintVO)
	{
		try
		{
			LOG.info("Enter : deleteComplaint");
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
				    "Do you want to delete selected Complaint", "Confirm", "Delete Complaint", DialogOptions.OK_CANCEL);
			if(response.equals(DialogResponse.OK))
			{
				for(ComplaintVO complaintVO2 : tempcomplaintList)
				{
					if(complaintVO.getId()==complaintVO2.getId())
					{
						tempcomplaintList.remove(complaintVO2);
						serviceDAO.deleteComplaint(complaintVO2);
						break;
					}
				}
				fillTable(tempcomplaintList);
				
			}
			LOG.info("Exit : deleteComplaint");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class ButtonCell extends TableCell<ComplaintVO, Boolean> {
	       
		Image buttonDeleteImage = new Image(getClass().getResourceAsStream("/com/kc/style/delete.png"));
		Image buttonEditImage = new Image(getClass().getResourceAsStream("/com/kc/style/edit.png"));
		final Button cellDeleteButton = new Button("", new ImageView(buttonDeleteImage));
		final Button cellEditButton = new Button("", new ImageView(buttonEditImage));
		
       
         
        ButtonCell(){
            
        	
        	cellDeleteButton.getStyleClass().add("editDeleteButton");
        	cellDeleteButton.setTooltip(new Tooltip("Delete"));
        	cellEditButton.getStyleClass().add("editDeleteButton");
        	cellEditButton.setTooltip(new Tooltip("Edit"));
        	
        	cellEditButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent paramT) {
					try {
						FXMLLoader menuLoader = new FXMLLoader(this.getClass()
							.getResource("/com/kc/view/service-modifyComplaint.fxml"));
					BorderPane serviceModify;
					serviceModify = (BorderPane) menuLoader.load();
					Stage modifyStage = new Stage();
					Scene modifyScene = new Scene(serviceModify);
					modifyStage.setResizable(false);
					modifyStage.setHeight(650);
					modifyStage.setWidth(800);
					modifyStage.initModality(Modality.WINDOW_MODAL);
					modifyStage.initOwner(LoginController.primaryStage);
					modifyStage.setScene(modifyScene);
					modifyStage.show();
					((ServiceModifyComplaintController) menuLoader.getController())
					.fillTextFieldValues(ButtonCell.this
							.getTableView().getItems()
							.get(ButtonCell.this.getIndex()));

					modifyStage
					.setOnCloseRequest(new EventHandler<WindowEvent>() {

						@Override
						public void handle(WindowEvent paramT) {
							try
							{
								tempList = serviceDAO.getComplaintList();
								tempcomplaintList = QuotationUtil.fillComplaintListFromService(tempList, customerList);
								fillTable(tempcomplaintList);
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					
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
						deleteComplaint(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
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
            	box.getChildren().addAll(cellEditButton, cellDeleteButton);
                setGraphic(box);
            }
        }
    }
}
