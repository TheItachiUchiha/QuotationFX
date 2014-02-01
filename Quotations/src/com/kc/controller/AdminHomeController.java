package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.kc.dao.HelpDAO;
import com.kc.model.HelpVO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class AdminHomeController implements Initializable {

	@FXML
	private ToggleButton priceestimation;
	@FXML
	private ToggleButton enquiry;
	@FXML
	private ToggleButton sales;
	@FXML
	private ToggleButton quotation;
	@FXML
	private ToggleButton status;
	@FXML
	private ToggleButton reports;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(LoginController.modulesVO.getPriceEstimation().equalsIgnoreCase("N"))
		{
			priceestimation.setDisable(true);
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
		if(LoginController.modulesVO.getReport().equalsIgnoreCase("N"))
		{
			reports.setDisable(true);
		}
	}
	public void priceEstimation()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation.fxml"));
			BorderPane price = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(price);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void enquiry()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/enquiry.fxml"));
			BorderPane enquiry = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(enquiry);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void quotationPreparation()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/quotation.fxml"));
			BorderPane quotation = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(quotation);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void salesOrder()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesOrder.fxml"));
			BorderPane quotation = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(quotation);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void statusReminder()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/statusReminder.fxml"));
			BorderPane quotation = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(quotation);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void serviceRegistry()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/serviceRegistry.fxml"));
			BorderPane service = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(service);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void reports()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/report.fxml"));
			BorderPane report = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(report);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void productDispatch()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/productDispatch.fxml"));
			BorderPane report = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(report);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
