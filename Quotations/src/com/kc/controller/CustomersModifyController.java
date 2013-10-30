package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.kc.dao.CustomersDAO;
import com.kc.model.CustomersVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CustomersModifyController implements Initializable{
	private static final Logger LOG = LogManager.getLogger(CustomersModifyController.class);
	private ObservableList<CustomersVO> customersList;
	private CustomersDAO customersDAO;
	private CustomersVO customersVO;
	
	public CustomersModifyController(){
		customersDAO = new CustomersDAO();
		customersVO = new CustomersVO();
	}
 	
	@FXML
	private AutoCompleteTextField <CustomersVO> customerNameAutoFill;
	
	@FXML
	private HBox modifyHbox; 

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		// TODO Auto-generated method stub
		
		try{
			customersList = customersDAO.getCustomers();
			
			customerNameAutoFill.setItems(customersList);
			
			
			customerNameAutoFill.setPromptText("Name Of Component");
			
			
			customerNameAutoFill.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					GridPane gridPane = (GridPane) modifyHbox.getChildren().get(0);
					ObservableList<Node> listTextField = gridPane.getChildren();
					
					for(CustomersVO customersVO: customersList)
					{
						if(customersVO.getCustomerName().equals(customerNameAutoFill.getText())){
							modifyHbox.setVisible(true);
							customerNameAutoFill.setDisable(true);
							CustomersModifyController.this.customersVO.setId(customersVO.getId());
							for(Node node : listTextField)
							{
								if(null!=node.getId())
								{
									if(node.getId().equals("customerName"))
									{
										((TextField)node).setText(customersVO.getCustomerName());
									}
									else if(node.getId().equals("companyName"))
									{
										((TextField)node).setText(customersVO.getCompanyName());
									}
									else if(node.getId().equals("address"))
									{
										((TextArea)node).setText(customersVO.getAddress());
									}
									else if(node.getId().equals("city"))
									{
										((TextField)node).setText(customersVO.getCity());
									}
									else if(node.getId().equals("state"))
									{
										((TextField)node).setText(customersVO.getState());
									}
									else if(node.getId().equals("emailId"))
									{
										((TextField)node).setText(customersVO.getEmailId());
									}
									else if(node.getId().equals("contactNumber"))
									{
										((TextField)node).setText(customersVO.getContactNumber());
									}
									else if(node.getId().equals("customerType"))
									{
										((TextField)node).setText(customersVO.getCustomerType());
									}
									else if(node.getId().equals("tinNumber"))
									{
										((TextField)node).setText(customersVO.getTinNumber());
									}
								}
							}
						}
					}
				}
			});
			}
			catch (Exception e) {
				LOG.error(e.getMessage());
			}
	}
	public void modifyComponent()
	{
		try
		{
		GridPane gridPane = (GridPane) modifyHbox.getChildren().get(0);
		ObservableList<Node> listTextField = gridPane.getChildren();
		CustomersVO customersVO = new CustomersVO();
			for(Node node : listTextField)
			{
				if(node.getId().equals("customerName"))
				{
					customersVO.setCustomerName(((TextField)node).getText());
				}
				else if(node.getId().equals("companyName"))
				{
					customersVO.setCompanyName(((TextField)node).getText());
				}
				else if(node.getId().equals("address"))
				{
					customersVO.setAddress(((TextArea)node).getText());
				}
				else if(node.getId().equals("city"))
				{
					customersVO.setCity(((TextField)node).getText());
				}
				else if(node.getId().equals("state"))
				{
					customersVO.setState(((TextField)node).getText());
				}
				else if(node.getId().equals("emailId"))
				{
					customersVO.setEmailId(((TextField)node).getText());
				}
				else if(node.getId().equals("contactNumber"))
				{
					customersVO.setContactNumber(((TextField)node).getText());
				}
				else if(node.getId().equals("customerType"))
				{
					customersVO.setCustomerType(((TextField)node).getText());
				}
				else if(node.getId().equals("tinNumber"))
				{
					customersVO.setTinNumber(((TextField)node).getText());
				}
			}
			customersVO.setId(this.customersVO.getId());
			customersDAO.updateCustomer(customersVO);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}


}
