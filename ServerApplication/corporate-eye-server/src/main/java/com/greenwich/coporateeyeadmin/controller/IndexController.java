package com.greenwich.coporateeyeadmin.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	 @GetMapping(value = { "/"})
	    public void redirectToServices(HttpServletResponse httpServletResponse){
	        httpServletResponse.setHeader("Location", "/admin/");
	        httpServletResponse.setStatus(302);
	    }
	 
	 @GetMapping(value = { "admin"})
	    public void rer(HttpServletResponse httpServletResponse){
		 	httpServletResponse.setHeader("Location", "/admin/");
	        httpServletResponse.setStatus(302);
	    }
	 
	 @GetMapping(value = { "403"})
	    public String ret(HttpServletResponse httpServletResponse){
		 	return "error/403";
	    }
	 

}
