package com.greenwich.coporateeyeadmin.dto.client;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestrictedProcessDto
{
	  private Long id;
      private String processName;
      private String policyName;

}
