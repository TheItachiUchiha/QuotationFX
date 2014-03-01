package com.kc.controller;

import java.io.IOException;
import java.net.URL;
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
import com.kc.dao.CustomersDAO;
import com.kc.model.CustomersVO;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;

@SuppressWarnings("rawtypes")
public class CustomersViewController implements Initializable {
	private static final Logger LOG = LogManager
			.getLogger(CustomersViewController.class);
	private ObservableList<CustomersVO> customersList;
	private ObservableList<String> searchByList;
	private CustomersDAO customersDAO;

	public CustomersViewController() {
		customersDAO = new CustomersDAO();
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
	private TableView<CustomersVO> customerTable;
	@FXML
	private TableColumn<CustomersVO, String> name;
	@FXML
	private TableColumn<CustomersVO, String> companyName;
	@FXML
	private TableColumn<CustomersVO, String> address;
	@FXML
	private TableColumn<CustomersVO, String> city;
	@FXML
	private TableColumn<CustomersVO, String> state;
	@FXML
	private TableColumn<CustomersVO, String> emailId;
	@FXML
	private TableColumn<CustomersVO, String> contactNumber;
	@FXML
	private TableColumn<CustomersVO, String> tinNumber;
	@FXML
	private TableColumn<CustomersVO, String> customerType;
	@FXML
	private TableColumn<CustomersVO, String> telephone;
	@FXML
	private TableColumn<CustomersVO, String> website;
	@FXML
	private TableColumn action;
	@FXML
	private Label message;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		LOG.info("Enter : initialize");
		try {
			
			AdminHomeController.currentPage.setText("VIEW CUSTOMERS");
			customersList = customersDAO.getCustomers();
			customerTable.getSelectionModel().setSelectionMode(
					SelectionMode.MULTIPLE);
			searchByList = FXCollections.observableArrayList();
			searchByList.add("Customer Name");
			searchByList.add("Company Name");
			searchByList.add("State");
			searchByList.add("City");
			searchByList.add("Email Id");
			searchByList.add("TIN Number");
			searchByList.add("Customer Type");
			combo.setItems(searchByList);
			
			updateTable(customersList);

			combo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue ov, String t, String t1) {
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

			action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomersVO, Boolean>, ObservableValue<Boolean>>() {

				@Override
				public ObservableValue<Boolean> call(
						TableColumn.CellDataFeatures<CustomersVO, Boolean> p) {
					return new SimpleBooleanProperty(p.getValue() != null);
				}
			});

			action.setCellFactory(new Callback<TableColumn<CustomersVO, Boolean>, TableCell<CustomersVO, Boolean>>() {

				@Override
				public TableCell<CustomersVO, Boolean> call(
						TableColumn<CustomersVO, Boolean> p) {
					return new ButtonCell();
				}

			});
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : initialize");
	}
	
	public void updateTable(ObservableList<CustomersVO> tempList)
	{
		name.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("customerName"));
		companyName.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("companyName"));
		address.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("address"));
		city.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("city"));
		state.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("state"));
		emailId.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("emailId"));
		contactNumber.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("contactNumber"));
		tinNumber.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("tinNumber"));
		customerType.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("customerType"));
		telephone.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("telephone"));
		website.setCellValueFactory(new PropertyValueFactory<CustomersVO, String>("website"));
		customerTable.setItems(tempList);
	}

	@SuppressWarnings("unchecked")
	private void fillAutoCompleteFromComboBox(String t1) {
		LOG.info("Enter : fillAutoCompleteFromComboBox");
		try {
			customersList = customersDAO.getCustomers();
			final ObservableList<String> tempList = FXCollections
					.observableArrayList();
			if (t1.equals("Customer Name")) {
				for (CustomersVO customersVO : customersList) {
					if(!tempList.contains(customersVO.getCustomerName()))
	        		{
					tempList.add(customersVO.getCustomerName());
	        		}
				}
			} else if (t1.equals("Company Name")) {
				for (CustomersVO customersVO : customersList) {
					if(!tempList.contains(customersVO.getCompanyName()))
	        		{
					tempList.add(customersVO.getCompanyName());
	        		}
				}
			} else if (t1.equals("State")) {
				for (CustomersVO customersVO : customersList) {
					if(!tempList.contains(customersVO.getState()))
	        		{
					tempList.add(customersVO.getState());
	        		}
				}
			} else if (t1.equals("City")) {
				for (CustomersVO customersVO : customersList) {
					if(!tempList.contains(customersVO.getCity()))
	        		{
					tempList.add(customersVO.getCity());
	        		}
				}
			} else if (t1.equals("Email Id")) {
				for (CustomersVO customersVO : customersList) {
					if(!tempList.contains(customersVO.getEmailId()))
	        		{
					tempList.add(customersVO.getEmailId());
	        		}
				}
			} else if (t1.equals("TIN Number")) {
				for (CustomersVO customersVO : customersList) {
					if(!tempList.contains(customersVO.getTinNumber()))
	        		{
					tempList.add(customersVO.getTinNumber());
	        		}
				}
			} else if (t1.equals("Customer Type")) {
				for (CustomersVO customersVO : customersList) {
					if(!tempList.contains(customersVO.getCustomerType()))
	        		{
					tempList.add(customersVO.getCustomerType());
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
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : fillAutoCompleteFromComboBox");
	}

	private void fillTableFromData() {
		LOG.info("Enter : fillTableFromData");
		try {
			ObservableList<CustomersVO> tempList = FXCollections
					.observableArrayList();
			String tempString = keyword.getText();
			if (combo.getSelectionModel().getSelectedItem()
					.equals("Customer Name")) {
				for (CustomersVO customersVO : customersList) {
					if (customersVO.getCustomerName().equalsIgnoreCase(
							tempString)) {
						tempList.add(customersVO);
					}
				}
			} else if (combo.getSelectionModel().getSelectedItem()
					.equals("Company Name")) {
				for (CustomersVO customersVO : customersList) {
					if (customersVO.getCompanyName().equalsIgnoreCase(
							tempString)) {
						tempList.add(customersVO);
					}
				}
			} else if (combo.getSelectionModel().getSelectedItem()
					.equals("State")) {
				for (CustomersVO customersVO : customersList) {
					if (customersVO.getState().equalsIgnoreCase(tempString)) {
						tempList.add(customersVO);
					}
				}
			} else if (combo.getSelectionModel().getSelectedItem()
					.equals("City")) {
				for (CustomersVO customersVO : customersList) {
					if (customersVO.getCity().equalsIgnoreCase(tempString)) {
						tempList.add(customersVO);
					}
				}
			} else if (combo.getSelectionModel().getSelectedItem()
					.equals("Email Id")) {
				for (CustomersVO customersVO : customersList) {
					if (customersVO.getEmailId().equalsIgnoreCase(tempString)) {
						tempList.add(customersVO);
					}
				}
			} else if (combo.getSelectionModel().getSelectedItem()
					.equals("TIN Number")) {
				for (CustomersVO customersVO : customersList) {
					if (customersVO.getTinNumber().equalsIgnoreCase(tempString)) {
						tempList.add(customersVO);
					}
				}
			} else if (combo.getSelectionModel().getSelectedItem()
					.equals("Customer Type")) {
				for (CustomersVO customersVO : customersList) {
					if (customersVO.getCustomerType().equalsIgnoreCase(
							tempString)) {
						tempList.add(customersVO);
					}
				}
			}

			updateTable(tempList);
			
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit : fillTableFromData");
	}

	public void deleteCustomers(CustomersVO customersVO) {
		LOG.info("Enter : deleteCustomers");
		try {
			DialogResponse response = Dialogs.showConfirmDialog(new Stage(),
					"Do you want to delete selected customer(s)", "Confirm",
					"Delete customer", DialogOptions.OK_CANCEL);
			if (response.equals(DialogResponse.OK)) {
				if(combo.getSelectionModel().getSelectedIndex()>-1)
				{
					customersDAO.deleteCustomers(customersVO);
					message.setText(CommonConstants.COMPONENT_DELETE_SUCCESS);
					message.getStyleClass().remove("failure");
					message.getStyleClass().add("success");
					message.setVisible(true);
					fillAutoCompleteFromComboBox(combo.getSelectionModel()
							.getSelectedItem());
					fillTableFromData();
				}
				else
				{
					for(CustomersVO  customersVO2 : customersList)
					{
						if(customersVO2.getId()==customersVO.getId())
						{
							customersList.remove(customersVO2);
							customersDAO.deleteCustomers(customersVO2);
							break;
						}
					}
					updateTable(customersList);
				}
			}
		} catch (Exception e) {
			message.setText(CommonConstants.FAILURE);
			message.getStyleClass().remove("success");
			message.getStyleClass().add("failure");
			message.setVisible(true);
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : deleteCustomers");
	}

	private class ButtonCell extends TableCell<CustomersVO, Boolean> {

		Image buttonDeleteImage = new Image(getClass().getResourceAsStream(
				"/com/kc/style/delete.png"));
		Image buttonEditImage = new Image(getClass().getResourceAsStream(
				"/com/kc/style/edit.png"));
		final Button cellDeleteButton = new Button("", new ImageView(
				buttonDeleteImage));
		final Button cellEditButton = new Button("", new ImageView(
				buttonEditImage));

		ButtonCell() {

			cellDeleteButton.getStyleClass().add("editDeleteButton");
			cellDeleteButton.setTooltip(new Tooltip("Delete"));
			cellEditButton.getStyleClass().add("editDeleteButton");
			cellEditButton.setTooltip(new Tooltip("Edit"));

			cellDeleteButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					deleteCustomers(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));
				}
			});

			cellEditButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					LOG.info("Enter : handle");
					try {
						FXMLLoader menuLoader = new FXMLLoader(this.getClass()
								.getResource("/com/kc/view/customers-modify.fxml"));
						BorderPane customerModify;
						customerModify = (BorderPane) menuLoader.load();
						customerModify.setTop(new HBox());
						customerModify.getCenter().setVisible(true);
						Stage modifyStage = new Stage();
						Scene modifyScene = new Scene(customerModify);
						modifyStage.setResizable(false);
						modifyStage.setHeight(600);
						modifyStage.setWidth(600);
						modifyStage.initModality(Modality.WINDOW_MODAL);
						modifyStage.initOwner(LoginController.primaryStage);
						modifyStage.setScene(modifyScene);
						modifyStage.show();
						((CustomersModifyController) menuLoader.getController())
								.fillTextFieldValues(ButtonCell.this
										.getTableView().getItems()
										.get(ButtonCell.this.getIndex()));
						modifyStage
								.setOnCloseRequest(new EventHandler<WindowEvent>() {

									@Override
									public void handle(WindowEvent paramT) {
										try
										{
											if(combo.getSelectionModel().getSelectedIndex()>-1)
											{
												fillAutoCompleteFromComboBox(combo.getSelectionModel().getSelectedItem());
												for (CustomersVO customersVO : customersList) {
													if (customersVO.getId() == ButtonCell.this.getTableView()
															.getItems()
															.get(ButtonCell.this.getIndex())
															.getId()) {
														updateAutoField(customersVO, combo.getSelectionModel().getSelectedItem());
													}
												}
												fillTableFromData();
											}
											else
											{
												customersList=customersDAO.getCustomers();
												updateTable(customersList);
											}
										}
										catch (Exception e) {
											e.printStackTrace();
										}
									}
								});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LOG.error(e.getMessage());
					}
					LOG.info("Exit : handle");
				}
			});
		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				HBox box = new HBox();
				box.getChildren().addAll(cellEditButton, cellDeleteButton);
				setGraphic(box);
			}
		}

	}
	
	private void updateAutoField(CustomersVO customersVO, String t1) {
		if (t1.equals("Customer Name")) {
			keyword.setText(customersVO.getCustomerName());
		} else if (t1.equals("Company Name")) {
			keyword.setText(customersVO.getCompanyName());
		} else if (t1.equals("State")) {
			keyword.setText(customersVO.getState());
		} else if (t1.equals("City")) {
			keyword.setText(customersVO.getCity());
		} else if (t1.equals("Email Id")) {
			keyword.setText(customersVO.getEmailId());
		} else if (t1.equals("TIN Number")) {
			keyword.setText(customersVO.getTinNumber());
		} else if (t1.equals("Customer Type")) {
			keyword.setText(customersVO.getCustomerType());
		}
	}
}