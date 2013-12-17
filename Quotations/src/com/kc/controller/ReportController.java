package com.kc.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import com.kc.constant.CommonConstants;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.ProductsDAO;
import com.kc.dao.ReportsDAO;
import com.kc.model.EnquiryVO;
import com.kc.service.ReportService;
import com.kc.util.CustomReportTemplate;
import com.kc.util.QuotationUtil;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class ReportController implements Initializable {

	ProductsDAO productsDAO;
	ReportsDAO reportsDAO;
	ReportService reportService;
	EnquiryDAO enquiryDAO;
	
	public ReportController()
	{
		productsDAO = new ProductsDAO();
		reportsDAO = new ReportsDAO();
		reportService = new ReportService();
		enquiryDAO = new EnquiryDAO();
	}
	/*@FXML
    private ComboBox<String> typeCombo;

    @FXML
    private ListView<String> periodList;

    @FXML
    private ComboBox<String> reportTypeCombo;
    
    @FXML
    private GridPane reportGrid;
    
    @FXML
    private TilePane tile;
    
    @FXML
    private ComboBox<String> yearCombo;*/
	
	 @FXML
	    private AutoCompleteTextField<String> customAutoFill;

	    @FXML
	    private GridPane customGrid;

	    @FXML
	    private ComboBox<String> customMonthCombo;

	    @FXML
	    private ComboBox<String> customMonthYearCombo;

	    @FXML
	    private ComboBox<String> customTypeCombo;

	    @FXML
	    private Label messageCustom;

	    @FXML
	    private Label messageStandard;

	    @FXML
	    private ComboBox<String> monthCombo;

	    @FXML
	    private HBox monthHBox;

	    @FXML
	    private ComboBox<String> monthYearCombo;

	    @FXML
	    private ComboBox<String> periodCombo;

	    @FXML
	    private VBox periodVBox;

	    @FXML
	    private ComboBox<String> reportTypeCombo;

	    @FXML
	    private GridPane standardGrid;

	    @FXML
	    private TilePane tile;

	    @FXML
	    private ComboBox<String> typeCombo;

	    @FXML
	    private ComboBox<String> yearCombo;

	    @FXML
	    private HBox yearHBox;

	    @FXML
	    private VBox reportVBox;

	    @FXML
	    private HBox customHBox;
    
    private ObservableList<String> reportTypeList = FXCollections.observableArrayList();
    private ObservableList<String> salesList = FXCollections.observableArrayList();
    private ObservableList<String> serviceList = FXCollections.observableArrayList();
    private ObservableList<String> monthList = FXCollections.observableArrayList();
    private ObservableList<CustomReportTemplate> listOfTemplate = FXCollections.observableArrayList();
    private ObservableList<EnquiryVO> listOfEnquiries = FXCollections.observableArrayList();
    private ObservableList<String> yearList = FXCollections.observableArrayList();
    String startDate;
	String endDate;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
		
			reportVBox.getChildren().removeAll(standardGrid,customGrid);
			periodVBox.getChildren().removeAll(yearHBox,monthHBox);
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			monthCombo.setItems(monthList);
			customMonthCombo.setItems(monthList);
			
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			yearCombo.setItems(yearList);
			monthYearCombo.setItems(yearList);
			customMonthYearCombo.setItems(yearList);
			
			reportTypeCombo.valueProperty().addListener(new ChangeListener<String>() {
	
				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					if(newValue.equalsIgnoreCase("Standard Report"))
					{
						reportVBox.getChildren().add(standardGrid);
						reportVBox.getChildren().remove(customGrid);
					}
					else if(newValue.equalsIgnoreCase("Custom Report"))
					{
						reportVBox.getChildren().add(customGrid);
						reportVBox.getChildren().remove(standardGrid);
					}
				}
			});
			periodCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String oldValue, String newValue) {
						if(newValue.equalsIgnoreCase("Year"))
						{
							periodVBox.getChildren().add(yearHBox);
							periodVBox.getChildren().remove(monthHBox);
						}
						else if(newValue.equalsIgnoreCase("Month"))
						{
							periodVBox.getChildren().add(monthHBox);
							periodVBox.getChildren().remove(yearHBox);
						}
				}
			});
			customTypeCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String oldValue, String newValue) {
					
					if(newValue.equalsIgnoreCase("Service Engineer Name"))
					{
						customHBox.setVisible(true);
					}
					else
					{
						customHBox.setVisible(false);
					}
				}
			});
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void createReport()
	{
		Map<String, List<Integer>> listOfCategories = FXCollections.observableHashMap();
		Map<String, List<Integer>> listOfSubCategories = FXCollections.observableHashMap();
		Map<String, List<Integer>> listOfCustomerType = FXCollections.observableHashMap();
		Map<String, List<Integer>> listOfCustomerState = FXCollections.observableHashMap();
		try
		{
			if(reportTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Standard Report"))
			{
				if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Product Category"))
				{
					listOfCategories = reportService.getCategoriesForProduct();
					if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Month"))
					{
						startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
						endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
					}
					else if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Year"))
					{
						startDate = "01/" + "01/" + yearCombo.getSelectionModel().getSelectedItem();
						endDate = "31/" + "12/" + yearCombo.getSelectionModel().getSelectedItem();
					}
					listOfEnquiries = reportsDAO.salesReport(startDate, endDate);
					Set<String> keySet = listOfCategories.keySet();
					 
					for(String category : keySet)
					{
						int totalEnquiries = 0;
						int totalSuccessfulEnquiries = 0;
						double totalRevenue = 0;
						int serviceCount = 0;
						for(EnquiryVO enquiryVO : listOfEnquiries)
						{
							if(listOfCategories.get(category).contains(enquiryVO.getProductId()))
							{
								totalEnquiries++;
								if(enquiryVO.getSales().equalsIgnoreCase("Y"))
								{
									totalSuccessfulEnquiries++;
									totalRevenue = totalRevenue + enquiryVO.getTotalRevenue();
								}
							}
						}
						ObservableList<PieChart.Data> pieChartData =
				                FXCollections.observableArrayList(
				                new PieChart.Data("Total Enquiries", totalEnquiries),
				                new PieChart.Data("Total Successful Leads", totalSuccessfulEnquiries));
						CustomReportTemplate chart = new CustomReportTemplate(pieChartData);
				        chart.getPieChart().setTitle("Enquiry vs Sales");
				        chart.setFirstBoldLabelLeft("Category Name");
				        chart.setFirstBoldLabelRight(category);
				        chart.setFirstLabelLeft("Total Enquiries");
				        chart.setFirstLabelRight(String.valueOf(totalEnquiries));
				        chart.setSecondLabelLeft("Total Successful Leads");
				        chart.setSecondLabelRight(String.valueOf(totalSuccessfulEnquiries));
				        chart.setThirdLabelLeft("Total Un-Successful Enquiries");
				        chart.setThirdLabelRight(String.valueOf(totalEnquiries - totalSuccessfulEnquiries));
				        chart.setThirdLabelLeft("Total Revenue");
				        chart.setThirdLabelRight(String.valueOf(totalRevenue));
				        chart.setAlignment(Pos.CENTER);
				        tile.getChildren().add(chart);
						
					}
				}
				else if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Product Subcategory"))
				{
					listOfSubCategories = reportService.getSubCategoriesForProduct();
					if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Month"))
					{
						startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
						endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
					}
					else if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Year"))
					{
						startDate = "01/" + "01/" + yearCombo.getSelectionModel().getSelectedItem();
						endDate = "31/" + "12/" + yearCombo.getSelectionModel().getSelectedItem();
					}
					listOfEnquiries = reportsDAO.salesReport(startDate, endDate);
					Set<String> keySet = listOfSubCategories.keySet();
					
					for(String subCategory : keySet)
					{
						int totalEnquiries = 0;
						int totalSuccessfulEnquiries = 0;
						double totalRevenue = 0;
						int serviceCount = 0;
						for(EnquiryVO enquiryVO : listOfEnquiries)
						{
							if(listOfSubCategories.get(subCategory).contains(enquiryVO.getProductId()))
							{
								totalEnquiries++;
								if(enquiryVO.getSales().equalsIgnoreCase("Y"))
								{
									totalSuccessfulEnquiries++;
									totalRevenue = totalRevenue + enquiryVO.getTotalRevenue();
								}
							}
						}
						ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				                new PieChart.Data("Total Enquiries", totalEnquiries),
				                new PieChart.Data("Total Successful Leads", totalSuccessfulEnquiries));
						CustomReportTemplate chart = new CustomReportTemplate(pieChartData);
				        chart.getPieChart().setTitle("Enquiry vs Sales");
				        chart.setFirstBoldLabelLeft("SubCategory Name : ");
				        chart.setFirstBoldLabelRight(subCategory);
				        chart.setFirstLabelLeft("Total Enquiries");
				        chart.setFirstLabelRight(String.valueOf(totalEnquiries));
				        chart.setSecondLabelLeft("Total Successful Leads");
				        chart.setSecondLabelRight(String.valueOf(totalSuccessfulEnquiries));
				        chart.setThirdLabelLeft("Total Un-Successful Enquiries");
				        chart.setThirdLabelRight(String.valueOf(totalEnquiries - totalSuccessfulEnquiries));
				        chart.setThirdLabelLeft("Total Revenue");
				        chart.setThirdLabelRight(String.valueOf(totalRevenue));
				        chart.setAlignment(Pos.CENTER);
				        tile.getChildren().add(chart);
						
					}
				}
				else if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Customer Type"))
				{
					listOfCustomerType = reportService.getCustomerTypeForProduct();
					if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Month"))
					{
						startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
						endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
					}
					else if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Year"))
					{
						startDate = "01/" + "01/" + yearCombo.getSelectionModel().getSelectedItem();
						endDate = "31/" + "12/" + yearCombo.getSelectionModel().getSelectedItem();
					}
					listOfEnquiries = reportsDAO.salesReport(startDate, endDate);
					Set<String> keySet = listOfCustomerType.keySet();
					
					for(String customer : keySet)
					{
						int totalEnquiries = 0;
						int totalSuccessfulEnquiries = 0;
						double totalRevenue = 0;
						int serviceCount = 0;
						for(EnquiryVO enquiryVO : listOfEnquiries)
						{
							if(listOfCustomerType.get(customer).contains(enquiryVO.getCustomerId()))
							{
								totalEnquiries++;
								if(enquiryVO.getSales().equalsIgnoreCase("Y"))
								{
									totalSuccessfulEnquiries++;
									totalRevenue = totalRevenue + enquiryVO.getTotalRevenue();
								}
							}
						}
						ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				                new PieChart.Data("Total Enquiries", totalEnquiries),
				                new PieChart.Data("Total Successful Leads", totalSuccessfulEnquiries));
						CustomReportTemplate chart = new CustomReportTemplate(pieChartData);
				        chart.getPieChart().setTitle("Enquiry vs Sales");
				        chart.setFirstBoldLabelLeft("Customer Type : ");
				        chart.setFirstBoldLabelRight(null!=customer?customer:"");
				        chart.setFirstLabelLeft("Total Enquiries");
				        chart.setFirstLabelRight(String.valueOf(totalEnquiries));
				        chart.setSecondLabelLeft("Total Successful Leads");
				        chart.setSecondLabelRight(String.valueOf(totalSuccessfulEnquiries));
				        chart.setThirdLabelLeft("Total Un-Successful Enquiries");
				        chart.setThirdLabelRight(String.valueOf(totalEnquiries - totalSuccessfulEnquiries));
				        chart.setThirdLabelLeft("Total Revenue");
				        chart.setThirdLabelRight(String.valueOf(totalRevenue));
				        chart.setAlignment(Pos.CENTER);
				        tile.getChildren().add(chart);
						
					}
				}
				else if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Customer Location"))
				{
					listOfCustomerState = reportService.getCustomerStateForProduct();
					if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Month"))
					{
						startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
						endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
					}
					else if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Year"))
					{
						startDate = "01/" + "01/" + yearCombo.getSelectionModel().getSelectedItem();
						endDate = "31/" + "12/" + yearCombo.getSelectionModel().getSelectedItem();
					}
					listOfEnquiries = reportsDAO.salesReport(startDate, endDate);
					Set<String> keySet = listOfCustomerState.keySet();
					
					for(String state : keySet)
					{
						int totalEnquiries = 0;
						int totalSuccessfulEnquiries = 0;
						double totalRevenue = 0;
						int serviceCount = 0;
						for(EnquiryVO enquiryVO : listOfEnquiries)
						{
							if(listOfCustomerState.get(state).contains(enquiryVO.getCustomerId()))
							{
								totalEnquiries++;
								if(enquiryVO.getSales().equalsIgnoreCase("Y"))
								{
									totalSuccessfulEnquiries++;
									totalRevenue = totalRevenue + enquiryVO.getTotalRevenue();
								}
							}
						}
						ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				                new PieChart.Data("Total Enquiries", totalEnquiries),
				                new PieChart.Data("Total Successful Leads", totalSuccessfulEnquiries));
						CustomReportTemplate chart = new CustomReportTemplate(pieChartData);
				        chart.getPieChart().setTitle("Enquiry vs Sales");
				        chart.setFirstBoldLabelLeft("Customer State : ");
				        chart.setFirstBoldLabelRight(null!=state?state:"");
				        chart.setFirstLabelLeft("Total Enquiries");
				        chart.setFirstLabelRight(String.valueOf(totalEnquiries));
				        chart.setSecondLabelLeft("Total Successful Leads");
				        chart.setSecondLabelRight(String.valueOf(totalSuccessfulEnquiries));
				        chart.setThirdLabelLeft("Total Un-Successful Enquiries");
				        chart.setThirdLabelRight(String.valueOf(totalEnquiries - totalSuccessfulEnquiries));
				        chart.setThirdLabelLeft("Total Revenue");
				        chart.setThirdLabelRight(String.valueOf(totalRevenue));
				        chart.setAlignment(Pos.CENTER);
				        tile.getChildren().add(chart);
						
					}
				}
			}
			else if(reportTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Custom Report"))
			{
				
				if(customTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Service Engineer Name"))
				{
					ObservableList<Map<String, Object>> tableData = FXCollections.observableArrayList();
					startDate = "01/" + QuotationUtil.monthDigitFromString(customMonthCombo.getSelectionModel().getSelectedItem()) + "/" + customMonthYearCombo.getSelectionModel().getSelectedItem();
					endDate = "31/" + QuotationUtil.monthDigitFromString(customMonthCombo.getSelectionModel().getSelectedItem()) + "/" + customMonthYearCombo.getSelectionModel().getSelectedItem();
				
					
					tableData = reportsDAO.getServicingEngineerDetails(startDate, endDate, customAutoFill.getText());
					final Label label = new Label("Student IDs");
				    label.setFont(new Font("Arial", 20));
					ObservableList<Map<String, Object>> tableList = FXCollections.observableArrayList();
					TableColumn<Map, String> ref_no = new TableColumn<>("Product Ref_No");
			        TableColumn<Map, String> dateOfService = new TableColumn<>("Date of Service");
			        TableColumn<Map, String> custName = new TableColumn<>("Customer Name");
			        TableColumn<Map, String> compName = new TableColumn<>("Company Name");
			        TableColumn<Map, String> location = new TableColumn<>("Location");
			        TableColumn<Map, String> revenue = new TableColumn<>("Service Revenue");
			 
			        ref_no.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_REF));
			        ref_no.setMinWidth(130);
			        dateOfService.setCellValueFactory(new MapValueFactory("DATE_OF_SERVICE"));
			        dateOfService.setMinWidth(130);
			        custName.setCellValueFactory(new MapValueFactory("CUSTOMER_NAME"));
			        custName.setMinWidth(130);
			        compName.setCellValueFactory(new MapValueFactory("COMPANY_NAME"));
			        compName.setMinWidth(130);
			        location.setCellValueFactory(new MapValueFactory("LOCATION"));
			        location.setMinWidth(130);
			        revenue.setCellValueFactory(new MapValueFactory("REVENUE"));
			        revenue.setMinWidth(130);
			        
			 
			        TableView table_view = new TableView<>(tableData);
			        table_view.getColumns().setAll(ref_no, dateOfService, custName, compName, location, revenue);
			        
			 
			        final VBox vbox = new VBox();
			 
			        vbox.setSpacing(5);
			        vbox.setPadding(new Insets(10, 0, 0, 10));
			        vbox.getChildren().addAll(label, table_view);
				        tile.getChildren().add(vbox);
						
					
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
