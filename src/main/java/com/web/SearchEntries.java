package com.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.omg.CORBA.portable.InputStream;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.constants.UrlConstants;
import com.google.gson.JsonObject;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.util.Request;

public class SearchEntries extends Controller {

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO Auto-generated method stub
		String searchItem = request.getParameter("name");
		String url = String.format(UrlConstants.AUTOCOMPLETE_OFFER_URL, searchItem);
		Object entry = getEntry(url);
		
		return new JsonView(entry);
	}
	
	private Object getEntry(String url){
		String data = Request.excutePost(url, Request.getDefaultHeaders());
		List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/spec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        return chainr.transform( JsonUtils.jsonToObject(data) );
	}
	
//	private Object fillMetadata(Object entry){
//		String id = new JSONObject(entry.toString()).getString("id");
//		String url = "http://mgr.vizrt.it/api/asset/item/%s/metadata";
//		if(StringUtils.isNotBlank(id)){			
//			final int PRETTY_PRINT_INDENT_FACTOR = 4;
//			String body = Request.excutePost(String.format(url, id), Request.getMetadataHeaders());
//			
//			JSONObject xmlJSONObj = XML.toJSONObject(body);
//			String jsonPrettyPrintString = xmlJSONObj.toString();
//			List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/metadataSpec.json" );
//	        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
//	        Object transformedOutput = chainr.transform( JsonUtils.jsonToObject(jsonPrettyPrintString) );
//			return new JsonView(transformedOutput);
//	}
	
	private static class SearchEntry{
		private int id;
		private String name;
	}
	
	
	
}
