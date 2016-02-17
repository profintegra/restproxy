package com.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FailLoginView implements View {

	@Override
	public void view(HttpServletRequest request, HttpServletResponse response) throws IOException {	
		response.setStatus(401);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
	}

}
