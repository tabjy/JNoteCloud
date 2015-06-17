package com.tabjy.jnote;

import java.io.File;
import java.io.IOException;











import java.net.URL;

import com.tabjy.jnote.util.Configuration;
import com.tabjy.jnote.util.ThreadUtil;
import com.tabjy.jnote.view.AlertWindowController;
import com.tabjy.jnote.view.ConfirmController;
import com.tabjy.jnote.view.ExistingUserController;
import com.tabjy.jnote.view.OverviewController;
import com.tabjy.jnote.view.AlertWindowWithInfoController;
import com.tabjy.jnote.view.SettingController;
import com.tabjy.jnote.view.WizardController;
import com.tabjy.jnote.view.NewUserController;
import com.tabjy.jnote.view.ValidNewUserController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SettingWindow extends Application {

	public Stage primaryStage;
    private BorderPane rootLayout;
    
    public static void main(String[] args) {
    	ThreadUtil.printCurrentThread("Mainapp");
    	Configuration.initConfig();
		//launch(args);
    	SettingWindow sw = new SettingWindow();
    	sw.start(new Stage());
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Setting");
        initLayout();
	}

	private void initLayout() {
		FXMLLoader borderPaneLoader = new FXMLLoader();
		borderPaneLoader.setLocation(SettingWindow.class.getResource("view/RootLayout.fxml"));
        try {
			rootLayout = (BorderPane) borderPaneLoader.load();
		} catch (IOException e) {
			System.err.println("Cannot locate RootLayout.fxml!");
			e.printStackTrace();
		}
        Scene scene = new Scene(rootLayout);
        
        String css = MainApp.class.getResource("view/"+Configuration.getStyleSheet()+".css").toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);	
        primaryStage.show();
        
        FXMLLoader anchorPaneLoader = new FXMLLoader();
        anchorPaneLoader.setLocation(MainApp.class.getResource("view/Setting.fxml"));
        AnchorPane overview = null;
		try {
			overview = (AnchorPane) anchorPaneLoader.load();
		} catch (IOException e) {
			System.err.println("Cannot locate Setting.fxml!");
			e.printStackTrace();
		}
        rootLayout.setCenter(overview);
        
        SettingController controller = anchorPaneLoader.getController();
        controller.setDialogStage(primaryStage);
        
        
	}
	
	
}