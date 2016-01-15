package com.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.portable.InputStream;

public class Request {
	
	public static Map<String, String> getDefaultHeaders(){
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Authorization", "Basic dXVzZXI6SW5tZWRpYQ==");
		return headers;
	}
	
	public static Map<String, String> getMetadataHeaders(){
		Map<String, String> headers = getDefaultHeaders();
		headers.put("Accept", "*/*");
		return headers;
	}
	
	public static Map<String, String> getUploadMetaHeaders(){
		Map<String, String> headers = getDefaultHeaders();
		headers.put("Accept", "application/vnd.vizrt.payload+xml");
		headers.put("Content-Type", "application/vnd.vizrt.payload+xml");
		headers.put("Connection", "keep-alive");
		return headers;
	}
	
	public static void uploadMetaData(String targetUrl, String xml){
		HttpURLConnection connection = null;
		try{
			URL url = new URL(targetUrl);
			Map<String, String> headers = getUploadMetaHeaders();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("PUT");
			for(String key: headers.keySet())
				connection.setRequestProperty(key, headers.get(key));
			connection.setRequestProperty("Content-Length", String.valueOf(xml.length()));
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    connection.setDoInput(true);
		    OutputStream outStream = new BufferedOutputStream(connection.getOutputStream());
		    outStream.write(xml.getBytes());
		    outStream.flush();
		    outStream.close();
		    java.io.InputStream inStream = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(inStream));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
		    String line;
		    while((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    String result = rd.toString();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(connection != null)
				connection.disconnect();
		}
	}
	
	public static String excutePost(String targetURL, Map<String, String> headers) {
		  HttpURLConnection connection = null;  
		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection)url.openConnection();
		    connection.setRequestMethod("GET");
		    for(String key: headers.keySet())
		    	connection.setRequestProperty(key, headers.get(key));
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    //Get Response  
		    java.io.InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
		    String line;
		    while((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if(connection != null) {
		      connection.disconnect(); 
		    }
		  }
	}

}
