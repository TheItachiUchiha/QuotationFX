package com.kc.model;

import javafx.collections.ObservableList;

public class EnquiryVO {
	private int id;
	private String referedBy;
	private String customerrequirements;
	private String purchasePeriod;
	private String customerDocument;
	private String emailMessage;
	private String priceEstimation;
	private String QuotationPreparation;
	private String sales;
	private String emailSent;
	private String date;
	private int customerId;
	private String productName;
	private String flag;
	private String refNumber;
	private int productId;
	private double margin;
	private String peDate;
	private String qpDate;
	private String mailSentDate;
	private String salesDate;
	private int reminderSent;
	private double totalRevenue;
	private String enquiryCustomerType;
	private ObservableList<ComponentsVO> list;
	
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public ObservableList<ComponentsVO> getList() {
		return list;
	}
	public void setList(ObservableList<ComponentsVO> list) {
		this.list = list;
	}
	public double getMargin() {
		return margin;
	}
	public void setMargin(double margin) {
		this.margin = margin;
	}
	public String getPeDate() {
		return peDate;
	}
	public void setPeDate(String peDate) {
		this.peDate = peDate;
	}
	public String getQpDate() {
		return qpDate;
	}
	public void setQpDate(String qpDate) {
		this.qpDate = qpDate;
	}
	public String getMailSentDate() {
		return mailSentDate;
	}
	public void setMailSentDate(String mailSentDate) {
		this.mailSentDate = mailSentDate;
	}
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	public int getReminderSent() {
		return reminderSent;
	}
	public void setReminderSent(int reminderSent) {
		this.reminderSent = reminderSent;
	}
	public double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public String getEnquiryCustomerType() {
		return enquiryCustomerType;
	}
	public void setEnquiryCustomerType(String enquiryCustomerType) {
		this.enquiryCustomerType = enquiryCustomerType;
	}
	
}
