package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;

public class HelpController implements Initializable {
	
	private static final Logger LOG = LogManager.getLogger(HelpController.class);

    @FXML
    private VBox companyVBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(LoginController.userType.equals(CommonConstants.NORMAL))
		{
			companyVBox.getChildren().remove(1);
			companyVBox.getChildren().remove(1);
			companyVBox.getChildren().remove(1);
			companyVBox.getChildren().remove(1);
		}
		
	}
	public void employees()
	{
		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/kc/view/help-employees.fxml"));
			BorderPane employe = (BorderPane) loader.load();
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().remove(1);
			((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().add(employe);
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
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("Company Details");
		((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
		((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(companyDetails);
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
			((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).setText("About");
			((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).setVisible(true);
			((BorderPane)((TabPane)((SplitPane)((BorderPane)LoginController.home.getCenter()).getCenter()).getItems().get(1)).getTabs().get(0).getContent()).setCenter(about);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	

}
