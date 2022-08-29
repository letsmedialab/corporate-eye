package com.example.servingwebcontent.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.servingwebcontent.dto.client.ClientUserDto;
import com.example.servingwebcontent.model.CUser;
import com.example.servingwebcontent.repo.UserRepository;
import com.example.servingwebcontent.util.GeneralUtils;

@Service
public class APIServiceImpl implements APIService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public ClientUserDto checkUser(String userName, String password) {
		
		Optional<CUser> user = userRepository.findByUsername(userName);
		
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
	    
		
		if(user.isPresent()&& bc.matches(password, user.get().getPassword()))
		{
			return new ClientUserDto(userName, password,  user.get().getName() , user.get().getEnabled());
		}
		else
		{
			return new ClientUserDto(userName, password,  "No User" , false);
		}
	}
	
	
}
