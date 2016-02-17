package com.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.json.XML;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.constants.UrlConstants;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.util.Request;
import com.web.utils.HeaderBuilder;

public class EditMetadata extends Controller {

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO Auto-generated method stub
		String term = request.getParameter("term");
		
		String url = Request.prepareMetaSchemaUrl(term);
		String body = Request.excuteGet(url, new HeaderBuilder().authorization(request.getHeader("Coockie")).acceptAll().build());
		
		JSONObject xmlJSONObj = XML.toJSONObject(body);
		String jsonPrettyPrintString = xmlJSONObj.toString();
		return new JsonView(jsonPrettyPrintString);
	}
	
	
}
