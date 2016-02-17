package com.web;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.constants.UrlConstants;
import com.mvc.Controller;
import com.mvc.FailLoginView;
import com.mvc.JsonView;
import com.mvc.TextView;
import com.mvc.View;
import com.util.Request;

public class Login extends Controller {
	
	
	@Override
	public View post(HttpServletRequest request) throws Exception {
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		
		if(StringUtils.isNotBlank(login) && StringUtils.isNotBlank(password)){
			
			String params = String.format(UrlConstants.LOGIN_PARAMS, login, password);
			if(Request.login(UrlConstants.LOGIN_URL, params, Collections.EMPTY_MAP) == 200)
				return new TextView();
			
		}
		
		return new FailLoginView();
	}

}
