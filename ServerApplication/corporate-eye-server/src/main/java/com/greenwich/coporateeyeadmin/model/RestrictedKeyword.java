package com.greenwich.coporateeyeadmin.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.greenwich.coporateeyeadmin.model.interfaces.RestrictedModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class RestrictedKeyword extends AuditBean  implements RestrictedModel{

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO) 
  private Long id; 
  
 
  private String restrictedKeyword;
  
  @Column
  private String restrictedRegex;
  
  @Column
  private Boolean restrictUsersByDefault;
  
  @Column
  private Boolean restrictGroupsByDefault;
  
  @Column
  private Boolean enabled;
  
  @ManyToMany( cascade = { CascadeType.ALL })
	@JoinTable(name = "keyword_cgroup", joinColumns = { @JoinColumn(name = "restricted_keyword_id") }, inverseJoinColumns = {
			@JoinColumn(name = "cgroup_id") })
  private Set<CGroup> groups = new HashSet<>();  
  
 
  @ManyToMany( cascade = { CascadeType.ALL })
	@JoinTable(name = "keyword_cuser", joinColumns = { @JoinColumn(name = "restricted_keyword_id") }, inverseJoinColumns = {
			@JoinColumn(name = "cuser_id") })
  private Set<CUser> users = new HashSet<>();
  
  @Column (name = "policyName" , unique = true) 
  private String policyName;

}