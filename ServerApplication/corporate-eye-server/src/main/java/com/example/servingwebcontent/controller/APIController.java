package com.example.servingwebcontent.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servingwebcontent.dto.client.ClientUserDto;
import com.example.servingwebcontent.model.CUser;
import com.example.servingwebcontent.service.APIService;

@RestController
@RequestMapping("api/v1")
public class APIController {
	
	@Autowired
	APIService apiService;
	

	@GetMapping(value = "testAPI", produces = MediaType.APPLICATION_JSON_VALUE)
	public String testAPI() {

		

		return "{\n"
				+ "  \"street\": \"4627 Sunset Ave\",\n"
				+ "  \"city\": \"San Diego\"\n"
				+ "}";
	}
	
	@PostMapping(value = "validateCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ClientUserDto checkLogin(@RequestBody ClientUserDto clientUser){
		
		return apiService.checkUser(clientUser.getUserName(), clientUser.getPassWord());
	}

}
