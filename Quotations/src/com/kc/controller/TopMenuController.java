package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;
import com.kc.model.HelpVO;

public class TopMenuController implements Initializable {
	
	HelpDAO helpDAO;
	public TopMenuController() {
		helpDAO = new HelpDAO();
	}

	private static final Logger LOG = LogManager.getLogger(TopMenuController.class);
	
		@FXML
	    private MenuItem dispatch;

	    @FXML
	    private MenuItem quotation;

	    @FXML
	    private ImageView comLogo;

	    @FXML
	    private Label companyLable;

	    @FXML
	    private Label currentUser;

	    @FXML
	    private MenuItem enquiry;
	    
	    @FXML
	    private MenuItem companyDetails;
	    
	    @FXML
	    private MenuItem employee;

	    @FXML
	    private Menu homeMenu;

	    @FXML
	    private MenuBar menu;

	    @FXML
	    private HBox menuHbox;

	    @FXML
	    private MenuItem priceEstimation;

	    @FXML
	    private MenuItem report;

	    @FXML
	    private MenuItem sales;

	    @FXML
	    private MenuItem service;

	    @FXML
	    private MenuItem status;
	
	
	TabPane tabPane;
	Tab tab;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try{
			
				currentUser.setText("Welcome "+LoginController.currentUser);
				HelpVO helpVO = new HelpVO();
				helpVO = helpDAO.getCompanyDetails();
				Image image = new Image("file:"+helpVO.getCompanyLogo().getPath());
				comLogo.setImage(image);
				companyLable.setText(helpVO.getName());
				if(LoginController.userType.equals(CommonConstants.NORMAL))
				{
					menu.getMenus().remove(1);
					menu.getMenus().remove(1);
					menu.getMenus().remove(1);
					menu.getMenus().remove(1);
					menu.getMenus().remove(1);
					companyDetails.setDisable(true);
					employee.setDisable(true);
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
				if(LoginController.modulesVO.getReport().equalsIgnoreCase("N"))
				{
					report.setDisable(true);
				}
				if(LoginController.modulesVO.getEnquiry().equalsIgnoreCase("N"))
				{
					enquiry.setDisable(true);
				}
				if(LoginController.modulesVO.getProductDispatch().equalsIgnoreCase("N"))
				{
					dispatch.setDisable(true);
				}
				if(LoginController.modulesVO.getService().equalsIgnoreCase("N"))
				{
					service.setDisable(true);
				}
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
			LoginController.home.setCenter(enquiry);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void priceEstimation()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/priceEstimation.fxml"));
			BorderPane price = (BorderPane) loader.load();
			LoginController.home.setCenter(price);
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
			LoginController.home.setCenter(quotation);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void salesOrder()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/salesOrder.fxml"));
			BorderPane sales = (BorderPane) loader.load();
			LoginController.home.setCenter(sales);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void statusReminder()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/statusReminder.fxml"));
			BorderPane status = (BorderPane) loader.load();
			LoginController.home.setCenter(status);
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
			LoginController.home.setCenter(service);
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
			LoginController.home.setCenter(report);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void productDispatch()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/productDispatch.fxml"));
			BorderPane dispatch = (BorderPane) loader.load();
			LoginController.home.setCenter(dispatch);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void newProduct()
	{
		LOG.info("Enter : newProduct");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/products-create.fxml"));
		BorderPane productCreate = (BorderPane) menuLoader.load();
		
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(productCreate);
		tab.setClosable(false);
		//tab.setText("Create Product");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(productCreate);
		LoginController.home.setAlignment(productCreate, Pos.TOP_LEFT);
		
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : newProduct");
	}
	
	public void modifyProduct()
	{
		LOG.info("Enter : modifyProduct");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/products-modify.fxml"));
		BorderPane productCreate = (BorderPane) menuLoader.load();
		
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(productCreate);
		tab.setClosable(false);
		//tab.setText("Modify Product");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(productCreate);
		LoginController.home.setAlignment(productCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : modifyProduct");
	}
	
	public void viewProduct()
	{
		LOG.info("Enter : viewProduct");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/products-view.fxml"));
		BorderPane productCreate = (BorderPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(productCreate);
		tab.setClosable(false);
		//tab.setText("View Products");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(productCreate);
		LoginController.home.setAlignment(productCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : viewProduct");
	}
	
	public void newComponent()
	{

		LOG.info("Enter : newComponent");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/components-create.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(componentCreate);
		tab.setClosable(false);
		//tab.setText("Create Component");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(componentCreate);
		LoginController.home.setAlignment(componentCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : newComponent");
	}
	public void modifyComponent()
	{
		LOG.info("Enter : modifyComponent");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/components-modify.fxml"));
		BorderPane componentCreate = (BorderPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(componentCreate);
		tab.setClosable(false);
		//tab.setText("Modify Component");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(componentCreate);
		LoginController.home.setAlignment(componentCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : modifyComponent");
	}
	public void viewComponent()
	{
		LOG.info("Enter : viewComponent");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/components-view.fxml"));
		BorderPane componentCreate = (BorderPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(componentCreate);
		tab.setClosable(false);
		//tab.setText("View Components");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(componentCreate);
		LoginController.home.setAlignment(componentCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : viewComponent");
	}
	
	public void newCustomer()
	{

		LOG.info("Enter : newCustomer");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/customers-create.fxml"));
		GridPane customerCreate = (GridPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(customerCreate);
		tab.setClosable(false);
		//tab.setText("Create Customer");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(customerCreate);
		LoginController.home.setAlignment(customerCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : newCustomer");
	}
	public void modifyCustomer()
	{
		LOG.info("Enter : modifyCustomer");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/customers-modify.fxml"));
		BorderPane customerCreate = (BorderPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(customerCreate);
		tab.setClosable(false);
		//tab.setText("Modify Customer");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(customerCreate);
		LoginController.home.setAlignment(customerCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : modifyCustomer");
	}
	public void viewCustomers()
	{
		LOG.info("Enter : viewCustomers");
		try{
			FXMLLoader menuLoader = new FXMLLoader(
					this.getClass()
							.getResource("/com/kc/view/customers-view.fxml"));
			BorderPane customerCreate = (BorderPane) menuLoader.load();
			
			/*tab = new Tab();
			tabPane = new TabPane();
			tab.setContent(customerCreate);
			tab.setClosable(false);
			//tab.setText("View Customers");
			tabPane.getTabs().add(tab);*/
			LoginController.home.setCenter(customerCreate);
			LoginController.home.setAlignment(customerCreate, Pos.TOP_LEFT);
			
			}
			catch (Exception e) {
				LOG.error(e.getMessage());
			}
		LOG.info("Exit : viewCustomers");
	}
	
	public void newUser()
	{

		LOG.info("Enter : newUser");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/users-create.fxml"));
		GridPane userCreate = (GridPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(userCreate);
		tab.setClosable(false);
		//tab.setText("Create User");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(userCreate);
		LoginController.home.setAlignment(userCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : newUser");
	}
	public void modifyUser()
	{
		LOG.info("Enter : modifyUser");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/users-modify.fxml"));
		BorderPane userCreate = (BorderPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(userCreate);
		tab.setClosable(false);
		//tab.setText("Modify User");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(userCreate);
		LoginController.home.setAlignment(userCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : modifyUser");
	}
	
	public void viewUser()
	{
		LOG.info("Enter : viewUser");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/users-view.fxml"));
		BorderPane userCreate = (BorderPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(userCreate);
		tab.setClosable(false);
		//tab.setText("View Users");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(userCreate);
		LoginController.home.setAlignment(userCreate, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : viewUser");
	}
	
	public void importDB()
	{

		LOG.info("Enter : importDB");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/backup-import.fxml"));
		GridPane importb = (GridPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(importb);
		tab.setClosable(false);
		//tab.setText("Import");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(importb);
		LoginController.home.setAlignment(importb, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : importDB");
	}
	public void exportDB()
	{

		LOG.info("Enter : exportDB");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/backup-export.fxml"));
		GridPane exportb = (GridPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(exportb);
		//tab.setText("Export");
		tab.setClosable(false);
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(exportb);
		LoginController.home.setAlignment(exportb, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : exportDB");
	}
	
	public void employees()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/help-employees.fxml"));
			BorderPane employe = (BorderPane) loader.load();
			
			LoginController.home.setCenter(employe);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void companyDetails()
	{
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("/com/kc/view/help-companyDetails.fxml"));
		GridPane companyDetails = (GridPane) menuLoader.load();
		/*tab = new Tab();
		tabPane = new TabPane();
		tab.setContent(companyDetails);
		tab.setClosable(false);
		//tab.setText("Company details");
		tabPane.getTabs().add(tab);*/
		LoginController.home.setCenter(companyDetails);
		LoginController.home.setAlignment(companyDetails, Pos.TOP_LEFT);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void about()
	{
		try{
			FXMLLoader menuLoader = new FXMLLoader(
					LoginController.class
							.getResource("/com/kc/view/help-about.fxml"));
			GridPane about = (GridPane) menuLoader.load();
			
			/*tab = new Tab();
			tabPane = new TabPane();
			tab.setContent(about);
			tab.setClosable(false);
			//tab.setText("About");
			tabPane.getTabs().add(tab);*/
			LoginController.home.setCenter(about);
			LoginController.home.setAlignment(about, Pos.TOP_LEFT);
			
		}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void logout()
	{
		LoginController.primaryStage.setScene(LoginController.scene);
		LoginController.message.setVisible(false);
		LoginController.getUsername().requestFocus();
	}
}
