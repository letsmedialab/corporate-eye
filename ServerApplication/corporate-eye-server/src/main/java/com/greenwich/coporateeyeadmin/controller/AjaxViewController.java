package com.greenwich.coporateeyeadmin.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenwich.coporateeyeadmin.model.CGroup;
import com.greenwich.coporateeyeadmin.model.CUser;
import com.greenwich.coporateeyeadmin.model.EventLog;
import com.greenwich.coporateeyeadmin.model.RestrictedEmail;
import com.greenwich.coporateeyeadmin.model.RestrictedFile;
import com.greenwich.coporateeyeadmin.model.RestrictedKeyword;
import com.greenwich.coporateeyeadmin.model.RestrictedProcess;
import com.greenwich.coporateeyeadmin.model.RestrictedUrl;
import com.greenwich.coporateeyeadmin.model.UserLog;
import com.greenwich.coporateeyeadmin.repo.EventLogRepository;
import com.greenwich.coporateeyeadmin.repo.GroupRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedEmailRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedFileRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedKeywordRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedProcessRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedUrlRepository;
import com.greenwich.coporateeyeadmin.repo.UserLogRepository;
import com.greenwich.coporateeyeadmin.repo.UserRepository;

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
	
	@Autowired
	private UserLogRepository userLogRepository ;
	
	@Autowired
	private EventLogRepository eventLogRepository;
	
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
	
	@GetMapping("getActivityLogTableContent")
	public String getActivityLogTableContentForCurrentUser( @RequestParam(name = "keyword", required = false, defaultValue = "") 
	String keyword , Model model)
	{
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username ;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
		   username = principal.toString();
		}
		
		List<UserLog> data = userLogRepository.findByUser_UsernameAndMessageStartsWithOrUser_UsernameAndTitleStartsWithOrderByDateTimeDesc(username,keyword,username,keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",data);
		return "ajax/activityTableContent";
	}
	
	@GetMapping("getEventLogTableContent")
	public String getEventLogTableContent( @RequestParam(name = "keyword", required = false, defaultValue = "") 
	String keyword
	,@RequestParam(name = "userid", required = false, defaultValue = "") String userid
	, Model model)
	{
		Long id = -1l;
		
		try {
			
			id= Long.parseLong(keyword);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		List<EventLog> data = eventLogRepository.findByUser_UsernameAndIdOrUser_UsernameAndMessageStartsWithOrUser_UsernameAndTitleStartsWithOrderByDateTimeDesc(userid,id,userid,keyword,userid,keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("data",data);
		return "ajax/eventLogTableContent";
	}
	

}
