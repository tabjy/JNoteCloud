package com.tabjy.jnote.view;

import com.tabjy.jnote.util.LaunchBrowser;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WizardController {
	private Stage dialogStage;
	boolean isNewUser;
	boolean responed = false;
	
	/**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.centerOnScreen();
        
        this.dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	if (!responed){
        			System.exit(0); //exit if user choose neither option and directly hit Alt+F4
        		}
            }
        });
    }
	
	@FXML
	private void handleNewUser(){
		isNewUser = true;
		responed = true;
		dialogStage.close();
	}
	
	@FXML
	private void handleExistingUser(){
		isNewUser = false;
		responed = true;
		dialogStage.close();
	}
	
	@FXML
	private void handleForgot(){
		LaunchBrowser.openURL("http://jnote.tabjy.com/forgot");
	}
	
	public boolean isNewUser(){
		return isNewUser;
		
	}
	
}
