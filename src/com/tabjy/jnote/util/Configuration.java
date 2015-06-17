package com.tabjy.jnote.util;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.tabjy.jnote.MainApp;
import com.tabjy.jnote.model.Callback;
import com.tabjy.jnote.model.JsonData;
import com.tabjy.jnote.view.ValidNewUserController;

public class Configuration {
	private static String HOST;
	private static String UID;
	private static String THEME;
	private static MainApp mainApp;
	
	public static void initConfig(){
		File file=new File(System.getProperty("java.io.tmpdir")+"/com.tabjy.jnote/config.properties");    
		if(!file.exists()){    
		    try {    
		        file.createNewFile();    
		    } catch (IOException e) {      
		        e.printStackTrace();    
		    }    
		}
		
		String host = PropertiesConfig.readData("host");
		if (host == null){
			//load default
			HOST = "jnote.tabjy.com";
			PropertiesConfig.writeData("host", "jnote.tabjy.com");
		} else {
			HOST = host;
		}
		
		String uid = PropertiesConfig.readData("uid");
		System.out.print(uid);
		if (uid == null || uid.length()!=16){
			UID = null;
			PropertiesConfig.writeData("uid", "");
		} else {
			String post = "uid="+uid; 
			HTTPRequest.sendPost(Constants.getAPI("existingUser"), post, new Callback(){
				@Override
				public void onConnectionFailed(){
					UID = null;
				}
				
				@Override
				public void onSuccess(JsonData jsonData){
					UID = uid;
				}
				
				@Override
				public void onErrorReturned(JsonData jsonData){
					UID = null;
				}
			});
		}
		
		String theme = PropertiesConfig.readData("theme");
		if (theme == null){
			//load default
			THEME = "default";
			PropertiesConfig.writeData("theme", "default");
		} else {
			THEME =theme;
		}
	}
	
	public static String getUid(){
		return UID;
	}
	
	public static boolean hasUid(){
		if (UID == null){
			return false;
		} else {
			return true;
		}
	}
	
	public static String getHost(){
		return HOST;
	}
	
	public static String getTheme(){
		return THEME;
	}
	
	public static String getStyleSheet(){
		return THEME;
	}
	
}
