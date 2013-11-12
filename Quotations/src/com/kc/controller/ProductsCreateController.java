package com.kc.controller;

import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.kc.constant.CommonConstants;
import com.kc.dao.ProductsDAO;
import com.kc.model.ComponentsVO;
import com.kc.model.ProductsVO;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ProductsCreateController {
	private static final Logger LOG = LogManager.getLogger(ProductsCreateController.class);
	
	public static Stage stage;
	
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
    @FXML
    private TextField productCategory;
    @FXML
    private TextField productSubCategory;
    @FXML
    private TextField productName;
    @FXML
    private TextField productCode;
    @FXML
    private Label message;
    
    public TableView<ComponentsVO> getComponentTable() {
		return componentTable;
	}
    
	ProductsDAO productsDAO=new ProductsDAO();
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
					ObservableList<ComponentsVO> list = ((ProductComponentAddController)menuLoader.getController()).getComponentTable().getItems();
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
					componentTable.setItems(list);
				}
					
			});		
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : addComponent");
	}
	public void saveProduct()
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
			ProductsVO productsVO=new ProductsVO();
			productsVO.setProductCategory(productCategory.getText());
			productsVO.setProductSubCategory(productSubCategory.getText());
			productsVO.setProductName(productName.getText());
			productsVO.setProductCode(productCode.getText());
			productsVO.setList(componentTable.getItems());
			productsDAO.saveProducts(productsVO);
			message.setText(CommonConstants.PRODUCT_ADD_SUCCESS);
			message.getStyleClass().remove("failure");
			message.getStyleClass().add("success");
			message.setVisible(true);
		}
		}
		catch (SQLException s) {
			if (s.getErrorCode() == CommonConstants.UNIQUE_CONSTRAINT) {
				message.setText(CommonConstants.DUPLICATE_PRODUCT_CODE);
				message.getStyleClass().remove("success");
				message.getStyleClass().add("failure");
				message.setVisible(true);
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : saveProduct");
	}
}