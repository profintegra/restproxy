package com.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class XmlView implements View {
    
    private String jsonParam;
    private Gson gson = new Gson();
    
//    public XmlView(Object obj){ 
//    	jsonParam = gson.toJson(obj);
//    }
    
    public XmlView(String json){
    	jsonParam = json;
    }
    
    @Override
    public void view(HttpServletRequest request, HttpServletResponse response)
                    throws IOException {
            response.setContentType("application/xml");
            response.setContentLength(jsonParam.length());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.getWriter().print(jsonParam);               
    }

}
