package com.kc.util;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CustomReportTemplate extends VBox 
{
	private HBox firstBox = new HBox();
	private HBox secondBox = new HBox();
	private HBox thirdBox = new HBox();
	private HBox fourthBox = new HBox();
	private HBox fifthBox = new HBox();
	private HBox sixthBox = new HBox();
	
	private VBox detailsBox = new VBox();
	private VBox pieBox = new VBox();
	private PieChart pieChart;
	
	private Label firstBoldLabelLeft = new Label();
	private Label firstBoldLabelRight = new Label();
	private Label firstLabelLeft = new Label();
	private Label firstLabelRight = new Label();
	private Label secondLabelLeft = new Label();
	private Label secondLabelRight = new Label();
	private Label thirdLabelLeft = new Label();
	private Label thirdLabelRight = new Label();
	private Label fourthLabelLeft = new Label();
	private Label fourthLabelRight = new Label();
	private Label fifthLabelLeft = new Label();
	private Label fifthLabelRight = new Label();
	
	public void setFirstBoldLabelLeft(String firstBoldLabelLeft) {
		this.firstBoldLabelLeft.setText(firstBoldLabelLeft);
	}
	public void setFirstBoldLabelRight(String firstBoldLabelRight) {
		this.firstBoldLabelRight.setText(firstBoldLabelRight);
	}
	public void setFirstLabelLeft(String firstLabelLeft) {
		this.firstLabelLeft.setText(firstLabelLeft);
	}
	public void setFirstLabelRight(String firstLabelRight) {
		this.firstLabelRight.setText(firstLabelRight);
	}
	public void setSecondLabelLeft(String secondLabelLeft) {
		this.secondLabelLeft.setText(secondLabelLeft);
	}
	public void setSecondLabelRight(String secondLabelRight) {
		this.secondLabelRight.setText(secondLabelRight);
	}
	public void setThirdLabelLeft(String thirdLabelLeft) {
		this.thirdLabelLeft.setText(thirdLabelLeft);
	}
	public void setThirdLabelRight(String thirdLabelRight) {
		this.thirdLabelRight.setText(thirdLabelRight);
	}
	public void setFourthLabelLeft(String fourthLabelLeft) {
		this.fourthLabelLeft.setText(fourthLabelLeft);
	}
	public void setFourthLabelRight(String fourthLabelRight) {
		this.fourthLabelRight.setText(fourthLabelRight);
	}
	public void setFifthLabelLeft(String fifthLabelLeft) {
		this.fifthLabelLeft.setText(fifthLabelLeft);
	}
	public void setFifthLabelRight(String fifthLabelRight) {
		this.fifthLabelRight.setText(fifthLabelRight);
	}
	public PieChart getPieChart() {
		return pieChart;
	}
	public void setPieChart(PieChart pieChart) {
		this.pieChart = pieChart;
	}
	public CustomReportTemplate()
	{
		
	}
	public CustomReportTemplate(ObservableList<PieChart.Data> pieChartData)
	{
		firstBoldLabelLeft.setFont(Font.font("ARIAL", FontWeight.BOLD, 20));
		firstBoldLabelRight.setFont(Font.font("ARIAL", FontWeight.BOLD, 20));
		
		
		firstBox.setAlignment(Pos.CENTER);
		secondBox.setAlignment(Pos.CENTER);
		thirdBox.setAlignment(Pos.CENTER);
		fourthBox.setAlignment(Pos.CENTER);
		fifthBox.setAlignment(Pos.CENTER);
		sixthBox.setAlignment(Pos.CENTER);
		
		firstBox.setSpacing(10);
		secondBox.setSpacing(10);
		thirdBox.setSpacing(10);
		fourthBox.setSpacing(10);
		fifthBox.setSpacing(10);
		sixthBox.setSpacing(10);
		
		firstBox.setPadding(new Insets(20, 20, 20, 20));
		
		firstBox.getChildren().addAll(firstBoldLabelLeft, firstBoldLabelRight);
		secondBox.getChildren().addAll(firstLabelLeft, firstLabelRight);
		thirdBox.getChildren().addAll(secondLabelLeft, secondLabelRight);
		fourthBox.getChildren().addAll(thirdLabelLeft, thirdLabelRight);
		fifthBox.getChildren().addAll(fourthLabelLeft, fourthLabelRight);
		sixthBox.getChildren().addAll(fifthLabelLeft, fifthLabelRight);
		detailsBox.setAlignment(Pos.CENTER);
		detailsBox.getChildren().addAll(firstBox, secondBox, thirdBox, fourthBox, fifthBox, sixthBox);
		
		pieChart = new PieChart(pieChartData);
		pieChart.setPrefSize(150, 150);
		pieChart.setLegendSide(Side.RIGHT);
		detailsBox.setPrefSize(200, 200);
		pieBox.setPrefSize(370, 250);
		pieBox.setAlignment(Pos.CENTER);
		pieBox.getChildren().addAll(pieChart);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(detailsBox, pieBox);
		
		this.setId("pieBox");
		
	}
}
