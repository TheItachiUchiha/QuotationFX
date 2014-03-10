package com.kc.util;


import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressIndicatorStage
{
	Stage progressStage;
	public ProgressIndicatorStage(Stage stage)
	{
		progressStage = new Stage();
		progressStage.initStyle(StageStyle.TRANSPARENT);
		progressStage.initModality(Modality.WINDOW_MODAL);
		progressStage.initOwner(stage);
	}
	
	public void showProgress()
	{
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				ProgressIndicator indicator = new ProgressIndicator();
				Scene scene =new Scene(indicator,30, 30);
				progressStage.setScene(scene);
				progressStage.show();
			}
		});
		
	}
	public void closeProgress()
	{
		progressStage.close();
	}
}
