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
import com.web.utils.HeaderBuilder;

public class SearchEntries extends Controller {

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO Auto-generated method stub
		String searchItem = request.getParameter("name");
		String term = request.getParameter("term");
		if(StringUtils.isBlank(term)) term = "item-default";
		String url = String.format(UrlConstants.AUTOCOMPLETE_OFFER_URL, searchItem, term);
		Object entry = getEntry(url);
		
		return new JsonView(entry);
	}
	
	private Object getEntry(String url){
		String data = Request.excuteGet(url, new HeaderBuilder().authorization().acceptJson().build());
		List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/spec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        return chainr.transform( JsonUtils.jsonToObject(data) );
	}	
	
}
