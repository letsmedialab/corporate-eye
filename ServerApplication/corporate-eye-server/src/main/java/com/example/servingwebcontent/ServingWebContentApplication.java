package com.example.servingwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.example.servingwebcontent.model.CGroup;
import com.example.servingwebcontent.model.CUser;
import com.example.servingwebcontent.repo.GroupRepository;
import com.example.servingwebcontent.repo.UserRepository;
import com.example.servingwebcontent.util.GeneralUtils;

@SpringBootApplication
public class ServingWebContentApplication {
	@Autowired
    GroupRepository groupRepository;
	@Autowired
    UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(ServingWebContentApplication.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		try {
		CGroup initGroup =  new CGroup();
		
		CUser initUser = new CUser();
		
		initUser.setEnabled(true);
		initUser.setName("Administrator");
		initUser.setPassword(GeneralUtils.bCryptEncode("0spyn@123"));
		initUser.setRole("ROLE_ADMIN");
		initUser.setUsername("admin");
	
		initUser = userRepository.save(initUser);
		
		initGroup.getUsers().add(initUser);
		initGroup.setGroupName("Default");
		
		
		groupRepository.save(initGroup);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
