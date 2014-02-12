package com.kc.model;

public class ComplaintVO {
	
	private int id;
	private String referenceNo;
	private String productName;
	private String customerName;
	private String customerCity;
	private String dateOfComplaint;
	private int ServiceCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getDateOfComplaint() {
		return dateOfComplaint;
	}
	public void setDateOfComplaint(String dateOfComplaint) {
		this.dateOfComplaint = dateOfComplaint;
	}
	public int getServiceCount() {
		return ServiceCount;
	}
	public void setServiceCount(int serviceCount) {
		ServiceCount = serviceCount;
	}
	
}
