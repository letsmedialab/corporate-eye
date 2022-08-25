package com.example.servingwebcontent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.model.RestrictedKeyword;
import com.example.servingwebcontent.model.CGroup;
import com.example.servingwebcontent.model.CUser;
import com.example.servingwebcontent.repo.RestrictedKeywordRepository;
import com.example.servingwebcontent.repo.GroupRepository;
import com.example.servingwebcontent.repo.UserRepository;

@Controller
@RequestMapping("ajax")
public class AjaxViewController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RestrictedKeywordRepository restrictedKeywordRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@GetMapping("userTableContent")
	public String userTableContent( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		List<CUser> users = userRepository.search(keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",users);
		return "ajax/userTableContent";
	}
	
	@GetMapping("groupTableContent")
	public String groupTableContent( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		List<CGroup> groups = groupRepository.findAll();
		
		
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",groups);
		return "ajax/groupTableContent";
	}
	
	
	@GetMapping("keywordTableContent")
	public String keywordTableContent( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		List<RestrictedKeyword> keywords = restrictedKeywordRepository.search(keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",keywords);
		return "ajax/keywordTableContent";
	}
	
}
