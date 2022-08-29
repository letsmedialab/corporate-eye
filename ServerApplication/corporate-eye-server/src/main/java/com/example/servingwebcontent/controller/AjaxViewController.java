package com.example.servingwebcontent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.model.CGroup;
import com.example.servingwebcontent.model.CUser;
import com.example.servingwebcontent.model.RestrictedEmail;
import com.example.servingwebcontent.model.RestrictedFile;
import com.example.servingwebcontent.model.RestrictedKeyword;
import com.example.servingwebcontent.model.RestrictedProcess;
import com.example.servingwebcontent.model.RestrictedUrl;
import com.example.servingwebcontent.repo.GroupRepository;
import com.example.servingwebcontent.repo.RestrictedEmailRepository;
import com.example.servingwebcontent.repo.RestrictedFileRepository;
import com.example.servingwebcontent.repo.RestrictedKeywordRepository;
import com.example.servingwebcontent.repo.RestrictedProcessRepository;
import com.example.servingwebcontent.repo.RestrictedUrlRepository;
import com.example.servingwebcontent.repo.UserRepository;

@Controller
@RequestMapping("admin/ajax")
public class AjaxViewController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestrictedKeywordRepository restrictedKeywordRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private RestrictedProcessRepository processRepository;
	
	@Autowired
	private RestrictedEmailRepository emailRepository;
	
	@Autowired
	private RestrictedUrlRepository urlRepository;
	
	@Autowired
	private RestrictedFileRepository fileRepository;
	
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
		List<CGroup> groups; 
		
		
		groups = groupRepository.search(keyword);
		
		
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
	
	@GetMapping("processTableContent")
	public String processTableContent( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		List<RestrictedProcess> data = processRepository.search(keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",data);
		return "ajax/processTableContent";
	}
	
	@GetMapping("emailTableContent")
	public String emailTableContent( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		List<RestrictedEmail> data = emailRepository.search(keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",data);
		return "ajax/emailTableContent";
	}
	@GetMapping("urlTableContent")
	public String urlTableContent( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		List<RestrictedUrl> data = urlRepository.search(keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",data);
		return "ajax/urlTableContent";
	}
	@GetMapping("fileTableContent")
	public String fileTableContent( @RequestParam(name = "query", required = false, defaultValue = "") 
	String keyword ,Model model)
	{
		List<RestrictedFile> data = fileRepository.search(keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",data);
		return "ajax/fileTableContent";
	}
	
}
