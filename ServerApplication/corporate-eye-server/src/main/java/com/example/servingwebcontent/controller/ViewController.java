package com.example.servingwebcontent.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.model.CUser;

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
	
	@GetMapping("configKeywords")
	public String configKeywords( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		
		model.addAttribute("keyword",keyword);
		
		return "configKeywords";
	}
	
	@GetMapping("configProcesses")
	public String configProcesses( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		
		model.addAttribute("keyword",keyword);
		
		return "configProcesses";
	}
	
	@GetMapping("groups")
	public String groups( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		
		model.addAttribute("keyword",keyword);
		
		return "groups";
	}
	
}
