package com.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.json.XML;

import com.constants.UrlConstants;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.util.Request;
import com.web.model.Response;
import com.web.utils.HeaderBuilder;

public class MetadataForm extends Controller {


	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		
		String path = request.getParameter("path");
		Response responseDetail = new Response();
		if(StringUtils.isNotBlank(path)){			
			String body = Request.excuteGet(UrlConstants.HOST + path, new HeaderBuilder().acceptAll().authorization(request.getHeader(UrlConstants.AUTH_HEADER)).build(), responseDetail);			
			JSONObject json = XML.toJSONObject(body);
			String prettyJson = json.toString(1);
			return new JsonView(prettyJson, responseDetail);			
		}		
		return super.get(request, pathInfo);			
	}
	
	
}
