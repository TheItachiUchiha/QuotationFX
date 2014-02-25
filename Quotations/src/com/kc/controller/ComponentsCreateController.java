package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class ComponentsCreateController implements Initializable{
	
	private static final Logger LOG = LogManager.getLogger(ComponentsCreateController.class);
	
	private ComponentsDAO componentsDAO;
	private Validation validation;

	@FXML
	private AutoCompleteTextField<String> componentName;
	@FXML
	private AutoCompleteTextField<String> componentCategory;
	@FXML
	private AutoCompleteTextField<String> subCategory;
	@FXML
	private AutoCompleteTextField<String> vendor;
	@FXML
	private AutoCompleteTextField<String> model;
	@FXML
	private AutoCompleteTextField<String> type;
	@FXML
	private AutoCompleteTextField<String> size;
	@FXML
	private TextField costPrice;
	@FXML
	private TextField dealerPrice;
	@FXML
	private TextField endUserPrice;
	@FXML
	private Label message;
	
	private ObservableList<String> categorylist=FXCollections.observableArrayList();
	private ObservableList<String> subcategorylist=FXCollections.observableArrayList();
	private ObservableList<String> namelist=FXCollections.observableArrayList();
	private ObservableList<String> vendorlist=FXCollections.observableArrayList();
	private ObservableList<String> Modellist=FXCollections.observableArrayList();
	private ObservableList<String> typelist=FXCollections.observableArrayList();
	private ObservableList<String> sizelist=FXCollections.observableArrayList();
	
	public ComponentsCreateController()
	{
		 componentsDAO = new ComponentsDAO();
		 validation = new Validation();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			AdminHomeController.currentPage.setText("CREATE COMPONENT");
			validation.allowAsAmount(costPrice);
			validation.allowAsAmount(dealerPrice);
			validation.allowAsAmount(endUserPrice);
			
			categorylist = componentsDAO.getComponentCategoryList();
			subcategorylist = componentsDAO.getComponentSubcategoryList();
			namelist = componentsDAO.getComponentNameList();
			vendorlist = componentsDAO.getComponentVendorList();
			Modellist = componentsDAO.getComponentModelList();
			typelist = componentsDAO.getComponentTypeList();
			sizelist = componentsDAO.getComponentSizeList();
			
			componentCategory.setItems(categorylist);
			subCategory.setItems(subcategorylist);
			componentName.setItems(namelist);
			vendor.setItems(vendorlist);
			model.setItems(Modellist);
			type.setItems(typelist);
			size.setItems(sizelist);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveComponents()
	{
		LOG.info("Enter : saveComponents");

		try
		{
			if(validation.isEmptyAutoComplte(componentName,vendor,componentCategory,subCategory,model,size,type))
			{
				message.setText(CommonConstants.MANDATORY_FIELDS);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
			else if(validation.isEmpty(costPrice,endUserPrice,dealerPrice))
			{
				message.setText(CommonConstants.MANDATORY_FIELDS);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
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
				message.getStyleClass().remove("failure");
				message.getStyleClass().add("success");
				message.setVisible(true);
			}
		}
		catch (SQLException s) {
			if (s.getErrorCode() == CommonConstants.UNIQUE_CONSTRAINT) {
				message.setText(CommonConstants.DUPLICATE_COMPONENT);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : saveComponents");
	}
}
