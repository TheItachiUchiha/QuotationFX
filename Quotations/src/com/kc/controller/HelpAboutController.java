package com.kc.controller;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.kc.dao.HelpDAO;
import com.kc.model.HelpVO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelpAboutController implements Initializable {
	
	HelpDAO  helpDAO;
	public HelpAboutController() {
		helpDAO = new HelpDAO();
	}
	@FXML
    private Label address;

    @FXML
    private ImageView companyLogo;

    @FXML
    private Label contact;

    @FXML
    private Label description;

    @FXML
    private ImageView homeLogo;

    @FXML
    private Label name;
    HelpVO helpVO = new HelpVO();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			
			AdminHomeController.currentPage.setText("ABOUT");
			helpVO = helpDAO.getCompanyDetails();
			description.setWrapText(true);
			address.setWrapText(true);
			name.setText(helpVO.getName());
			description.setText(helpVO.getDescription());
			address.setText(helpVO.getAddress());
			contact.setText(helpVO.getContact());
			Image image = new Image("file:"+helpVO.getCompanyLogo().getPath());
			companyLogo.setImage(image);
			Image image2 = new Image("file:"+helpVO.getHomeLogo().getPath());
			homeLogo.setImage(image2);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


}
