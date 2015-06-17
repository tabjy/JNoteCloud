package com.tabjy.jnote.view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import com.tabjy.jnote.MainApp;
import com.tabjy.jnote.util.AsyncNewUser;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ValidNewUserController {
	private Stage dialogStage;
	private String uid;
	private String email;
	
	public boolean responed = false;
	public boolean goBack = false;
	
	public MainApp mainApp;
	
	@FXML
	private Button continueBtn;
	@FXML
	private Label hintLabel;
	@FXML
	private Label uidLabel;
	@FXML
	private Hyperlink hyperLink;
	

	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.centerOnScreen();
		continueBtn.setDisable(true);
        
        this.dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	if (!responed){
        			System.exit(0); //exit if user choose neither option and directly hit Alt+F4
        		}
            }
        });
        
        this.dialogStage.setOnShown(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent we) {
				start();
			}
        });
	}
	
	public void setData(String uid, String email) {
		this.uid = uid;
		this.email = email;
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void start() {
		String post = "uid="+uid+"&email="+email;
		AsyncNewUser asyncTask = new AsyncNewUser();
		asyncTask.setParent(this);
		asyncTask.setPost(post);
		Platform.runLater(asyncTask);
	}
	
	public void handleFailed(){
		hintLabel.setText("Creating user failed. Plase go back and try again.");
		uidLabel.setText("ERROR!");
	}
	
	public void handleSuccess(){
		continueBtn.setDisable(false);
		hyperLink.setDisable(false);
		hintLabel.setText("Congratulations! You are ready to go. An email with your UID has been sent.\n"+
		"If you are missing the email, please check your Spam or Junk folder.");
		uidLabel.setText(uid);
	}
	
	@FXML
	private void copyClipboard(){
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard(); 
        Transferable tText = new StringSelection(uid); 
        clip.setContents(tText, null); 
	}
	
	@FXML
	private void handleContinue(){
		dialogStage.close();
	}
	
	@FXML
	private void handleBack(){
		goBack = true;
		dialogStage.close();
	}
	
	public String goBack(){
		// Flag=> 1: go back; 0: continue
		if (goBack){
			return "1";
		} else {
			return "0";
		}
	}

	public String getUID() {
		return uid;
	}

	
}
