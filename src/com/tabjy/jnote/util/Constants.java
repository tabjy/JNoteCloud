package com.tabjy.jnote.util;

public class Constants {
	private final static String VERSION = "0.3.5";
	private final static String REMOTE_SERVER = "http://" + Configuration.getHost()+"/api/";
	private final static String INSERT_API = getRemoteServer()+"/index.php?c=api&_table=note&_interface=insert";
	private final static String LIST_API = getRemoteServer()+"/index.php?c=api&_table=note&_interface=list";
	private final static String UPDATE_API = getRemoteServer()+"/index.php?c=api&_table=note&_interface=update";
	private final static String REMOVE_API = getRemoteServer()+"/index.php?c=api&_table=note&_interface=remove";
	private final static String NEW_USER = getRemoteServer()+"/createUser.php";
	private final static String EXISTING_USER = getRemoteServer()+"/verifyUser.php";
	
	public static String getAPI(String option){
		if (option.equalsIgnoreCase("list")){
			return LIST_API;
		}else if (option.equalsIgnoreCase("insert")){
			return INSERT_API;
		}else if (option.equalsIgnoreCase("update")){
			return UPDATE_API;
		}else if (option.equalsIgnoreCase("remove")){
			return REMOVE_API;
		}else if (option.equalsIgnoreCase("newUser")){
			return NEW_USER;
		}else if (option.equalsIgnoreCase("existingUser")){
			return EXISTING_USER;
		}else {
			return null;
		}
		
	}
	public static String getRemoteServer(){
		return REMOTE_SERVER;
	}

}
