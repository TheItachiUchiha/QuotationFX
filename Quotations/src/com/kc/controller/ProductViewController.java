package com.kc.controller;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ProductsDAO;
import com.kc.model.ProductsVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

public class ProductViewController implements Initializable{
	private static final Logger LOG = LogManager.getLogger(ProductViewController.class);
	private ObservableList<ProductsVO> productsList;
	private ObservableList<String> searchByList;
	private ProductsDAO productsDAO;
	
	public ProductViewController(){
		productsDAO = new ProductsDAO();
	}
	@FXML
	private AutoCompleteTextField<String> keyword;
	@FXML
	private ComboBox<String> combo;
	@FXML
	private Button go;
	@FXML
    private TableView<ProductsVO> productsTable;
	@FXML private TableColumn<ProductsVO, String> productName;
    @FXML private TableColumn<ProductsVO, String> productCategory;
    @FXML private TableColumn<ProductsVO, String> productSubCategory;
    @FXML private TableColumn<ProductsVO, String> productCode;
    @FXML private TableColumn action;
    @FXML private Label message;
	


	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		try{
			LOG.info("Enter : initialize");
			productsList = productsDAO.getProducts();
			productsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			searchByList = FXCollections.observableArrayList();
			searchByList.add("Product Category");
			searchByList.add("Sub Category");
			searchByList.add("Product Name");
			searchByList.add("Product Code");
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
	                new Callback<TableColumn.CellDataFeatures<ProductsVO, Boolean>,
	                ObservableValue<Boolean>>() {
	 
	            @Override
	            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ProductsVO, Boolean> p) {
	                return new SimpleBooleanProperty(p.getValue() != null);
	            }
	        });
	 
	        action.setCellFactory(
	                new Callback<TableColumn<ProductsVO, Boolean>, TableCell<ProductsVO, Boolean>>() {
	 
	            @Override
	            public TableCell<ProductsVO, Boolean> call(TableColumn<ProductsVO, Boolean> p) {
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
			productsList = productsDAO.getProducts();
			final ObservableList<String> tempList = FXCollections.observableArrayList(); 
			if(t1.equals("Product Category"))
	        {
	        	for(ProductsVO productsVO : productsList)
	        	{
	        		if(!tempList.contains(productsVO.getProductCategory()))
	        		{
	        			tempList.add(productsVO.getProductCategory());
	        		}
	        	}
	        }
	        else if(t1.equals("Sub Category"))
	        {
	        	for(ProductsVO productsVO : productsList)
	        	{
	        		if(!tempList.contains(productsVO.getProductSubCategory()))
	        		{
	        			tempList.add(productsVO.getProductSubCategory());
	        		}
	        	}
	        }
	        else if(t1.equals("Product Name"))
	        {
	        	for(ProductsVO productsVO : productsList)
	        	{
	        		if(!tempList.contains(productsVO.getProductName()))
	        		{
	        			tempList.add(productsVO.getProductName());
	        		}
	        	}
	        }
	        else if(t1.equals("Product Code"))
	        {
	        	for(ProductsVO productsVO : productsList)
	        	{
	        		if(!tempList.contains(productsVO.getProductCode()))
	        		{
	        			tempList.add(productsVO.getProductCode());
	        		}
	        	}
	        }
			keyword.setItems(tempList);
			
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : fillAutoCompleteFromComboBox");
	}
	
	private void fillTableFromData()
	{
		LOG.info("Enter : fillTableFromData");
		try{
			
			ObservableList<ProductsVO> tempList =  FXCollections.observableArrayList();
			String tempString = keyword.getText();
			if(combo.getSelectionModel().getSelectedItem().equals("Product Category"))
			{
				for(ProductsVO productsVO : productsList)
				{
					if(productsVO.getProductCategory().equalsIgnoreCase(tempString))
					{
						tempList.add(productsVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Sub Category"))
			{
				for(ProductsVO productsVO : productsList)
				{
					if(productsVO.getProductSubCategory().equalsIgnoreCase(tempString))
					{
						tempList.add(productsVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Product Name"))
			{
				for(ProductsVO productsVO : productsList)
				{
					if(productsVO.getProductName().equalsIgnoreCase(tempString))
					{
						tempList.add(productsVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Product Code"))
			{
				for(ProductsVO productsVO : productsList)
				{
					if(productsVO.getProductCode().equalsIgnoreCase(tempString))
					{
						tempList.add(productsVO);
					}
				}
			}
			productName.setCellValueFactory(new PropertyValueFactory<ProductsVO, String>("productName"));
			productCategory.setCellValueFactory(new PropertyValueFactory<ProductsVO, String>("productCategory"));
			productSubCategory.setCellValueFactory(new PropertyValueFactory<ProductsVO, String>("productSubCategory"));
			productName.setCellValueFactory(new PropertyValueFactory<ProductsVO, String>("productName"));
			productCode.setCellValueFactory(new PropertyValueFactory<ProductsVO, String>("productCode"));
			productsTable.setItems(tempList);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : fillTableFromData");
	}
	public void deleteProducts(ProductsVO productsVO)
	{
		LOG.info("Enter : deleteProducts");
		try{
				DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
					    "Do you want to delete selected product(s)", "Confirm", "Delete Product", DialogOptions.OK_CANCEL);
				if(response.equals(DialogResponse.OK))
				{
					productsDAO.deleteProducts(productsVO);
					message.setText(CommonConstants.PRODUCT_DELETE_SUCCESS);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
					fillAutoCompleteFromComboBox(combo.getSelectionModel().getSelectedItem());
					fillTableFromData();
					
				}
		}
		catch (Exception e) {
			message.setText(CommonConstants.FAILURE);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			message.setVisible(true);
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : deleteProducts");
	}

	private class ButtonCell extends TableCell<ProductsVO, Boolean> {
       
		Image buttonDeleteImage = new Image(getClass().getResourceAsStream("../style/delete.png"));
		Image buttonEditImage = new Image(getClass().getResourceAsStream("../style/edit.png"));
		final Button cellDeleteButton = new Button("", new ImageView(buttonDeleteImage));
		final Button cellEditButton = new Button("", new ImageView(buttonEditImage));
       
         
        ButtonCell(){
            
        	
        	cellDeleteButton.getStyleClass().add("editDeleteButton");
        	cellDeleteButton.setTooltip(new Tooltip("Delete"));
        	cellEditButton.getStyleClass().add("editDeleteButton");
        	cellEditButton.setTooltip(new Tooltip("Edit"));
        	
        	cellDeleteButton.setOnAction(new EventHandler<ActionEvent>(){
 
                @Override
                public void handle(ActionEvent t) {
                    deleteProducts(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
                }
            });
        	
        	/*cellEditButton.setOnAction(new EventHandler<ActionEvent>(){
        		 
                @Override
                public void handle(ActionEvent t) {
                	LOG.info("Enter : handle");
                	try {FXMLLoader menuLoader = new FXMLLoader(this.getClass()
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
										for (ProductsVO productsVO : productsList) {
											if (productsVO.getId() == ButtonCell.this.getTableView()
													.getItems()
													.get(ButtonCell.this.getIndex())
													.getId()) {
												updateAutoField(productsVO, combo.getSelectionModel().getSelectedItem());
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
            });*/
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
	/*private void updateAutoField(ProductsVO productsVO, String t1) {
		if(t1.equals("Product Category"))
        {
        	keyword.setText(productsVO.getProductCategory());
        }
        else if(t1.equals("Sub Category"))
        {
        	keyword.setText(productsVO.getProductSubCategory());
        }
        else if(t1.equals("Product Name"))
        {
        	keyword.setText(productsVO.getProductName());
        }
        else if(t1.equals("Product Code"))
        {
        	keyword.setText(productsVO.getProductCode());
        }
	}*/
}
