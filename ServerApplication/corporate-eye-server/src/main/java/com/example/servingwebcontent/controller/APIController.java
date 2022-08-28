package com.example.servingwebcontent.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servingwebcontent.model.CUser;

@RestController
@RequestMapping("api/v1")
public class APIController {

	@GetMapping(value = "testAPI", produces = MediaType.APPLICATION_JSON_VALUE)
	public String testAPI() {

		

		return "{\n"
				+ "  \"street\": \"4627 Sunset Ave\",\n"
				+ "  \"city\": \"San Diego\"\n"
				+ "}";
	}

}
