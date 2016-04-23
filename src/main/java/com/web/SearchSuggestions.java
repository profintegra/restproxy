package com.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

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

public class SearchSuggestions extends Controller {

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO Auto-generated method stub
		String searchItem = request.getParameter("name");
		String term = request.getParameter("term");
		Response responseDetail = new Response();
		if(StringUtils.isBlank(term)) term = "item-default";
		String url = String.format(UrlConstants.AUTOCOMPLETE_OFFER_URL, searchItem, term);
		Object entry = getEntry(url, request.getHeader(UrlConstants.AUTH_HEADER), responseDetail);
		return new JsonView(entry, responseDetail);
	}
	
	private Object getEntry(String url, String token, Response responseDetail){
		String data = Request.excuteGet(url, new HeaderBuilder().authorization(token).acceptJson().build(), responseDetail);
		if(responseDetail.getResponseCode() == 401 || responseDetail.getResponseCode() == 403 ){
			return 401;
		}
		List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/spec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        return chainr.transform( JsonUtils.jsonToObject(data) );
	}	
	
}
