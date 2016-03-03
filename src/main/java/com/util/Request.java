package com.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.portable.InputStream;

import com.constants.UrlConstants;
import com.web.utils.HeaderBuilder;

public class Request {
	
	private static final String SERIES_SEARCH = "series";
	private static final String PROGRAM_SEARCH = "program";
	private static final String FOLDER_SEARCH = "folder";
	private static final String ITEM_SET_SEARCH = "itemset";
	private static final String LOG_TRACK_ITEM_SEARCH = "logtrackitem";
	

	public static String prepareSearchUrl(String searched, String term){
		
		String url = "";
		
		if(StringUtils.isNotBlank(term)){
			switch(term){
				case SERIES_SEARCH:
				case PROGRAM_SEARCH:
				case FOLDER_SEARCH:
					url = UrlConstants.COLLECTION_SEARCH;
					break;
				case ITEM_SET_SEARCH:
					url = UrlConstants.PACKAGES_SEARCH;
					break;
				case LOG_TRACK_ITEM_SEARCH:
					url = UrlConstants.ENTRIES_SEARCH;
					break;
				default:
					url = UrlConstants.ITEM_SEARCH;
			}
			if(searched.equals("%40"))
				return String.format(url, term);
			else
				return String.format(url + "&search.default=%s", term, searched);
		}else if(StringUtils.isNotBlank(searched)){
			return String.format(UrlConstants.EMPTY_SEARCH + "&search.default=%s", searched);
		}
		
		return String.format(UrlConstants.ENTITY_SEARCH, searched);
		
	}
	 
	public static String prepareMetaSchemaUrl(String term){

		if(StringUtils.isNotBlank(term)){
			switch(term){
				case SERIES_SEARCH:
					return UrlConstants.SERIES_META_SCHEMA_URL;
				case PROGRAM_SEARCH:
					return UrlConstants.PROGRAM_META_SCHEMA_URL;
				case LOG_TRACK_ITEM_SEARCH:
				case FOLDER_SEARCH:
				case ITEM_SET_SEARCH:				
				default:
					return UrlConstants.ITEM_META_SCHEMA_URL;
			}			
		}		
		return UrlConstants.ITEM_META_SCHEMA_URL;		
	}
	
	public static String prepareMetaUrl(String id, String term){
		
		String url = "";
		
		if(StringUtils.isNotBlank(term)){
			switch(term){
				case SERIES_SEARCH:
				case PROGRAM_SEARCH:
				case FOLDER_SEARCH:
					url = UrlConstants.COLLECTION_META;//, term, searched);
					break;
				case ITEM_SET_SEARCH:
					url = UrlConstants.PACKAGES_META;//, term, searched);
					break;
				case LOG_TRACK_ITEM_SEARCH:
					url = UrlConstants.ENTRIES_META;//, term, searched);
					break;
				default:
					url = UrlConstants.METADATA_URL;
			}
			
			return String.format(url, id);
			
		}
		
		return String.format(UrlConstants.METADATA_URL, id);
		
	}
	
	public static String modifyUrl(String url, Map<String, String[]> queryParam){
		try{
			for(String key: queryParam.keySet()){
				if( !(key.equalsIgnoreCase("filter") || key.equalsIgnoreCase("name") || key.equalsIgnoreCase("term")) )
					url += "&" + key + "=" + URLEncoder.encode(queryParam.get(key)[0], "UTF-8");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}
	
	public static void uploadMetaData(String targetUrl, String xml, String token){
		HttpURLConnection connection = null;
		try{
			URL url = new URL(targetUrl);
			Map<String, String> headers = new HeaderBuilder().authorization(token).connectionKeepAlive().contentTypeVndXml().acceptVndXml().build();
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
	
	public static String excuteGet(String targetURL, Map<String, String> headers) {
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
		    System.out.println(connection.getHeaderFields());
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
	
	public static String login(String targetURL, String params, Map<String, String> headers) throws IOException {
		  HttpURLConnection connection = null; 
		  int statusCode = -1;
		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    byte[] postData = params.getBytes(StandardCharsets.UTF_8);
		    connection = (HttpURLConnection)url.openConnection();
		    connection.setRequestMethod("POST");
		    for(String key: headers.keySet())
		    	connection.setRequestProperty(key, headers.get(key));
		    connection.setDoOutput(true);
		    connection.connect();
		    try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
		    	   wr.write( postData );
		    	}
		    java.io.InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
		    String line;
		    while((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    System.out.println(connection.getHeaderFields());
		    return response.toString();
		   
		  }catch(Exception e){			  
			  e.printStackTrace();
		  } finally {
			    if(connection != null) {
			      connection.disconnect(); 
			    }
			    
		  }
		  return String.valueOf(connection.getResponseCode());
	}

}
