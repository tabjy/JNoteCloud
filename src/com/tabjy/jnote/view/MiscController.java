package com.tabjy.jnote.view;

import com.tabjy.jnote.util.Configuration;
import com.tabjy.jnote.util.PropertiesConfig;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MiscController {
	@FXML
	private Label hint;
	@FXML
	private TextField host;
	
	public void setDefault(){
		hint.setText("");
		host.setText(Configuration.getHost());
	}
	
	@FXML
	private void handleChanged(){
		PropertiesConfig.writeData("host", host.getText());
		hint.setText("This will take effect after you restart the program.");
	}
}
