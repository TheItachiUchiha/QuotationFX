package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
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
import com.kc.dao.PriceEstimationDAO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.QuotationUtil;
import com.kc.util.Validation;

public class PriceEstimationViewController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(PriceEstimationViewController.class);
	PriceEstimationDAO priceEstimationDAO;
	CustomersDAO customersDAO;
	Validation validation;
	String currentYear="";
	public PriceEstimationViewController()
	{
		priceEstimationDAO = new PriceEstimationDAO();
		customersDAO = new CustomersDAO();
		validation = new Validation();
		currentYear = yearFormat.format(new Date());
	}
	@FXML
	private ComboBox<String> monthCombo;
	@FXML
	private ComboBox<String> yearCombo;
	
	@FXML
	private Button search;
	
	@FXML
 	private TableView<EnquiryViewVO> priceEstimationTable;
	@FXML private TableColumn<EnquiryViewVO, String> referenceNo;
    @FXML private TableColumn<EnquiryViewVO, String> productName;
    @FXML private TableColumn<EnquiryViewVO, String> customerName;
    @FXML private TableColumn<EnquiryViewVO, String> companyName;
    @FXML private TableColumn<EnquiryViewVO, String> referedBy;
    @FXML private TableColumn<EnquiryViewVO, String> dateOfEnquiry;
    @FXML private TableColumn<EnquiryViewVO, String> peDate;
    @FXML private TableColumn action;
	
    private ObservableList<String> monthList = FXCollections.observableArrayList();
    private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> refList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	 SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	 SimpleDateFormat yearFormat = new SimpleDateFormat(CommonConstants.YEAR_FORMAT);
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			customerList = customersDAO.getCustomers();
			
			enquiryList = priceEstimationDAO.getPriceEstimation();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if(new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(currentYear))
				{
					refList.add(enquiryVO);
				}
			}
			fillTable(refList);
			priceEstimationTable.setVisible(true);
			
			search.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					try
					{
						if(monthCombo.getSelectionModel().getSelectedIndex()==-1|| yearCombo.getSelectionModel().getSelectedIndex()==-1)
						{
							Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
						}
						else
						{
							enquiryList = priceEstimationDAO.getPriceEstimation();
							enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
							refList.clear();
							for(EnquiryViewVO enquiryVO : enquiryViewList)
							{
								if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem()))
								{
									refList.add(enquiryVO);
								}
							}
							if(refList.isEmpty())
							{
								priceEstimationTable.getItems().clear();
								Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.NO_ENQUIRY);
							}
							else
							{
								fillTable(refList);
								priceEstimationTable.setVisible(true);
							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
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
		}
		
	}
	
	public void reset()
	{
		try
		{
			yearCombo.getSelectionModel().clearSelection();
			monthCombo.getSelectionModel().clearSelection();
			refList.clear();
			enquiryList = priceEstimationDAO.getPriceEstimation();
			enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
			for(EnquiryViewVO enquiryVO : enquiryViewList)
			{
				if(new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(currentYear))
				{
					refList.add(enquiryVO);
				}
			}
			fillTable(refList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillTable(ObservableList<EnquiryViewVO> refList)
	{

		try
		{
			priceEstimationTable.setItems(refList);
			referenceNo.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referenceNo"));
			productName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("productName"));
			companyName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("companyName"));
			customerName.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("customerName"));
			referedBy.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("referedBy"));
			dateOfEnquiry.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("dateOfEnquiry"));
			peDate.setCellValueFactory(new PropertyValueFactory<EnquiryViewVO, String>("peDate"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deletePriceEstimation(EnquiryViewVO enquryViewVO) throws Exception
	{
		LOG.info("Enter : deletePriceEstimation");
		try{
				DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
					    "Do you want to delete selected Price Estimation(s)", "Confirm", "Delete PE", DialogOptions.OK_CANCEL);
				if(response.equals(DialogResponse.OK))
				{
					for(EnquiryViewVO enquiryViewVO2 : refList)
					{
						if(enquryViewVO.getId()==enquiryViewVO2.getId())
						{
							refList.remove(enquiryViewVO2);
							priceEstimationDAO.deletePriceEstimation(enquiryViewVO2);
							break;
						}
					}
				}
				LOG.info("Exit : deletePriceEstimation");
		}
		catch(SQLException s)
		{
			s.printStackTrace();
		}
	}
	private class ButtonCell extends TableCell<EnquiryViewVO, Boolean> {
	       
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
							.getResource("/com/kc/view/priceEstimation-modify-popUp.fxml"));
					BorderPane priceEstimationModify;
					priceEstimationModify = (BorderPane) menuLoader.load();
					Stage modifyStage = new Stage();
					Scene modifyScene = new Scene(priceEstimationModify);
					modifyStage.setResizable(true);
					modifyStage.setHeight(650);
					modifyStage.setWidth(1100);
					modifyStage.initModality(Modality.WINDOW_MODAL);
					modifyStage.initOwner(LoginController.primaryStage);
					modifyStage.setScene(modifyScene);
					modifyStage.show();
					((PriceEstimationModifyPopupController) menuLoader.getController())
					.fillTextFieldValues(ButtonCell.this
							.getTableView().getItems()
							.get(ButtonCell.this.getIndex()));
					modifyStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) {
							try
							{
								refList.clear();
								if(monthCombo.getSelectionModel().getSelectedIndex()==-1 || yearCombo.getSelectionModel().getSelectedIndex()==-1)
								{
									enquiryList = priceEstimationDAO.getPriceEstimation();
									enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
									for(EnquiryViewVO enquiryVO : enquiryViewList)
									{
										if(new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(currentYear))
										{
											refList.add(enquiryVO);
										}
									}
								}
								else
								{
									enquiryList = priceEstimationDAO.getPriceEstimation();
									enquiryViewList = QuotationUtil.fillEnquiryViewListFromEnquiryList(enquiryList, customerList);
									refList.clear();
									for(EnquiryViewVO enquiryVO : enquiryViewList)
									{
										if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem()))
										{
											refList.add(enquiryVO);
										}
									}
								}
								fillTable(refList);
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
							deletePriceEstimation(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
							fillTable(refList);
							
					} catch (Exception e) {
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
            	box.getChildren().addAll(cellEditButton, cellDeleteButton);
                setGraphic(box);
            }
        }
	}
}