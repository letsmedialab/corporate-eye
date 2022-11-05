package com.greenwich.coporateeyeadmin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@MappedSuperclass
public class AuditBean {
	
	@Column
	private Date createdDate;
	@Column
	private Date modifiedDate;

}
