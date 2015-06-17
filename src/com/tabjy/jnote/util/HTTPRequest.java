package com.tabjy.jnote.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.tabjy.jnote.model.Callback;
import com.tabjy.jnote.model.JsonData;

public class HTTPRequest {
    /**
     * HTTP GET REQUEST
     * 
     * @param url
     *            Target URL
     * @param param
     *            in form of name1=value1&name2=value2
     * @return result
     * 			  server's respond
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // Open a connection
            URLConnection connection = realUrl.openConnection();
            // Set header
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // establish connection
            connection.connect();

            Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
             //   System.out.println(key + "--->" + map.get(key));
            }

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("A exception occured while sending a GET request! " + e);
            e.printStackTrace();
        }
        // final clean up
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        
        
        
        return result;
    }
    
    /**
     * HTTP GET REQUEST WITH CALLBACK
     * 
     * @param url
     *            Target URL
     * @param param
     *            in form of name1=value1&name2=value2
     * @param callback      
     *     		  what to do after finish communicating with server.
     * @return result
     * 			  server's respond
     */
    public static String sendGet(String url, String param, Callback callback) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // Open a connection
            URLConnection connection = realUrl.openConnection();
            // Set header
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // establish connection
            connection.connect();

            Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
             //   System.out.println(key + "--->" + map.get(key));
            }

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
          //Construct note array
            JsonData jsonData = new JsonData(result);
            if (jsonData.isSuccessed()){
            	callback.onSuccess(jsonData);
            } else if (!jsonData.isSuccessed()){
            	callback.onErrorReturned(jsonData);
            }
            
        } catch (Exception e) {
            System.out.println("A exception occured while sending a GET request! " + e);
            e.printStackTrace();
            callback.onConnectionFailed();
        }
        // final clean up
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        
        
        
        return result;
    }

    /**
     * HTTP POST REQUEST
     * 
     * @param url
     *            Target URL
     * @param param
     *            in form of name1=value1&name2=value2
     * @return result
     * 			  server's respond
     */
    

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // Open a connection
            URLConnection conn = realUrl.openConnection();
            // Set header
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // establish connection
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            
            out = new PrintWriter(conn.getOutputStream());

            out.print(param);

            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("A exception occured while sending a POST request! "+e);
            e.printStackTrace();
            
        }
        //final clean up
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    /**
     * HTTP POST REQUEST WITH CALLBACK
     * 
     * @param url
     *            Target URL
     * @param param
     *            in form of name1=value1&name2=value2
     * @param callback      
     *      	  what to do after finish communicating with server.
     * @return result
     * 			  server's respond
     */
    

    public static String sendPost(String url, String param, Callback callback) {
    	ThreadUtil.printCurrentThread("Network operation to send post request");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // Open a connection
            URLConnection conn = realUrl.openConnection();
            // Set header
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // establish connection
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            
            out = new PrintWriter(conn.getOutputStream());

            out.print(param);

            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
            //Construct note array
            JsonData jsonData = new JsonData(result);
            if (jsonData.isSuccessed()){
            	callback.onSuccess(jsonData);
            } else if (!jsonData.isSuccessed()){
            	callback.onErrorReturned(jsonData);
            }
            
        } catch (Exception e) {
            System.out.println("A exception occured while sending a POST request! "+e);
            //e.printStackTrace();
            callback.onConnectionFailed();
        }
        //final clean up
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        return result;
    }    
}