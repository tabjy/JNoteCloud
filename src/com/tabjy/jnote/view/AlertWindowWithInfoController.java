package com.tabjy.jnote.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AlertWindowWithInfoController {
	
	@FXML
	Label title;
	@FXML
	Label content;
	@FXML
	TextArea more;
	@FXML
	Button ok;
	
	
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
    
	public void setMsg(String titleMsg, String contentMsg, String moreMsg) {
		more.setWrapText(true);
		title.setText(titleMsg);
		content.setText(contentMsg);
		more.setText(moreMsg);
		java.awt.Toolkit.getDefaultToolkit().beep();
	}
	
	@FXML
	private void handleOK(){
		dialogStage.close();
	}
}
