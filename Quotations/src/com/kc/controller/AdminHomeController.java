package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

public class AdminHomeController implements Initializable {

		@FXML
	    private ToggleButton dispatch;

	    @FXML
	    private ToggleButton enquiry;

	    @FXML
	    private ToggleButton priceEstimation;

	    @FXML
	    private ToggleButton quotation;

	    @FXML
	    private ToggleButton report;

	    @FXML
	    private ToggleButton sales;

	    @FXML
	    private ToggleButton service;

	    @FXML
	    private ToggleButton status;

	    @FXML
	    public static Label currentPage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(LoginController.modulesVO.getEnquiry().equalsIgnoreCase("N"))
		{
			enquiry.setDisable(true);
		}
		if(LoginController.modulesVO.getPriceEstimation().equalsIgnoreCase("N"))
		{
			priceEstimation.setDisable(true);
		}
		if(LoginController.modulesVO.getQuotation().equalsIgnoreCase("N"))
		{
			quotation.setDisable(true);
		}
		if(LoginController.modulesVO.getSalesOrderManagement().equalsIgnoreCase("N"))
		{
			sales.setDisable(true);
		}
		if(LoginController.modulesVO.getStatusReminder().equalsIgnoreCase("N"))
		{
			status.setDisable(true);
		}
		if(LoginController.modulesVO.getProductDispatch().equalsIgnoreCase("N"))
		{
			dispatch.setDisable(true);
		}
		if(LoginController.modulesVO.getService().equalsIgnoreCase("N"))
		{
			service.setDisable(true);
		}
		if(LoginController.modulesVO.getReport().equalsIgnoreCase("N"))
		{
			report.setDisable(true);
		}
	}
	public void priceEstimation()
	{
		try{
			AdminHomeController.currentPage.setText("PRICE ESTIMATION");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation.fxml"));
			BorderPane price = (BorderPane) loader.load();
			LoginController.home.setCenter(price);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void enquiry()
	{
		try{
			AdminHomeController.currentPage.setText("ENQUIRY");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/enquiry.fxml"));
			BorderPane enquiry = (BorderPane) loader.load();
			LoginController.home.setCenter(enquiry);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void quotation()
	{
		try{
			AdminHomeController.currentPage.setText("EMAIL QUOTATION");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation.fxml"));
			BorderPane quotation = (BorderPane) loader.load();
			LoginController.home.setCenter(quotation);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sales()
	{
		try{
			AdminHomeController.currentPage.setText("SALES LEAD");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesOrder.fxml"));
			BorderPane sales = (BorderPane) loader.load();
			LoginController.home.setCenter(sales);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void status()
	{
		try{
			AdminHomeController.currentPage.setText("STATUS / REMINDER");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/statusReminder.fxml"));
			BorderPane status = (BorderPane) loader.load();
			LoginController.home.setCenter(status);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void service()
	{
		try{
			AdminHomeController.currentPage.setText("SERVICE / REGISTRY");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/serviceRegistry.fxml"));
			BorderPane service = (BorderPane) loader.load();
			LoginController.home.setCenter(service);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void report()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/report.fxml"));
			BorderPane report = (BorderPane) loader.load();
			LoginController.home.setCenter(report);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dispatch()
	{
		try{
			AdminHomeController.currentPage.setText("MATERIAL DISPATCH");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/productDispatch.fxml"));
			BorderPane dispatch = (BorderPane) loader.load();
			LoginController.home.setCenter(dispatch);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
