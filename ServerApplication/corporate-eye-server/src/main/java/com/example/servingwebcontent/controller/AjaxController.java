package com.example.servingwebcontent.controller;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servingwebcontent.UserRepository;
import com.example.servingwebcontent.model.Users;
import com.example.servingwebcontent.util.GeneralUtils;
import com.google.gson.Gson;

@RestController
@RequestMapping("ajax")
public class AjaxController {

	@Autowired
	UserRepository userRepository;

	@GetMapping(value = "checkUsername", produces = MediaType.APPLICATION_JSON_VALUE)
	public String checkUsername(@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		Optional<Users> user = userRepository.findByUsername(username);

		return String.valueOf(user.isPresent());
	}

	@GetMapping(value = "getUserDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUserDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id) {

		Optional<Users> user = userRepository.findById(id);

		return new Gson().toJson(user.get());
	}
	
	@PostMapping("addUser")
	public String addUser(@RequestBody Users user) {
		try {

			user.setPassword(GeneralUtils.bCryptEncode(user.getPassword()));
			userRepository.save(user);

		} catch (Exception ex) {
			return "failed";
		}

		return "success";

	}
	
	@PostMapping("updateUser")
	public String updateUser(@RequestBody Users user) {
		try {
			
			Users currentUser = userRepository.findById(user.getId()).get();
			
			
			currentUser.setName(user.getName());
			currentUser.setRole(user.getRole());
			currentUser.setEnabled(user.getEnabled());

			if(user.getPassword().trim().length()>0)
			{
				currentUser.setPassword(GeneralUtils.bCryptEncode(user.getPassword()));
			}
			
			userRepository.save(currentUser);
			

		} catch (Exception ex) {
			
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}
	
	
	@PostMapping("deleteUser/{userId}")
	public String deleteUser(@PathVariable (required = true) Long userId) {
		try {
			
			userRepository.deleteById(userId);

		} catch (Exception ex) {
			return "failed";
		}

		return "success";

	}
}
