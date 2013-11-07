package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.kc.constant.CommonConstants;
import com.kc.dao.ComponentsDAO;
import com.kc.model.ComponentsVO;
import com.kc.model.CustomersVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class ComponentsModifyController implements Initializable {

	private static final Logger LOG = LogManager
			.getLogger(ComponentsModifyController.class);
	private ObservableList<ComponentsVO> componentsList;
	private ComponentsDAO componentsDAO;
	private ComponentsVO componentsVO;

	public ComponentsModifyController() {
		componentsDAO = new ComponentsDAO();
		componentsVO = new ComponentsVO();
	}

	@FXML
	private HBox modifyHbox;
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
	@FXML
	private ComboBox<String> categoryCombo;
	@FXML
	private ComboBox<String> subcategoryCombo;
	@FXML
	private ComboBox<ComponentsVO> componentCombo;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		try {
			componentsList = componentsDAO.getComponents();
			final ObservableList<ComponentsVO> tempComponentsList = FXCollections.observableArrayList();
			
			ObservableList<String> tempCategoryList = FXCollections.observableArrayList();
			final ObservableList<String> tempSubCategoryList = FXCollections.observableArrayList();
			final ObservableList<ComponentsVO> tempComponentList = FXCollections.observableArrayList();
			subcategoryCombo.setItems(tempSubCategoryList);
			componentCombo.setItems(tempComponentList);
			
			
			for(ComponentsVO componentVO : componentsList)
			{
				if(!tempCategoryList.contains(componentVO.getComponentCategory()))
				{
					tempCategoryList.add(componentVO.getComponentCategory());
				}
			}

			categoryCombo.setItems(tempCategoryList);
			
			categoryCombo.valueProperty().addListener(
					new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {
							componentsList.clear();
							try {
								componentsList = componentsDAO.getComponents();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							tempSubCategoryList.clear();
							
							tempComponentsList.clear();
							for(ComponentsVO componentsVO : componentsList)
							{
								if(componentsVO.getComponentCategory().equals(t1))
								{
									if(!tempSubCategoryList.contains(componentsVO.getSubCategory()))
									{
										tempSubCategoryList.add(componentsVO.getSubCategory());
										tempComponentsList.add(componentsVO);
									}
								}
							}
						}
					});

			subcategoryCombo.valueProperty().addListener(
					new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {
							
							
							tempComponentList.clear();
							
							for(ComponentsVO componentsVO : tempComponentsList)
							{
								if(componentsVO.getSubCategory().equals(t1))
								{
									tempComponentList.add(componentsVO);
								}
							}
						}
					});

			componentCombo.valueProperty().addListener(
					new ChangeListener<ComponentsVO>() {

						@Override
						public void changed(ObservableValue ov, ComponentsVO t,
								ComponentsVO t1) {

							if(null != t1)
							{
							modifyHbox.setVisible(true);
							fillTextFieldValues(t1);
							}
						}
					});
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
	public void fillTextFieldValues(ComponentsVO componentsVO)
	{
		ComponentsModifyController.this.componentsVO.setId(componentsVO.getId());
		componentName.setText(componentsVO.getComponentName());
		componentCategory.setText(componentsVO.getComponentCategory());
		subCategory.setText(componentsVO.getSubCategory());
		vendor.setText(componentsVO.getVendor());
		model.setText(componentsVO.getModel());
		type.setText(componentsVO.getType());
		size.setText(componentsVO.getSize());
		costPrice.setText(String.valueOf(componentsVO.getCostPrice()));
		dealerPrice.setText(String.valueOf(componentsVO.getDealerPrice()));
		endUserPrice.setText(String.valueOf(componentsVO.getEndUserPrice()));
	}

	public void modifyComponent() {
		try {
				ComponentsVO componentsVO = new ComponentsVO();
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
				componentsVO.setId(this.componentsVO.getId());
				
				componentsDAO.updateComponent(componentsVO);
				message.setText(CommonConstants.COMPONENT_MODIFY_SUCCESS);
				message.setVisible(true);
				message.getStyleClass().remove("failure");
				message.getStyleClass().add("success");
		} catch (Exception e) {
			message.setText(CommonConstants.FAILURE);
			message.setVisible(true);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			e.printStackTrace();
		}
	}
}
