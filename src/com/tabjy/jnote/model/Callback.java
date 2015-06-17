package com.tabjy.jnote.model;

public interface Callback {
	public void onConnectionFailed();
	public void onSuccess(JsonData jsonData);
	//public void onErrorReturned();
	public void onErrorReturned(JsonData jsonData);
	//public void onParserJsonFailed(String result);
}
