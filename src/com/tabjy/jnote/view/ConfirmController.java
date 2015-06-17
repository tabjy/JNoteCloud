package com.tabjy.jnote.view;

import com.tabjy.jnote.util.LaunchBrowser;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfirmController {
	private Stage dialogStage;
	boolean isOkClicked = false;
	boolean responed = false;
	
	@FXML
	private Label line1;
	@FXML
	private Label line2;
	
	/**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.centerOnScreen();
    }
	
	public void setText(String line1, String line2){
		this.line1.setText(line1);
		this.line2.setText(line2);
	}
	
	@FXML
	private void handleOk(){
		isOkClicked = true;
		responed = true;
		dialogStage.close();
	}
	
	@FXML
	private void handleCancel(){
		isOkClicked = false;
		responed = true;
		dialogStage.close();
	}
	
	public boolean isOkClicked(){
		return isOkClicked;
		
	}
	
}
