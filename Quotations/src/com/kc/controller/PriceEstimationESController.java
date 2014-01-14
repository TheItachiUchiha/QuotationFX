package com.kc.controller;

import java.net.URL;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.PriceEstimationDAO;
import com.kc.model.ComponentsVO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.QuotationUtil;

public class PriceEstimationESController implements Initializable{
	
	private static final Logger LOG = LogManager.getLogger(PriceEstimationNewController.class);
	private EnquiryDAO enquiryDAO;
	private CustomersDAO customersDAO;
	private PriceEstimationDAO priceEstimationDAO;
	
	public PriceEstimationESController()
	{
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		priceEstimationDAO = new PriceEstimationDAO();
	}
    @FXML
    private TableColumn<EnquiryViewVO, String> peDone;

    @FXML
    private TableColumn action;

    @FXML
    private TableColumn<EnquiryViewVO, String> companyName;

    @FXML
    private TableColumn<EnquiryViewVO, String> customerName;

    @FXML
    private TableColumn<EnquiryViewVO, String> dateOfEnquiry;

    @FXML
    private TableColumn<EnquiryViewVO, String> dateOfPe;

    @FXML
    private TableView<EnquiryViewVO> enquiryTable;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private Label processedEnquiry;

    @FXML
    private TableColumn<EnquiryViewVO, String> productName;

    @FXML
    private TableColumn<EnquiryViewVO, String> referedBy;

    @FXML
    private TableColumn<EnquiryViewVO, String> referenceNo;

    @FXML
    private Label totalEnquiry;

    @FXML
    private Label unProcessedEnquiry;

    @FXML
    private ComboBox<String> yearCombo;
    
    @FXML
    private GridPane gridPane;
    
    private String flag="";
    
    private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enqList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> processedEnquiryList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> unprocessedEnquiryList = FXCollections.observableArrayList();
	SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			enqList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			monthCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable, String oldValue, String newValue) {
					try{
						if(null!=newValue && (null == oldValue || !oldValue.equals(newValue)))
						{
							if(yearCombo.getSelectionModel().getSelectedIndex()!=-1)
							{
								ObservableList<EnquiryVO> tempProcessedList = FXCollections.observableArrayList();
								ObservableList<EnquiryVO> tempUnProcessedList = FXCollections.observableArrayList();
								for(EnquiryVO enquiryVO: enqList)
								{
									if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDate())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDate())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem()))
									{
										if(enquiryVO.getPriceEstimation().equalsIgnoreCase("Y"))
										{
											tempProcessedList.add(enquiryVO);
										}
										else if(enquiryVO.getPriceEstimation().equalsIgnoreCase("N"))
										{
											tempUnProcessedList.add(enquiryVO);
										}
									}
								}
								processedEnquiryList = QuotationUtil.fillEnquiryViewListFromEnquiryList(tempProcessedList, customerList);;
								unprocessedEnquiryList = QuotationUtil.fillEnquiryViewListFromEnquiryList(tempUnProcessedList, customerList);;
								totalEnquiry.setText(String.valueOf(processedEnquiryList.size()+unprocessedEnquiryList.size()));
								processedEnquiry.setText(String.valueOf(processedEnquiryList.size()));
								unProcessedEnquiry.setText(String.valueOf(unprocessedEnquiryList.size()));
								gridPane.setVisible(true);
								enquiryTable.setVisible(false);
							}
						}
					}
				catch (Exception e) {
					e.printStackTrace();
					LOG.error(e.getMessage());
				}}
			});
			
			
			yearCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable, String oldValue, String newValue) {
					try{
						if(null!=newValue && (null == oldValue || !oldValue.equals(newValue)))
						{
							if(monthCombo.getSelectionModel().getSelectedIndex()!=-1)
							{
								ObservableList<EnquiryVO> tempProcessedList = FXCollections.observableArrayList();
								ObservableList<EnquiryVO> tempUnProcessedList = FXCollections.observableArrayList();
								for(EnquiryVO enquiryVO: enqList)
								{
									if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDate())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDate())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem()))
									{
										if(enquiryVO.getPriceEstimation().equalsIgnoreCase("Y"))
										{
											tempProcessedList.add(enquiryVO);
										}
										else if(enquiryVO.getPriceEstimation().equalsIgnoreCase("N"))
										{
											tempUnProcessedList.add(enquiryVO);
										}
									}
								}
								processedEnquiryList =QuotationUtil.fillEnquiryViewListFromEnquiryList(tempProcessedList, customerList);;
								unprocessedEnquiryList = QuotationUtil.fillEnquiryViewListFromEnquiryList(tempUnProcessedList, customerList);;
								totalEnquiry.setText(String.valueOf(processedEnquiryList.size()+unprocessedEnquiryList.size()));
								processedEnquiry.setText(String.valueOf(processedEnquiryList.size()));
								unProcessedEnquiry.setText(String.valueOf(unprocessedEnquiryList.size()));
								gridPane.setVisible(true);
								enquiryTable.setVisible(false);
							}
						}
					}
				catch (Exception e) {
					e.printStackTrace();
					LOG.error(e.getMessage());
				}}
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
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
	public void viewProcessedList()
	{
		flag = "P";
		fillComponentTable(processedEnquiryList);
		enquiryTable.setVisible(true);
	}
	
	public void viewUnProcessedList()
	{
		flag = "U";
		fillComponentTable(unprocessedEnquiryList);
		enquiryTable.setVisible(true);
	}
	
	public void fillComponentTable(ObservableList<EnquiryViewVO> enquiryViewList)
	{
		referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
		productName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
		customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
		companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
		referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
		dateOfEnquiry.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("dateOfEnquiry"));
		dateOfPe.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("peDate"));
		peDone.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("priceEstimation"));
		if(flag.equalsIgnoreCase("U"))
		{
			action.setVisible(false);
		}
		else if(flag.equalsIgnoreCase("P"))
		{
			action.setVisible(true);
		}
		enquiryTable.setItems(enquiryViewList);
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
					unProcessPriceEstimation(ButtonCell.this.getTableView().getItems()
							.get(ButtonCell.this.getIndex()));
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
	
	public void unProcessPriceEstimation(EnquiryViewVO enquiryViewVO)
	{
		try{
			priceEstimationDAO.deletePriceEstimation(enquiryViewVO);
			enqList = enquiryDAO.getEnquries();
			ObservableList<EnquiryVO> tempProcessedList = FXCollections.observableArrayList();
			ObservableList<EnquiryVO> tempUnProcessedList = FXCollections.observableArrayList();
			for(EnquiryVO enquiryVO: enqList)
			{
				if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDate())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDate())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem()))
				{
					if(enquiryVO.getPriceEstimation().equalsIgnoreCase("Y"))
					{
						tempProcessedList.add(enquiryVO);
					}
					else if(enquiryVO.getPriceEstimation().equalsIgnoreCase("N"))
					{
						tempUnProcessedList.add(enquiryVO);
					}
				}
			}
			processedEnquiryList = QuotationUtil.fillEnquiryViewListFromEnquiryList(tempProcessedList, customerList);
			unprocessedEnquiryList = QuotationUtil.fillEnquiryViewListFromEnquiryList(tempUnProcessedList, customerList);
			totalEnquiry.setText(String.valueOf(processedEnquiryList.size()+unprocessedEnquiryList.size()));
			processedEnquiry.setText(String.valueOf(processedEnquiryList.size()));
			unProcessedEnquiry.setText(String.valueOf(unprocessedEnquiryList.size()));
			
			if(flag.equalsIgnoreCase("P"))
			{
				fillComponentTable(processedEnquiryList);
			}
			else if(flag.equalsIgnoreCase("U"))
			{
				fillComponentTable(unprocessedEnquiryList);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
    
}

