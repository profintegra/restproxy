package com.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.web.model.Response;

public class JsonView implements View {
    
    private String jsonParam;
    private Gson gson = new Gson();
    private Response responseDetail;
    
    public JsonView(Object obj, Response responseDetail){ 
    	jsonParam = gson.toJson(obj);
    	this.responseDetail = responseDetail;
    }
    
    public JsonView(String json, Response responseDetail){
    	jsonParam = json;
    	this.responseDetail = responseDetail;
    }
    
    @Override
    public void view(HttpServletRequest request, HttpServletResponse response)
                    throws IOException {
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Expose-Headers", "Token");
            if(this.responseDetail.getResponseCode() == 401 || this.responseDetail.getResponseCode() == 403){
            	response.setStatus(401);
            }else if(StringUtils.isNotBlank(this.responseDetail.getToken())){
            	response.setHeader("Token", this.responseDetail.getToken());
            }
            //response.setCharacterEncoding("utf-8");
            response.getWriter().print(jsonParam);               
    }

}
