package com.example.servingwebcontent.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "cumpwsusers")
public class Users {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id; 
  @Column(name = "username")
  private String username;
  @Column(name = "password")
  private String password;
  @Column
  private String role;
  @Column
  private String name;
  @Column
  private Boolean enabled;
  

  @ManyToMany
  @JoinTable(
		  name = "user_group", 
		  joinColumns = @JoinColumn(name = "employee_id"), 
		  inverseJoinColumns = @JoinColumn(name = "rule_group_id"))
  private Set<RuleGroup> groups;

}