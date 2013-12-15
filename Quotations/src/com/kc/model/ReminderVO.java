package com.kc.model;

public class ReminderVO {
	
	int id;
	int totalReminder;
	String referenceNo;
	int frequency;
	String lastSent;
	String nextSend;
	String status;
	String subject;
	String emailMessage;
	String reciever;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmailMessage() {
		return emailMessage;
	}
	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}
	public String getReciever() {
		return reciever;
	}
	public void setReciever(String reciever) {
		this.reciever = reciever;
	}
	
}
