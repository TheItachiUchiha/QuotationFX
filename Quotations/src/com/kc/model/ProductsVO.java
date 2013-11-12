package com.kc.model;

import javafx.collections.ObservableList;


public class ProductsVO {
	
	private int id;
	
	private String productName;
	
	private String productCategory;
	
	private String productSubCategory;
	
	private String productCode;
	
	private ObservableList<ComponentsVO> list;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	public String toString()
	{
		return this.productName;
	}
}
