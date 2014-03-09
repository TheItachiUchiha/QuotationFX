package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.DispatchDAO;
import com.kc.model.DispatchVO;

import eu.schudt.javafx.controls.calendar.DatePicker;

public class ProductDispatchViewController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ProductDispatchViewController.class);
	
	 @FXML
	    private TableColumn action;

	    @FXML
	    private TableView<DispatchVO> dispatchTable;

	    @FXML
	    private ComboBox<String> keyCombo;

	    @FXML
	    private HBox calenderBox;
	    
	    @FXML
	    private HBox keyHBox;
	    
	    @FXML
	    private VBox keyVBox;

	    @FXML
	    private ComboBox<String> searchCombo;

	    @FXML
	    private HBox searchHBox;

	    @FXML
	    private TableColumn<DispatchVO , String> companyName;

	    @FXML
	    private TableColumn<DispatchVO , String> dispatchDate;

	    @FXML
	    private TableColumn<DispatchVO , Double> frieghtAmount;

	    @FXML
	    private TableColumn<DispatchVO , String> invoiceDate;

	    @FXML
	    private TableColumn<DispatchVO , String> invoiceNo;

	    @FXML
	    private TableColumn<DispatchVO , String> invoiceValue;

	    @FXML
	    private TableColumn<DispatchVO , String> shippingTo;

	    @FXML
	    private TableColumn<DispatchVO , String> transporter;
	    
	    DispatchDAO dispatchDAO;
	    private DatePicker calendar;
	    String currentYear="";
	    
	    private ObservableList<DispatchVO> dispatchList = FXCollections.observableArrayList();
	    private ObservableList<DispatchVO> finalDispatchList = FXCollections.observableArrayList();
	    private ObservableList<String> keyList = FXCollections.observableArrayList();
	    SimpleDateFormat yearFormat = new SimpleDateFormat(CommonConstants.YEAR_FORMAT);
	    SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	    
	    public ProductDispatchViewController() {
			dispatchDAO = new DispatchDAO();
			currentYear = yearFormat.format(new Date());
		}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
			keyVBox.getChildren().removeAll(keyHBox,calenderBox);
			
			calendar = new DatePicker(Locale.ENGLISH);
    		calendar.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    		calendar.getCalendarView().todayButtonTextProperty().set("Today");
    		calendar.getCalendarView().setShowWeeks(false);
    		calendar.getStylesheets().add("com/kc/style/DatePicker.css");
    		((TextField)calendar.getChildren().get(0)).setEditable(false);
    		((TextField)calendar.getChildren().get(0)).setPrefWidth(200);
    		calenderBox.getChildren().add(calendar);
			
			dispatchList = dispatchDAO.getProductDispatch();
			for(DispatchVO dispatchVO : dispatchList)
			{
				if(new SimpleDateFormat("yyyy").format(formatter.parse(dispatchVO.getInvoiceDate())).equalsIgnoreCase(currentYear))
				{
					finalDispatchList.add(dispatchVO);
				}
			}
			fillTable(finalDispatchList);
			dispatchTable.setVisible(true);
			action.setSortable(false);
	        
	        action.setCellValueFactory(
	                new Callback<TableColumn.CellDataFeatures<DispatchVO, Boolean>,
	                ObservableValue<Boolean>>() {
	 
	            @Override
	            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<DispatchVO, Boolean> p) {
	                return new SimpleBooleanProperty(p.getValue() != null);
	            }
	        });
	        action.setCellFactory(
	                new Callback<TableColumn<DispatchVO, Boolean>, TableCell<DispatchVO, Boolean>>() {
	 
	            @Override
	            public TableCell<DispatchVO, Boolean> call(TableColumn<DispatchVO, Boolean> p) {
	                return new ButtonCell();
	            }
	         
	        });
	        
	        searchCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					if(newValue!=null)
					{
						if(newValue.equalsIgnoreCase("Calender") || newValue.equalsIgnoreCase("Invoice Date") || newValue.equalsIgnoreCase("Dispatch Date"))
						{
							keyVBox.getChildren().clear();
							keyVBox.getChildren().add(calenderBox);
						}
						else
						{
							keyVBox.getChildren().clear();
							keyVBox.getChildren().add(keyHBox);
						}
						keyVBox.setVisible(false);
						dispatchTable.setVisible(false);
					}
				}
			});
	        keyCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					dispatchTable.setVisible(false);
				}
			});
	        ((TextField)calendar.getChildren().get(0)).textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					finalDispatchList.clear();
					if(newValue!=null)
					{
						if(searchCombo.getSelectionModel().getSelectedIndex()!=-1)
						{
							if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Invoice Date"))
							{
								for(DispatchVO dispatchVO : dispatchList)
								{
									if(((TextField)calendar.getChildren().get(0)).getText().equals(dispatchVO.getInvoiceDate()))
									{
										finalDispatchList.add(dispatchVO);
									}
								}
							}
							else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Dispatch Date"))
							{
								for(DispatchVO dispatchVO : dispatchList)
								{
									if(((TextField)calendar.getChildren().get(0)).getText().equals(dispatchVO.getDispatchDate()))
									{
										finalDispatchList.add(dispatchVO);
									}
								}
							}
						}
						
						if(finalDispatchList.isEmpty())
						{
							Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.NO_ENQUIRY);
						}
						else
						{
							fillTable(finalDispatchList);
							dispatchTable.setVisible(true);
						}
						
					}
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void reset()
	{
		try
		{
			searchCombo.getSelectionModel().clearSelection();
			keyCombo.getSelectionModel().clearSelection();
			((TextField)calendar.getChildren().get(0)).setText("");
			dispatchList = dispatchDAO.getProductDispatch();
			finalDispatchList.clear();
			
			for(DispatchVO dispatchVO : dispatchList)
			{
				if(new SimpleDateFormat("yyyy").format(formatter.parse(dispatchVO.getInvoiceDate())).equalsIgnoreCase(currentYear))
				{
					finalDispatchList.add(dispatchVO);
				}
			}
			fillTable(finalDispatchList);
			dispatchTable.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//fill the ProductDispatch table 
	public void fillTable(ObservableList<DispatchVO> tempList)
	{
		try
		{
			dispatchTable.setItems(tempList);
			invoiceNo.setCellValueFactory(new PropertyValueFactory<DispatchVO, String>("invoiceNo"));
			invoiceDate.setCellValueFactory(new PropertyValueFactory<DispatchVO, String>("invoiceDate"));
			invoiceValue.setCellValueFactory(new PropertyValueFactory<DispatchVO, String>("noOfItems"));
			companyName.setCellValueFactory(new PropertyValueFactory<DispatchVO, String>("companyName"));
			shippingTo.setCellValueFactory(new PropertyValueFactory<DispatchVO, String>("shippingTo"));
			transporter.setCellValueFactory(new PropertyValueFactory<DispatchVO, String>("transporter"));
			frieghtAmount.setCellValueFactory(new PropertyValueFactory<DispatchVO, Double>("freightAmount"));
			dispatchDate.setCellValueFactory(new PropertyValueFactory<DispatchVO, String>("dispatchDate"));
		}
		catch (Exception e) {
			e.printStackTrace();
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
				for(DispatchVO dispatchVO : dispatchList)
				{
					if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Invoice Number"))
					{
						if(!keyList.contains(dispatchVO.getInvoiceNo()))
						{
							keyList.add(dispatchVO.getInvoiceNo());
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Shipping To"))
					{
						if(!keyList.contains(dispatchVO.getShippingTo()))
						{
							keyList.add(dispatchVO.getShippingTo());
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Company Name"))
					{
						if(!keyList.contains(dispatchVO.getCompanyName()))
						{
							keyList.add(dispatchVO.getCompanyName());
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Transporter"))
					{
						if(!keyList.contains(dispatchVO.getTransporter()))
						{
							keyList.add(dispatchVO.getTransporter());
						}
					}
				}
				FXCollections.sort(keyList);
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
				finalDispatchList.clear();
				for(DispatchVO dispatchVO : dispatchList)
				{
					if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Invoice Number"))
					{
						if(keyCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(dispatchVO.getInvoiceNo()))
						{
							finalDispatchList.add(dispatchVO);
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Shipping To"))
					{
						if(keyCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(dispatchVO.getShippingTo()))
						{
							finalDispatchList.add(dispatchVO);
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Company Name"))
					{
						if(keyCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(dispatchVO.getCompanyName()))
						{
							finalDispatchList.add(dispatchVO);
						}
					}
					else if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Transporter"))
					{
						if(keyCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(dispatchVO.getTransporter()))
						{
							finalDispatchList.add(dispatchVO);
						}
					}
				}
				fillTable(finalDispatchList);
				dispatchTable.setVisible(true);
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	// delete the selected product Dispatch Entry
	public void deleteDispatch(DispatchVO dispatchVO)
	{
		try
		{
			LOG.info("Enter : deleteDispatch");
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
				    "Do you want to delete selected Product Dispatch", "Confirm", "Delete Product Dispatch", DialogOptions.OK_CANCEL);
			if(response.equals(DialogResponse.OK))
			{
				for(DispatchVO  dispatchVO2 : finalDispatchList)
				{
					if(dispatchVO2.getId()==dispatchVO.getId())
					{
						finalDispatchList.remove(dispatchVO2);
						dispatchDAO.deleteDispatch(dispatchVO2);
						break;
					}
				}
				
				
				fillTable(finalDispatchList);
				
			}
			LOG.info("Exit : deleteDispatch");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void filtaerTableByKey(ObservableList<DispatchVO> tempList,String type , String key)
	{
		ObservableList<DispatchVO> finalTempList = FXCollections.observableArrayList();
		for(DispatchVO dispatchVO : tempList)
		{
			if(type.equalsIgnoreCase("Invoice Number"))
			{
				if(dispatchVO.getInvoiceNo().equals(key))
				{
					finalTempList.add(dispatchVO);
				}
			}
			else if(type.equalsIgnoreCase("Company Name"))
			{
				if(dispatchVO.getCompanyName().equals(key))
				{
					finalTempList.add(dispatchVO);
				}
			}
			else if(type.equalsIgnoreCase("Shipping To"))
			{
				if(dispatchVO.getShippingTo().equals(key))
				{
					finalTempList.add(dispatchVO);
				}
			}
			else if(type.equalsIgnoreCase("Transporter"))
			{
				if(dispatchVO.getTransporter().equals(key))
				{
					finalTempList.add(dispatchVO);
				}
			}
		}
		fillTable(finalTempList);
	}
	public void filtaerTableByDate(ObservableList<DispatchVO> tempList,String type,String key)
	{
		ObservableList<DispatchVO> finalTempList = FXCollections.observableArrayList();
		for(DispatchVO dispatchVO : tempList)
		{
			if(type.equalsIgnoreCase("Invoice Date"))
			{
				if(dispatchVO.getInvoiceDate().equals(key))
				{
					finalTempList.add(dispatchVO);
				}
			}
			else if(type.equalsIgnoreCase("Dispatch Date"))
			{
				if(dispatchVO.getDispatchDate().equals(key))
				{
					finalTempList.add(dispatchVO);
				}
			}
		}
		fillTable(finalTempList);
	}
	private class ButtonCell extends TableCell<DispatchVO, Boolean> {
	       
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
							.getResource("/com/kc/view/productDispatch-modify.fxml"));
					BorderPane serviceModify;
					serviceModify = (BorderPane) menuLoader.load();
					Stage modifyStage = new Stage();
					Scene modifyScene = new Scene(serviceModify);
					modifyStage.setResizable(false);
					modifyStage.setHeight(460);
					modifyStage.setWidth(800);
					modifyStage.initModality(Modality.WINDOW_MODAL);
					modifyStage.initOwner(LoginController.primaryStage);
					modifyStage.setScene(modifyScene);
					modifyStage.show();
					((ProductDispatchModifyController) menuLoader.getController())
					.fillTextFieldValues(ButtonCell.this
							.getTableView().getItems()
							.get(ButtonCell.this.getIndex()));

					modifyStage
					.setOnCloseRequest(new EventHandler<WindowEvent>() {

						@Override
						public void handle(WindowEvent paramT) {
							try
							{
								if(searchCombo.getSelectionModel().getSelectedIndex()==-1)
								{
									dispatchList = dispatchDAO.getProductDispatch();
									finalDispatchList.clear();
									for(DispatchVO dispatchVO : dispatchList)
									{
										if(new SimpleDateFormat("yyyy").format(formatter.parse(dispatchVO.getInvoiceDate())).equalsIgnoreCase(currentYear))
										{
											finalDispatchList.add(dispatchVO);
										}
									}
									fillTable(finalDispatchList);
								}
								else
								{
									finalDispatchList = dispatchDAO.getProductDispatch();
									if(searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Invoice date") || searchCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Dispatch date"))
									{
										filtaerTableByDate(finalDispatchList, searchCombo.getSelectionModel().getSelectedItem(),((TextField)calendar.getChildren().get(0)).getText());
									}
									else
									{
										filtaerTableByKey(finalDispatchList, searchCombo.getSelectionModel().getSelectedItem(), keyCombo.getSelectionModel().getSelectedItem());
									}
								}
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
                    	deleteDispatch(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
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
