package com.greenwich.coporateeyeadmin.dto.client;

import java.util.ArrayList;
import java.util.List;

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
	private List<Long> groupIds = new ArrayList<>();
	
}
