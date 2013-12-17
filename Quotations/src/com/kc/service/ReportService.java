package com.kc.service;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.kc.dao.ProductsDAO;

public class ReportService 
{
	ProductsDAO productsDAO;
	
	public ReportService()
	{
		productsDAO = new ProductsDAO();
	}
	
	public Map<String, List<Integer>> getCategoriesForProduct() throws SQLException
	{
		ObservableList<Map<String, Integer>> listOfCategories = FXCollections.observableArrayList();
		listOfCategories = productsDAO.getCategoriesForProduct();
		Map<String, List<Integer>> listCategories = FXCollections.observableHashMap();
		
		
		for(Map<String, Integer> map : listOfCategories)
		{
			Set<String> keySet = map.keySet();
			String key = keySet.iterator().next();
			List<Integer> list = FXCollections.observableArrayList();
			if(!listCategories.keySet().contains(key))
			{
				for(Map<String, Integer> map1 : listOfCategories)
				{
					if(key.equals(map1.keySet().iterator().next()))
					{
						list.add((Integer)map1.get(key));
					}
				}
				listCategories.put(key, list);
			}
		}
		return listCategories;
	}
	
	public Map<String, List<Integer>> getSubCategoriesForProduct() throws SQLException
	{
		ObservableList<Map<String, Integer>> listOfSubCategories = FXCollections.observableArrayList();
		listOfSubCategories = productsDAO.getSubCategoriesForProduct();
		Map<String, List<Integer>> listSubCategories = FXCollections.observableHashMap();
		
		
		for(Map<String, Integer> map : listOfSubCategories)
		{
			Set<String> keySet = map.keySet();
			String key = keySet.iterator().next();
			List<Integer> list = FXCollections.observableArrayList();
			if(!listSubCategories.keySet().contains(key))
			{
				for(Map<String, Integer> map1 : listOfSubCategories)
				{
					if(key.equals(map1.keySet().iterator().next()))
					{
						list.add((Integer)map1.get(key));
					}
				}
				listSubCategories.put(key, list);
			}
		}
		return listSubCategories;
	}
	
	public Map<String, List<Integer>> getCustomerTypeForProduct() throws SQLException
	{
		ObservableList<Map<String, Integer>> listOfCustomers = FXCollections.observableArrayList();
		listOfCustomers = productsDAO.getCustomerTypeForProduct();
		Map<String, List<Integer>> listCustomers = FXCollections.observableHashMap();
		
		
		for(Map<String, Integer> map : listOfCustomers)
		{
			Set<String> keySet = map.keySet();
			String key = keySet.iterator().next();
			List<Integer> list = FXCollections.observableArrayList();
			if(!listCustomers.keySet().contains(key))
			{
				for(Map<String, Integer> map1 : listOfCustomers)
				{
					if(key.equals(map1.keySet().iterator().next()))
					{
						list.add((Integer)map1.get(key));
					}
				}
				listCustomers.put(key, list);
			}
		}
		return listCustomers;
	}
	
	public Map<String, List<Integer>> getCustomerStateForProduct() throws SQLException
	{
		ObservableList<Map<String, Integer>> listOfStates = FXCollections.observableArrayList();
		listOfStates = productsDAO.getCustomerStateForProduct();
		Map<String, List<Integer>> listStates = FXCollections.observableHashMap();
		
		
		for(Map<String, Integer> map : listOfStates)
		{
			Set<String> keySet = map.keySet();
			String key = keySet.iterator().next();
			List<Integer> list = FXCollections.observableArrayList();
			if(!listStates.keySet().contains(key))
			{
				for(Map<String, Integer> map1 : listOfStates)
				{
					if(key.equals(map1.keySet().iterator().next()))
					{
						list.add((Integer)map1.get(key));
					}
				}
				listStates.put(key, list);
			}
		}
		return listStates;
	}
}
