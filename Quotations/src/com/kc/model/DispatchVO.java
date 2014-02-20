package com.kc.model;

public class DispatchVO {
	private int id;
	private String referenceNo;
	private String invoiceNo;
	private String invoiceDate;
	private String billingName;
	private String shippingTo;
	private String companyName;
	private String transporter;
	private String dispatchDate;
	private String trackingNo;
	private int noOfItems;
	private String freightMode;
	private Double freightAmount;
	private String customerEmail;
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
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getBillingName() {
		return billingName;
	}
	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}
	public String getShippingTo() {
		return shippingTo;
	}
	public void setShippingTo(String shippingTo) {
		this.shippingTo = shippingTo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTransporter() {
		return transporter;
	}
	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}
	public String getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public int getNoOfItems() {
		return noOfItems;
	}
	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}
	public String getFreightMode() {
		return freightMode;
	}
	public void setFreightMode(String freightMode) {
		this.freightMode = freightMode;
	}
	public Double getFreightAmount() {
		return freightAmount;
	}
	public void setFreightAmount(Double freightAmount) {
		this.freightAmount = freightAmount;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
}
