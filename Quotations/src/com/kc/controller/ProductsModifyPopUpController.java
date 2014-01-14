package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ProductsDAO;
import com.kc.model.ComponentsVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ProductsVO;
import com.kc.util.EditingCell;
import com.kc.util.Validation;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class ProductsModifyPopUpController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(ProductsModifyController.class);
	private ProductsVO productsVO;
	private ObservableList<ComponentsVO> componentList;
	private ProductsDAO productsDAO;
	public static Stage stage;
	private Validation validate;

	public ProductsModifyPopUpController()
	{
		productsDAO = new ProductsDAO();
		validate = new Validation();
	}
    @FXML
    private TableColumn action;

    @FXML
    private TableColumn<ComponentsVO, String> category;

    @FXML
    private TableColumn<ComponentsVO, String> componentName;

    @FXML
    private TableView<ComponentsVO> componentTable;

    @FXML
    private TableColumn<ComponentsVO, Double> costPrice;

    @FXML
    private TableColumn<ComponentsVO, Double> dealerPrice;

    @FXML
    private TableColumn<ComponentsVO, Double> endUserPrice;

    @FXML
    private Label message;

    @FXML
    private TableColumn<ComponentsVO, String> model;

    @FXML
    private HBox modifyHBox;

    @FXML
    private TextField productCategoryTextField;

    @FXML
    private TextField productCodeTextField;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField productSubCategoryTextField;

    @FXML
    private TableColumn<ComponentsVO, Integer> quantity;

    @FXML
    private GridPane showGrid;

    @FXML
    private TableColumn<ComponentsVO, String> size;

    @FXML
    private TableColumn<ComponentsVO, String> subcategory;

    @FXML
    private VBox tableVBox;

    @FXML
    private TableColumn<ComponentsVO, String> type;

    @FXML
    private TableColumn<ComponentsVO, String> vendor;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		try{
			componentTable.setEditable(true);
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

    public void addComponent() {
    	LOG.info("Enter : addComponent");
		try{
			final FXMLLoader menuLoader = new FXMLLoader(this.getClass()
					.getResource("/com/kc/view/product-component-add.fxml"));
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
					costPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Double>("totalCostPrice"));
					dealerPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Double>("totalDealerPrice"));
					endUserPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Double>("totalEndUserPrice"));
					for(ComponentsVO componentsVO : list)
					{
						if(componentList.size()==0)
						{
							componentList.addAll(list);
						}
						else
						{
							List<Integer> ids = new ArrayList<Integer>();
							for(ComponentsVO componentsVO2 : componentList)
							{
								ids.add(componentsVO2.getId());
							}
							if(!ids.contains(componentsVO.getId()))
							{
								componentList.add(componentsVO);
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

    public void modifyProduct() {
    	LOG.info("Enter : modifyProduct");
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
		LOG.info("Exit : modifyProduct");
    } 
    
    public void fillTextFieldValues(ProductsVO productsVO)
    {
    	try{
	    	this.productsVO = productsVO;
	    	this.componentList = productsDAO.getComponentsForProduct(productsVO.getId());
	    	componentTable.setItems(this.componentList);
	    	fillComponentTable();
	    	productNameTextField.setText(productsVO.getProductName());
	    	productCategoryTextField.setText(productsVO.getProductCategory());
	    	productSubCategoryTextField.setText(productsVO.getProductSubCategory());
	    	productCodeTextField.setText(productsVO.getProductCode());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		LOG.error(e.getMessage());
    	}
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
		costPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Double>("totalCostPrice"));
		dealerPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Double>("totalDealerPrice"));
		endUserPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Double>("totalEndUserPrice"));
		quantity.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Integer>("quantity"));
		componentTable.setItems(componentList);
	}
    
    private class ButtonCell extends TableCell<ComponentsVO, Boolean> {
	       
		Image buttonDeleteImage = new Image(getClass().getResourceAsStream("/com/kc/style/delete.png"));
		//Image buttonEditImage = new Image(getClass().getResourceAsStream("../style/edit.png"));
		final Button cellDeleteButton = new Button("", new ImageView(buttonDeleteImage));
		//final Button cellEditButton = new Button("", new ImageView(buttonEditImage));
       
         
        ButtonCell(){
            
        	
        	cellDeleteButton.getStyleClass().add("editDeleteButton");
        	cellDeleteButton.setTooltip(new Tooltip("Delete"));
        	
        	cellDeleteButton.setOnAction(new EventHandler<ActionEvent>(){
 
                @Override
                public void handle(ActionEvent t) {
                   componentList.remove(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
                   fillComponentTable();
                }
            });
        }
 
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
            	HBox box = new HBox();
            	box.setAlignment(Pos.CENTER);
            	box.getChildren().addAll(cellDeleteButton);
                setGraphic(box);
            }
        }
    }
}