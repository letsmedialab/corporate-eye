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
  
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "restrictedkeyword_id")
  private Set<CGroup> allowedGroups = new HashSet<>();
  
  
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "restrictedkeyword_id")
  private Set<CUser> allowedUsers = new HashSet<>();
  
  @Column
  private String policyName;

}