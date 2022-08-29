package com.example.servingwebcontent.service;

import org.springframework.stereotype.Service;

import com.example.servingwebcontent.dto.client.ClientUserDto;

@Service
public interface APIService {
	
	public ClientUserDto checkUser(String userName ,  String password);
	

}
