package com.kc.model;


public class UsersVO {
	
	private int id;
	
	private String name;
	
	private String designation;

	private String mobileNumber;

	private String username;

	private String password;

	private String quotation;
	
	private String enquiry;
	
	private String productDispatch;
	
	private String service;
	
	private String priceEstimation;

	private String salesOrderManagement;
	
	private String statusReminder;
	
	private String report;
	
	private String view;
	
	private String edit;
	
	private String delete;

	private String userType;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getQuotation() {
		return quotation;
	}

	public void setQuotation(String quotation) {
		this.quotation = quotation;
	}

	public String getPriceEstimation() {
		return priceEstimation;
	}

	public void setPriceEstimation(String priceEstimation) {
		this.priceEstimation = priceEstimation;
	}

	public String getSalesOrderManagement() {
		return salesOrderManagement;
	}

	public void setSalesOrderManagement(String salesOrderManagement) {
		this.salesOrderManagement = salesOrderManagement;
	}

	public String getStatusReminder() {
		return statusReminder;
	}

	public void setStatusReminder(String statusReminder) {
		this.statusReminder = statusReminder;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}
	
	public String toString()
	{
		return this.name;
	}

	public String getEnquiry() {
		return enquiry;
	}

	public void setEnquiry(String enquiry) {
		this.enquiry = enquiry;
	}

	public String getProductDispatch() {
		return productDispatch;
	}

	public void setProductDispatch(String productDispatch) {
		this.productDispatch = productDispatch;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
	
}
