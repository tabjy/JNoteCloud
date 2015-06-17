package com.tabjy.jnote.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {
	
	static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
	public static String getTimeFromStamp(Long stamp){
		Date time=new Date((long)stamp*1000);
		String reportDate = df.format(time);
		//System.out.println(reportDate);
		return reportDate;
		
	}
	
	public static String getTimeFromObject(Date data){
		String reportDate = df.format(data);
		//System.out.println(reportDate);
		return reportDate;
	}
	
	public static String getCurrentTime(){
		Date today = Calendar.getInstance().getTime();
		return getTimeFromObject(today);
	}
	
	public static long getCurrentTimeStamp(){
		Date today = Calendar.getInstance().getTime();
		return Math.round(today.getTime()/1000);
	}
	
	
	public static void main(String[] args) {
		System.out.print(getTimeFromStamp(getCurrentTimeStamp()));
	}
}
