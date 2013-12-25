package com.kc.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;
import com.kc.model.HelpVO;
import com.kc.util.Validation;

public class HelpCompanyDetailsController implements Initializable {
private static final Logger LOG = LogManager.getLogger(HelpCompanyDetailsController.class);
HelpDAO helpDAO;
Validation validation;
public HelpCompanyDetailsController() {
	validation = new Validation();
	helpDAO = new HelpDAO();
}

@FXML
private TextArea address;

@FXML
private TextField companyLogo;

@FXML
private TextField companyName;

@FXML
private TextField contactDetails;

@FXML
private TextArea description;

@FXML
private TextField homeLogo;

@FXML
private Label message;

@FXML
private Button cLogoButton;

@FXML
private Button hLogoButton;

@Override
public void initialize(URL location, ResourceBundle resources) {
	cLogoButton.setOnAction(new EventHandler<ActionEvent>() {
	     @Override
	     public void handle(ActionEvent event) {
	     	   Stage stage=new Stage();
	           FileChooser fileChooser = new FileChooser();
	           File tempFile = fileChooser.showOpenDialog(stage);
	           if(tempFile!=null){
	                   companyLogo.setText(tempFile.getPath());
	           }
	       }
	});
	hLogoButton.setOnAction(new EventHandler<ActionEvent>() {
	     @Override
	     public void handle(ActionEvent event) {
	     	   Stage stage=new Stage();
	           FileChooser fileChooser = new FileChooser();
	           File tempFile = fileChooser.showOpenDialog(stage);
	           if(tempFile!=null){
	                   homeLogo.setText(tempFile.getPath());
	           }
	       }
	});
	validation.allowAsPhoneNumber(contactDetails);
}

	public void saveConfiguration()
	{
		try
		{
			HelpVO helpVO = new HelpVO();
			helpVO.setName(companyName.getText());
			helpVO.setDescription(description.getText());
			helpVO.setAddress(address.getText());
			helpVO.setContact(contactDetails.getText());
			helpVO.setCompanyLogo(new File (companyLogo.getText()));
			helpVO.setHomeLogo(new File (homeLogo.getText()));
			
			helpDAO.updateCompanyDetails(helpVO);
			
			message.setText(CommonConstants.CONF_SAVED);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
			message.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}