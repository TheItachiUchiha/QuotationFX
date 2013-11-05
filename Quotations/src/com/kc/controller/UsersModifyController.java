package com.kc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.kc.constant.CommonConstants;
import com.kc.dao.UsersDAO;
import com.kc.model.UsersVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class UsersModifyController implements Initializable{
	private static final Logger LOG = LogManager.getLogger(UsersModifyController.class);
	private ObservableList<UsersVO> usersList;
	private UsersDAO usersDAO;
	private UsersVO usersVO;
	
	public UsersModifyController(){
		usersDAO = new UsersDAO();
		usersVO = new UsersVO();
	}
 	
	@FXML
	private AutoCompleteTextField <UsersVO> userNameAutoFill;
	
	@FXML
	private HBox modifyHbox; 
	
	@FXML
    private CheckBox quotation;

    @FXML
    private CheckBox priceEstimation;
    
    @FXML
    private CheckBox report;
    
    @FXML
    private CheckBox salesOrderManagement;
    
    @FXML
    private CheckBox statusReminder;
    
    @FXML
    private CheckBox view;
    
    @FXML
    private CheckBox edit;
    
    @FXML
    private CheckBox delete;
    
    @FXML
    private ComboBox<String> userType;
    
    @FXML
	private Label message;
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		// TODO Auto-generated method stub
		
		try{
			usersList = usersDAO.getUsers();
			
			userNameAutoFill.setItems(usersList);
			
			
			userNameAutoFill.setPromptText("Name Of User");
			
			
			userNameAutoFill.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					GridPane gridPane = (GridPane) modifyHbox.getChildren().get(0);
					ObservableList<Node> listTextField = gridPane.getChildren();
					
					for(UsersVO usersVO: usersList)
					{
						if(usersVO.getName().equals(userNameAutoFill.getText())){
							modifyHbox.setVisible(true);
							userNameAutoFill.setDisable(true);
							UsersModifyController.this.usersVO.setId(usersVO.getId());
							for(Node node : listTextField)
							{
								if(null!=node.getId())
								{
									if(node.getId().equals("name"))
									{
										((TextField)node).setText(usersVO.getName());
									}
									else if(node.getId().equals("designation"))
									{
										((TextField)node).setText(usersVO.getDesignation());
									}
									else if(node.getId().equals("mobileNumber"))
									{
										((TextField)node).setText(usersVO.getMobileNumber());
									}
									else if(node.getId().equals("username"))
									{
										((TextField)node).setText(usersVO.getUsername());
									}
									else if(node.getId().equals("password"))
									{
										((TextField)node).setText(usersVO.getPassword());
									}
								}
							}
							if(usersVO.getQuotation().equalsIgnoreCase("Y"))
							{
								quotation.setSelected(true);
							}
				
							if(usersVO.getPriceEstimation().equalsIgnoreCase("Y"))
							{
								priceEstimation.setSelected(true);
							}
							if(usersVO.getReport().equalsIgnoreCase("Y"))
							{
								report.setSelected(true);
							}
				
							if(usersVO.getSalesOrderManagement().equalsIgnoreCase("Y"))
							{
								salesOrderManagement.setSelected(true);
							}
							if(usersVO.getStatusReminder().equalsIgnoreCase("Y"))
							{
								statusReminder.setSelected(true);
							}
				
							if(usersVO.getView().equalsIgnoreCase("Y"))
							{
								view.setSelected(true);
							}
							if(usersVO.getEdit().equalsIgnoreCase("Y"))
							{
								edit.setSelected(true);
							}
				
							if(usersVO.getDelete().equalsIgnoreCase("Y"))
							{
								delete.setSelected(true);
							}
							if(usersVO.getUserType().equalsIgnoreCase("ADMIN"))
							{
								userType.getSelectionModel().selectFirst();
							}
							else
							{
								userType.getSelectionModel().selectLast();
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
	public void modifyUser()
	{
		try
		{
		GridPane gridPane = (GridPane) modifyHbox.getChildren().get(0);
		ObservableList<Node> listTextField = gridPane.getChildren();
		UsersVO usersVO = new UsersVO();
			for(Node node : listTextField)
			{
				if(null!=node.getId())
				{
					if(node.getId().equals("name"))
					{
						usersVO.setName(((TextField)node).getText());
					}
					else if(node.getId().equals("designation"))
					{
						usersVO.setDesignation(((TextField)node).getText());
					}
					else if(node.getId().equals("mobileNumber"))
					{
						usersVO.setMobileNumber(((TextField)node).getText());
					}
					else if(node.getId().equals("username"))
					{
						usersVO.setUsername(((TextField)node).getText());
					}
					else if(node.getId().equals("password"))
					{
						usersVO.setPassword(((TextField)node).getText());
					}
				}
			}
			if(quotation.isSelected())
			{
				usersVO.setQuotation("Y");
			}
			else
			{
				usersVO.setQuotation("N");
			}
			if(priceEstimation.isSelected())
			{
				usersVO.setPriceEstimation("Y");
			}
			else
			{
				usersVO.setPriceEstimation("N");
			}
			if(report.isSelected())
			{
				usersVO.setReport("Y");
			}
			else
			{
				usersVO.setReport("N");
			}
			if(salesOrderManagement.isSelected())
			{
				usersVO.setSalesOrderManagement("Y");
			}
			else
			{
				usersVO.setSalesOrderManagement("N");
			}
			if(statusReminder.isSelected())
			{
				usersVO.setStatusReminder("Y");
			}
			else
			{
				usersVO.setStatusReminder("N");
			}
			if(view.isSelected())
			{
				usersVO.setView("Y");
			}
			else
			{
				usersVO.setView("N");
			}
			if(edit.isSelected())
			{
				usersVO.setEdit("Y");
			}
			else
			{
				usersVO.setEdit("N");
			}
			if(delete.isSelected())
			{
				usersVO.setDelete("Y");
			}
			else
			{
				usersVO.setDelete("N");
			}
			if(userType.getSelectionModel().getSelectedItem().equalsIgnoreCase("Admin"))
			{
				usersVO.setUserType("ADMIN");
			}
			else
			{
				usersVO.setUserType("NORMAL");
			}
			usersVO.setId(this.usersVO.getId());
			usersDAO.updateUser(usersVO);
			message.setText(CommonConstants.USER_MODIFY_SUCCESS);
			message.setVisible(true);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
