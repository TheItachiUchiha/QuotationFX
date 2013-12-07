package com.kc.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.ComponentsDAO;
import com.kc.dao.CustomersDAO;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.PriceEstimationDAO;
import com.kc.dao.ProductsDAO;
import com.kc.model.ComponentsVO;
import com.kc.model.CustomersVO;
import com.kc.model.EnquiryVO;
import com.kc.model.EnquiryViewVO;
import com.kc.util.Validation;

public class PriceEstimationNewController implements Initializable {

	private static final Logger LOG = LogManager.getLogger(PriceEstimationNewController.class);
	EnquiryDAO enquiryDAO;
	CustomersDAO customersDAO;
	private Validation validate;
	ComponentsDAO componentsDAO;
	ProductsDAO productsDAO;
	PriceEstimationDAO priceEstimationDAO;
	int productId;
	public PriceEstimationNewController()
	{
		enquiryDAO = new EnquiryDAO();
		customersDAO = new CustomersDAO();
		componentsDAO = new ComponentsDAO();
		productsDAO = new ProductsDAO();
		priceEstimationDAO = new PriceEstimationDAO();
		this.validate = new Validation();
	}
	public static Stage stage;
	private ObservableList<ComponentsVO> componentsList=FXCollections.observableArrayList();
	@FXML
    private TableView<ComponentsVO> componentTable;
	@FXML private TableColumn<ComponentsVO, String> name;
    @FXML private TableColumn<ComponentsVO, String> category;
    @FXML private TableColumn<ComponentsVO, String> subCategory;
    @FXML private TableColumn<ComponentsVO, String> vendor;
    @FXML private TableColumn<ComponentsVO, String> model;
    @FXML private TableColumn<ComponentsVO, String> type;
    @FXML private TableColumn<ComponentsVO, String> size;
    @FXML private TableColumn<ComponentsVO, String> costPrice;
    @FXML private TableColumn<ComponentsVO, String> dealerPrice;
    @FXML private TableColumn<ComponentsVO, String> endUserPrice;
    @FXML private TableColumn action;
    @FXML private TableColumn quantity;
    @FXML
    private Label costPriceTotal;

    @FXML
    private Button createPriceEstimation;
    @FXML
    private Label dealerPriceTotal;

    @FXML
    private TextArea eaddress;

    @FXML
    private TextField ecity;

    @FXML
    private TextField ecompanyName;

    @FXML
    private TextField econtactNumber;

    @FXML
    private TextField ecustomerFile;

    @FXML
    private TextField ecustomerName;

    @FXML
    private TextField ecustomerType;

    @FXML
    private TextField eemailId;

    @FXML
    private TextField eenquiryType;
    @FXML
    private Label endUserPriceTotal;

    @FXML
    private Button enquiryDetails;

    @FXML
    private TextField eproductName;

    @FXML
    private TextField epurchasePeriod;

    @FXML
    private TextField ereferedBy;

    @FXML
    private TextArea erequirements;

    @FXML
    private TextField estate;

    @FXML
    private VBox estimationVBox;

    @FXML
    private TextField etinNumber;

    @FXML
    private TextField marginValue;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private TextField productName;
    @FXML
    private ComboBox<String> referenceCombo;

    @FXML
    private TextField referenceNo;

    @FXML
    private Button search;
    @FXML
    private Label totalProfit;
    @FXML
    private Button viewFile;

    @FXML
    private ComboBox<String> yearCombo;
	double costPriceValue=0;
	double dealerPriceValue=0;
	double endUserPriceValue=0;
	
	private ObservableList<String> monthList = FXCollections.observableArrayList();
	private ObservableList<String> yearList = FXCollections.observableArrayList();
	private ObservableList<String> refList = FXCollections.observableArrayList();
	private ObservableList<EnquiryViewVO> enquiryViewList = FXCollections.observableArrayList();
	private ObservableList<EnquiryVO> enquiryList = FXCollections.observableArrayList();
	private ObservableList<CustomersVO> customerList = FXCollections.observableArrayList();
	private ObservableList<ComponentsVO> componentList = FXCollections.observableArrayList();
	 SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			componentTable.setItems(componentsList);
			componentTable.setEditable(true);
			
			final Callback<TableColumn<ComponentsVO, Integer>, TableCell<ComponentsVO, Integer>> cellFactory = new Callback<TableColumn<ComponentsVO, Integer>, TableCell<ComponentsVO, Integer>>() {
				public TableCell<ComponentsVO, Integer> call(TableColumn<ComponentsVO, Integer> p) {
					return new EditingCell();
				}
			};
			createPriceEstimation.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					
					estimationVBox.setVisible(true);
					try {
							componentList = productsDAO.getComponentsForProduct(productId);
							fillComponentTable();
							costPriceTotal.setText(String.valueOf(costPriceValue));
							endUserPriceTotal.setText(String.valueOf(endUserPriceValue));
							dealerPriceTotal.setText(String.valueOf(dealerPriceValue));
						} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			quantity.setCellValueFactory(new Callback<CellDataFeatures<ComponentsVO, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(CellDataFeatures<ComponentsVO, Integer> cellData) {
                  ComponentsVO item = cellData.getValue();
                  if (item == null) {
                    return null;
                  } else {
                	  /*ObservableMap<String,ItemTypeVO> itemTypesMap = FXCollections.observableHashMap();
		 		    	itemTypesMap = item.getListType();
		 		    */
		 		    	return new ReadOnlyObjectWrapper<Integer>(1);
		 		    
                  }
                }
              });
			quantity.setCellFactory(cellFactory);
	 		
	 		quantity.setOnEditCommit(
	 				new EventHandler<TableColumn.CellEditEvent<ComponentsVO, Integer>>() {
	 				public void handle(TableColumn.CellEditEvent<ComponentsVO, Integer> t) {
	 				((ComponentsVO)t.getTableView().getItems().get(
	 				t.getTablePosition().getRow())).setQuantity(t.getNewValue());
	 				}
	 				});
	 		
	 		
	 		
	 		
	 	//componentTable.getColumns().add(quantity);
			
			action.setSortable(false);
	         
	        action.setCellValueFactory(
	                new Callback<TableColumn.CellDataFeatures<ComponentsVO, Boolean>,
	                ObservableValue<Boolean>>() {
	 
	            @Override
	            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ComponentsVO, Boolean> p) {
	                return new SimpleBooleanProperty(p.getValue() != null);
	            }
	        });
	 
	        action.setCellFactory(
	                new Callback<TableColumn<ComponentsVO, Boolean>, TableCell<ComponentsVO, Boolean>>() {
	 
	            @Override
	            public TableCell<ComponentsVO, Boolean> call(TableColumn<ComponentsVO, Boolean> p) {
	                return new ButtonCell();
	            }
	         
	        });
	        referenceCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0,
						String arg1, String arg2) {
					componentTable.getSelectionModel().clearSelection();
					costPriceValue=0;
					dealerPriceValue=0;
					endUserPriceValue=0;
					
				}
			});
	        referenceNo.setEditable(false);
	        productName.setEditable(false);
			eenquiryType.setEditable(false);
			eproductName.setEditable(false);
			erequirements.setEditable(false);
			ecustomerFile.setEditable(false);
			ecustomerType.setEditable(false);
			ecustomerName.setEditable(false);
			etinNumber.setEditable(false);
			eemailId.setEditable(false);
			ereferedBy.setEditable(false);
			eaddress.setEditable(false);
			estate.setEditable(false);
			ecity.setEditable(false);
			econtactNumber.setEditable(false);
			epurchasePeriod.setEditable(false);
			monthList.addAll(Arrays.asList(CommonConstants.MONTHS.split(",")));
			yearList.addAll(Arrays.asList(CommonConstants.YEARS.split(",")));
			monthCombo.setItems(monthList);
			yearCombo.setItems(yearList);
			enquiryList = enquiryDAO.getEnquries();
			customerList = customersDAO.getCustomers();
			enquiryViewList = fillEnquiryViewListFromEnquiryList(enquiryList);
			search.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					try
					{
						refList.clear();
						for(EnquiryViewVO enquiryVO : enquiryViewList)
						{
							if(new SimpleDateFormat("MMM").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(monthCombo.getSelectionModel().getSelectedItem())&&new SimpleDateFormat("yyyy").format(formatter.parse(enquiryVO.getDateOfEnquiry())).equalsIgnoreCase(yearCombo.getSelectionModel().getSelectedItem())&&(enquiryDAO.estimationConfirm(enquiryVO.getId())).equalsIgnoreCase("N"))
							{
								refList.add(enquiryVO.getReferenceNo());
							}
						}
						if(refList.isEmpty())
						{
							Dialogs.showInformationDialog(LoginController.primaryStage,CommonConstants.WARNING_MESSAGE);
						}
						else
						{
							referenceCombo.setItems(refList);	
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
			
			enquiryDetails.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					for(EnquiryViewVO enquiryViewVO: enquiryViewList)
					{
						if(referenceCombo.getSelectionModel().getSelectedItem().equals(enquiryViewVO.getReferenceNo()))
						{
							productId=enquiryViewVO.getProductId();
							
							referenceNo.setText(enquiryViewVO.getReferenceNo());
							productName.setText(enquiryViewVO.getProductName());
							eenquiryType.setText(enquiryViewVO.getEnquiryType());
							eproductName.setText(enquiryViewVO.getProductName());
							erequirements.setText(enquiryViewVO.getCustomerRequirement());
							ecustomerName.setText(enquiryViewVO.getCustomerName());
							ecompanyName.setText(enquiryViewVO.getCompanyName());
							etinNumber.setText(enquiryViewVO.getTinNumber());
							eemailId.setText(enquiryViewVO.getEmailId());
							ereferedBy.setText(enquiryViewVO.getReferedBy());
							ecustomerType.setText(enquiryViewVO.getCustomerType());
							eaddress.setText(enquiryViewVO.getAddress());
							estate.setText(enquiryViewVO.getState());
							ecity.setText(enquiryViewVO.getCity());
							econtactNumber.setText(enquiryViewVO.getContactNumber());
							ecustomerFile.setText(enquiryViewVO.getCustomerFile());
							epurchasePeriod.setText(enquiryViewVO.getPurchasePeriod());
							
						}
					}
					
				}
			});

			viewFile.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent paramT) {
					try {
						Desktop.getDesktop().open(new File(ecustomerFile.getText()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public TableView<ComponentsVO> getComponentTable() {
			return componentTable;
		}
	public void addComponent()
	{
		LOG.info("Enter : addComponent");
		try{
			final FXMLLoader menuLoader = new FXMLLoader(this.getClass()
					.getResource("../view/product-component-add.fxml"));
			BorderPane componentadd;
			Stage componentAddStage = new Stage();
			stage = componentAddStage;
			componentadd = (BorderPane) menuLoader.load();
			Scene componentAddscene = new Scene(componentadd);
			componentAddStage.setResizable(false);
			componentAddStage.setHeight(500);
			componentAddStage.setWidth(900);
			componentAddStage.initModality(Modality.WINDOW_MODAL);
			componentAddStage.initOwner(LoginController.primaryStage);
			componentAddStage.setScene(componentAddscene);
			componentAddStage.show();
			
			componentAddStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent paramT) {
					ObservableList<ComponentsVO> list = ((ProductComponentAddController)menuLoader.getController()).getAddedComponentList();
					name.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentName"));
					category.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentCategory"));
					subCategory.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("subCategory"));
					vendor.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("vendor"));
					model.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("model"));
					type.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("type"));
					size.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("size"));
					costPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("costPrice"));
					dealerPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("dealerPrice"));
					endUserPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("endUserPrice"));
					for(ComponentsVO componentsVO : list)
					{
						if(componentList.size()==0)
						{
							componentList.addAll(list);
						}
						else
						{
							List<Integer> ids = new ArrayList<Integer>();
							for(ComponentsVO componentsVO2 : componentList)
							{
								ids.add(componentsVO2.getId());
							}
							if(!ids.contains(componentsVO.getId()))
							{
								componentList.add(componentsVO);
							}
						}
					}
				}
					
			});		
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : addComponent");
	}
	public void savePriceEstimation()
	{
		EnquiryVO enquiryVO=new EnquiryVO();
		enquiryVO.setList(componentTable.getItems());
		for(EnquiryViewVO enquiryViewVO : enquiryViewList)
		{
			if(enquiryViewVO.getReferenceNo().equals(referenceCombo.getSelectionModel().getSelectedItem()))
			{
				enquiryVO.setId(enquiryViewVO.getId());	
			}
		}
		priceEstimationDAO.savePriceEstimation(enquiryVO);
	}
	public void fillComponentTable()
	{
		name.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentName"));
		category.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("componentCategory"));
		subCategory.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("subCategory"));
		vendor.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("vendor"));
		model.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("model"));
		type.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("type"));
		size.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("size"));
		costPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("costPrice"));
		dealerPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("dealerPrice"));
		endUserPrice.setCellValueFactory(new PropertyValueFactory<ComponentsVO, String>("endUserPrice"));
		quantity.setCellValueFactory(new PropertyValueFactory<ComponentsVO, Integer>("quantity"));
		for(ComponentsVO componentsVO : componentList)
		{
		costPriceValue+=Double.parseDouble(componentsVO.getCostPrice());
		dealerPriceValue+=Double.parseDouble(componentsVO.getDealerPrice());
		endUserPriceValue+=Double.parseDouble(componentsVO.getEndUserPrice());
		}
		componentTable.setItems(componentList);
	}
	
	public void deleteComponents(ComponentsVO componentsVO)
	{
		LOG.info("Enter : deleteComponents");
		try{
			componentTable.getItems().remove(componentsVO);
		}
		catch (Exception e) {
			
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : deleteComponents");
	}
	public ObservableList<EnquiryViewVO> fillEnquiryViewListFromEnquiryList(ObservableList<EnquiryVO> enquiryList)
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
			enquiryViewVO.setQuotationPreparation(enquiryVO.getQuotationPreparation());
			enquiryViewVO.setEmailSent(enquiryVO.getEmailSent());
			enquiryViewVO.setSales(enquiryVO.getSales());
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
	private class ButtonCell extends TableCell<ComponentsVO, Boolean> {

		Image buttonDeleteImage = new Image(getClass().getResourceAsStream(
				"../style/delete.png"));
		final Button cellDeleteButton = new Button("", new ImageView(
				buttonDeleteImage));
		ButtonCell() {

			cellDeleteButton.getStyleClass().add("editDeleteButton");
			cellDeleteButton.setTooltip(new Tooltip("Delete"));

			cellDeleteButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					deleteComponents(ButtonCell.this.getTableView().getItems()
							.get(ButtonCell.this.getIndex()));
				}
			});
		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				HBox box = new HBox();
				box.getChildren().addAll(cellDeleteButton);
				setGraphic(box);
			}
		}
	}
	
	class EditingCell extends TableCell<ComponentsVO, Integer> {
		 
	      private TextField textField;
	     
	      public EditingCell() {}
	     
	      @Override
	      public void startEdit() {
	          super.startEdit();
	         
	          if (textField == null) {
	              createTextField();
	          }
	         
	          setGraphic(textField);
	          setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	          //textField.selectAll();
	          Platform.runLater(new Runnable() {
		            @Override
		            public void run() {
		                textField.requestFocus();
		                textField.selectAll();
		            }
		        });
	      }
	     
	      @Override
	      public void cancelEdit() {
	          super.cancelEdit();
	         
	          setText(String.valueOf(getItem()));
	          setContentDisplay(ContentDisplay.TEXT_ONLY);
	      }
	 
	      @Override
	      public void updateItem(Integer item, boolean empty) {
	          super.updateItem(item, empty);
	         
	          if (empty) {
	              setText(null);
	              setGraphic(null);
	          } else {
	              if (isEditing()) {
	                  if (textField != null) {
	                      textField.setText(getString());
	                  }
	                  setGraphic(textField);
	                  setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	              } else {
	                  setText(getString());
	                  setContentDisplay(ContentDisplay.TEXT_ONLY);
	              }
	          }
	      }
	
	      private void createTextField() {
	          textField = new TextField();
	          validate.allowDigit(textField);
	          //textField.setText(getString());
	          //textField.setText("0");
	          textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
	          
	         /* textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

	        	    @Override
	        	    public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
	        	        if (!arg2) {
	        	            commitEdit(Integer.parseInt(textField.getText()));
	        	        }
	        	    }
	        	});*/
	          
	          textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
		            @Override
		            public void handle(KeyEvent t) {
		                if (t.getCode() == KeyCode.ENTER) {
		                	//For not allowing to commit when Enter is pressed with empty Textfield
		                	if (!validate.isEmpty(textField)){
			                	if (!validate.isWord(textField.getText())){
		                    commitEdit(Integer.parseInt(textField.getText()));
			                	}}
		                } else if (t.getCode() == KeyCode.ESCAPE) {
		                    cancelEdit();
		                } else if (t.getCode() == KeyCode.TAB) {
		                	//For not allowing to commit when TAB is pressed with empty Textfield
		                	if (!validate.isEmpty(textField)){
			                	if (!validate.isWord(textField.getText())){
		                    commitEdit(Integer.parseInt(textField.getText()));
			                	}}
		                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
		                    if (nextColumn != null) {
		                        getTableView().edit(getTableRow().getIndex(), nextColumn);
		                    }
		                }
		            }
		        });
	          textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
		            @Override
		            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		                if (!newValue && textField != null) {
		                	//For not allowing to commit when mouse is pressed with empty Textfield
		                	if (!validate.isEmpty(textField)){
		                	if (!validate.isWord(textField.getText())){
		                		  commitEdit(Integer.parseInt(textField.getText()));
		                	}
		                	}
		                	
		                			                  
		                }
		            }
		        });
	      }
	     
	      private String getString() {
	          return getItem() == null ? "" : getItem().toString();
	      }
	      
	      private TableColumn<ComponentsVO, ?> getNextColumn(boolean forward) {
		        List<TableColumn<ComponentsVO, ?>> columns = new ArrayList<>();
		        for (TableColumn<ComponentsVO, ?> column : getTableView().getColumns()) {
		            columns.addAll(getLeaves(column));
		        }
		        //There is no other column that supports editing.
		        if (columns.size() < 2) {
		            return null;
		        }
		        int currentIndex = columns.indexOf(getTableColumn());
		        int nextIndex = currentIndex;
		        if (forward) {
		            nextIndex++;
		            if (nextIndex > columns.size() - 1) {
		                nextIndex = 0;
		            }
		        } else {
		            nextIndex--;
		            if (nextIndex < 0) {
		                nextIndex = columns.size() - 1;
		            }
		        }
		        return columns.get(nextIndex);
		    }
	      
	      	private List<TableColumn<ComponentsVO, ?>> getLeaves(TableColumn<ComponentsVO, ?> root) {
		        List<TableColumn<ComponentsVO, ?>> columns = new ArrayList<>();
		        if (root.getColumns().isEmpty()) {
		            //We only want the leaves that are editable.
		            if (root.isEditable()) {
		                columns.add(root);
		            }
		            return columns;
		        } else {
		            for (TableColumn<ComponentsVO, ?> column : root.getColumns()) {
		                columns.addAll(getLeaves(column));
		            }
		            return columns;
		        }
	      	}
	  }
}
