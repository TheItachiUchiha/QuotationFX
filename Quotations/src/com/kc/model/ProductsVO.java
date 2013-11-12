package com.kc.model;

import javafx.collections.ObservableList;


public class ProductsVO {
	
	private String productName;
	
	private String productCategory;
	
	private String productSubCategory;
	
	private String productCode;
	
	private ObservableList<ComponentsVO> list;

	public String getProductSubCategory() {
		return productSubCategory;
	}

	public void setProductSubCategory(String productSubCategory) {
		this.productSubCategory = productSubCategory;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public ObservableList<ComponentsVO> getList() {
		return list;
	}

	public void setList(ObservableList<ComponentsVO> list) {
		this.list = list;
	}
}
