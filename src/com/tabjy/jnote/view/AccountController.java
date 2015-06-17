package com.tabjy.jnote.view;

import com.tabjy.jnote.util.Configuration;
import com.tabjy.jnote.util.PropertiesConfig;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccountController {
	@FXML
	private Label hint;
	@FXML
	private Label uid;
	
	public void setDefault(){
		uid.setText(Configuration.getUid());
		hint.setText("");
	}
	
	@FXML
	private void handleClearUID(){
		PropertiesConfig.writeData("uid", "");
		hint.setText("Your UID has been cleared. This will take effect after you restart the program.");
	}
}
