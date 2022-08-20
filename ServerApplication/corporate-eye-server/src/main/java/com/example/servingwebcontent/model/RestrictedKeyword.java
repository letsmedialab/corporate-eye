package com.example.servingwebcontent.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class RestrictedKeyword {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO) 
  private Long id; 
  
  @Column
  private String restrictedKeyword;
  
  @Column
  private String restrictedRegex;
  
  @Column
  @OneToMany
  private Set<Users> allowedGroupId;
  
  @Column
  @OneToMany
  private Set<RuleGroup> allowedUserId;
  
  @Column
  private String policyName;

}