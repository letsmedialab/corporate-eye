package com.greenwich.coporateeyeadmin.dto.client;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestrictedFileDto
{
    private Long id;
    private String fileName;
    private String fileSHA;
    private String policyName;

}
