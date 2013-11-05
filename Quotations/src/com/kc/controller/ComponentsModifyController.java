package com.kc.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ComponentsDAO;
import com.kc.model.ComponentsVO;
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
			
			ObservableList<String> tempCategoryList = FXCollections.observableArrayList();
			final ObservableList<String> tempSubCategoryList = FXCollections.observableArrayList();
			final ObservableList<ComponentsVO> tempComponentList = FXCollections.observableArrayList();
			
			for(ComponentsVO componentVO : componentsList)
			{
				tempCategoryList.add(componentVO.getComponentCategory());
			}

			categoryCombo.setItems(tempCategoryList);
			
			categoryCombo.valueProperty().addListener(
					new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {
							for(ComponentsVO componentsVO : componentsList)
							{
								if(componentsVO.getComponentCategory().equals(t1))
								{
									tempSubCategoryList.add(componentsVO.getSubCategory());
								}
							}
							subcategoryCombo.setItems(tempSubCategoryList);
						}
					});

			subcategoryCombo.valueProperty().addListener(
					new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {
							for(ComponentsVO componentsVO : componentsList)
							{
								if(componentsVO.getSubCategory().equals(t1))
								{
									tempComponentList.add(componentsVO);
								}
							}
							componentCombo.setItems(tempComponentList);
						}
					});

			componentCombo.valueProperty().addListener(
					new ChangeListener<ComponentsVO>() {

						@Override
						public void changed(ObservableValue ov, ComponentsVO t,
								ComponentsVO t1) {

							modifyHbox.setVisible(true);
							GridPane gridPane = (GridPane) modifyHbox
									.getChildren().get(0);
							ObservableList<Node> listTextField = gridPane
									.getChildren();

							ComponentsModifyController.this.componentsVO.setId(t1.getId());
									for (Node node : listTextField) {
										if (null != node.getId()) {
											if (node.getId().equals(
													"componentName")) {
												((TextField) node).setText(t1
														.getComponentName());
											} else if (node.getId().equals(
													"componentCategory")) {
												((TextField) node).setText(t1
														.getComponentCategory());
											} else if (node.getId().equals(
													"subCategory")) {
												((TextField) node).setText(t1
														.getSubCategory());
											} else if (node.getId().equals(
													"vendor")) {
												((TextField) node)
														.setText(t1
																.getVendor());
											} else if (node.getId().equals(
													"model")) {
												((TextField) node)
														.setText(t1
																.getModel());
											} else if (node.getId().equals(
													"type")) {
												((TextField) node)
														.setText(t1
																.getType());
											} else if (node.getId().equals(
													"size")) {
												((TextField) node)
														.setText(t1
																.getSize());
											} else if (node.getId().equals(
													"costPrice")) {
												((TextField) node).setText(String.valueOf(t1
														.getCostPrice()));
											} else if (node.getId().equals(
													"dealerPrice")) {
												((TextField) node).setText(String.valueOf(t1
														.getDealerPrice()));
											} else if (node.getId().equals(
													"endUserPrice")) {
												((TextField) node).setText(String.valueOf(t1
														.getEndUserPrice()));
											}
										}
									}
						}
					});
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	public void modifyComponent() {
		try {
			GridPane gridPane = (GridPane) modifyHbox.getChildren().get(0);
			ObservableList<Node> listTextField = gridPane.getChildren();
			ComponentsVO componentsVO = new ComponentsVO();
			for (Node node : listTextField) {
				if (node.getId().equals("componentName")) {
					componentsVO.setComponentName(((TextField) node).getText());
				} else if (node.getId().equals("componentCategory")) {
					componentsVO.setComponentCategory(((TextField) node)
							.getText());
				} else if (node.getId().equals("subCategory")) {
					componentsVO.setSubCategory(((TextField) node).getText());
				} else if (node.getId().equals("vendor")) {
					componentsVO.setVendor(((TextField) node).getText());
				} else if (node.getId().equals("model")) {
					componentsVO.setModel(((TextField) node).getText());
				} else if (node.getId().equals("type")) {
					componentsVO.setType(((TextField) node).getText());
				} else if (node.getId().equals("size")) {
					componentsVO.setSize(((TextField) node).getText());
				} else if (node.getId().equals("costPrice")) {
					componentsVO.setCostPrice(Double
							.parseDouble(((TextField) node).getText()));
				} else if (node.getId().equals("dealerPrice")) {
					componentsVO.setDealerPrice(Double
							.parseDouble(((TextField) node).getText()));
				} else if (node.getId().equals("endUserPrice")) {
					componentsVO.setEndUserPrice(Double
							.parseDouble(((TextField) node).getText()));
				}
			}
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
