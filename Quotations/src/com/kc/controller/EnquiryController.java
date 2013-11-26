package com.kc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import com.kc.dao.ProductsDAO;
import com.kc.model.ProductsVO;

public class EnquiryController implements Initializable {
	
	private ObservableList<ProductsVO> productsList;
	private ProductsDAO productsDAO;
	private ProductsVO productsVO;
	public EnquiryController() {
		productsDAO = new ProductsDAO();
		productsVO = new ProductsVO();
	}
	
	@FXML
	private RadioButton standard;
	@FXML
	private RadioButton custom;
	@FXML
	private GridPane standardGrid;
	@FXML
	private GridPane customGrid;
	@FXML
	private ComboBox<String> productCategory;
	@FXML
	private ComboBox<String> subcategory;
	@FXML
	private ComboBox<ProductsVO> productName;
	@FXML
	private TextArea customerRequirement;
	@FXML
	private TextField referedBy;
	@FXML
	private ComboBox<String> customerType;
	@FXML
	private TextField customerName;
	@FXML
	private TextField companyName;
	@FXML
	private TextField tinNumber;
	@FXML
	private TextField emailId;
	@FXML
	private TextArea address;
	@FXML
	private TextField city;
	@FXML
	private TextField state;
	@FXML
	private TextField contactNumber;
	@FXML
	private TextField purchasePeriod;
	@FXML
	private RadioButton yes;
	@FXML
	private RadioButton no;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		standard.setSelected(true);
		if(standard.isSelected())
		{
			customGrid.setVisible(false);
			standardGrid.setVisible(true);
		}
		else if(custom.isSelected())
		{
			standardGrid.setVisible(false);
			customGrid.setVisible(true);
		}
		
		try {

			productsList = productsDAO.getProducts();
			final ObservableList<ProductsVO> tempProductsList = FXCollections
					.observableArrayList();

			ObservableList<String> tempCategoryList = FXCollections
					.observableArrayList();
			final ObservableList<String> tempSubCategoryList = FXCollections
					.observableArrayList();
			final ObservableList<ProductsVO> tempProductList = FXCollections
					.observableArrayList();
			subcategory.setItems(tempSubCategoryList);
			productName.setItems(tempProductList);

			for (ProductsVO productsVO : productsList) {
				if (!tempCategoryList.contains(productsVO
						.getProductCategory())) {
					tempCategoryList.add(productsVO.getProductCategory());
				}
			}

			productCategory.setItems(tempCategoryList);

			productCategory.valueProperty().addListener(
					new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {
							try {
								productsList.clear();

								productsList = productsDAO.getProducts();

								tempSubCategoryList.clear();

								tempProductsList.clear();
								for (ProductsVO productsVO : productsList) {
									if (productsVO.getProductCategory()
											.equals(t1)) {
										if (!tempSubCategoryList
												.contains(productsVO
														.getProductSubCategory())) {
											tempSubCategoryList
													.add(productsVO
															.getProductSubCategory());
											tempProductsList
													.add(productsVO);
										}
									}
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

			subcategory.valueProperty().addListener(
					new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue ov, String t,
								String t1) {

							tempProductList.clear();

							for (ProductsVO productsVO : tempProductsList) {
								if (productsVO.getProductSubCategory().equals(t1)) {
									tempProductList.add(productsVO);
								}
							}
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
