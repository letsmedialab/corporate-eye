package com.example.servingwebcontent.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString

public class RuleGroup {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO) 
  private Long id; 
  @Column
  private String groupName;
  
  @ManyToMany(mappedBy = "groups")
  @Column
  private Set<Users> users;

}