package com.tabjy.jnote.util;

import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.text.SimpleDateFormat;  
import java.util.Date;  

import javafx.application.Platform;
import javafx.scene.control.Label;
  
public class NetworkState implements Runnable {  

	private static Label label;
    public static void main(String[] args) {  
        NetworkState ns = new NetworkState();  
        Platform.runLater(ns);
        //new Thread(ns).start();//Start new thread 
    } 
    
    public static void setLabel(Label label2){
    	label = label2;
    }
  
    // ÅÐ¶ÏÍøÂç×´Ì¬  
    public void isConnect() {  
        Runtime runtime = Runtime.getRuntime();  
        try {  
            Process process = runtime.exec("ping " + "www.google.ca");  
            InputStream is = process.getInputStream();  
            InputStreamReader isr = new InputStreamReader(is);  
            BufferedReader br = new BufferedReader(isr);  
            String line = null;  
            StringBuffer sb = new StringBuffer();  
            while ((line = br.readLine()) != null) {  
                sb.append(line);  
            }  
            is.close();  
            isr.close();  
            br.close();  
  
            if (null != sb && !sb.toString().equals("")) {  
                String logString = "";  
                if (sb.toString().indexOf("32") > 0) {  
                    // ÍøÂç³©Í¨  
                	label.setText("Online");
                    logString = "Connection: ONLINE at " + this.getCurrentTime();
//                  System.out.println(logString);  
                } else {  
                    // ÍøÂç²»³©Í¨  
                	label.setText("Offline");
                    logString = "Connection: OFFLINE at " + this.getCurrentTime();  
//                  System.out.println(logString); 
                }  
                System.out.println(logString);
                System.out.println("=================");
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
 
    public String getCurrentTime() {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String time = sdf.format(new Date());  
        return time;  
    }  
  
    @Override  
    public void run() {  
        // TODO Auto-generated method stub  
        while (true) {  
            this.isConnect();  
            try {  
                // Wait 5s and check again  
                Thread.sleep(5000);  
            } catch (InterruptedException e) {  
                //e.printStackTrace();  
            }  
        }  
    }  
  
}  