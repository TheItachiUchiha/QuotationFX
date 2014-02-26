package com.kc.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.constant.CommonConstants;
import com.kc.dao.HelpDAO;

public class HelpThemeController implements Initializable{
	
	private static final Logger LOG = LogManager.getLogger(HelpThemeController.class);
	
	 	@FXML
	    private ToggleButton blue;

	    @FXML
	    private ToggleGroup colorToggle;

	    @FXML
	    private ToggleButton cyan;

	    @FXML
	    private ToggleButton green;

	    @FXML
	    private ToggleButton pink;
	    
	    HelpDAO helpDAO;
	    
	    private Map<String, String> theme = new HashMap<String, String>();
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
	    
	    public HelpThemeController() {
				
	    	helpDAO = new HelpDAO();
		}

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		
		theme = helpDAO.getBackground();
		if(theme.get(CommonConstants.KEY_BACKGROUND).equalsIgnoreCase("background-blue"))
		{
			blue.setSelected(true);
		}
		else if(theme.get(CommonConstants.KEY_BACKGROUND).equalsIgnoreCase("background-pink"))
		{
			pink.setSelected(true);
		}
		else if(theme.get(CommonConstants.KEY_BACKGROUND).equalsIgnoreCase("background-green"))
		{
			green.setSelected(true);
		}
		else if(theme.get(CommonConstants.KEY_BACKGROUND).equalsIgnoreCase("background-cyan"))
		{
			cyan.setSelected(true);
		}
			
		
		colorToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(
					ObservableValue<? extends Toggle> paramObservableValue,
					Toggle paramT1, Toggle paramT2) {
					
					if(blue.isSelected())
					{
						theme.put(CommonConstants.KEY_BACKGROUND, "background-blue");
					}
					else if(green.isSelected())
					{
						theme.put(CommonConstants.KEY_BACKGROUND, "background-green");
					}
					else if(pink.isSelected())
					{
						theme.put(CommonConstants.KEY_BACKGROUND, "background-pink");
					}
					else if(cyan.isSelected())
					{
						theme.put(CommonConstants.KEY_BACKGROUND, "background-cyan");
					}
					helpDAO.saveConfiguration(theme, simpleDateFormat.format(new Date()));
					theme = helpDAO.getBackground();
					LoginController.home.getStyleClass().add(theme.get(CommonConstants.KEY_BACKGROUND));
			}
		});
	}

}
