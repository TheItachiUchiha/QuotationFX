package com.kc.model;



public class ComponentsVO {

	private int id;
	
	private String componentName;
	
	private String componentCategory;
	
	private String subCategory;
	
	private String vendor;
	
	private String model;
	
	private String type;
	
	private String size;
	
	private String costPrice;
	
	private String dealerPrice;
	
	private String endUserPrice;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getComponentCategory() {
		return componentCategory;
	}

	public void setComponentCategory(String componentCategory) {
		this.componentCategory = componentCategory;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getDealerPrice() {
		return dealerPrice;
	}

	public void setDealerPrice(String dealerPrice) {
		this.dealerPrice = dealerPrice;
	}

	public String getEndUserPrice() {
		return endUserPrice;
	}

	public void setEndUserPrice(String endUserPrice) {
		this.endUserPrice = endUserPrice;
	}
	
	public String toString()
	{
		return this.componentName;
	}
	
	
}
