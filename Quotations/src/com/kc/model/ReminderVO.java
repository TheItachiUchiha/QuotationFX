package com.kc.model;

public class ReminderVO {
	
	int id;
	int totalReminder;
	String referenceNo;
	int frequency;
	String lastSent;
	String nextSend;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalReminder() {
		return totalReminder;
	}
	public void setTotalReminder(int totalReminder) {
		this.totalReminder = totalReminder;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getLastSent() {
		return lastSent;
	}
	public void setLastSent(String lastSent) {
		this.lastSent = lastSent;
	}
	public String getNextSend() {
		return nextSend;
	}
	public void setNextSend(String nextSend) {
		this.nextSend = nextSend;
	}
	

}
