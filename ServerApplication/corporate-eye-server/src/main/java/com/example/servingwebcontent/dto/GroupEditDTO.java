package com.example.servingwebcontent.dto;

import java.util.Set;

import com.example.servingwebcontent.model.CGroup;
import com.example.servingwebcontent.model.CUser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class GroupEditDTO {
	private Long id;
	private String name;
	private Set<CUser> users;
	
	public GroupEditDTO(CGroup group)
	{
		id = group.getId();
		name = group.getGroupName();
		users = group.getUsers();
	}
}
