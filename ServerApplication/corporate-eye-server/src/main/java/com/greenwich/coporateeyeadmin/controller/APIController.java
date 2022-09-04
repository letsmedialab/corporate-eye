package com.greenwich.coporateeyeadmin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenwich.coporateeyeadmin.dto.client.ClientUserDto;
import com.greenwich.coporateeyeadmin.model.CUser;
import com.greenwich.coporateeyeadmin.service.APIService;

@RestController
@RequestMapping("api/v1")
public class APIController {

	@Autowired
	APIService apiService;

	@GetMapping(value = "testAPI", produces = MediaType.APPLICATION_JSON_VALUE)
	public String testAPI() {

		return "{\n" + "  \"street\": \"4627 Sunset Ave\",\n" + "  \"city\": \"San Diego\"\n" + "}";
	}

	@PostMapping(value = "validateCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ClientUserDto checkLogin(@RequestBody ClientUserDto clientUser) {

		return apiService.checkUser(clientUser.getUserName(), clientUser.getPassWord());
	}

	@PutMapping(value = "logEvent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ClientUserDto logEvent(@RequestBody ClientUserDto clientUser) {

		return apiService.checkUser(clientUser.getUserName(), clientUser.getPassWord());
	}

	@GetMapping(value = "getEmailConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getEmailConfig(
			@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		return apiService.prepareEmailConfig(username);
	}

	@GetMapping(value = "getFileConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getFileConfig(@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		return apiService.prepareFileConfig(username);
	}

	@GetMapping(value = "getProcessConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProcessConfig(
			@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		return apiService.prepareProcessConfig(username);
	}

	@GetMapping(value = "getKeywordConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getKeywordConfig(
			@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		return apiService.prepareKeywordConfig(username);
	}

	@GetMapping(value = "getUrlConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUrlConfig(@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		return apiService.prepareUrlConfig(username);
	}
	
	@GetMapping(value = "getMonitoredApplicationConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getMonitoredApplicationConfig(@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		return apiService.prepareApplicationConfig(username);
	}
	
	@GetMapping(value = "getUpdateHash", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUpdateHash(@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		return apiService.getHashUpdateValues(username);
	}
	
}
