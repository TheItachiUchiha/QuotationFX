package com.kc.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ComponentsController {
	
	private static final Logger LOG = LogManager.getLogger(ComponentsController.class);
	
	public void newComponent()
	{

		LOG.info("Enter : newComponent");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("../view/components-create.fxml"));
		GridPane componentCreate = (GridPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Create Component");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(componentCreate);
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
						.getResource("../view/components-modify.fxml"));
		BorderPane componentCreate = (BorderPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Modify Component");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(componentCreate);
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
						.getResource("../view/components-view.fxml"));
		BorderPane componentCreate = (BorderPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("View Component");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(componentCreate);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : viewComponent");
	}
}
