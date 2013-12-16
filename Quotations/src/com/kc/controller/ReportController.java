package com.kc.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

import com.kc.constant.CommonConstants;
import com.kc.dao.ProductsDAO;
import com.kc.dao.ReportsDAO;
import com.kc.model.EnquiryVO;
import com.kc.util.CustomReportTemplate;
import com.kc.util.QuotationUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

public class ReportController implements Initializable {

	ProductsDAO productsDAO;
	ReportsDAO reportsDAO;
	
	public ReportController()
	{
		productsDAO = new ProductsDAO();
		reportsDAO = new ReportsDAO();
	}
	@FXML
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
    private ComboBox<String> yearCombo;
    
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
		reportTypeList.add("Sales Report");
		reportTypeList.add("Service Report");
		reportTypeCombo.setItems(reportTypeList);
		
		salesList.add("Product Category");
		salesList.add("Product Subcategory");
		salesList.add("Customer Type");
		salesList.add("Customer State");
		
		serviceList.add("Reference No");
		serviceList.add("Service Engineer Name");
		serviceList.add("Customer name");
		
		monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
		periodList.setItems(monthList);
		periodList.getSelectionModel().selectionModeProperty().set(SelectionMode.MULTIPLE);
		
		yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
		yearCombo.setItems(yearList);
		
		reportTypeCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				reportGrid.setVisible(true);
				periodList.getSelectionModel().clearSelection();
				
				if(reportTypeCombo.getSelectionModel().getSelectedItem().equals("Sales Report"))
				{
					typeCombo.setItems(salesList);
					periodList.getSelectionModel().clearSelection();
				}
				else if (reportTypeCombo.getSelectionModel().getSelectedItem().equals("Service Report"))
				{
					typeCombo.setItems(serviceList);
					periodList.getSelectionModel().clearSelection();
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
		ObservableList<Map<String, Object>> listOfCategories = FXCollections.observableArrayList();
		ObservableList<Map<String, Object>> listOfSubCategories = FXCollections.observableArrayList();
		ObservableList<Map<String, Object>> listOfCustomerType = FXCollections.observableArrayList();
		//ObservableList<Map<String, Object>> listOfSubCategories = FXCollections.observableArrayList();
		try
		{
			if(reportTypeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Sales Report"))
			{
				if(typeCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("Product Category"))
				{
					listOfCategories = productsDAO.getCategoriesForProduct();
					startDate = "01/" + QuotationUtil.monthDigitFromString(periodList.getSelectionModel().getSelectedItems().get(0)) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					endDate = "31/" + QuotationUtil.monthDigitFromString(periodList.getSelectionModel().getSelectedItems().get(periodList.getSelectionModel().getSelectedItems().size()-1)) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					listOfEnquiries = reportsDAO.salesReport(startDate, endDate);
					
					for(Map<String, Object> category : listOfCategories)
					{
						int totalEnquiries = 0;
						int totalSuccessfulEnquiries = 0;
						double totalRevenue = 0;
						int serviceCount = 0;
						for(EnquiryVO enquiryVO : listOfEnquiries)
						{
							if(enquiryVO.getProductId() == (Integer)category.get("id"))
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
				        chart.setFirstBoldLabelRight(category.get("category").toString());
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
					listOfSubCategories = productsDAO.getSubCategoriesForProduct();
					startDate = "01/" + QuotationUtil.monthDigitFromString(periodList.getSelectionModel().getSelectedItems().get(0)) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					endDate = "31/" + QuotationUtil.monthDigitFromString(periodList.getSelectionModel().getSelectedItems().get(periodList.getSelectionModel().getSelectedItems().size()-1)) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					listOfEnquiries = reportsDAO.salesReport(startDate, endDate);
					
					for(Map<String, Object> category : listOfSubCategories)
					{
						int totalEnquiries = 0;
						int totalSuccessfulEnquiries = 0;
						double totalRevenue = 0;
						int serviceCount = 0;
						for(EnquiryVO enquiryVO : listOfEnquiries)
						{
							if(enquiryVO.getProductId() == (Integer)category.get("id"))
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
				        chart.setFirstBoldLabelRight(category.get("subcategory").toString());
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
					listOfCustomerType = productsDAO.getCustomerTypeForProduct();
					startDate = "01/" + QuotationUtil.monthDigitFromString(periodList.getSelectionModel().getSelectedItems().get(0)) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					endDate = "31/" + QuotationUtil.monthDigitFromString(periodList.getSelectionModel().getSelectedItems().get(periodList.getSelectionModel().getSelectedItems().size()-1)) + "/" + yearCombo.getSelectionModel().getSelectedItem();
					listOfEnquiries = reportsDAO.salesReport(startDate, endDate);
					
					for(Map<String, Object> customers : listOfCustomerType)
					{
						int totalEnquiries = 0;
						int totalSuccessfulEnquiries = 0;
						double totalRevenue = 0;
						int serviceCount = 0;
						for(EnquiryVO enquiryVO : listOfEnquiries)
						{
							if(enquiryVO.getCustomerId() == (Integer)customers.get("id"))
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
				        chart.setFirstBoldLabelRight((null!=customers.get("customerType"))?customers.get("customerType").toString():"");
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
