package com.kc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
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
    @FXML private TableColumn action;
    @FXML private Label message;
	


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
			
			action.setSortable(false);
	         
	        action.setCellValueFactory(
	                new Callback<TableColumn.CellDataFeatures<ComponentsVO, Boolean>,
	                ObservableValue<Boolean>>() {
	 
	            @Override
	            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ComponentsVO, Boolean> p) {
	                return new SimpleBooleanProperty(p.getValue() != null);
	            }
	        });
	 
	        action.setCellFactory(
	                new Callback<TableColumn<ComponentsVO, Boolean>, TableCell<ComponentsVO, Boolean>>() {
	 
	            @Override
	            public TableCell<ComponentsVO, Boolean> call(TableColumn<ComponentsVO, Boolean> p) {
	                return new ButtonCell();
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
	public void deleteComponents(ComponentsVO componentsVO)
	{
		LOG.info("Enter : deleteComponents");
		try{
				DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
					    "Do you want to delete selected components", "Confirm", "Delete Component", DialogOptions.OK_CANCEL);
				if(response.equals(DialogResponse.OK))
				{
					componentsDAO.deleteComponents(componentsVO);
					message.setText(CommonConstants.COMPONENT_DELETE_SUCCESS);
					message.setVisible(true);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					fillAutoCompleteFromComboBox(combo.getSelectionModel().getSelectedItem());
					fillTableFromData();
					
				}
		}
		catch (Exception e) {
			message.setText(CommonConstants.FAILURE);
			message.setVisible(true);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : deleteComponents");
	}

	private class ButtonCell extends TableCell<ComponentsVO, Boolean> {
       
		Image buttonDeleteImage = new Image(getClass().getResourceAsStream("../style/delete.png"));
		Image buttonEditImage = new Image(getClass().getResourceAsStream("../style/edit.png"));
		final Button cellDeleteButton = new Button("", new ImageView(buttonDeleteImage));
		final Button cellEditButton = new Button("", new ImageView(buttonEditImage));
       
         
        ButtonCell(){
            
        	
        	cellDeleteButton.getStyleClass().add("editDeleteButton");
        	cellEditButton.getStyleClass().add("editDeleteButton");
        	
        	cellDeleteButton.setOnAction(new EventHandler<ActionEvent>(){
 
                @Override
                public void handle(ActionEvent t) {
                    deleteComponents(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
                }
            });
        	
        	cellEditButton.setOnAction(new EventHandler<ActionEvent>(){
        		 
                @Override
                public void handle(ActionEvent t) {
                	LOG.info("Enter : handle");
                	try {
						FXMLLoader menuLoader = new FXMLLoader(this.getClass()
								.getResource("../view/components-modify.fxml"));
						BorderPane componentModify;
						componentModify = (BorderPane) menuLoader.load();
						componentModify.setTop(new HBox());
						componentModify.getCenter().setVisible(true);
						Stage modifyStage = new Stage();
						Scene modifyScene = new Scene(componentModify);
						modifyStage.setResizable(false);
						modifyStage.setHeight(500);
						modifyStage.setWidth(600);
						modifyStage.initModality(Modality.WINDOW_MODAL);
						modifyStage.initOwner(LoginController.primaryStage);
						modifyStage.setScene(modifyScene);
						modifyStage.show();
						((ComponentsModifyController) menuLoader.getController())
								.fillTextFieldValues(ButtonCell.this
										.getTableView().getItems()
										.get(ButtonCell.this.getIndex()));
						modifyStage
								.setOnCloseRequest(new EventHandler<WindowEvent>() {

									@Override
									public void handle(WindowEvent paramT) {
										fillAutoCompleteFromComboBox(combo.getSelectionModel().getSelectedItem());
										for (ComponentsVO componentsVO : componentsList) {
											if (componentsVO.getId() == ButtonCell.this.getTableView()
													.getItems()
													.get(ButtonCell.this.getIndex())
													.getId()) {
												updateAutoField(componentsVO, combo.getSelectionModel().getSelectedItem());
											}
										}
										fillTableFromData();
									}
								});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LOG.error(e.getMessage());
					}
                	LOG.info("Exit : handle");
                }
            });
        }
 
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
            	HBox box = new HBox();
            	box.getChildren().addAll(cellEditButton, cellDeleteButton);
                setGraphic(box);
            }
        }
    }
	private void updateAutoField(ComponentsVO componentsVO, String t1) {
		if(t1.equals("Component Category"))
        {
        	keyword.setText(componentsVO.getComponentCategory());
        }
        else if(t1.equals("Sub Category"))
        {
        	keyword.setText(componentsVO.getSubCategory());
        }
        else if(t1.equals("Component Name"))
        {
        	keyword.setText(componentsVO.getComponentName());
        }
        else if(t1.equals("Vendor"))
        {
        	keyword.setText(componentsVO.getVendor());
        }
        else if(t1.equals("Model"))
        {
        	keyword.setText(componentsVO.getModel());
        }
        else if(t1.equals("Type"))
        {
        	keyword.setText(componentsVO.getType());
        }
        else if(t1.equals("Size"))
        {
        	keyword.setText(componentsVO.getSize());
        }
	}
}
