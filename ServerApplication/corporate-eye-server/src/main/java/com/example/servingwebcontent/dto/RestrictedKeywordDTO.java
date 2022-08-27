package com.example.servingwebcontent.dto;

import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestrictedKeywordDTO {
	
	private Long id;
	
	private String keywordName;

	private String policyName;

	private String regEx;

	private Map<String, String> userNames;

	private Set<String> groupNames;

	private Boolean enabled;

	private Boolean restrictUsersByDefault;

	private Boolean restrictGroupsByDefault;
}
