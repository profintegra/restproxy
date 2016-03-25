package com.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

public class ProfileDetails extends Controller {

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		Response responseDetail = new Response();
		String data = Request.excuteGet(UrlConstants.PROFILE_DETAIL, new HeaderBuilder().authorization(request.getHeader(UrlConstants.AUTH_HEADER)).acceptJson().build(), responseDetail);
		List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/profileSpec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        Object transformedOutput = chainr.transform( JsonUtils.jsonToObject(data) );
		return new JsonView(transformedOutput, responseDetail);
	}
	
	
}
