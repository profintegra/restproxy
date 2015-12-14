package com.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;

public class LeftNavEntries extends Controller {
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		// TODO Auto-generated method stub
		
		NavBar search = new NavBar(0, "Search", "ti-search", "root.search");
		NavBar task = new NavBar(1, "Task2", "ti-menu-alt", "root.tasks");
		List<NavBar> list = new ArrayList();
		list.add(search);
		list.add(task);
		Map<String, List> navbars = new HashMap<String, List>();
		navbars.put("leftNavEntry", list);
		
		return new JsonView(navbars);
	}
	
	
	
	private static class NavBar{
		
		private String title;
		private String icon;
		private String route;
		private int id;
		
		public NavBar(int id, String title, String icon, String route){
			this.id = id;
			this.title = title;
			this.icon = icon;
			this.route = route;
		}
	}

}
