package com.kc.model;

public class EmployeeVO {
	private int id;
	
	private String name;
	
	private String designation;
	
	private String mobileNo;
	
	private String address;
	
	private String serviceRating;

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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(String serviceRating) {
		this.serviceRating = serviceRating;
	}
	public String toString()
	{
		return this.name;
	}
}
