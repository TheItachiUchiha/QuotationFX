package com.kc.util;

import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;

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
			enquiryViewVO.setServiceDone(enquiryVO.getServiceDone());
			enquiryViewVO.setServiceDate(enquiryVO.getServiceDate());
			enquiryViewVO.setReminderCount(enquiryVO.getReminderCount());
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
}
