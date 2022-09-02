package com.greenwich.coporateeyeadmin.dto.client;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestrictedEmailDto
{
	  private Long id;
      private String email;
      private String policyName;

}
