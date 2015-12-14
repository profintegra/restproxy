package com.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.util.Downloader;

public class SearchSuggestions extends Controller {

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO Auto-generated method stub
		String clear = request.getParameter("name");
		String searchItem = URLEncoder.encode(request.getParameter("name"), "UTF-8").replaceAll("\\+", "%20");
		String url = String.format("http://mgr.vizrt.it/api/search/item?num=10&start=1&qProfile=item-default&qFacet=on&qHighlightMode=vms&search.default=%s&krrr=0.7290897415950894&User=uuser&Passwd=inmedia", searchItem);
		String data = Downloader.excutePost(url, "");
		List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/suggestionSpec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        Object transformedOutput = chainr.transform( JsonUtils.jsonToObject(data) );
		return new JsonView(transformedOutput);
	}
}
