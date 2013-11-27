package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.controller.ProductsCreateController.EditingCell;
import com.kc.dao.ProductsDAO;
import com.kc.model.ComponentsVO;
import com.kc.model.ProductsVO;
import com.kc.util.Validation;

@SuppressWarnings("unchecked")
public class ProductsModifyController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(ProductsModifyController.class);
	private ObservableList<ProductsVO> productsList;
	private ObservableList<ComponentsVO> componentList;
	private ProductsDAO productsDAO;
	public static Stage stage;
	private Validation validate;
	
	@FXML
	private GridPane selectGrid;
	@FXML
	private GridPane showGrid;
	@FXML
	private VBox tableVBox;
	@FXML
	private HBox modifyHBox;

	@FXML
	private Button addComponent;
	@FXML
	private ComboBox<String> productCategory;
	@FXML
	private ComboBox<ProductsVO> productName;
	@FXML
	private ComboBox<String> productSubcategory;

	@FXML
	private TextField productCategoryTextField;
	@FXML
	private TextField productNameTextField;
	@FXML
	private TextField productSubCategoryTextField;
	@FXML
	private TextField productCodeTextField;

	@FXML
	private TableView<ComponentsVO> componentTable;
	@FXML
	private TableColumn<ComponentsVO, String> category;
	@FXML
	private TableColumn<ComponentsVO, String> componentName;
	@FXML
	private TableColumn<ComponentsVO, String> costPrice;
	@FXML
	private TableColumn<ComponentsVO, String> dealerPrice;
	@FXML
	private TableColumn<ComponentsVO, String> endUserPrice;
	@FXML
	private TableColumn<ComponentsVO, String> model;
	@FXML
	private TableColumn<ComponentsVO, String> size;
	@FXML
	private TableColumn<ComponentsVO, String> subcategory;
	@FXML
	private TableColumn<ComponentsVO, String> type;
	@FXML
	private TableColumn<ComponentsVO, String> vendor;
	@FXML
	private TableColumn action;
    @FXML 
    private TableColumn quantity;
	
	 @FXML private Label message;

	
	public ProductsModifyController()
	{
		productsDAO = new ProductsDAO();
		validate = new Validation();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ObservableList<String> tempProductCategoryList = FXCollections.observableArrayList();
		final ObservableList<String> tempProductSubCategoryList = FXCollections.observableArrayList();
		final ObservableList<ProductsVO> tempProductList = FXCollections.observableArrayList();
		try{
			productsList = productsDAO.getProducts();
			productCategory.setItems(tempProductCategoryList);
			productSubcategory.setItems(tempProductSubCategoryList);
			productName.setItems(tempProductList);
			componentTable.setEditable(true);
			
			
			for(ProductsVO productsVO: productsList)
			{
				if(!tempProductCategoryList.contains(productsVO.getProductCategory()))
				{
					tempProductCategoryList.add(productsVO.getProductCategory());
				}
			}
			
			productCategory.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,String oldValue, String newValue) {
					if(null!=newValue)
					{
						showGrid.setVisible(false);
						tableVBox.setVisible(false);
						modifyHBox.setVisible(false);
						message.setVisible(false);
						tempProductSubCategoryList.clear();
						for(ProductsVO productsVO: productsList)
						{
							if(productsVO.getProductCategory().equals(newValue))
							{
								if(!tempProductSubCategoryList.contains(productsVO.getProductSubCategory()))
								{
									tempProductSubCategoryList.add(productsVO.getProductSubCategory());
								}
							}
						}
					}
				}
			});
			
			
			productSubcategory.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(null!=newValue)
					{
						showGrid.setVisible(false);
						tableVBox.setVisible(false);
						modifyHBox.setVisible(false);
						message.setVisible(false);
						tempProductList.clear();
						for(ProductsVO productsVO: productsList)
						{
							if(productsVO.getProductSubCategory().equals(newValue))
							{
								if(!tempProductList.contains(productsVO))
								{
									tempProductList.add(productsVO);
								}
							}
						}
					}
				}
			});
			
			productName.valueProperty().addListener(new ChangeListener<ProductsVO>() {

				@Override
				public void changed(
						ObservableValue<? extends ProductsVO> observable, ProductsVO oldValue, ProductsVO newValue) {
					try{
						if(null!=newValue)
						{
							showGrid.setVisible(true);
							tableVBox.setVisible(true);
							modifyHBox.setVisible(true);
							message.setVisible(false);
							productCategoryTextField.setText(productCategory.getValue());
							productSubCategoryTextField.setText(productSubcategory.getValue());
							productNameTextField.setText(newValue.getProductName());
							productCodeTextField.setText(newValue.getProductCode());
							componentList = productsDAO.getComponentsForProduct(newValue.getId());
							fillComponentTable();
						}
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
						LOG.error(e.getMessage());
					}
				}
			});
			
			final Callback<TableColumn<ComponentsVO, Integer>, TableCell<ComponentsVO, Integer>> cellFactory = new Callback<TableColumn<ComponentsVO, Integer>, TableCell<ComponentsVO, Integer>>() {
				public TableCell<ComponentsVO, Integer> call(TableColumn<ComponentsVO, Integer> p) {
					return new EditingCell();
				}
			};
			
			
			quantity.setCellValueFactory(new Callback<CellDataFeatures<ComponentsVO, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(CellDataFeatures<ComponentsVO, Integer> cellData) {
                  ComponentsVO item = cellData.getValue();
                  if (item == null) {
                    return null;
                  } else {
                	  /*ObservableMap<String,ItemTypeVO> itemTypesMap = FXCollections.observableHashMap();
		 		    	itemTypesMap = item.getListType();
		 		    */
		 		    	return new ReadOnlyObjectWrapper<Integer>(item.getQuantity());
		 		    
                  }
                }
              });
			quantity.setCellFactory(cellFactory);
	 		
	 		quantity.setOnEditCommit(
	 				new EventHandler<TableColumn.CellEditEvent<ComponentsVO, Integer>>() {
	 				public void handle(TableColumn.CellEditEvent<ComponentsVO, Integer> t) {
	 				((ComponentsVO)t.getTableView().getItems().get(
	 				t.getTablePosition().getRow())).setQuantity(t.getNewValue());
	 				}
	 				});
			
			
			
			
			action.setSortable(false);
	        
	        action.setCellValueFactory(
	                new Callback<TableColumn.CellDataFeatures<ComponentsVO, Boolean>,ObservableValue<Boolean>>() {
	 
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
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
	public void addComponent()
	{
		LOG.info("Enter : addComponent");
		try{
			final FXMLLoader menuLoader = new FXMLLoader(this.getClass()
					.getResource("../view/product-component-add.fxml"));
			BorderPane componentadd;
			Stage componentAddStage = new Stage();
			stage = componentAddStage;
			componentadd = (BorderPane) menuLoader.load();
			Scene componentAddscene = new Scene(componentadd);
			componentAddStage.setResizable(false);
			componentAddStage.setHeight(500);
			componentAddStage.setWidth(900);
			componentAddStage.initModality(Modality.WINDOW_MODAL);
			componentAddStage.initOwner(LoginController.primaryStage);
			componentAddStage.setScene(componentAddscene);
			componentAddStage.show();
			
			componentAddStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent paramT) {
					ObservableList<ComponentsVO> list = ((ProductComponentAddController)menuLoader.getController()).getAddedComponentList();
					componentName.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentName"));
					category.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentCategory"));
					subcategory.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("subCategory"));
					vendor.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("vendor"));
					model.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("model"));
					type.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("type"));
					size.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("size"));
					costPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("costPrice"));
					dealerPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("dealerPrice"));
					endUserPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("endUserPrice"));
					for(ComponentsVO componentsVO : list)
					{
						if(componentList.size()==0)
						{
							componentList.addAll(list);
						}
						else
						{
							for(ComponentsVO tempC : componentList )
							{
								if(tempC.getId() != componentsVO.getId())
								{
									componentList.add(componentsVO);
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
		LOG.info("Exit : addComponent");
	}
	public void fillComponentTable()
	{
		componentName.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentName"));
		category.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentCategory"));
		subcategory.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("subCategory"));
		vendor.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("vendor"));
		model.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("model"));
		type.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("type"));
		size.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("size"));
		costPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("costPrice"));
		dealerPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("dealerPrice"));
		endUserPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("endUserPrice"));
		quantity.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Integer>("quantity"));
		
		componentTable.setItems(componentList);
	}
	
	public void modifyProduct()
	{
		LOG.info("Enter : saveProduct");
		try
		{
			if(componentTable.getItems().size()==0)
			{
				message.setText(CommonConstants.NO_PRODUCT_COMPONENT);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
		else
		{
			ProductsVO productsVO=productName.getValue();
			productsVO.setProductCategory(productCategoryTextField.getText());
			productsVO.setProductSubCategory(productSubCategoryTextField.getText());
			productsVO.setProductName(productNameTextField.getText());
			productsVO.setProductCode(productCodeTextField.getText());
			productsVO.setList(componentTable.getItems());
			productsDAO.modifyProducts(productsVO);
			message.setText(CommonConstants.PRODUCT_MODIFY_SUCCESS);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
			message.setVisible(true);
		}
		}
		catch (SQLException s) {
			s.printStackTrace();
			if (s.getErrorCode() == CommonConstants.UNIQUE_CONSTRAINT) {
				message.setText(CommonConstants.DUPLICATE_PRODUCT_CODE);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : saveProduct");
	}
	
	
	private class ButtonCell extends TableCell<ComponentsVO, Boolean> {
	       
		Image buttonDeleteImage = new Image(getClass().getResourceAsStream("../style/delete.png"));
		//Image buttonEditImage = new Image(getClass().getResourceAsStream("../style/edit.png"));
		final Button cellDeleteButton = new Button("", new ImageView(buttonDeleteImage));
		//final Button cellEditButton = new Button("", new ImageView(buttonEditImage));
       
         
        ButtonCell(){
            
        	
        	cellDeleteButton.getStyleClass().add("editDeleteButton");
        	cellDeleteButton.setTooltip(new Tooltip("Delete"));
        	//cellEditButton.getStyleClass().add("editDeleteButton");
        	//cellEditButton.setTooltip(new Tooltip("Edit"));
        	
        	cellDeleteButton.setOnAction(new EventHandler<ActionEvent>(){
 
                @Override
                public void handle(ActionEvent t) {
                   componentList.remove(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
                   fillComponentTable();
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
            });*/
        }
 
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
            	HBox box = new HBox();
            	box.getChildren().addAll(cellDeleteButton);
                setGraphic(box);
            }
        }
    }
	
	class EditingCell extends TableCell<ComponentsVO, Integer> {
		 
	      private TextField textField;
	     
	      public EditingCell() {}
	     
	      @Override
	      public void startEdit() {
	          super.startEdit();
	         
	          if (textField == null) {
	              createTextField();
	          }
	         
	          setGraphic(textField);
	          setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	          //textField.selectAll();
	          Platform.runLater(new Runnable() {
		            @Override
		            public void run() {
		                textField.requestFocus();
		                textField.selectAll();
		            }
		        });
	      }
	     
	      @Override
	      public void cancelEdit() {
	          super.cancelEdit();
	         
	          setText(String.valueOf(getItem()));
	          setContentDisplay(ContentDisplay.TEXT_ONLY);
	      }
	 
	      @Override
	      public void updateItem(Integer item, boolean empty) {
	          super.updateItem(item, empty);
	         
	          if (empty) {
	              setText(null);
	              setGraphic(null);
	          } else {
	              if (isEditing()) {
	                  if (textField != null) {
	                      textField.setText(getString());
	                  }
	                  setGraphic(textField);
	                  setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	              } else {
	                  setText(getString());
	                  setContentDisplay(ContentDisplay.TEXT_ONLY);
	              }
	          }
	      }
	
	      private void createTextField() {
	          textField = new TextField();
	          validate.allowDigit(textField);
	          //textField.setText(getString());
	          //textField.setText("0");
	          textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
	          
	         /* textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

	        	    @Override
	        	    public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
	        	        if (!arg2) {
	        	            commitEdit(Integer.parseInt(textField.getText()));
	        	        }
	        	    }
	        	});*/
	          
	          textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
		            @Override
		            public void handle(KeyEvent t) {
		                if (t.getCode() == KeyCode.ENTER) {
		                	//For not allowing to commit when Enter is pressed with empty Textfield
		                	if (!validate.isEmpty(textField)){
			                	if (!validate.isWord(textField.getText())){
		                    commitEdit(Integer.parseInt(textField.getText()));
			                	}}
		                } else if (t.getCode() == KeyCode.ESCAPE) {
		                    cancelEdit();
		                } else if (t.getCode() == KeyCode.TAB) {
		                	//For not allowing to commit when TAB is pressed with empty Textfield
		                	if (!validate.isEmpty(textField)){
			                	if (!validate.isWord(textField.getText())){
		                    commitEdit(Integer.parseInt(textField.getText()));
			                	}}
		                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
		                    if (nextColumn != null) {
		                        getTableView().edit(getTableRow().getIndex(), nextColumn);
		                    }
		                }
		            }
		        });
	          textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
		            @Override
		            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		                if (!newValue && textField != null) {
		                	//For not allowing to commit when mouse is pressed with empty Textfield
		                	if (!validate.isEmpty(textField)){
		                	if (!validate.isWord(textField.getText())){
		                		  commitEdit(Integer.parseInt(textField.getText()));
		                	}
		                	}
		                	
		                			                  
		                }
		            }
		        });
	      }
	     
	      private String getString() {
	          return getItem() == null ? "" : getItem().toString();
	      }
	      
	      private TableColumn<ComponentsVO, ?> getNextColumn(boolean forward) {
		        List<TableColumn<ComponentsVO, ?>> columns = new ArrayList<>();
		        for (TableColumn<ComponentsVO, ?> column : getTableView().getColumns()) {
		            columns.addAll(getLeaves(column));
		        }
		        //There is no other column that supports editing.
		        if (columns.size() < 2) {
		            return null;
		        }
		        int currentIndex = columns.indexOf(getTableColumn());
		        int nextIndex = currentIndex;
		        if (forward) {
		            nextIndex++;
		            if (nextIndex > columns.size() - 1) {
		                nextIndex = 0;
		            }
		        } else {
		            nextIndex--;
		            if (nextIndex < 0) {
		                nextIndex = columns.size() - 1;
		            }
		        }
		        return columns.get(nextIndex);
		    }
	      
	      	private List<TableColumn<ComponentsVO, ?>> getLeaves(TableColumn<ComponentsVO, ?> root) {
		        List<TableColumn<ComponentsVO, ?>> columns = new ArrayList<>();
		        if (root.getColumns().isEmpty()) {
		            //We only want the leaves that are editable.
		            if (root.isEditable()) {
		                columns.add(root);
		            }
		            return columns;
		        } else {
		            for (TableColumn<ComponentsVO, ?> column : root.getColumns()) {
		                columns.addAll(getLeaves(column));
		            }
		            return columns;
		        }
	      	}
	  }

}
