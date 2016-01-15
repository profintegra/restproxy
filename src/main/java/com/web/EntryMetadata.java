package com.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

public class EntryMetadata extends Controller {
	
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		
		String id = request.getParameter("id");
		String url = UrlConstants.METADATA_URL;
		if(StringUtils.isNotBlank(id)){
			
			final int PRETTY_PRINT_INDENT_FACTOR = 4;
			String body = Request.excutePost(String.format(url, id), Request.getMetadataHeaders());
			
			JSONObject xmlJSONObj = XML.toJSONObject(body);
			String jsonPrettyPrintString = xmlJSONObj.toString();
			List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/metadataSpec.json" );
	        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
	        Object transformedOutput = chainr.transform( JsonUtils.jsonToObject(jsonPrettyPrintString) );
			return new JsonView(transformedOutput);
		}			
		return new JsonView(null);
			
		
	}

}
