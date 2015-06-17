package com.tabjy.jnote.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javafx.stage.Stage;

public class AlertWindowController {
	
	@FXML
	Label title;
	@FXML
	Label content;

	
	
	 private Stage dialogStage;

	/**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.centerOnScreen();
    }
    
	public void setMsg(String titleMsg, String contentMsg) {
		//TODO doesn't work!!!!!
		title.setText(titleMsg);
		content.setText(contentMsg);
		java.awt.Toolkit.getDefaultToolkit().beep();
	}
	
	@FXML
	private void handleOK(){
		dialogStage.close();
	}
	
	@FXML
	private void handleQuit(){
		System.exit(0);
	}
}
