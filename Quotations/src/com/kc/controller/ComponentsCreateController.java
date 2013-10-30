package com.kc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
	private TextField vender;
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
	
	public void saveComponents()
	{
		ComponentsVO componentsVO=new ComponentsVO();
		componentsVO.setComponentName(componentName.getText());
		componentsVO.setComponentCategory(componentCategory.getText());
		componentsVO.setSubCategory(subCategory.getText());
		componentsVO.setVender(vender.getText());
		componentsVO.setModel(model.getText());
		componentsVO.setType(type.getText());
		componentsVO.setSize(size.getText());
		componentsVO.setCostPrice(Double.parseDouble(costPrice.getText()));
		componentsVO.setDealerPrice(Double.parseDouble(dealerPrice.getText()));
		componentsVO.setEndUserPrice(Double.parseDouble(endUserPrice.getText()));
		
		componentsDAO.saveComponent(componentsVO);
	}
	
}
