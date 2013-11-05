package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.kc.constant.CommonConstants;
import com.kc.dao.UsersDAO;
import com.kc.model.UsersVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class UsersViewController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(UsersViewController.class);
	private ObservableList<UsersVO> usersList;
	private ObservableList<UsersVO> modulesList;
	private ObservableList<String> searchByList;
	private UsersDAO usersDAO;
	
	public UsersViewController(){
		usersDAO = new UsersDAO();
	}
	
	
	@FXML
	private HBox modifyHbox; 
	
	@FXML
	private GridPane topGrid;
	
	@FXML
    private TableView<UsersVO> usersTable;
	
	@FXML private TableColumn<UsersVO, String> name;
    @FXML private TableColumn<UsersVO, String> username;
    @FXML private TableColumn<UsersVO, String> password;
    @FXML private TableColumn<UsersVO, String> modules;
    @FXML private TableColumn<UsersVO, String> userType;
    @FXML private Label message;
	


	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		try{
			
			usersList = usersDAO.getModules();
			usersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			searchByList = FXCollections.observableArrayList();
			searchByList.add("Name");
			searchByList.add("Username");
			((ComboBox<String>)topGrid.getChildren().get(3)).setItems(searchByList);
			
			((ComboBox<String>)topGrid.getChildren().get(3)).valueProperty().addListener(new ChangeListener<String>() {
	            
				@Override public void changed(ObservableValue ov, String t, String t1) {                
					 fillAutoCompleteFromComboBox(t1);
	            }
	        });
			
			((AutoCompleteTextField<String>)topGrid.getChildren().get(1)).setOnAction(new EventHandler<ActionEvent>() {
				
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
			if(t1.equals("Name"))
	        {
	        	for(UsersVO usersVO : usersList)
	        	{
	        		tempList.add(usersVO.getName());
	        	}
	        }
	        else if(t1.equals("Username"))
	        {
	        	for(UsersVO usersVO : usersList)
	        	{
	        		tempList.add(usersVO.getUsername());
	        	}
	        }
	        ((AutoCompleteTextField<String>)topGrid.getChildren().get(1)).setItems(tempList);
	        ((AutoCompleteTextField<String>)topGrid.getChildren().get(1)).setText("");
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}	
	}
	
	private void fillTableFromData()
	{
		try{
			//adusersList = usersDAO.getModules();
			ObservableList<UsersVO> tempList =  FXCollections.observableArrayList();
			String tempString = ((AutoCompleteTextField<String>)topGrid.getChildren().get(1)).getText();
			if(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem().equals("Name"))
			{
				for(UsersVO usersVO : usersList)
				{
					if(usersVO.getName().equalsIgnoreCase(tempString))
					{
						tempList.add(usersVO);
					}
				}
			}
			else if(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem().equals("Username"))
			{
				for(UsersVO usersVO : usersList)
				{
					if(usersVO.getUsername().equalsIgnoreCase(tempString))
					{
						tempList.add(usersVO);
					}
				}
			}
			modulesList= usersDAO.getModules();
			name.setCellValueFactory(new PropertyValueFactory<UsersVO, String>("Name"));
			username.setCellValueFactory(new PropertyValueFactory<UsersVO, String>("username"));
			password.setCellValueFactory(new PropertyValueFactory<UsersVO, String>("password"));
			modules.setCellValueFactory(new PropertyValueFactory<UsersVO, String>("quotation"));
			userType.setCellValueFactory(new PropertyValueFactory<UsersVO, String>("userType"));
			usersTable.setItems(tempList);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}
	public void deleteUsers()
	{
		ObservableList<UsersVO> userList = FXCollections.observableArrayList();
		try{
			userList = usersTable.getSelectionModel().getSelectedItems();
			if(userList.size()>0)
			{
				DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
					    "Do you want to delete selected user(s)", "Confirm", "Delete user", DialogOptions.OK_CANCEL);
				if(response.equals(DialogResponse.OK))
				{
					usersDAO.deleteUsers(userList);
					message.setText(CommonConstants.USER_DELETE_SUCCESS);
					message.setVisible(true);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					fillTableFromData();
					fillAutoCompleteFromComboBox(((ComboBox<String>)topGrid.getChildren().get(3)).getSelectionModel().getSelectedItem());
				}
			}
			else{
				Dialogs.showInformationDialog(new Stage(), "Please select atleast one user",
					    "Delete user", "Delete user");
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
