package com.greenwich.coporateeyeadmin.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class EventLog {

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
	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "attachment_id", referencedColumnName = "id" , nullable = true)
	private Attachment attachment;

	@ManyToOne
	@JoinColumn(name = "cuser_id", nullable = false)

	CUser user;

	public String getDisplayDateTime()
	{
		return dateTime.format( DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a"));
	}

}