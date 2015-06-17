package com.tabjy.jnote.util;

import com.tabjy.jnote.model.Callback;
import com.tabjy.jnote.model.JsonData;
import com.tabjy.jnote.view.ValidNewUserController;

public class AsyncNewUser implements Runnable {
	private ValidNewUserController parent;
	private String post;
	
	public void createUser(){		
		HTTPRequest.sendPost(Constants.getAPI("newUser"), post, new Callback(){
			
			@Override
			public void onConnectionFailed(){
				parent.mainApp.showAlterWindow("Connection Failed!","Cannot connect to remote server! Please check your Internet connection.\n"+
						"Click OK to retry.");
				createUser();
			}
			
			@Override
			public void onSuccess(JsonData jsonData){
				parent.responed = true;
				parent.handleSuccess();
			}
			
			@Override
			public void onErrorReturned(JsonData jsonData){
				parent.mainApp.showAlterWindow("Creating User Failed!",
						"Connected to remote server, but an error was returned.\n"+
						"Error Code: "+jsonData.getErrCode() +"\n"+
						"Error Messege: " + jsonData.getErrMsg() + "\n" +
						"Raw data recived is printed below:",jsonData.getRaw());
				parent.goBack = true;
				parent.handleFailed();
			}
		});
	}

	public void setParent(ValidNewUserController parent) {
		this.parent = parent;
	}

	public void setPost(String post) {
		this.post = post;
		
	}

	@Override
	public void run() {
		ThreadUtil.printCurrentThread("Network operation to create user");
		createUser();
	}
}
