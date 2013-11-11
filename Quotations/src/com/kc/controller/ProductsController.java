package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ProductsController {
	
	private static final Logger LOG = LogManager.getLogger(ProductsController.class);
	public void newProduct()
	{
		LOG.info("Enter : newProduct");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("../view/products-create.fxml"));
		BorderPane productCreate = (BorderPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Modify Customer");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(productCreate);
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
						.getResource("../view/products-modify.fxml"));
		BorderPane productCreate = (BorderPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Modify Customer");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(productCreate);
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
						.getResource("../view/products-view.fxml"));
		BorderPane productCreate = (BorderPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Modify Customer");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(productCreate);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : viewProduct");
	}
}
