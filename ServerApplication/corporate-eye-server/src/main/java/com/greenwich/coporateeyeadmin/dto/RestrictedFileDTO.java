package com.greenwich.coporateeyeadmin.dto;

import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestrictedFileDTO {
	
	private Long id;
	
	private String fileName;

	private String policyName;

	private String hash;

	private Map<String, String> userNames;

	private Set<String> groupNames;

	private Boolean enabled;

	private Boolean restrictUsersByDefault;

	private Boolean restrictGroupsByDefault;
}
