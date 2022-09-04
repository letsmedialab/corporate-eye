package com.greenwich.coporateeyeadmin.dto.client;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestrictedUrlDto
{
	  private Long id;
      private List<String> urls;
      private String policyName;

}
