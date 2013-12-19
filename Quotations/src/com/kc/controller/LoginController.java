package com.kc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kc.dao.EnquiryDAO;
import com.kc.dao.LoginDAO;
import com.kc.model.ModulesVO;
import com.kc.service.ReminderEmailSendService;

public class LoginController extends Application implements Initializable{

	private static final Logger LOG = LogManager.getLogger(LoginController.class);
	public static Stage primaryStage;
	public static Stage emailStage; 
	public static Scene scene;
	public static BorderPane home;
	public static BorderPane login;
	private LoginDAO loginDAO;
	private EnquiryDAO enquiryDAO;
	public static ModulesVO modulesVO=new ModulesVO();

	@FXML
	private static TextField username;
	@FXML
	private TextField password;
	@FXML
	public static Label message;
	
	public static String userType;
	
	public static TextField getUsername()
	{
		return username;
	}
	
	public static Label emailLabel;
	
	public LoginController()
	{
		loginDAO = new LoginDAO();
		enquiryDAO = new EnquiryDAO();
		enquiryDAO.checkAndUpdateEnquiryNumber();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		username.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		        if(event.getCode() == KeyCode.ENTER) {
		            doLogin();
		        } 
		        event.consume();
		    }
		});
		
		password.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		        if(event.getCode() == KeyCode.ENTER) {
		            doLogin();
		        }
		        event.consume();
		    }
		});
		
	}
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		LOG.info("Enter : start");
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Quotation");
			//this.primaryStage.setResizable(false);
			// Load the root layout from the fxml file
			FXMLLoader loader = new FXMLLoader(
					LoginController.class.getResource("../view/root.fxml"));
			login = (BorderPane) loader.load();
			Scene scene = new Scene(this.login);
			this.scene=scene;
			this.primaryStage.setHeight(710);
			this.primaryStage.setScene(this.scene);
			this.primaryStage.show();
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : start");
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("static-access")
	public void doLogin() {
		LOG.info("Enter : doLogin");
		try {
			if (loginDAO.verifyUser(username.getText(), password.getText())) {
				username.setText("");
				password.setText("");
				FXMLLoader loader = new FXMLLoader(
						LoginController.class
								.getResource("../view/admin.fxml"));
				FXMLLoader subMenuLoader = new FXMLLoader(
						LoginController.class
								.getResource("../view/home-admin.fxml"));
				this.home = (BorderPane) loader.load();
				BorderPane subMenu = (BorderPane) subMenuLoader.load();
				this.home.setCenter(subMenu);
				Scene scene = new Scene(this.home);
				this.primaryStage.setScene(scene);
				this.primaryStage.setResizable(true);
				sendEmails();
			}
			else
			{
				message.setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		LOG.info("Exit : doLogin");
	}
	
	public void sendEmails()
	{
		//Creating a new stage for sending emails
		ReminderEmailSendService emailSendService = new ReminderEmailSendService();
		Thread th = new Thread(emailSendService);
	    th.setDaemon(true);
	    th.start();
		ProgressBar bar = new ProgressBar();
		bar.setPrefSize(200, 24);
		bar.progressProperty().bind(emailSendService.progressProperty());
	    emailStage = new Stage();
	    emailStage.setTitle("Sending Reminder Emails");
	    emailStage.setWidth(400);
	    emailStage.setHeight(200);
	    emailStage.initModality(Modality.WINDOW_MODAL);
	    emailStage.initOwner(LoginController.primaryStage);
	    GridPane layout = new GridPane();
	    layout.setAlignment(Pos.CENTER);
	    layout.setPadding(new Insets(15));
	    layout.setVgap(15);
	    layout.getStylesheets().add(
	            getClass().getResource(
	                "../style/gui.css"
	            ).toExternalForm()
	        );
	    Label label = new Label("Sending Email to : ");
	    label.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    emailLabel = new Label();
	    
	    layout.add(label, 0, 0);
	    layout.add(emailLabel, 1, 0);
	    layout.add(bar, 0, 1, 2, 2);
	    Scene newScene = new Scene(layout);
	    emailStage.setScene(newScene);
	    emailStage.show();
	}
}
