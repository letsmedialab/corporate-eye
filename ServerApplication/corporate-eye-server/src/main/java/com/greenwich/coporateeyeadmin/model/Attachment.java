package com.greenwich.coporateeyeadmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Attachment {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO) 
  private Long id; 

  
  @Column
  private Boolean path;
  
  @Column
  private Boolean fileName;
  
  @Column
  private Boolean deleted;
  
  
  @OneToOne(mappedBy = "attachment")
  private EventLog eventLog;

}