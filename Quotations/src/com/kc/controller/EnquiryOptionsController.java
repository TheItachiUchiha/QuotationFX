package com.kc.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EnquiryOptionsController implements Initializable {
	
	@FXML
	private GridPane optionsGrid;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final TextField filePath = new TextField();
		 Button browse = new Button();
       browse.setText("Browse");
       final HBox hBox =new HBox(5);
       hBox.getChildren().addAll(filePath,browse);
       optionsGrid.add(hBox,1,1);

       browse.setOnAction(new EventHandler<ActionEvent>() {

     @Override
     public void handle(ActionEvent event) {
  	   Stage stage=new Stage();
        FileChooser fileChooser = new FileChooser();
        File tempFile = fileChooser.showOpenDialog(stage);
        if(tempFile!=null){
                filePath.setText(tempFile.getPath());
        }
       
     }
       });
		
	}

}
