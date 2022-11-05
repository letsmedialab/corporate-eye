package com.greenwich.coporateeyeadmin.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonitoredApplicationDto {
	private Long id;
	private String applicationName;
	private String applicationPath;
	private Integer applicationRevision;
	private String policyName;

}
