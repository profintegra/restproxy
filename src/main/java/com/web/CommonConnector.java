package com.web;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.XML;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.constants.UrlConstants;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.util.Request;
import com.web.model.Response;
import com.web.utils.HeaderBuilder;

public class CommonConnector extends Controller {
	
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO load all model
		
		String clear = request.getParameter("name");
		String url = "";
		String searchItem = URLEncoder.encode(request.getParameter("name"), "UTF-8").replaceAll("\\+", "%20");
		String term = request.getParameter("term");
		Response responseDetail = new Response();
		url = Request.prepareSearchUrl(searchItem, term);
		String data = Request.excuteGet(Request.modifyUrl(url, request.getParameterMap()), new HeaderBuilder().authorization(request.getHeader(UrlConstants.AUTH_HEADER)).acceptAll().build(), responseDetail);
		if(data != null){
			String converted = XML.toJSONObject(data).toString(1);
			List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/commonConnectorSpec.json" );
	        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
	        Object transformedOutput = chainr.transform( JsonUtils.jsonToObject(converted) );
	        //responseDetail.setResponseCode(401);
			return new JsonView(transformedOutput, responseDetail);
		}
		return new JsonView(StringUtils.EMPTY, responseDetail);
	}
	

}
