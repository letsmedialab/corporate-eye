package com.greenwich.coporateeyeadmin.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenwich.coporateeyeadmin.model.CUser;

@Controller
@RequestMapping("admin/")
public class ViewController {

	@GetMapping("index")
	public String  redirectToServices(HttpServletResponse httpServletResponse) {
		return "index";
	}
	
	@GetMapping("")
	public String redirectToDash(HttpServletResponse httpServletResponse) {
		return "index";
	}

	@GetMapping("components")
	public String about() {

		return "components";
	}

	@GetMapping("users")
	public String users(@RequestParam(name = "query", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "users";
	}

	@GetMapping("configKeywords")
	public String configKeywords(@RequestParam(name = "query", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "configKeywords";
	}

	@GetMapping("configProcesses")
	public String configProcesses(@RequestParam(name = "query", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "configProcesses";
	}

	@GetMapping("configEmails")
	public String configEmails(@RequestParam(name = "query", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "configEmail";
	}

	@GetMapping("configFiles")
	public String configFiles(@RequestParam(name = "query", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "configFile";
	}

	@GetMapping("configUrls")
	public String configUrls(@RequestParam(name = "query", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "configUrl";
	}

	@GetMapping("groups")
	public String groups(@RequestParam(name = "query", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "groups";
	}
	
	@GetMapping("activityLog")
	public String activityLog(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "activityLog";
	}
	
	@GetMapping("eventLog")
	public String eventLog(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "eventLog";
	}
	
	@GetMapping("monitorApplication")
	public String monitorApplication(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
			Model model) {

		model.addAttribute("keyword", keyword);

		return "configApplication";
	}

}
