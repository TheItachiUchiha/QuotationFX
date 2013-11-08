package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.kc.constant.CommonConstants;
import com.kc.dao.ComponentsDAO;
import com.kc.model.ComponentsVO;
import com.kc.util.Validation;

public class ComponentsCreateController implements Initializable{
	
	private static final Logger LOG = LogManager.getLogger(ComponentsCreateController.class);
	
	private ComponentsDAO componentsDAO;
	private Validation validation;

	@FXML
	private TextField componentName;
	@FXML
	private TextField componentCategory;
	@FXML
	private TextField subCategory;
	@FXML
	private TextField vendor;
	@FXML
	private TextField model;
	@FXML
	private TextField type;
	@FXML
	private TextField size;
	@FXML
	private TextField costPrice;
	@FXML
	private TextField dealerPrice;
	@FXML
	private TextField endUserPrice;
	@FXML
	private Label message;
	
	public ComponentsCreateController()
	{
		 componentsDAO = new ComponentsDAO();
		 validation = new Validation();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		validation.allowAsAmount(costPrice);
		validation.allowAsAmount(dealerPrice);
		validation.allowAsAmount(endUserPrice);
		
	}
	
	public void saveComponents()
	{
		LOG.info("Enter : saveComponents");

		try
		{
			if(validation.isEmpty(componentName, componentCategory, subCategory, vendor, model, type, size, costPrice, dealerPrice, endUserPrice))
			{
				message.setText(CommonConstants.MANDATORY_FIELDS);
				message.setVisible(true);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
			}
			else
			{
				ComponentsVO componentsVO=new ComponentsVO();
				componentsVO.setComponentName(componentName.getText());
				componentsVO.setComponentCategory(componentCategory.getText());
				componentsVO.setSubCategory(subCategory.getText());
				componentsVO.setVendor(vendor.getText());
				componentsVO.setModel(model.getText());
				componentsVO.setType(type.getText());
				componentsVO.setSize(size.getText());
				componentsVO.setCostPrice(Double.parseDouble(costPrice.getText()));
				componentsVO.setDealerPrice(Double.parseDouble(dealerPrice.getText()));
				componentsVO.setEndUserPrice(Double.parseDouble(endUserPrice.getText()));
				componentsDAO.saveComponent(componentsVO);
				message.setText(CommonConstants.COMPONENT_ADD_SUCCESS);
				message.setVisible(true);
				message.getStyleClass().remove("failure");
				message.getStyleClass().add("success");
			}
		}
		catch (SQLException s) {
			if (s.getErrorCode() == CommonConstants.UNIQUE_CONSTRAINT) {
				message.setText(CommonConstants.DUPLICATE_COMPONENT);
				message.setVisible(true);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : saveComponents");
	}
}
