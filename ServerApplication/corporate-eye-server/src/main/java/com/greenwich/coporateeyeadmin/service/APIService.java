package com.greenwich.coporateeyeadmin.service;

import org.springframework.stereotype.Service;

import com.greenwich.coporateeyeadmin.dto.client.ClientUserDto;

@Service
public interface APIService {
	
	public ClientUserDto checkUser(String userName ,  String password);

	public String prepareEmailConfig(String username);

	public String prepareUrlConfig(String username);

	public String prepareProcessConfig(String username);

	public String prepareKeywordConfig(String username);

	public String prepareFileConfig(String username);

	public String getHashUpdateValues(String username);

	public String prepareApplicationConfig(String username);
	

}
