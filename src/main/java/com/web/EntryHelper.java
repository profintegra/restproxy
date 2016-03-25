package com.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.json.XML;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.constants.UrlConstants;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.util.Request;
import com.web.model.Response;
import com.web.utils.HeaderBuilder;

public class EntryHelper extends Controller{
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO Auto-generated method stub
		
		String id = request.getParameter("id");
		Response responseDetail = new Response();
		if(StringUtils.isNotBlank(id)){
			String url = String.format(UrlConstants.ITEM_HELPER_METADATA_URL, id);			
			String body = Request.excuteGet(url, new HeaderBuilder().authorization(request.getHeader(UrlConstants.AUTH_HEADER)).acceptAll().build(), responseDetail);
			JSONObject xmlJSONObj = XML.toJSONObject(body);
			String prettyJson = xmlJSONObj.toString(1);
			List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/entryHelperSpec.json" );
	        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
	        Object transformedOutput = chainr.transform( JsonUtils.jsonToObject(prettyJson) );
			return new JsonView(transformedOutput, responseDetail);
		}
			
		
		return new JsonView(null, responseDetail);
	}

}
