package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.DispatchVO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductDispatchCreateController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(ProductDispatchCreateController.class);

	@FXML
    private TextField FreightAmount;

    @FXML
    private TextField Freightmode;

    @FXML
    private TableColumn action;

    @FXML
    private TextField billingName;

    @FXML
    private TextArea body;

    @FXML
    private TextField cc;

    @FXML
    private TextField copanyName;

    @FXML
    private RadioButton createDispatch;

    @FXML
    private TextField dispatchDate;

    @FXML
    private GridPane dispatchGrid;

    @FXML
    private TableView<DispatchVO> dispatchTable;

    @FXML
    private ToggleGroup dispatchToggle;

    @FXML
    private TextField invoiceDate;

    @FXML
    private TextField invoiceNo;

    @FXML
    private VBox mainBox;

    @FXML
    private Label message;

    @FXML
    private Label messageMail;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private TextField numberOfItems;

    @FXML
    private ComboBox<String> referenceCombo;

    @FXML
    private HBox referenceHBox;

    @FXML
    private HBox monthHBox;

    @FXML
    private TextField sender;

    @FXML
    private TextField shippingTo;

    @FXML
    private TextField trackingNo;

    @FXML
    private TextField transporter;

    @FXML
    private TableColumn<DispatchVO,String> vCustomerName;

    @FXML
    private TableColumn<DispatchVO,String> vDispatchDate;

    @FXML
    private TableColumn<DispatchVO,String> vFrieghtAmount;

    @FXML
    private TableColumn<DispatchVO,String> vInvoiceDate;

    @FXML
    private TableColumn<DispatchVO,String> vInvoiceNo;

    @FXML
    private TableColumn<DispatchVO,String> vInvoiceValue;

    @FXML
    private TableColumn<DispatchVO,String> vShippingTo;

    @FXML
    private TableColumn<DispatchVO,String> vTransporter;

    @FXML
    private RadioButton viewDispatch;

    @FXML
    private ComboBox<String> yearCombo;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		createDispatch.setSelected(true);
		mainBox.getChildren().removeAll(dispatchTable,dispatchGrid);
		
		if(createDispatch.isSelected())
		{
			//mainBox.getChildren().removeAll(dispatchTable,dispatchGrid);
			mainBox.getChildren().add(dispatchGrid);
			mainBox.getChildren().remove(dispatchTable);
			monthHBox.setVisible(true);
		}
		else if(viewDispatch.isSelected())
		{
			//mainBox.getChildren().removeAll(dispatchTable,dispatchGrid);
			mainBox.getChildren().add(dispatchGrid);
			mainBox.getChildren().remove(dispatchGrid);
			monthHBox.setVisible(false);
		}
		
		dispatchToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable,
					Toggle oldValue, Toggle newValue) {
				referenceHBox.setVisible(false);
				mainBox.setVisible(false);
				if(createDispatch.isSelected())
				{
					//mainBox.getChildren().removeAll(dispatchTable,dispatchGrid);
					mainBox.getChildren().add(dispatchGrid);
					mainBox.getChildren().remove(dispatchTable);
					monthHBox.setVisible(true);
				}
				else if(viewDispatch.isSelected())
				{
					//mainBox.getChildren().removeAll(dispatchTable,dispatchGrid);
					mainBox.getChildren().add(dispatchGrid);
					mainBox.getChildren().remove(dispatchGrid);
					monthHBox.setVisible(false);
				}
				
			}
		});
		monthCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				referenceHBox.setVisible(false);
				mainBox.setVisible(false);
				
			}
		});
		
		yearCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				referenceHBox.setVisible(false);
				mainBox.setVisible(false);
				
			}
		});
		referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				mainBox.setVisible(false);
				
			}
		});
	}
	
	public void search()
	{
		referenceHBox.setVisible(true);
	}
	public void go()
	{
		System.out.println("in GO");
		mainBox.setVisible(true);
	}
}
