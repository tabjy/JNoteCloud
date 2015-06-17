package com.tabjy.jnote.view;

import java.security.NoSuchAlgorithmException;

import com.tabjy.jnote.util.MD5;

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

public class NewUserController {
	private Stage dialogStage;
	private String uid;
	private String email = null;
	
	private int count;
	private final int LIMIT = 500; //just for dev propose, shuold be 1000

	
	@FXML
	private TextField emailField;
	@FXML
	private Button continueBtn;
	@FXML
	private AnchorPane mousePane;
	@FXML
	private Label progress;
	@FXML
	private Label hint;
	
	private String randomBuffer = "";
	
	private boolean responed = false;
	private boolean goBack = false;
	
	/**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
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
        
        mousePane.setOnMouseMoved(new  EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (count <= LIMIT){
					randomBuffer += +mouseEvent.getScreenX()+""+mouseEvent.getScreenY();
					progress.setText(Math.round(((double)count/(double)LIMIT)*100)+"");				
				}
				if (count == LIMIT) {
					try {
						uid = MD5.getMD5(randomBuffer);
						continueBtn.setDisable(false);
						hint.setText("You may now continue");
						System.out.println("New UID: " + uid);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					responed = true;
				}
				//System.out.println(count + ": x:" +mouseEvent.getScreenX()+" y:"+mouseEvent.getScreenY() );
				//System.out.println(count);
				count++;
			}
        });
        	
        
    }
	
	@FXML
	private void handleContinue(){
		email = emailField.getText();
		dialogStage.close();
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
	
	public String getEmail(){
		return email;
	}
	
}
