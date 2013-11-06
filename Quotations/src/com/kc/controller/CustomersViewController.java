package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.CustomersDAO;
import com.kc.model.CustomersVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@SuppressWarnings("rawtypes")
public class CustomersViewController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(CustomersViewController.class);
	private ObservableList<CustomersVO> customersList;
	private ObservableList<String> searchByList;
	private CustomersDAO customersDAO;
	
	public CustomersViewController(){
		customersDAO = new CustomersDAO();
	}
	
	
	@FXML
	private HBox modifyHbox; 
	
	@FXML
	private GridPane topGrid;
	
	@FXML
	private AutoCompleteTextField<String> keyword;
	
	@FXML
	private ComboBox<String> combo;
	
	@FXML
	private Button go;
	
	@FXML
    private TableView<CustomersVO> customerTable;
	
	@FXML private TableColumn<CustomersVO, String> name;
    @FXML private TableColumn<CustomersVO, String> companyName;
    @FXML private TableColumn<CustomersVO, String> address;
    @FXML private TableColumn<CustomersVO, String> city;
    @FXML private TableColumn<CustomersVO, String> state;
    @FXML private TableColumn<CustomersVO, String> emailId;
    @FXML private TableColumn<CustomersVO, String> contactNumber;
    @FXML private TableColumn<CustomersVO, String> tinNumber;
    @FXML private TableColumn<CustomersVO, String> customerType;
    @FXML private Label message;
	


	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		try{
			
			customersList = customersDAO.getCustomers();
			customerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			searchByList = FXCollections.observableArrayList();
			searchByList.add("Customer Name");
			searchByList.add("Company Name");
			searchByList.add("State");
			searchByList.add("City");
			searchByList.add("Email Id");
			searchByList.add("TIN Number");
			searchByList.add("Customer Type");
			combo.setItems(searchByList);
			
			combo.valueProperty().addListener(new ChangeListener<String>() {
	            
				@Override public void changed(ObservableValue ov, String t, String t1) {                
					 fillAutoCompleteFromComboBox(t1);
	            }
	        });
			
			keyword.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					
					fillTableFromData();
				}
			});
			
			go.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					
					fillTableFromData();
				}
			});
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fillAutoCompleteFromComboBox(String t1)
	{
			try{
			final ObservableList<String> tempList = FXCollections.observableArrayList(); 
			if(t1.equals("Customer Name"))
	        {
	        	for(CustomersVO customersVO : customersList)
	        	{
	        		tempList.add(customersVO.getCustomerName());
	        	}
	        }
	        else if(t1.equals("Company Name"))
	        {
	        	for(CustomersVO customersVO : customersList)
	        	{
	        		tempList.add(customersVO.getCompanyName());
	        	}
	        }
	        else if(t1.equals("State"))
	        {
	        	for(CustomersVO customersVO : customersList)
	        	{
	        		tempList.add(customersVO.getState());
	        	}
	        }
	        else if(t1.equals("City"))
	        {
	        	for(CustomersVO customersVO : customersList)
	        	{
	        		tempList.add(customersVO.getCity());
	        	}
	        }
	        else if(t1.equals("Email Id"))
	        {
	        	for(CustomersVO customersVO : customersList)
	        	{
	        		tempList.add(customersVO.getEmailId());
	        	}
	        }
	        else if(t1.equals("TIN Number"))
	        {
	        	for(CustomersVO customersVO : customersList)
	        	{
	        		tempList.add(customersVO.getTinNumber());
	        	}
	        }
	        else if(t1.equals("Customer Type"))
	        {
	        	for(CustomersVO customersVO : customersList)
	        	{
	        		tempList.add(customersVO.getCustomerType());
	        	}
	        }
	        keyword.setItems(tempList);
	        keyword.setText("");
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}	
	}
	
	private void fillTableFromData()
	{
		try{
			customersList = customersDAO.getCustomers();
			ObservableList<CustomersVO> tempList =  FXCollections.observableArrayList();
			String tempString = keyword.getText();
			if(combo.getSelectionModel().getSelectedItem().equals("Customer Name"))
			{
				for(CustomersVO customersVO : customersList)
				{
					if(customersVO.getCustomerName().equalsIgnoreCase(tempString))
					{
						tempList.add(customersVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Company Name"))
			{
				for(CustomersVO customersVO : customersList)
				{
					if(customersVO.getCompanyName().equalsIgnoreCase(tempString))
					{
						tempList.add(customersVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("State"))
			{
				for(CustomersVO customersVO : customersList)
				{
					if(customersVO.getState().equalsIgnoreCase(tempString))
					{
						tempList.add(customersVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("City"))
			{
				for(CustomersVO customersVO : customersList)
				{
					if(customersVO.getCity().equalsIgnoreCase(tempString))
					{
						tempList.add(customersVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Email Id"))
			{
				for(CustomersVO customersVO : customersList)
				{
					if(customersVO.getEmailId().equalsIgnoreCase(tempString))
					{
						tempList.add(customersVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("TIN Number"))
			{
				for(CustomersVO customersVO : customersList)
				{
					if(customersVO.getTinNumber().equalsIgnoreCase(tempString))
					{
						tempList.add(customersVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Customer Type"))
			{
				for(CustomersVO customersVO : customersList)
				{
					if(customersVO.getCustomerType().equalsIgnoreCase(tempString))
					{
						tempList.add(customersVO);
					}
				}
			}
			
			
			name.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("customerName"));
			companyName.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("companyName"));
			address.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("address"));
			city.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("city"));
			state.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("state"));
			emailId.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("emailId"));
			contactNumber.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("contactNumber"));
			tinNumber.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("tinNumber"));
			customerType.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("customerType"));
			customerTable.setItems(tempList);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void modifyCustomer()
	{
		
	}
	
	public void deleteCustomers()
	{
		ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
		try{
			customerList = customerTable.getSelectionModel().getSelectedItems();
			if(customerList.size()>0)
			{
				DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
					    "Do you want to delete selected customer(s)", "Confirm", "Delete customer", DialogOptions.OK_CANCEL);
				if(response.equals(DialogResponse.OK))
				{
					customersDAO.deleteCustomers(customerList);
					message.setText(CommonConstants.COMPONENT_DELETE_SUCCESS);
					message.setVisible(true);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					fillTableFromData();
					fillAutoCompleteFromComboBox(combo.getSelectionModel().getSelectedItem());
				}
			}
			else{
				Dialogs.showInformationDialog(new Stage(), "Please select atleast one customer",
					    "Delete customer", "Delete customer");
			}
		}
		catch (Exception e) {
			message.setText(CommonConstants.FAILURE);
			message.setVisible(true);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
		}
		
	}

}
