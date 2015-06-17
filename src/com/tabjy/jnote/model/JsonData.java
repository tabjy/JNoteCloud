package com.tabjy.jnote.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonData {
	private int err_code;
	private String err_msg;
	private ArrayList<Note> noteData = new ArrayList<Note>();
	private String raw;
	
	
	public JsonData(){
		
	}
	
	public JsonData(String jsonData){
		this.raw = jsonData;
		JSONObject jsonobj = new JSONObject(jsonData);
		err_code = jsonobj.getInt("err_code");
		err_msg = jsonobj.getString("err_msg");
		
		try {
			JSONObject data = jsonobj.getJSONObject("data");
			JSONArray items = data.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {
				JSONObject dataObject = items.getJSONObject(i);
				int id = dataObject.getInt("id");
				String title = dataObject.getString("title");
				String content = dataObject.getString("content");
				int time = dataObject.getInt("time");
				String uid =dataObject.getString("uid");
				noteData.add(new Note(id, title, content, (long) time, uid, true));
		    }
		} catch(JSONException e) {
			try {
				JSONArray items = jsonobj.getJSONArray("data");
				for (int i = 0; i < items.length(); i++) {
					JSONObject dataObject = items.getJSONObject(i);
					int id = dataObject.getInt("id");
					String title = dataObject.getString("title");
					String content = dataObject.getString("content");
					int time = dataObject.getInt("time");
					String uid =dataObject.getString("uid");
					noteData.add(new Note(id, title, content, (long) time, uid, true));
				}
		    } catch (JSONException e2){
		    	// do nothing
		    }
		}
	}
	
	public int getErrCode(){
		return err_code;
	}
	
	public String getErrMsg(){
		return err_msg;
	}
	
	public ArrayList<Note> getNoteData(){
		return noteData;
	}
	
	public boolean isSuccessed(){
		if (err_code ==0 ){
			return true;
		} else {
			return false;
		}
	}
	
	public String getRaw(){
		return this.raw;
	}
}
