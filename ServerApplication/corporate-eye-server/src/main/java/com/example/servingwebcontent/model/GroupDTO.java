package com.example.servingwebcontent.model;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
	
	private long id;
	private String groupName;
	private Map<String, String> usernames;

	public static void main(String[] args) {
		
		GroupDTO d = new GroupDTO();
		
		Map<String,String> map = new HashMap<>();
		map.put("dd", "xx");
		map.put("dd1", "xx1");
		d.setGroupName("dd");
		d.setUsernames(map);
		
	
		
	}

}


