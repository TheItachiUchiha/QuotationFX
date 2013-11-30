package com.kc.model;

public class EnquiryViewVO {
	private int id;
	private String referenceNo;
	private String referedBy;
	private String purchasePeriod;
	private String dateOfEnquiry;
	private String productName;
	private String customerName;
	private String companyName;
	private String city;
	private String state;
	private String enquiryType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReferedBy() {
		return referedBy;
	}
	public void setReferedBy(String referedBy) {
		this.referedBy = referedBy;
	}
	public String getPurchasePeriod() {
		return purchasePeriod;
	}
	public void setPurchasePeriod(String purchasePeriod) {
		this.purchasePeriod = purchasePeriod;
	}
	public String getDateOfEnquiry() {
		return dateOfEnquiry;
	}
	public void setDateOfEnquiry(String dateOfEnquiry) {
		this.dateOfEnquiry = dateOfEnquiry;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEnquiryType() {
		return enquiryType;
	}
	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}
	
}
