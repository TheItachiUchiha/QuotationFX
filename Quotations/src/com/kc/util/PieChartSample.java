package com.kc.util;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;
 
public class PieChartSample extends Application {
 
    @Override public void start(Stage stage) {
    	try{
        Scene scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);
 
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30));
        CustomReportTemplate chart = new CustomReportTemplate(pieChartData);
        chart.getPieChart().setTitle("Imported Fruits");
        chart.setFirstBoldLabelLeft("ABCDEF");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    	}
    	catch (Throwable e) {
    		e.printStackTrace();
    	}
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}