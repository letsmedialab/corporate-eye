package com.greenwich.coporateeyeadmin.dto.client;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestrictedKeywordDto
{
   
	private Long id;
    private List<String> restrictedKeyword;
    private String restrictedRegex;
    private String policyName;

}
