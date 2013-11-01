package com.kc.controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ComponentsDAO;
import com.kc.model.ComponentsVO;

public class ComponentsCreateController {
	
private static final Logger LOG = LogManager.getLogger(ComponentsCreateController.class);
	
	ComponentsDAO componentsDAO = new ComponentsDAO();

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
	
	public void saveComponents()
	{
		try
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
		}
		catch (SQLException s) {
			if (s.getErrorCode() == CommonConstants.UNIQUE_CONSTRAINT) {
				message.setText(CommonConstants.DUPLICATE_COMPONENT);
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
}
