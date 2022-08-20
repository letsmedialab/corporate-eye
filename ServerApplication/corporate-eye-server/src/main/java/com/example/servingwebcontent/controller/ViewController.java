package com.example.servingwebcontent.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.model.Users;

@Controller
public class ViewController {

	@GetMapping("index")
	public String index()
	{
		
		return "index";
	}
	@GetMapping("components")
	public String about()
	{
		
		return "components";
	}
	@GetMapping("users")
	public String users( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		
		model.addAttribute("keyword",keyword);
		
		return "users";
	}
	
}
