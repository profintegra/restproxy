package com.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.portable.InputStream;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.util.Downloader;

public class SearchEntries extends Controller {

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO Auto-generated method stub
		String searchItem = request.getParameter("name");
		String url = String.format("http://mgr.vizrt.it/api/search/suggest/%s?num=5&qAutocomplete=1&qProfile=item-default&qSynonym=1&krrr=0.9741306724026799&User=uuser&Passwd=inmedia", searchItem);
		String data = Downloader.excutePost(url, "");
		List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/spec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        Object transformedOutput = chainr.transform( JsonUtils.jsonToObject(data) );
		return new JsonView(transformedOutput);
	}
	
	private static class SearchEntry{
		private int id;
		private String name;
	}
	
	
	
}
