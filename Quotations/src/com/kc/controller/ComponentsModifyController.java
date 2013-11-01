package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

	private static final Logger LOG = LogManager.getLogger(ComponentsModifyController.class);
	private ObservableList<ComponentsVO> componentsList;
	private ComponentsDAO componentsDAO;
	private ComponentsVO componentsVO;
	
	public ComponentsModifyController(){
		componentsDAO = new ComponentsDAO();
		componentsVO = new ComponentsVO();
	}
 	
	@FXML
	private AutoCompleteTextField<ComponentsVO> componentNameAutoFill;
	
	@FXML
	private HBox modifyHbox; 
	@FXML
	private Label message;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
			try{
			componentsList = componentsDAO.getComponents();
			
			componentNameAutoFill.setItems(componentsList);
			
			
			componentNameAutoFill.setPromptText("Name Of Component");
			
			
			componentNameAutoFill.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					GridPane gridPane = (GridPane) modifyHbox.getChildren().get(0);
					ObservableList<Node> listTextField = gridPane.getChildren();
					
					for(ComponentsVO componentsVO: componentsList)
					{
						if(componentsVO.getComponentName().equals(componentNameAutoFill.getText())){
							modifyHbox.setVisible(true);
							componentNameAutoFill.setDisable(true);
							ComponentsModifyController.this.componentsVO.setId(componentsVO.getId());
							for(Node node : listTextField)
							{
								if(null!=node.getId())
								{
									if(node.getId().equals("componentName"))
									{
										((TextField)node).setText(componentsVO.getComponentName());
									}
									else if(node.getId().equals("componentCategory"))
									{
										((TextField)node).setText(componentsVO.getComponentCategory());
									}
									else if(node.getId().equals("subCategory"))
									{
										((TextField)node).setText(componentsVO.getSubCategory());
									}
									else if(node.getId().equals("vendor"))
									{
										((TextField)node).setText(componentsVO.getVendor());
									}
									else if(node.getId().equals("model"))
									{
										((TextField)node).setText(componentsVO.getModel());
									}
									else if(node.getId().equals("type"))
									{
										((TextField)node).setText(componentsVO.getType());
									}
									else if(node.getId().equals("size"))
									{
										((TextField)node).setText(componentsVO.getSize());
									}
									else if(node.getId().equals("costPrice"))
									{
										((TextField)node).setText(String.valueOf(componentsVO.getCostPrice()));
									}
									else if(node.getId().equals("dealerPrice"))
									{
										((TextField)node).setText(String.valueOf(componentsVO.getDealerPrice()));
									}
									else if(node.getId().equals("endUserPrice"))
									{
										((TextField)node).setText(String.valueOf(componentsVO.getEndUserPrice()));
									}
								}
							}
						}
					}
				}
			});
			}
			catch (Exception e) {
				LOG.error(e.getMessage());
			}
	}
	public void modifyComponent()
	{
		try
		{
		GridPane gridPane = (GridPane) modifyHbox.getChildren().get(0);
		ObservableList<Node> listTextField = gridPane.getChildren();
		ComponentsVO componentsVO = new ComponentsVO();
			for(Node node : listTextField)
			{
				if(node.getId().equals("componentName"))
				{
					componentsVO.setComponentName(((TextField)node).getText());
				}
				else if(node.getId().equals("componentCategory"))
				{
					componentsVO.setComponentCategory(((TextField)node).getText());
				}
				else if(node.getId().equals("subCategory"))
				{
					componentsVO.setSubCategory(((TextField)node).getText());
				}
				else if(node.getId().equals("vendor"))
				{
					componentsVO.setVendor(((TextField)node).getText());
				}
				else if(node.getId().equals("model"))
				{
					componentsVO.setModel(((TextField)node).getText());
				}
				else if(node.getId().equals("type"))
				{
					componentsVO.setType(((TextField)node).getText());
				}
				else if(node.getId().equals("size"))
				{
					componentsVO.setSize(((TextField)node).getText());
				}
				else if(node.getId().equals("costPrice"))
				{
					componentsVO.setCostPrice(Double.parseDouble(((TextField)node).getText()));
				}
				else if(node.getId().equals("dealerPrice"))
				{
					componentsVO.setDealerPrice(Double.parseDouble(((TextField)node).getText()));
				}
				else if(node.getId().equals("endUserPrice"))
				{
					componentsVO.setEndUserPrice(Double.parseDouble(((TextField)node).getText()));
				}
			}
			componentsVO.setId(this.componentsVO.getId());
			componentsDAO.updateComponent(componentsVO);
			message.setText(CommonConstants.COMPONENT_MODIFY_SUCCESS);
			message.setVisible(true);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
		}
		catch (Exception e) {
			message.setText(CommonConstants.FAILURE);
			message.setVisible(true);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			e.printStackTrace();
		}
		
	}

	
}
