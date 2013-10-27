package com.kc.model;
import java.util.Date;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.dao.HomeDAO;

public class PriceEstimationVO {
	
	private static final Logger LOG = LogManager.getLogger(PriceEstimationVO.class);
	
	private String productCategory;
	
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getEndUser() {
		return endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getDealerEndUser() {
		return dealerEndUser;
	}

	public void setDealerEndUser(String dealerEndUser) {
		this.dealerEndUser = dealerEndUser;
	}

	private String subCategory;
	
	private String productName;
	
	private String referenceNo;
	
	private Date date;
	
	private String dealer;
	
	private String endUser;
	
	private String componentName;
	
	private String vender;
	
	private String model;
	
	private String type;
	
	private String size;
	
	private String quantity;
	
	private String costPrice;
	
	private String dealerEndUser;

}
