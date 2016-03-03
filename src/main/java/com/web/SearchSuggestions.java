package com.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.util.StringUtil;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.constants.UrlConstants;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.util.Request;
import com.web.utils.HeaderBuilder;

public class SearchSuggestions extends Controller {

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO Auto-generated method stub
		String clear = request.getParameter("name");
		String url = "";
		String searchItem = URLEncoder.encode(request.getParameter("name"), "UTF-8").replaceAll("\\+", "%20");
		String term = request.getParameter("term");

		url = Request.prepareSearchUrl(searchItem, term);
		String data = Request.excuteGet(Request.modifyUrl(url, request.getParameterMap()), new HeaderBuilder().authorization(request.getHeader(UrlConstants.AUTH_HEADER)).acceptJson().build());
		List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/suggestionSpec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        Object transformedOutput = chainr.transform( JsonUtils.jsonToObject(data) );
		return new JsonView(transformedOutput);
	}
}
