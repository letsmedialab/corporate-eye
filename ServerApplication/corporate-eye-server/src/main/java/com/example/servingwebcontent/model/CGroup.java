package com.example.servingwebcontent.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString

public class CGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column (unique = true)
	private String groupName;

	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,  }, mappedBy = "groups")
	@JsonIgnore
	private Set<CUser> users = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,  }, mappedBy = "allowedGroups")
	@JsonIgnore
	Set<RestrictedKeyword> keywords = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,  }, mappedBy = "allowedGroups")
	@JsonIgnore
	Set<RestrictedProcess> processes = new HashSet<>();
}