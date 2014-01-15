package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.SalesDAO;
import com.kc.model.ComponentsVO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.QuotationUtil;
import com.kc.util.Validation;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SalesViewController {

	private static final Logger LOG = LogManager.getLogger(SalesViewController.class);
	private EnquiryDAO enquiryDAO;
	private SalesDAO salesDAO;
	private CustomersDAO customersDAO;
	private Validation validation;
	
	public SalesViewController()
	{
		salesDAO = new SalesDAO();
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		validation = new Validation();
	}

    @FXML
    private TableColumn action;

    @FXML
    private TableColumn<EnquiryViewVO, String> companyName;

    @FXML
    private TableColumn<EnquiryViewVO, String> customerName;

    @FXML
    private TableColumn<EnquiryViewVO, String> dateOfEnquiry;

    @FXML
    private TableColumn<EnquiryViewVO, String> dateOfQuotation;

    @FXML
    private TableColumn<EnquiryViewVO, String> dateOfSalesOrder;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private TableColumn<EnquiryViewVO, String> productName;

    @FXML
    private TableColumn<EnquiryViewVO, String> referedBy;

    @FXML
    private TableColumn<EnquiryViewVO, String> referenceNo;

    @FXML
    private TableView<EnquiryViewVO> salesOrderTable;

    @FXML
    private Button view;

    @FXML
    private ComboBox<String> yearCombo;
    
    
    private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);


    @FXML
    void initialize() {
    	try{
    	monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
		yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
		yearCombo.setItems(yearList);
		monthCombo.setItems(monthList);
		enquiryList = enquiryDAO.getEnquries();
		customerList = customersDAO.getCustomers();
		enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
		view.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent paramT) {
				try{
				
					if(monthCombo.getSelectionModel().getSelectedIndex()==-1|| yearCombo.getSelectionModel().getSelectedIndex()==-1)
					{
						Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
					}
					else
					{
						salesOrderTable.setVisible(true);
						fillTable();
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					LOG.error(e.getMessage());
				}
				
			}
		});
		
		action.setSortable(false);
        
        action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ComponentsVO, Boolean>,
                ObservableValue<Boolean>>() {
 
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ComponentsVO, Boolean> p) {
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
	catch (Exception e)
	{
		e.printStackTrace();
	}
    }

private void fillTable()
{
	ObservableList<EnquiryViewVO> tempEnquiryList = FXCollections.observableArrayList();
	try
	{
		enquiryList = enquiryDAO.getEnquries();
		enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
		tempEnquiryList.clear();
		for(EnquiryViewVO enquiryVO : enquiryViewList)
		{
			if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem())&&(enquiryVO.getPriceEstimation().equalsIgnoreCase("Y")&&(enquiryVO.getQuotationPreparation().equalsIgnoreCase("Y") && enquiryVO.getEmailSent().equalsIgnoreCase("Y") && enquiryVO.getSales().equalsIgnoreCase("Y"))))
			{
				tempEnquiryList.add(enquiryVO);
			}
		}
		if(tempEnquiryList.isEmpty())
		{
			Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
		}
		else
		{
			salesOrderTable.setItems(tempEnquiryList);
			referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
			productName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
			companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
			customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
			referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
			dateOfEnquiry.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("dateOfEnquiry"));
			dateOfSalesOrder.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("salesDate"));
			dateOfQuotation.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("qpDate"));
		}
	}
	catch (Exception e) {
		e.printStackTrace();
	}
}
private class ButtonCell extends TableCell<EnquiryViewVO, Boolean> {

	Image buttonDeleteImage = new Image(getClass().getResourceAsStream(
			"/com/kc/style/delete.png"));
	final Button cellDeleteButton = new Button("", new ImageView(
			buttonDeleteImage));
	ButtonCell() {

		cellDeleteButton.getStyleClass().add("editDeleteButton");
		cellDeleteButton.setTooltip(new Tooltip("Delete"));

		cellDeleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				try
				{
					deleteSales(ButtonCell.this.getTableView().getItems()
						.get(ButtonCell.this.getIndex()));
					salesOrderTable.getItems().clear();
					fillTable();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Display button if the row is not empty
	@Override
	protected void updateItem(Boolean t, boolean empty) {
		super.updateItem(t, empty);
		if (!empty) {
			HBox box = new HBox();
			box.getChildren().addAll(cellDeleteButton);
			setGraphic(box);
		}
	}
}
public void deleteSales(EnquiryViewVO enquiryViewVO) throws Exception
{
	LOG.info("Enter : deleteSales");
	try{
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
				    "Do you want to delete selected Sales Order", "Confirm", "Delete Sales Order", DialogOptions.OK_CANCEL);
			if(response.equals(DialogResponse.OK))
			{
				salesDAO.deleteSales(enquiryViewVO.getId());
			}
			LOG.info("Exit : deleteSales");
	}
	catch(SQLException s)
	{
		s.printStackTrace();
	}
}
    
}
