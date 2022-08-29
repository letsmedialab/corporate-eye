package com.example.servingwebcontent.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientUserDto {
	
	private String userName;
	private String passWord;
	private String name;
	private Boolean isAuthenticated = false;
	
}
