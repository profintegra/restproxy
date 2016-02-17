package com.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class JsonView implements View {
    
    private String jsonParam;
    private Gson gson = new Gson();
    
    public JsonView(Object obj){ 
    	jsonParam = gson.toJson(obj);
    }
    
    public JsonView(String json){
    	jsonParam = json;
    }
    
    @Override
    public void view(HttpServletRequest request, HttpServletResponse response)
                    throws IOException {
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            //response.setCharacterEncoding("utf-8");
            response.getWriter().print(jsonParam);               
    }

}
