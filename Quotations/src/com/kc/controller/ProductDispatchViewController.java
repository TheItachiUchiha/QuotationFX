package com.kc.controller;

import java.net.URL;
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

import com.kc.dao.DispatchDAO;
import com.kc.model.DispatchVO;

public class ProductDispatchViewController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ProductDispatchViewController.class);
	
	 @FXML
	    private TableColumn action;

	    @FXML
	    private TableView<DispatchVO> dispatchTable;

	    @FXML
	    private ComboBox<String> keyCombo;

	    @FXML
	    private HBox monthHBox;

	    @FXML
	    private ComboBox<String> searchCombo;

	    @FXML
	    private HBox referenceHBox;

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
	    private ObservableList<DispatchVO> dispatchList = FXCollections.observableArrayList();
	    
	    public ProductDispatchViewController() {
			dispatchDAO = new DispatchDAO();
		}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
			fillTable();
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void fillTable()
	{
		try
		{
			dispatchList = dispatchDAO.getProductDispatch();
			dispatchTable.setItems(dispatchList);
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
	
	public void search()
	{
		
	}
	
	public void go()
	{
		
	}
	public void deleteDispatch(DispatchVO dispatchVO)
	{
		try
		{
			LOG.info("Enter : deleteDispatch");
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
				    "Do you want to delete selected Product Dispatch", "Confirm", "Delete Product Dispatch", DialogOptions.OK_CANCEL);
			if(response.equals(DialogResponse.OK))
			{
				dispatchDAO.deleteDispatch(dispatchVO);
				fillTable();
				
			}
			LOG.info("Exit : deleteDispatch");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
					modifyStage.setHeight(650);
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
							fillTable();
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
