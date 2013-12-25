package com.kc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.UsersDAO;
import com.kc.model.UsersVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

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
	private AutoCompleteTextField<String> keyword;
	@FXML
	private ComboBox<String> combo;
	@FXML
	private Button go;
	
	@FXML
	private HBox autoHBox;
	@FXML
    private TableView<UsersVO> usersTable;
	@FXML private TableColumn<UsersVO, String> name;
    @FXML private TableColumn<UsersVO, String> username;
    @FXML private TableColumn<UsersVO, String> password;
    @FXML private TableColumn<UsersVO, String> modules;
    @FXML private TableColumn<UsersVO, String> userType;
    @FXML private TableColumn action;
    @FXML private Label message;
	


	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		LOG.info("Enter : initialize");
		try{
			
			//autoHBox.getChildren().removeAll(keyword,go);
			usersList = usersDAO.getModules();
			usersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			searchByList = FXCollections.observableArrayList();
			searchByList.add("Name");
			searchByList.add("Username");
			combo.setItems(searchByList);
			
			combo.valueProperty().addListener(new ChangeListener<String>() {
	            
				@Override public void changed(ObservableValue ov, String t, String t1) {
					
					 fillAutoCompleteFromComboBox(t1);
					 keyword.setText("");
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
					if(combo.getSelectionModel().getSelectedIndex()==-1||keyword.getText().equals(""))
					{
						Dialogs.showInformationDialog(LoginController.primaryStage, CommonConstants.SELECT_KEYWORD);
					}
					else
					{
						fillTableFromData();
					}
				}
			});
			action.setSortable(false);
	         
	        action.setCellValueFactory(
	                new Callback<TableColumn.CellDataFeatures<UsersVO, Boolean>,
	                ObservableValue<Boolean>>() {
	 
	            @Override
	            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<UsersVO, Boolean> p) {
	                return new SimpleBooleanProperty(p.getValue() != null);
	            }
	        });
	 
	        action.setCellFactory(
	                new Callback<TableColumn<UsersVO, Boolean>, TableCell<UsersVO, Boolean>>() {
	 
	            @Override
	            public TableCell<UsersVO, Boolean> call(TableColumn<UsersVO, Boolean> p) {
	                return new ButtonCell();
	            }
	         
	        });
			
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : initialize");
	}
	
	@SuppressWarnings("unchecked")
	private void fillAutoCompleteFromComboBox(String t1)
	{
		LOG.info("Enter : fillAutoCompleteFromComboBox");
		try{
			modulesList = usersDAO.getModules();
			final ObservableList<String> tempList = FXCollections.observableArrayList(); 
			if(t1.equals("Name"))
	        {
	        	for(UsersVO usersVO : usersList)
	        	{
	        		if(!tempList.contains(usersVO.getName()))
	        		{
	        			tempList.add(usersVO.getName());
	        		}
	        	}
	        }
	        else if(t1.equals("Username"))
	        {
	        	for(UsersVO usersVO : usersList)
	        	{
	        		if(!tempList.contains(usersVO.getUsername()))
	        		{
	        			tempList.add(usersVO.getUsername());
	        		}
	        	}
	        }
			autoHBox.getChildren().removeAll(keyword,go);
			keyword = new AutoCompleteTextField<String>();
			//go = new Button();
			//go.setText("Go");
			autoHBox.getChildren().addAll(keyword,go);
			keyword.setPrefWidth(208);
	        keyword.setItems(tempList);
	        
	        
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : fillAutoCompleteFromComboBox");
	}
	
	private void fillTableFromData()
	{
		LOG.info("Enter : fillTableFromData");
		try{
			ObservableList<UsersVO> tempList =  FXCollections.observableArrayList();
			String tempString = keyword.getText();
			if(combo.getSelectionModel().getSelectedItem().equals("Name"))
			{
				for(UsersVO usersVO : modulesList)
				{
					if(usersVO.getName().equalsIgnoreCase(tempString))
					{
						tempList.add(usersVO);
					}
				}
			}
			else if(combo.getSelectionModel().getSelectedItem().equals("Username"))
			{
				for(UsersVO usersVO : modulesList)
				{
					if(usersVO.getUsername().equalsIgnoreCase(tempString))
					{
						tempList.add(usersVO);
					}
				}
			}
			
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
		LOG.info("Exit : fillTableFromData");
	}
	public void deleteUsers(UsersVO usersVO)
	{
		LOG.info("Enter : deleteUsers");
		try{
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
				    "Do you want to delete selected customer(s)", "Confirm", "Delete customer", DialogOptions.OK_CANCEL);
				if(response.equals(DialogResponse.OK))
				{
					usersDAO.deleteUsers(usersVO);
					message.setText(CommonConstants.USER_DELETE_SUCCESS);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
					fillAutoCompleteFromComboBox(combo.getSelectionModel().getSelectedItem());
					fillTableFromData();
				}
		}
		catch (Exception e) {
			message.setText(CommonConstants.FAILURE);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			message.setVisible(true);
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : deleteUsers");
	}
	private class ButtonCell extends TableCell<UsersVO, Boolean> {
	       
		Image buttonDeleteImage = new Image(getClass().getResourceAsStream("../style/delete.png"));
		Image buttonEditImage = new Image(getClass().getResourceAsStream("../style/edit.png"));
		final Button cellDeleteButton = new Button("", new ImageView(buttonDeleteImage));
		final Button cellEditButton = new Button("", new ImageView(buttonEditImage));
       
         
        ButtonCell(){
            
        	
        	cellDeleteButton.getStyleClass().add("editDeleteButton");
        	cellDeleteButton.setTooltip(new Tooltip("Delete"));
        	cellEditButton.getStyleClass().add("editDeleteButton");
        	cellEditButton.setTooltip(new Tooltip("Edit"));
        	
        	cellDeleteButton.setOnAction(new EventHandler<ActionEvent>(){
 
                @Override
                public void handle(ActionEvent t) {
                    deleteUsers(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
                }
            });
        	
        	cellEditButton.setOnAction(new EventHandler<ActionEvent>(){
        		 
                @Override
                public void handle(ActionEvent t) {
                	LOG.info("Enter : handle");
                	try {
						FXMLLoader menuLoader = new FXMLLoader(this.getClass()
								.getResource("../view/users-modify.fxml"));
						BorderPane userModify;
						userModify = (BorderPane) menuLoader.load();
						userModify.setTop(new HBox());
						userModify.getCenter().setVisible(true);
						Stage modifyStage = new Stage();
						Scene modifyScene = new Scene(userModify);
						modifyStage.setResizable(false);
						modifyStage.setHeight(500);
						modifyStage.setWidth(600);
						modifyStage.initModality(Modality.WINDOW_MODAL);
						modifyStage.initOwner(LoginController.primaryStage);
						modifyStage.setScene(modifyScene);
						modifyStage.show();
						ObservableList<UsersVO> tempList = usersDAO.getUsers();
						for(UsersVO usersVO : tempList)
						{
							if(usersVO.getId() == ButtonCell.this
									.getTableView().getItems()
									.get(ButtonCell.this.getIndex()).getId())
							{
								((UsersModifyController) menuLoader.getController())
								.fillTextFieldValues(usersVO);
								break;
							}
						}
						
						
						modifyStage
								.setOnCloseRequest(new EventHandler<WindowEvent>() {

									
									@Override
									public void handle(WindowEvent paramT) {
										
										fillAutoCompleteFromComboBox(combo.getSelectionModel().getSelectedItem());
										for (UsersVO usersVO : modulesList) {
											if (usersVO.getId() == ButtonCell.this.getTableView()
													.getItems()
													.get(ButtonCell.this.getIndex())
													.getId()) {
												updateAutoField(usersVO, combo.getSelectionModel().getSelectedItem());
											}
										}
										fillTableFromData();
									}
								});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LOG.error(e.getMessage());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	LOG.info("Exit : handle");
                }
            });
        }
 
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
            	HBox box = new HBox();
            	box.getChildren().addAll(cellEditButton, cellDeleteButton);
                setGraphic(box);
            }
        }
    }
	private void updateAutoField(UsersVO usersVO, String t1) {
		if(t1.equals("Name"))
        {
			keyword.setText(usersVO.getName());
        }
        else if(t1.equals("Username"))
        {
        	keyword.setText(usersVO.getUsername());
        }
	}
}