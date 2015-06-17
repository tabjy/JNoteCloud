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

public class MainApp extends Application {

	public Stage primaryStage;
    private BorderPane rootLayout;
    
    public static void main(String[] args) {
    	ThreadUtil.printCurrentThread("Mainapp");
    	Configuration.initConfig();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JNote Cloud");
        initLayout();
	}

	private void initLayout() {
		FXMLLoader borderPaneLoader = new FXMLLoader();
		borderPaneLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
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
        primaryStage.setWidth(1366);
        primaryStage.setHeight(768);	
        primaryStage.show();
        
        FXMLLoader anchorPaneLoader = new FXMLLoader();
        anchorPaneLoader.setLocation(MainApp.class.getResource("view/Overview.fxml"));
        AnchorPane overview = null;
		try {
			overview = (AnchorPane) anchorPaneLoader.load();
		} catch (IOException e) {
			System.err.println("Cannot locate Overview.fxml!");
			e.printStackTrace();
		}
        rootLayout.setCenter(overview);
        
        // Give the controller access to the main app.
        OverviewController controller = anchorPaneLoader.getController();
        controller.setMainApp(this);
        controller.loadUid();
        controller.downloadNote();	
	}
	
	public void showAlterWindow(String title, String content, String more) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AlertWindowWithInfo.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Alert");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			String css = MainApp.class.getResource("view/"+Configuration.getStyleSheet()+".css").toExternalForm();
	        scene.getStylesheets().clear();
	        scene.getStylesheets().add(css);
			dialogStage.setScene(scene);

			// Set the window into the controller.
			AlertWindowWithInfoController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMsg(title,content,more);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showAlterWindow(String title, String content) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AlertWindow.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Alert");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			String css = MainApp.class.getResource("view/"+Configuration.getStyleSheet()+".css").toExternalForm();
	        scene.getStylesheets().clear();
	        scene.getStylesheets().add(css);
			dialogStage.setScene(scene);

			// Set the window into the controller.
			AlertWindowController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMsg(title,content);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean showWizard() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Wizard.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			String css = MainApp.class.getResource("view/"+Configuration.getStyleSheet()+".css").toExternalForm();
	        scene.getStylesheets().clear();
	        scene.getStylesheets().add(css);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			WizardController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			

			return controller.isNewUser();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String[] showNewUser() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NewUser.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			String css = MainApp.class.getResource("view/"+Configuration.getStyleSheet()+".css").toExternalForm();
	        scene.getStylesheets().clear();
	        scene.getStylesheets().add(css);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			NewUserController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			
			String [] data = new String[3];
			data[0] = controller.getUID();
			data[1] = controller.getEmail();
			data[2] = controller.goBack();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] showValidNewUser(String uid, String email) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ValidNewUser.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			String css = MainApp.class.getResource("view/"+Configuration.getStyleSheet()+".css").toExternalForm();
	        scene.getStylesheets().clear();
	        scene.getStylesheets().add(css);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			ValidNewUserController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(uid,email);
			controller.setMainApp(this);
			//controller.start();
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			//controller.start();
			
			
			String [] data = new String[2];
			data[0] = controller.getUID();
			data[1] = controller.goBack();
			
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public String[] showExistingUser() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ExistingUser.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			String css = MainApp.class.getResource("view/"+Configuration.getStyleSheet()+".css").toExternalForm();
	        scene.getStylesheets().clear();
	        scene.getStylesheets().add(css);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			ExistingUserController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it
			controller.setMainApp(this);
			dialogStage.showAndWait();
			//controller.start();
			
			
			String [] data = new String[2];
			data[0] = controller.getUID();
			data[1] = controller.goBack();
			
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean showConfirm(String line1, String line2) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Confirm.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			String css = MainApp.class.getResource("view/"+Configuration.getStyleSheet()+".css").toExternalForm();
	        scene.getStylesheets().clear();
	        scene.getStylesheets().add(css);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			ConfirmController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setText(line1,line2);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void showAbout(){
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/About.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			String css = MainApp.class.getResource("view/"+Configuration.getStyleSheet()+".css").toExternalForm();
	        scene.getStylesheets().clear();
	        scene.getStylesheets().add(css);
			dialogStage.setScene(scene);
			dialogStage.showAndWait();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}