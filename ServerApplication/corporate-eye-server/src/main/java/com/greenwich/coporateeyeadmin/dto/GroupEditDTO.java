package com.greenwich.coporateeyeadmin.dto;

import java.util.Set;

import com.greenwich.coporateeyeadmin.model.CGroup;
import com.greenwich.coporateeyeadmin.model.CUser;

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
