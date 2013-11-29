package com.kc.model;

public class EnquiryVO {
	private int id;
	private String referedBy;
	private String customerrequirements;
	private String purchasePeriod;
	private String customerDocument;
	private String emailMessage;
	private String priceEstimation;
	private String QuotationPreparation;
	private String emailSent;
	private String date;
	private int customerId;
	private int productId;
	
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
	public String getCustomerrequirements() {
		return customerrequirements;
	}
	public void setCustomerrequirements(String customerrequirements) {
		this.customerrequirements = customerrequirements;
	}
	public String getPurchasePeriod() {
		return purchasePeriod;
	}
	public void setPurchasePeriod(String purchasePeriod) {
		this.purchasePeriod = purchasePeriod;
	}
	public String getCustomerDocument() {
		return customerDocument;
	}
	public void setCustomerDocument(String customerDocument) {
		this.customerDocument = customerDocument;
	}
	public String getEmailMessage() {
		return emailMessage;
	}
	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}
	public String getPriceEstimation() {
		return priceEstimation;
	}
	public void setPriceEstimation(String priceEstimation) {
		this.priceEstimation = priceEstimation;
	}
	public String getQuotationPreparation() {
		return QuotationPreparation;
	}
	public void setQuotationPreparation(String quotationPreparation) {
		QuotationPreparation = quotationPreparation;
	}
	public String getEmailSent() {
		return emailSent;
	}
	public void setEmailSent(String emailSent) {
		this.emailSent = emailSent;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
}
