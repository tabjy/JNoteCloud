package com.tabjy.jnote.view;

import com.tabjy.jnote.util.Configuration;
import com.tabjy.jnote.util.PropertiesConfig;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AppearanceController {
	@FXML
	private Label hint;
	@FXML
	private Button purple;
	@FXML
	private Button red;
	@FXML
	private Button blue;
	@FXML
	private Button pink;
	@FXML
	private Button green;
	@FXML
	private Button yellow;

	
	public void setDefault(){
		hint.setText("");
	}
	
	@FXML
	private void handlePurple(){
		setUnselectedStyle();
		purple.getStyleClass().add("colorBtn_selected");
		hint.setText("This will take effect after you restart the program.");
		PropertiesConfig.writeData("theme", "purple");
	}
	@FXML
	private void handleBlue(){
		setUnselectedStyle();
		blue.getStyleClass().add("colorBtn_selected");
		hint.setText("This will take effect after you restart the program.");
		PropertiesConfig.writeData("theme", "blue");
	}
	@FXML
	private void handleRed(){
		setUnselectedStyle();
		red.getStyleClass().add("colorBtn_selected");
		hint.setText("This will take effect after you restart the program.");
		PropertiesConfig.writeData("theme", "red");
	}
	@FXML
	private void handleGreen(){
		setUnselectedStyle();
		green.getStyleClass().add("colorBtn_selected");
		hint.setText("This will take effect after you restart the program.");
		PropertiesConfig.writeData("theme", "green");
	}
	@FXML
	private void handlePink(){
		setUnselectedStyle();
		pink.getStyleClass().add("colorBtn_selected");
		hint.setText("This will take effect after you restart the program.");
		PropertiesConfig.writeData("theme", "pink");
	}
	@FXML
	private void handleYellow(){
		setUnselectedStyle();
		yellow.getStyleClass().add("colorBtn_selected");
		hint.setText("This will take effect after you restart the program.");
		PropertiesConfig.writeData("theme", "yellow");
	}
	
	private void setUnselectedStyle(){
		purple.getStyleClass().remove("colorBtn_selected");
		red.getStyleClass().remove("colorBtn_selected");
		blue.getStyleClass().remove("colorBtn_selected");
		pink.getStyleClass().remove("colorBtn_selected");
		green.getStyleClass().remove("colorBtn_selected");
		yellow.getStyleClass().remove("colorBtn_selected");
	}
}
