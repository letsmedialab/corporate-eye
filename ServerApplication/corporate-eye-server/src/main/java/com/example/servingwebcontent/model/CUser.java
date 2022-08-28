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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "username", unique = true)

	private String username;
	@Column(name = "password")
	private String password;
	@Column
	private String role;
	@Column
	private String name;
	@Column
	private Boolean enabled;

	@ManyToMany( cascade = { CascadeType.ALL })
	@JoinTable(name = "cuser_cgroup", joinColumns = { @JoinColumn(name = "cuser_id") }, inverseJoinColumns = {
			@JoinColumn(name = "cgroup_id") })
	Set<CGroup> groups = new HashSet<>();
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,  }, mappedBy = "allowedUsers")
	@JsonIgnore
	Set<RestrictedKeyword> keywords = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,  }, mappedBy = "allowedUsers")
	@JsonIgnore
	Set<RestrictedProcess> processes = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,  }, mappedBy = "allowedUsers")
	@JsonIgnore
	Set<RestrictedFile> files = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,  }, mappedBy = "allowedUsers")
	@JsonIgnore
	Set<RestrictedEmail> emails = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,  }, mappedBy = "allowedUsers")
	@JsonIgnore
	Set<RestrictedUrl> urls = new HashSet<>();

}