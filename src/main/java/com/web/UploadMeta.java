package com.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.StringUtil;
import org.json.JSONObject;
import org.json.XML;

import com.constants.UrlConstants;
import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.mvc.XmlView;
import com.util.Request;
import com.web.model.Response;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import de.odysseus.staxon.xml.util.PrettyXMLStreamWriter;

public class UploadMeta extends Controller {
	
	@Override
	public View put(HttpServletRequest request, PathParser pathInfo) throws Exception {
				
		if(StringUtil.isNotBlank(request.getParameter("id")) && StringUtil.isNotBlank(request.getParameter("xmlns"))){
			String xml = convertJsonToXml(getJsonFromRequest(request));
			String id = request.getParameter("id");
			String term = request.getParameter("term");
			String url = Request.prepareMetaUrl(id, term);	
			xml = URLDecoder.decode(xml, "UTF-8");
			url = String.format(url, id);
			xml = xml.replaceFirst("<payload>", "<payload xmlns=\""+request.getParameter("xmlns")+"\" model=\""+Request.prepareMetaSchemaUrl(term)+"\">");
			 
			Request.uploadMetaData(url, xml, request.getHeader(UrlConstants.AUTH_HEADER));
			return new XmlView(xml);
		}		
		
		return new JsonView("", new Response());
		
		
	}
	
	private String getJsonFromRequest(HttpServletRequest request) throws IOException{
		BufferedReader reader = request.getReader();	
		StringBuffer buffer = new StringBuffer();
		String line;
		while((line = reader.readLine()) != null)
			buffer.append(line);
		return buffer.toString();
	}
	
	private String convertJsonToXml(String json) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();	
		JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).build();
		XMLStreamReader xmlReader = new JsonXMLInputFactory(config).createXMLStreamReader(new StringReader(json));
		Source source = new StAXSource(xmlReader);		
		XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(baos);
        Result result = new StAXResult(new PrettyXMLStreamWriter(writer));
        TransformerFactory.newInstance().newTransformer().transform(source, result);
        byte[] array = baos.toByteArray();        
        String xmlResult = new String(array);
        return xmlResult;
	}

}
