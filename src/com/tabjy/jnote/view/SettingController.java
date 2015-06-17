package com.tabjy.jnote.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;




import com.tabjy.jnote.MainApp;
import com.tabjy.jnote.SettingWindow;

public class SettingController {
	@FXML
	BorderPane container;
	
	private SettingWindow mainApp;
	private Stage dialogStage;

	public void setMainApp(SettingWindow settingWindow) {
		this.mainApp = settingWindow;
	}

	public void setDialogStage(Stage primaryStage) {
		 this.dialogStage = dialogStage;
		 //this.dialogStage.centerOnScreen();
		 loadAccountPane();
	}
	
	@FXML
	private void loadAccountPane(){
		FXMLLoader anchorPaneLoader = new FXMLLoader();
        anchorPaneLoader.setLocation(MainApp.class.getResource("view/Account.fxml"));
        AnchorPane overview = null;
		try {
			overview = (AnchorPane) anchorPaneLoader.load();
		} catch (IOException e) {
			System.err.println("Cannot locate Account.fxml!");
			e.printStackTrace();
		}
		container.setCenter(overview);
		AccountController controller = anchorPaneLoader.getController();
		controller.setDefault();
	}
	
	@FXML
	private void loadAppearancePane(){
		FXMLLoader anchorPaneLoader = new FXMLLoader();
        anchorPaneLoader.setLocation(MainApp.class.getResource("view/Appearance.fxml"));
        AnchorPane overview = null;
		try {
			overview = (AnchorPane) anchorPaneLoader.load();
		} catch (IOException e) {
			System.err.println("Cannot locate Appearance.fxml!");
			e.printStackTrace();
		}
		container.setCenter(overview);
		AppearanceController controller = anchorPaneLoader.getController();
		controller.setDefault();
	}
	
	@FXML
	private void loadMiscPane(){
		FXMLLoader anchorPaneLoader = new FXMLLoader();
        anchorPaneLoader.setLocation(MainApp.class.getResource("view/Misc.fxml"));
        AnchorPane overview = null;
		try {
			overview = (AnchorPane) anchorPaneLoader.load();
		} catch (IOException e) {
			System.err.println("Cannot locate Appearance.fxml!");
			e.printStackTrace();
		}
		container.setCenter(overview);
		MiscController controller = anchorPaneLoader.getController();
		controller.setDefault();
	}

}
