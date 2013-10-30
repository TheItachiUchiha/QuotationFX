package com.kc.model;



public class ComponentsVO {

	private int id;
	
	private String componentName;
	
	private String componentCategory;
	
	private String subCategory;
	
	private String vender;
	
	private String model;
	
	private String type;
	
	private String size;
	
	private double costPrice;
	
	private double dealerPrice;
	
	private double endUserPrice;

	
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

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
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

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public double getDealerPrice() {
		return dealerPrice;
	}

	public void setDealerPrice(double dealerPrice) {
		this.dealerPrice = dealerPrice;
	}

	public double getEndUserPrice() {
		return endUserPrice;
	}

	public void setEndUserPrice(double endUserPrice) {
		this.endUserPrice = endUserPrice;
	}
	
	public String toString()
	{
		return this.componentName;
	}
	
	
}
