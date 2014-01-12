package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
	private Label companyLable;
	@FXML
	private ImageView comLogo;
	@FXML private HBox menuHbox;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try{
		HelpVO helpVO = new HelpVO();
		helpVO = helpDAO.getCompanyDetails();
		Image image = new Image("file:"+helpVO.getCompanyLogo().getPath());
		comLogo.setImage(image);
		companyLable.setText(helpVO.getName());
		if(LoginController.userType.equals(CommonConstants.NORMAL))
		{
			menuHbox.getChildren().remove(1);
			menuHbox.getChildren().remove(1);
			menuHbox.getChildren().remove(1);
			menuHbox.getChildren().remove(1);
			menuHbox.getChildren().remove(1);
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void adminHome()
	{
		LOG.info("Enter : adminHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/home-admin.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : adminHome");
	}
	public void productsHome()
	{
		LOG.info("Enter : productsHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/products-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : productsHome");
	}
	public void componentsHome()
	{
		LOG.info("Enter : productsHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/components-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : productsHome");
	}
	public void usersHome()
	{
		LOG.info("Enter : usersHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/users-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : usersHome");
	}
	public void customersHome()
	{
		LOG.info("Enter : customersHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/customers-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : customersHome");
	}
	public void backupHome()
	{
		LOG.info("Enter : backupHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/backup-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : backupHome");
	}
	public void helpHome()
	{
		LOG.info("Enter : helpHome");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				LoginController.class
						.getResource("../view/help-home.fxml"));
		BorderPane subMenu = (BorderPane) menuLoader.load();
		LoginController.home.setCenter(subMenu);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : helpHome");
	}
	public void logout()
	{
		LoginController.primaryStage.setHeight(566.0);
		LoginController.primaryStage.setWidth(874.0);
		LoginController.primaryStage.setResizable(false);
		LoginController.primaryStage.setScene(LoginController.scene);
		LoginController.message.setVisible(false);
		LoginController.getUsername().requestFocus();
	}
}
