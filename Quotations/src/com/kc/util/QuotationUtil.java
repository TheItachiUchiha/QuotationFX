package com.kc.util;

import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.kc.model.ComplaintVO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.model.ServiceVO;

public class QuotationUtil {

	public static String doubleToString(double value)
	{
		DecimalFormat myFormatter = new DecimalFormat("###.##");
		String output = myFormatter.format(value);
		return output;
	}
	public static ObservableList<EnquiryViewVO> fillEnquiryViewListFromEnquiryList(ObservableList<EnquiryVO> enquiryList, ObservableList<CustomersVO> customerList)
	{
		ObservableList<EnquiryViewVO> tempList = FXCollections.observableArrayList();

		for(EnquiryVO enquiryVO : enquiryList)
		{
			EnquiryViewVO enquiryViewVO = new EnquiryViewVO();
			enquiryViewVO.setId(enquiryVO.getId());
			enquiryViewVO.setDateOfEnquiry(enquiryVO.getDate());
			enquiryViewVO.setProductName(enquiryVO.getProductName());
			enquiryViewVO.setPurchasePeriod(enquiryVO.getPurchasePeriod());
			enquiryViewVO.setReferedBy(enquiryVO.getReferedBy());
			enquiryViewVO.setReferenceNo(enquiryVO.getRefNumber());
			enquiryViewVO.setCustomerRequirement(enquiryVO.getCustomerrequirements());
			enquiryViewVO.setCustomerFile(enquiryVO.getCustomerDocument());
			enquiryViewVO.setPriceEstimation(enquiryVO.getPriceEstimation());
			enquiryViewVO.setDateOfEnquiry(enquiryVO.getDate());
			enquiryViewVO.setPeDate(enquiryVO.getPeDate());
			enquiryViewVO.setMargin(enquiryVO.getMargin());
			enquiryViewVO.setQuotationPreparation(enquiryVO.getQuotationPreparation());
			enquiryViewVO.setEmailSent(enquiryVO.getEmailSent());
			enquiryViewVO.setSales(enquiryVO.getSales());
			enquiryViewVO.setQpDate(enquiryVO.getQpDate());
			enquiryViewVO.setPeDate(enquiryVO.getPeDate());
			enquiryViewVO.setMailSentDate(enquiryVO.getMailSentDate());
			enquiryViewVO.setSalesDate(enquiryVO.getSalesDate());
			enquiryViewVO.setReminderSent(enquiryVO.getReminderSent());
			enquiryViewVO.setTotalRevenue(enquiryVO.getTotalRevenue());
			enquiryViewVO.setEnquiryCustomerType(enquiryVO.getEnquiryCustomerType());
			enquiryViewVO.setComplaintCount(enquiryVO.getComplaintCount());
			enquiryViewVO.setDispatchDone(enquiryVO.getDispatchDone());
			enquiryViewVO.setServiceCount(enquiryVO.getServiceCount());
			enquiryViewVO.setPurchaseOrderNo(enquiryVO.getPurchaseOrderNo());
			enquiryViewVO.setProductQuantity(enquiryVO.getProductQuantity());
			if(enquiryVO.getFlag().equalsIgnoreCase("C"))
			{
				enquiryViewVO.setEnquiryType("Custom");
			}
			else if(enquiryVO.getFlag().equalsIgnoreCase("S"))
			{
				enquiryViewVO.setEnquiryType("Standard");
			}
			
			for(CustomersVO customersVO : customerList)
			{
				if(customersVO.getId() == enquiryVO.getCustomerId())
				{
					enquiryViewVO.setCustomerName(customersVO.getCustomerName());
					enquiryViewVO.setCity(customersVO.getCity());
					enquiryViewVO.setCompanyName(customersVO.getCompanyName());
					enquiryViewVO.setState(customersVO.getState());
					enquiryViewVO.setAddress(customersVO.getAddress());
					enquiryViewVO.setEmailId(customersVO.getEmailId());
					enquiryViewVO.setTinNumber(customersVO.getTinNumber());
					enquiryViewVO.setContactNumber(customersVO.getContactNumber());
					enquiryViewVO.setCustomerId(customersVO.getId());
					if(customersVO.getCustomerType().equalsIgnoreCase("Dealer"))
					{
						enquiryViewVO.setCustomerType("Dealer");
					}
					else if(customersVO.getCustomerType().equalsIgnoreCase("End User"))
					{
						enquiryViewVO.setCustomerType("End User");
					}
					enquiryViewVO.setProductId(enquiryVO.getProductId());
				}
			}
			tempList.add(enquiryViewVO);
		}
		return tempList;
	}
	
	public static ObservableList<ComplaintVO> fillComplaintListFromService(ObservableList<ServiceVO> serviceList, ObservableList<CustomersVO> customerList)
	{
		ObservableList<ComplaintVO> tempList = FXCollections.observableArrayList();

		for(ServiceVO serviceVO : serviceList)
		{
			ComplaintVO complaintVO =new ComplaintVO();
			complaintVO.setId(serviceVO.getId());
			complaintVO.setDateOfComplaint(serviceVO.getComplaintDate());
			complaintVO.setProductName(serviceVO.getProductName());
			complaintVO.setReferenceNo(serviceVO.getReferenceNo());
			complaintVO.setComplaint(serviceVO.getComplaint());
			complaintVO.setServiceCount(serviceVO.getServiceCount());
			for(CustomersVO customersVO : customerList)
			{
				if(customersVO.getId() == serviceVO.getCustomerId())
				{
					complaintVO.setCustomerCity(customersVO.getCity());
					complaintVO.setCustomerName(customersVO.getCustomerName());
					complaintVO.setEmailId(customersVO.getEmailId());
				}
			}
			tempList.add(complaintVO);
		}
		return tempList;
	}
	
	public static String monthDigitFromString(String month)
	{
		try{
			if(month.equalsIgnoreCase("JAN"))
			{
				return "01";
			}
			else if(month.equalsIgnoreCase("FEB"))
			{
				return "02";
			}
			else if(month.equalsIgnoreCase("MAR"))
			{
				return "03";
			}
			else if(month.equalsIgnoreCase("APR"))
			{
				return "04";
			}
			else if(month.equalsIgnoreCase("MAY"))
			{
				return "05";
			}
			else if(month.equalsIgnoreCase("JUN"))
			{
				return "06";
			}
			else if(month.equalsIgnoreCase("JUL"))
			{
				return "07";
			}
			else if(month.equalsIgnoreCase("AUG"))
			{
				return "08";
			}
			else if(month.equalsIgnoreCase("SEP"))
			{
				return "09";
			}
			else if(month.equalsIgnoreCase("OCT"))
			{
				return "10";
			}
			else if(month.equalsIgnoreCase("NOV"))
			{
				return "11";
			}
			else if(month.equalsIgnoreCase("DEC"))
			{
				return "12";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
