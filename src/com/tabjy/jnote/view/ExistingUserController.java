package com.tabjy.jnote.view;

import java.security.NoSuchAlgorithmException;

import com.tabjy.jnote.MainApp;
import com.tabjy.jnote.model.Callback;
import com.tabjy.jnote.model.JsonData;
import com.tabjy.jnote.util.Constants;
import com.tabjy.jnote.util.HTTPRequest;
import com.tabjy.jnote.util.LaunchBrowser;
import com.tabjy.jnote.util.MD5;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ExistingUserController {
	private Stage dialogStage;
	private String uid;
	
	private int count;
	private final int LIMIT = 1000; //just for dev propose, shuold be 1000

	
	@FXML
	private TextField uidField;
	@FXML
	private Button continueBtn;
	@FXML
	private Label hint;

	private boolean responed = false;
	private boolean goBack = false;
	private MainApp mainApp;
	
	/**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.centerOnScreen();
        continueBtn.setDisable(true);
        
        //hint.setText("");
        
        this.dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	if (!responed){
        			System.exit(0); //exit if user choose neither option and directly hit Alt+F4
        		}
            }
        });       
    }
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void uidChanged(){
		if (uidField.getText().length() == 16){
			continueBtn.setDisable(false);
		} else {
			continueBtn.setDisable(true);
		}
	}
	
	@FXML
	private void handleContinue(){
		hint.setText("Verifying your account. This will take a few seconds. Please sit back and relax.");
		uid = uidField.getText();
		//System.out.println(uid);
		continueBtn.setDisable(true);
		verifyUID(uid);
		
	}
	
	private void verifyUID(String uid){
		String post = "uid=" + uid;
		HTTPRequest.sendPost(Constants.getAPI("existingUser"), post, new Callback(){
			
			@Override
			public void onConnectionFailed(){
				mainApp.showAlterWindow("Connection Failed!","Cannot connect to remote server! Please check your Internet connection.\n"+
						"Click OK to retry.");
				verifyUID(uid);
			}
			
			@Override
			public void onSuccess(JsonData jsonData){
				//System.out.println(jsonData.getRaw());
				responed = true;
				handleSuccess();
			}
			
			@Override
			public void onErrorReturned(JsonData jsonData){
				mainApp.showAlterWindow("Verifying User Failed!",
						"Connected to remote server, but an error was returned.\n"+
						"Error Code: "+jsonData.getErrCode() +"\n"+
						"Error Messege: " + jsonData.getErrMsg() + "\n" +
						"Raw data recived is printed below:",jsonData.getRaw());
				goBack = false;
				handleFailed();
			}
		});
	}
	
	private void handleSuccess(){
		dialogStage.close();
	}
	
	
	private void handleFailed(){
		hint.setText("Please confirm your UID and try again.");
	}
	
	@FXML
	private void handleForgot(){
		LaunchBrowser.openURL("http://jnote.tabjy.com/forgot");
	}
	
	@FXML
	private void handleBack(){
		goBack = true;
		dialogStage.close();
	}
	
	public String goBack(){
		// Flag: 1: go back; 0: continue
		if (goBack){
			return "1";
		} else {
			return "0";
		}
	}
	
	public String getUID(){
		return uid;
	}
	
}
