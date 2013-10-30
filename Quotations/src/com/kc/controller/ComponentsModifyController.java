package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.model.ComponentsVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class ComponentsModifyController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(ComponentsModifyController.class);
	private ObservableList<ComponentsVO> componentsList;
	
	@FXML
	private AutoCompleteTextField<ComponentsVO> componentNameAutoFill;
	
	@FXML
	private HBox modifyHbox; 
	




	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		componentsList = FXCollections.observableArrayList();
		
		ComponentsVO componentsVO = new  ComponentsVO();
		componentsVO.setComponentName("abc");
		componentsVO.setModel("hhh");
		componentsVO.setDealerPrice(999);
		
		componentsList.add(componentsVO);
		
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
						for(Node node : listTextField)
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
							else if(node.getId().equals("vender"))
							{
								((TextField)node).setText(componentsVO.getVender());
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
		});
	}
	
}
