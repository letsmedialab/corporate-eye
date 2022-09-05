package com.greenwich.coporateeyeadmin.model.interfaces;

import java.util.Set;

import com.greenwich.coporateeyeadmin.model.CGroup;
import com.greenwich.coporateeyeadmin.model.CUser;

public interface RestrictedModel {

	public Boolean getRestrictUsersByDefault();

	public Boolean getRestrictGroupsByDefault();

	public Set<CGroup> getGroups();
	
	public void setGroups(Set<CGroup> groups);
	
	public void setUsers(Set<CUser> users);

	public Set<CUser> getUsers();

	public Long getId();

	public String getPolicyName();
}
