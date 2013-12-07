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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.dao.ComponentsDAO;
import com.kc.model.ComponentsVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class ProductComponentAddController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(ProductComponentAddController.class);
	private ObservableList<ComponentsVO> componentsList;
	private ObservableList<ComponentsVO> addedComponentList = FXCollections.observableArrayList();
	private ObservableList<String> searchByList;
	private ComponentsDAO componentsDAO;
	
	public ProductComponentAddController(){
		componentsDAO = new ComponentsDAO();
	}
	
	public ObservableList<ComponentsVO> getAddedComponentList()
	{
		return addedComponentList;
	}
	@FXML
	private AutoCompleteTextField<String> keyword;
	@FXML
	private ComboBox<String> combo;
	@FXML
	private Button go;
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

	public TableView<ComponentsVO> getComponentTable() {
		return componentTable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		try{
			LOG.info("Enter : initialize");
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
			combo.setItems(searchByList);
			
			keyword.setPromptText("Type Keyword");
			
			combo.valueProperty().addListener(new ChangeListener<String>() {
	            
				@Override public void changed(ObservableValue ov, String t, String t1) {                
					 fillAutoCompleteFromComboBox(t1);
					 keyword.setText("");
	            }
	        });
			
			keyword.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					
					fillTableFromData();
				}
			});
			go.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					
					fillTableFromData();
				}
			});
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : initialize");
	}
	
	@SuppressWarnings("unchecked")
	private void fillAutoCompleteFromComboBox(String t1)
	{
		LOG.info("Enter : fillAutoCompleteFromComboBox");
		try{
			componentsList = componentsDAO.getComponents();
			final ObservableList<String> tempList = FXCollections.observableArrayList(); 
			if(t1.equals("Component Category"))
	        {
	        	for(ComponentsVO componentsVO : componentsList)
	        	{
	        		if(!tempList.contains(componentsVO.getComponentCategory()))
	        		{
	        			tempList.add(componentsVO.getComponentCategory());
	        		}
	        	}
	        }
	        else if(t1.equals("Sub Category"))
	        {
	        	for(ComponentsVO componentsVO : componentsList)
	        	{
	        		if(!tempList.contains(componentsVO.getSubCategory()))
	        		{
	        			tempList.add(componentsVO.getSubCategory());
	        		}
	        	}
	        }
	        else if(t1.equals("Component Name"))
	        {
	        	for(ComponentsVO componentsVO : componentsList)
	        	{
	        		if(!tempList.contains(componentsVO.getComponentName()))
	        		{
	        			tempList.add(componentsVO.getComponentName());
	        		}
	        	}
	        }
	        else if(t1.equals("Vendor"))
	        {
	        	for(ComponentsVO componentsVO : componentsList)
	        	{
	        		if(!tempList.contains(componentsVO.getVendor()))
	        		{
	        			tempList.add(componentsVO.getVendor());
	        		}
	        	}
	        }
	        else if(t1.equals("Model"))
	        {
	        	for(ComponentsVO componentsVO : componentsList)
	        	{
	        		if(!tempList.contains(componentsVO.getModel()))
	        		{
	        			tempList.add(componentsVO.getModel());
	        		}
	        	}
	        }
	        else if(t1.equals("Type"))
	        {
	        	for(ComponentsVO componentsVO : componentsList)
	        	{
	        		if(!tempList.contains(componentsVO.getType()))
	        		{
	        			tempList.add(componentsVO.getType());
	        		}
	        	}
	        }
	        else if(t1.equals("Size"))
	        {
	        	for(ComponentsVO componentsVO : componentsList)
	        	{
	        		if(!tempList.contains(componentsVO.getSize()))
	        		{
	        			tempList.add(componentsVO.getSize());
	        		}
	        	}
	        }
			keyword.setItems(tempList);
			
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : fillAutoCompleteFromComboBox");
	}
	
	private void fillTableFromData()
	{
		LOG.info("Enter : fillTableFromData");
		try{
			
			ObservableList<ComponentsVO> tempList =  FXCollections.observableArrayList();
			String tempString = keyword.getText();
			if(combo.getSelectionModel().getSelectedItem().equals("Component Category"))
			{
				for(ComponentsVO componentsVO : componentsList)
				{
					if(componentsVO.getComponentCategory().equalsIgnoreCase(tempString))
					{
						tempList.add(componentsVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Sub Category"))
			{
				for(ComponentsVO componentsVO : componentsList)
				{
					if(componentsVO.getSubCategory().equalsIgnoreCase(tempString))
					{
						tempList.add(componentsVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Component Name"))
			{
				for(ComponentsVO componentsVO : componentsList)
				{
					if(componentsVO.getComponentName().equalsIgnoreCase(tempString))
					{
						tempList.add(componentsVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Vendor"))
			{
				for(ComponentsVO componentsVO : componentsList)
				{
					if(componentsVO.getVendor().equalsIgnoreCase(tempString))
					{
						tempList.add(componentsVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Model"))
			{
				for(ComponentsVO componentsVO : componentsList)
				{
					if(componentsVO.getModel().equalsIgnoreCase(tempString))
					{
						tempList.add(componentsVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Type"))
			{
				for(ComponentsVO componentsVO : componentsList)
				{
					if(componentsVO.getType().equalsIgnoreCase(tempString))
					{
						tempList.add(componentsVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Size"))
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
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : fillTableFromData");
	}
	public void addComponents()
	{
		addedComponentList.addAll(componentTable.getSelectionModel().getSelectedItems());
		Dialogs.showInformationDialog(ProductsCreateController.stage, "Component(s) successfully added. Please close the window to continue");
	}
}
