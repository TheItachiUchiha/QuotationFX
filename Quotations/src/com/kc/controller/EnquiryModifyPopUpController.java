package com.kc.controller;

import com.mytdev.javafx.scene.control.AutoCompleteTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class EnquiryModifyPopUpController {

    @FXML
    private TextArea address;

    @FXML
    private ComboBox<?> categoryCombo;

    @FXML
    private TextField city;

    @FXML
    private TextField companyName;

    @FXML
    private TextField contactNumber;

    @FXML
    private RadioButton custom;

    @FXML
    private TextField customerName;

    @FXML
    private TextArea customerRequirements;

    @FXML
    private ComboBox<?> customerTypeCombo;

    @FXML
    private AutoCompleteTextField<?> emailId;

    @FXML
    private GridPane enquiryGrid;

    @FXML
    private Label messageNewEnquiry;

    @FXML
    private ComboBox<?> nameCombo;

    @FXML
    private HBox productHbox;

    @FXML
    private AutoCompleteTextField<?> productName;

    @FXML
    private ToggleGroup productType;

    @FXML
    private VBox productVbox;

    @FXML
    private TextField purchasePeriod;

    @FXML
    private AutoCompleteTextField<?> referedBy;

    @FXML
    private RadioButton standard;

    @FXML
    private TextField state;

    @FXML
    private ComboBox<?> subcategoryCombo;

    @FXML
    private AutoCompleteTextField<?> tinNumber;


    @FXML
    void saveEnquiries(ActionEvent event) {
    }

    @FXML
    void initialize() {
        

    }

}
