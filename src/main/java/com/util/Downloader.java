package com.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
	
	public static String excutePost(String targetURL, String urlParameters) {
		  HttpURLConnection connection = null;  
		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection)url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("Accept", "application/json");
		    connection.setRequestProperty("Set-Cookie", "token=whxYK0JKuZQeDjalV3JY1kejLcXd1sBVde40pQ_xjReHu57pFotFOffexoOLt9kDKDKx_konGJdpBjmGFwOzGrGk8Kg39PpSfy7-pBfbLCHXuMpuhh6IEWwFhBMTjEtAFkXgYN-XQ1M.; Path=/; Expires=Mon, 07-Dec-2015 00:22:30 GMT");
		    connection.setRequestProperty("API_KEY", "secret key");
		    connection.setRequestProperty("ANOTHER_HEADER", "Some header value");
		    connection.setRequestProperty("JSESSIONID", "096BDB029A9543FD3D589B02B7477F9A");
		    connection.setRequestProperty("atlassian.xsrf.token","BCSF-FP0G-8MD4-J36O|dd05e92d15f1e7f1266cf87a919cd4122bde3cf8|lin");
		    connection.setRequestProperty("Authorization", "Basic dXVzZXI6aW5tZWRpYQ==");
		    


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
