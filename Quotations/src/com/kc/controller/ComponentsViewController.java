package com.kc.controller;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ComponentsDAO;
import com.kc.model.ComponentsVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

@SuppressWarnings("rawtypes")
public class ComponentsViewController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(ComponentsViewController.class);
	private ObservableList<ComponentsVO> componentsList;
	private ObservableList<String> searchByList;
	private ComponentsDAO componentsDAO;
	
	public ComponentsViewController(){
		componentsDAO = new ComponentsDAO();
	}
	
	
	@FXML
	private HBox modifyHbox; 
	
	@FXML
	private GridPane topGrid;
	
	@FXML
    private TableView<ComponentsVO> componentTable;
	
	@FXML private TableColumn<ComponentsVO, String> name;
    @FXML private TableColumn<ComponentsVO, String> category;
    @FXML private TableColumn<ComponentsVO, String> subCategory;
    @FXML private TableColumn<ComponentsVO, String> vendor;
    @FXML private TableColumn<ComponentsVO, String> model;
    @FXML private TableColumn<ComponentsVO, String> type;
    @FXML private TableColumn<ComponentsVO, String> size;
    @FXML private TableColumn<ComponentsVO, Double> costPrice;
    @FXML private TableColumn<ComponentsVO, Double> dealerPrice;
    @FXML private TableColumn<ComponentsVO, Double> endUserPrice;
    @FXML private Label message;
	


	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		try{
			final ObservableList<String> tempList = FXCollections.observableArrayList(); 
			componentsList = componentsDAO.getComponents();
			componentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			searchByList = FXCollections.observableArrayList();
			searchByList.add("Component Category");
			searchByList.add("Sub Category");
			searchByList.add("Component Name");
			searchByList.add("Vendor");
			searchByList.add("Model");
			searchByList.add("Type");
			searchByList.add("Size");
			((ComboBox<String>)topGrid.getChildren().get(3)).setItems(searchByList);
			
			((ComboBox<String>)topGrid.getChildren().get(3)).valueProperty().addListener(new ChangeListener<String>() {
	            
				@Override public void changed(ObservableValue ov, String t, String t1) {                
	                if(t1.equals("Component Category"))
	                {
	                	for(ComponentsVO componentsVO : componentsList)
	                	{
	                		tempList.add(componentsVO.getComponentCategory());
	                	}
	                }
	                else if(t1.equals("Sub Category"))
	                {
	                	for(ComponentsVO componentsVO : componentsList)
	                	{
	                		tempList.add(componentsVO.getSubCategory());
	                	}
	                }
	                else if(t1.equals("Component Name"))
	                {
	                	for(ComponentsVO componentsVO : componentsList)
	                	{
	                		tempList.add(componentsVO.getComponentName());
	                	}
	                }
	                else if(t1.equals("Vendor"))
	                {
	                	for(ComponentsVO componentsVO : componentsList)
	                	{
	                		tempList.add(componentsVO.getVendor());
	                	}
	                }
	                else if(t1.equals("Model"))
	                {
	                	for(ComponentsVO componentsVO : componentsList)
	                	{
	                		tempList.add(componentsVO.getModel());
	                	}
	                }
	                else if(t1.equals("Type"))
	                {
	                	for(ComponentsVO componentsVO : componentsList)
	                	{
	                		tempList.add(componentsVO.getType());
	                	}
	                }
	                else if(t1.equals("Size"))
	                {
	                	for(ComponentsVO componentsVO : componentsList)
	                	{
	                		tempList.add(componentsVO.getSize());
	                	}
	                }
	                ((AutoCompleteTextField<String>)topGrid.getChildren().get(1)).setItems(tempList);
	            }
	        });
			
			((AutoCompleteTextField<String>)topGrid.getChildren().get(1)).setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					ObservableList<ComponentsVO> tempList =  FXCollections.observableArrayList();
					String tempString = ((AutoCompleteTextField<String>)topGrid.getChildren().get(1)).getText();
					if(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem().equals("Component Category"))
					{
						for(ComponentsVO componentsVO : componentsList)
						{
							if(componentsVO.getComponentCategory().equalsIgnoreCase(tempString))
							{
								tempList.add(componentsVO);
							}
						}
					}
					else if(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem().equals("Sub Category"))
					{
						for(ComponentsVO componentsVO : componentsList)
						{
							if(componentsVO.getSubCategory().equalsIgnoreCase(tempString))
							{
								tempList.add(componentsVO);
							}
						}
					}
					else if(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem().equals("Component Name"))
					{
						for(ComponentsVO componentsVO : componentsList)
						{
							if(componentsVO.getComponentName().equalsIgnoreCase(tempString))
							{
								tempList.add(componentsVO);
							}
						}
					}
					else if(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem().equals("Vendor"))
					{
						for(ComponentsVO componentsVO : componentsList)
						{
							if(componentsVO.getVendor().equalsIgnoreCase(tempString))
							{
								tempList.add(componentsVO);
							}
						}
					}
					else if(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem().equals("Model"))
					{
						for(ComponentsVO componentsVO : componentsList)
						{
							if(componentsVO.getModel().equalsIgnoreCase(tempString))
							{
								tempList.add(componentsVO);
							}
						}
					}
					else if(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem().equals("Type"))
					{
						for(ComponentsVO componentsVO : componentsList)
						{
							if(componentsVO.getType().equalsIgnoreCase(tempString))
							{
								tempList.add(componentsVO);
							}
						}
					}
					else if(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem().equals("Size"))
					{
						for(ComponentsVO componentsVO : componentsList)
						{
							if(componentsVO.getSize().equalsIgnoreCase(tempString))
							{
								tempList.add(componentsVO);
							}
						}
					}
					
					
					name.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentName"));
					category.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentCategory"));
					subCategory.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("subCategory"));
					vendor.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("vendor"));
					model.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("model"));
					type.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("type"));
					size.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("size"));
					costPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Double>("costPrice"));
					dealerPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Double>("dealerPrice"));
					endUserPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Double>("endUserPrice"));
					componentTable.setItems(tempList);
					
				}
			});
			
			
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modifyComponent()
	{
		
	}
	
	public void deleteComponents()
	{
		ObservableList<ComponentsVO> componentList = FXCollections.observableArrayList();
		try{
			componentList = componentTable.getSelectionModel().getSelectedItems();
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
				    "Do you want to continue?", "Confirm Dialog", "title", DialogOptions.OK_CANCEL);
			if(response.equals(DialogResponse.OK))
			{
				componentsDAO.deleteComponents(componentList);
				message.setText(CommonConstants.COMPONENT_DELETE_SUCCESS);
				message.setVisible(true);
				componentTable.setItems(componentsDAO.getComponents());
			}
		}
		catch (Exception e) {
			message.setText(CommonConstants.FAILURE);
			message.setVisible(true);
		}
		
	}
	
}
