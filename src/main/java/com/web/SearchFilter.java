package com.web;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtil;
import com.bazaarvoice.jolt.JsonUtils;
import com.constants.UrlConstants;
import com.google.gson.Gson;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.util.Request;
import com.web.utils.HeaderBuilder;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;

public class SearchFilter extends Controller {
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		String clear = request.getParameter("filter");		
		String term = request.getParameter("term");
		String url = "";
		if(StringUtils.isBlank(clear)) clear = "test";
		String searchItem = URLEncoder.encode(clear, "UTF-8").replaceAll("\\+", "%20");

		url = Request.prepareSearchUrl(searchItem, term);//String.format(UrlConstants.FACETS_SEARCH, searchItem);
		String data = Request.excuteGet(Request.modifyUrl(url, request.getParameterMap()), new HeaderBuilder().authorization(request.getHeader(UrlConstants.AUTH_HEADER)).acceptAll().build());		
		List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/filterSpec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        JSONArray arr = XML.toJSONObject(data).optJSONObject("atom:feed").optJSONArray("search:facets");
        Object transformedOutput;
        if(arr == null){
        	transformedOutput = requestByAcceptJson(url, request.getParameterMap(), request.getHeader(UrlConstants.AUTH_HEADER));   
        }else
        	transformedOutput = chainr.transform( JsonUtils.jsonToObject(XML.toJSONObject(data).toString(1)) );

		return new JsonView(transformedOutput);
	}
	
	private Object requestByAcceptJson(String url, Map<String, String[]> queryParam, String token){
		String data = Request.excuteGet(Request.modifyUrl(url, queryParam), new HeaderBuilder().authorization(token).acceptAll().build());
		List chainrSpecJSON = JsonUtils.classpathToList( "/json/sample/jsonFilterSpec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        String pretty = XML.toJSONObject(data).toString(1);
        String jsonObj = JsonUtils.jsonToObject(XML.toJSONObject(data).toString(1)).toString();
        Object transformedOutput = chainr.transform( JsonUtils.jsonToObject(XML.toJSONObject(data).toString(1)) );
        return transformedOutput;
        
	}

}
