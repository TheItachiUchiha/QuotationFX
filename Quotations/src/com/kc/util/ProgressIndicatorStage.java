package com.kc.util;

import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressIndicatorStage
{
	Stage progressStage;
	public ProgressIndicatorStage(Stage stage)
	{
		progressStage = new Stage();
		progressStage.initStyle(StageStyle.UNDECORATED);
		progressStage.initOwner(stage);
	}
	
	public void showProgress()
	{
		ProgressIndicator indicator = new ProgressIndicator();
		Scene scene =new Scene(indicator,30, 30);
		progressStage.setScene(scene);
		progressStage.show();
	}
	public void closeProgress()
	{
		progressStage.close();
	}
}
