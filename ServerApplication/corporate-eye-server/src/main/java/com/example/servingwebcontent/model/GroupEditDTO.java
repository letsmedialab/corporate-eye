package com.example.servingwebcontent.model;

import java.util.Set;

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
