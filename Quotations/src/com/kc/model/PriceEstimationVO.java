package com.kc.model;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
public class PriceEstimationVO {
	
	private static final Logger LOG = LogManager.getLogger(PriceEstimationVO.class);
	
	private String productCategory;
	
	private String subCategory;
	
	private String productName;
	
	private String referenceNo;
	
	private Date date;
	
	private String customerType;
	
	List<ComponentsVO> componentList;
	
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

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the componentList
	 */
	public List<ComponentsVO> getComponentList() {
		return componentList;
	}

	/**
	 * @param componentList the componentList to set
	 */
	public void setComponentList(List<ComponentsVO> componentList) {
		this.componentList = componentList;
	}
	
	
	
}
			