package com.example.servingwebcontent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.UserRepository;
import com.example.servingwebcontent.model.Users;

@Controller
@RequestMapping("ajax")
public class AjaxViewController {
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("tableContent")
	public String tableContent( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		List<Users> users = userRepository.search(keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",users);
		return "ajax/tableContent";
	}
	
}
