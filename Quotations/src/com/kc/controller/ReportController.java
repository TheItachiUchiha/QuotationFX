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
import javafx.scene.control.Dialogs;
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
import com.kc.dao.CustomersDAO;
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
	CustomersDAO customersDAO;
	
	public ReportController()
	{
		productsDAO = new ProductsDAO();
		reportsDAO = new ReportsDAO();
		reportService = new ReportService();
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
	}
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
	    
	    int flag=0;
    
    private ObservableList<String> monthList = FXCollections.observableArrayList();
    private ObservableList<EnquiryVO> listOfEnquiries = FXCollections.observableArrayList();
    private ObservableList<String> yearList = FXCollections.observableArrayList();
    private ObservableList<String> customerList = FXCollections.observableArrayList();
    private ObservableList<String> companyList = FXCollections.observableArrayList();
    private ObservableList<String> referenceList = FXCollections.observableArrayList();
    private ObservableList<String> referedByList = FXCollections.observableArrayList();
    String startDate;
	String endDate;
	private int customerId=0;
	TableView table_view = new TableView<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			AdminHomeController.currentPage.setText("REPORT");
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
					try{
						if(newValue.equalsIgnoreCase("Standard Report"))
						{
							reportVBox.getChildren().add(standardGrid);
							reportVBox.getChildren().remove(customGrid);
						}
						else if(newValue.equalsIgnoreCase("Custom Report"))
						{
							referedByList = reportsDAO.getReferedBy();
							referenceList = reportsDAO.getReferenceNos();
							companyList = reportsDAO.getCompany();
							customerList = reportsDAO.getCustomers();
							reportVBox.getChildren().add(customGrid);
							reportVBox.getChildren().remove(standardGrid);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
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
			customTypeCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> paramObservableValue,
						String oldValue, String newValue) {
					customAutoFill.setText("");
					customGrid.getChildren().remove(customAutoFill);
					customAutoFill = new AutoCompleteTextField<String>();
					if(newValue.equalsIgnoreCase("Reference No"))
					{
						customAutoFill.setItems(referenceList);
					}
					else if(newValue.equalsIgnoreCase("Customer Name"))
					{
						customAutoFill.setItems(customerList);
					}
					else if(newValue.equalsIgnoreCase("Company Name"))
					{
						customAutoFill.setItems(companyList);
					}
					else if(newValue.equalsIgnoreCase("Refered By"))
					{
						customAutoFill.setItems(referedByList);
					}
					customGrid.add(customAutoFill, 1, 1);
				}
			});
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void createReport()
	{
		if(tile.getChildren().size()>0)
		{
			tile.getChildren().remove(0);
		}
		Map<String, List<Integer>> listOfCategories = FXCollections.observableHashMap();
		Map<String, List<Integer>> listOfSubCategories = FXCollections.observableHashMap();
		Map<String, List<Integer>> listOfCustomerType = FXCollections.observableHashMap();
		Map<String, List<Integer>> listOfCustomerState = FXCollections.observableHashMap();
		try
		{
			if(reportTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Standard Report"))
			{
				if(typeCombo.getSelectionModel().getSelectedIndex()==-1||periodCombo.getSelectionModel().getSelectedIndex()==-1)
				{
					Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_TYPE_PERIOD);
				}
				else
				{
					flag=0;
					if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Month"))
					{
						if(monthCombo.getSelectionModel().getSelectedIndex()==-1||monthYearCombo.getSelectionModel().getSelectedIndex()==-1)
						{
							Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
						}
						else
						{
							startDate = "01/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
							endDate = "31/" + QuotationUtil.monthDigitFromString(monthCombo.getSelectionModel().getSelectedItem()) + "/" + monthYearCombo.getSelectionModel().getSelectedItem();
							flag=1;
						}
					}
					else if(periodCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Year"))
					{
						if(yearCombo.getSelectionModel().getSelectedIndex()==-1)
						{
							Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_YEAR);
						}
						else
						{
							startDate = "01/" + "01/" + yearCombo.getSelectionModel().getSelectedItem();
							endDate = "31/" + "12/" + yearCombo.getSelectionModel().getSelectedItem();
							flag=1;
						}
					}
				}
					if(flag==1)
					{
						if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Product Category"))
						{
							listOfCategories = reportService.getCategoriesForProduct();
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
				}
				else if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Customer Type"))
				{
					listOfCustomerType = reportService.getCustomerTypeForProduct();
					
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
				tile.getChildren().clear();
				if(customTypeCombo.getSelectionModel().getSelectedIndex()==-1)
				{
					Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_TYPE);
				}
				else
				{
					final Label label = new Label(customTypeCombo.getSelectionModel().getSelectedItem()+" : "+customAutoFill.getText());
					label.setFont(new Font("Arial", 20));
					if(customTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Service Engineer Name"))
					{
						if(customMonthCombo.getSelectionModel().getSelectedIndex()==-1||customMonthYearCombo.getSelectionModel().getSelectedIndex()==-1)
						{
							Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_MONTH_YEAR);
						}
						else
						{
							ObservableList<Map<String, Object>> tableData = FXCollections.observableArrayList();
							startDate = "01/" + QuotationUtil.monthDigitFromString(customMonthCombo.getSelectionModel().getSelectedItem()) + "/" + customMonthYearCombo.getSelectionModel().getSelectedItem();
							endDate = "31/" + QuotationUtil.monthDigitFromString(customMonthCombo.getSelectionModel().getSelectedItem()) + "/" + customMonthYearCombo.getSelectionModel().getSelectedItem();
						
							
							tableData = reportsDAO.getServicingEngineerDetails(startDate, endDate, customAutoFill.getText());
							ObservableList<Map<String, Object>> tableList = FXCollections.observableArrayList();
							TableColumn<Map, String> ref_no = new TableColumn<>("Product Ref_No");
					        TableColumn<Map, String> dateOfService = new TableColumn<>("Date of Service");
					        TableColumn<Map, String> custName = new TableColumn<>("Customer Name");
					        TableColumn<Map, String> compName = new TableColumn<>("Company Name");
					        TableColumn<Map, String> location = new TableColumn<>("Location");
					        TableColumn<Map, String> revenue = new TableColumn<>("Service Revenue");
					 
					        ref_no.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_REF));
					        ref_no.setMinWidth(100);
					        dateOfService.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_DATE));
					        dateOfService.setMinWidth(100);
					        custName.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_CUST));
					        custName.setMinWidth(100);
					        compName.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_COMP));
					        compName.setMinWidth(100);
					        location.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_LOC));
					        location.setMinWidth(100);
					        revenue.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_REVENUE));
					        revenue.setMinWidth(100);
					        
					 
					        table_view.setItems(tableData);
					        table_view.getColumns().setAll(ref_no, dateOfService, custName, compName, location, revenue);			
	
						}
					}
					else if(customTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Reference No"))
					{
						ObservableList<Map<String, Object>> tableData = FXCollections.observableArrayList();
						if(null!=customAutoFill.getText() && !customAutoFill.getText().equals(""))
						{
							tableData = reportsDAO.getEnquriesFromReference(customAutoFill.getText());
							createCustomReport(tableData);
						}
						
					}
					else if(customTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Customer Name"))
					{
						ObservableList<Map<String, Object>> tableData = FXCollections.observableArrayList();
						if(null!=customAutoFill.getText() && !customAutoFill.getText().equals(""))
						{
							tableData = reportsDAO.getEnquriesFromCustomerName(customAutoFill.getText());
							createCustomReport(tableData);
						}
						
					}
					else if(customTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Company Name"))
					{
						ObservableList<Map<String, Object>> tableData = FXCollections.observableArrayList();
						if(null!=customAutoFill.getText() && !customAutoFill.getText().equals(""))
						{
							tableData = reportsDAO.getEnquriesFromCompanyName(customAutoFill.getText());
							createCustomReport(tableData);
						}
					}
					else if(customTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Refered By"))
					{
						ObservableList<Map<String, Object>> tableData = FXCollections.observableArrayList();
						if(null!=customAutoFill.getText() && !customAutoFill.getText().equals(""))
						{
							tableData = reportsDAO.getEnquriesFromReferredBy(customAutoFill.getText());
							createCustomReport(tableData);
						}
						
					}
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
	public void createCustomReport(ObservableList<Map<String, Object>> tableData)
	{
		try
		{
			ObservableList<Map<String, Object>> tableList = FXCollections.observableArrayList();
			TableColumn<Map, String> ref_no = new TableColumn<>("Reference No");
			TableColumn<Map, String> dateOfEnquiry = new TableColumn<>("Date of Enquiry");
			TableColumn<Map, String> dateOfQuotation = new TableColumn<>("Date of Quotation");
	        TableColumn<Map, String> dateOfSales = new TableColumn<>("Date of Sales");
	        TableColumn<Map, String> ProductName = new TableColumn<>("Product Name");
	        TableColumn<Map, String> custName = new TableColumn<>("Customer Name");
	        TableColumn<Map, String> compName = new TableColumn<>("Company Name");
	        TableColumn<Map, String> location = new TableColumn<>("Location");
	        TableColumn<Map, String> referedBy = new TableColumn<>("Refered By");
	        TableColumn<Map, String> serviceCount = new TableColumn<>("No Of Services");
	        TableColumn<Map, String> revenueSale = new TableColumn<>("Sales Revenue");
	        TableColumn<Map, String> revenueService = new TableColumn<>("Service Revenue");
	 
	        ref_no.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_REF));
	        ref_no.setMinWidth(90);
	        dateOfEnquiry.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_DATE_ENQUIRY));
	        dateOfEnquiry.setMinWidth(95);
	        dateOfQuotation.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_DATE_QUOTATION));
	        dateOfQuotation.setMinWidth(100);
	        dateOfSales.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_DATE_SALES));
	        dateOfSales.setMinWidth(90);
	        ProductName.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_PROD));
	        ProductName.setMinWidth(90);
	        custName.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_CUST));
	        custName.setMinWidth(100);
	        compName.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_COMP));
	        compName.setMinWidth(100);
	        location.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_LOC));
	        location.setMinWidth(80);
	        referedBy.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_REFD));
	        referedBy.setMinWidth(85);
	        serviceCount.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_SERV_NO));
	        serviceCount.setMinWidth(90);
	        revenueSale.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_REVENUE));
	        revenueSale.setMinWidth(90);
	        revenueService.setCellValueFactory(new MapValueFactory(CommonConstants.KEY_REPORT_SERV));
	        revenueService.setMinWidth(100);
	 
	        table_view.setItems(tableData);
	        table_view.getColumns().setAll(ref_no, dateOfEnquiry,dateOfQuotation,dateOfSales,ProductName ,custName, compName, location, referedBy,serviceCount,revenueSale,revenueService);
	        
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
