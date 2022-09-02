package com.greenwich.coporateeyeadmin.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class UserLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String title;

	@Column
	private String message;

	@Column
	private Boolean deleted;
	
	@Column	
	private LocalDateTime dateTime = LocalDateTime.now();
	
	

	@ManyToOne
	@JoinColumn(name = "cuser_id", nullable = false)

	CUser user;
	
	public String getDisplayDateTime()
	{
		return dateTime.format( DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a"));
	}

}