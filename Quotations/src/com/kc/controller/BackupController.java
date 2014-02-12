package com.kc.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BackupController {

	private static final Logger LOG = LogManager.getLogger(BackupController.class);
	public void importDB()
	{

		LOG.info("Enter : importDB");
		try{
		FXMLLoader menuLoader = new FXMLLoader(
				this.getClass()
						.getResource("/com/kc/view/backup-import.fxml"));
		GridPane importb = (GridPane) menuLoader.load();
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Import");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setLeft(importb);
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
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Export");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setLeft(exportb);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : exportDB");
	}
}
