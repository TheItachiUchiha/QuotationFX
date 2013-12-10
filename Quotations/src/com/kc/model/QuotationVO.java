package com.kc.model;

public class QuotationVO {
	private int id;
	private int productId;
	private String quotationPath;
	private String wordPath;
	private String pdfPath;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getQuotationPath() {
		return quotationPath;
	}
	public void setQuotationPath(String quotationPath) {
		this.quotationPath = quotationPath;
	}
	public String getWordPath() {
		return wordPath;
	}
	public void setWordPath(String wordPath) {
		this.wordPath = wordPath;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

}
