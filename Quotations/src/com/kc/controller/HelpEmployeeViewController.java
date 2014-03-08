package com.kc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;
import com.kc.model.EmployeeVO;

public class HelpEmployeeViewController implements Initializable {
	private static final Logger LOG = LogManager.getLogger(HelpEmployeeViewController.class);


		@FXML
	    private TableColumn action;

	    @FXML
	    private TableColumn<EmployeeVO , String> address;

	    @FXML
	    private TableView<EmployeeVO> employeeTable;

	    @FXML
	    private TableColumn<EmployeeVO , String> designation;

	    @FXML
	    private Label message;

	    @FXML
	    private TableColumn<EmployeeVO , String> mobileNo;

	    @FXML
	    private TableColumn<EmployeeVO , String> name;

	    @FXML
	    private TableColumn<EmployeeVO , String> serviceRating;
	    
	    HelpDAO helpDAO;
	    
	    public HelpEmployeeViewController() {
			helpDAO = new HelpDAO();
		}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			
			fillTableFromData();
			action.setSortable(false);
	         
	        action.setCellValueFactory(
	                new Callback<TableColumn.CellDataFeatures<EmployeeVO, Boolean>,
	                ObservableValue<Boolean>>() {
	 
	            @Override
	            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<EmployeeVO, Boolean> p) {
	                return new SimpleBooleanProperty(p.getValue() != null);
	            }
	        });
	 
	        action.setCellFactory(
	                new Callback<TableColumn<EmployeeVO, Boolean>, TableCell<EmployeeVO, Boolean>>() {
	 
	            @Override
	            public TableCell<EmployeeVO, Boolean> call(TableColumn<EmployeeVO, Boolean> p) {
	                return new ButtonCell();
	            }
	         
	        });
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void fillTableFromData()
	{
		try
		{
			ObservableList<EmployeeVO> employeeList =  FXCollections.observableArrayList();
			employeeList= helpDAO.getEmployees();
			name.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("name"));
			designation.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("designation"));
			mobileNo.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("mobileNo"));
			address.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("address"));
			serviceRating.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("serviceRating"));
			employeeTable.setItems(employeeList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEmployee(EmployeeVO employeeVO)
	{
		LOG.info("Enter : deleteEmployee");
		try{
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
				    "Do you want to delete selected customer(s)", "Confirm", "Delete customer", DialogOptions.OK_CANCEL);
				if(response.equals(DialogResponse.OK))
				{
					helpDAO.deleteEmployees(employeeVO);
					message.setText(CommonConstants.EMPLOYEE_DELETE_SUCCESS);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
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
		LOG.info("Exit : deleteEmployee");
	}
	private class ButtonCell extends TableCell<EmployeeVO, Boolean> {
	       
		Image buttonDeleteImage = new Image(getClass().getResourceAsStream("/com/kc/style/delete.png"));
		Image buttonEditImage = new Image(getClass().getResourceAsStream("/com/kc/style/edit.png"));
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
                    deleteEmployee(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
                }
            });
        	
        	cellEditButton.setOnAction(new EventHandler<ActionEvent>(){
        		 
                @Override
                public void handle(ActionEvent t) {
                	LOG.info("Enter : handle");
                	try {
						FXMLLoader menuLoader = new FXMLLoader(this.getClass()
								.getResource("/com/kc/view/help-employees-modifypopup.fxml"));
						GridPane userModify;
						userModify = (GridPane) menuLoader.load();
						Stage modifyStage = new Stage();
						Scene modifyScene = new Scene(userModify);
						modifyStage.setResizable(false);
						modifyStage.setHeight(390);
						modifyStage.setWidth(500);
						modifyStage.initModality(Modality.WINDOW_MODAL);
						modifyStage.initOwner(LoginController.primaryStage);
						modifyStage.setScene(modifyScene);
						modifyStage.show();
						ObservableList<EmployeeVO> tempList = helpDAO.getEmployees();
						for(EmployeeVO employeeVO : tempList)
						{
							if(employeeVO.getId() == ButtonCell.this
									.getTableView().getItems()
									.get(ButtonCell.this.getIndex()).getId())
							{
								((HelpEmployeeModifyPopUpController) menuLoader.getController())
								.fillTextfieldValues(employeeVO);
								break;
							}
						}
						
						
						modifyStage
								.setOnCloseRequest(new EventHandler<WindowEvent>() {

									
									@Override
									public void handle(WindowEvent paramT) {
										
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
}
