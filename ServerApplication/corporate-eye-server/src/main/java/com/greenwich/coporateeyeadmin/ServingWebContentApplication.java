package com.greenwich.coporateeyeadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

import com.greenwich.coporateeyeadmin.model.CGroup;
import com.greenwich.coporateeyeadmin.model.CUser;
import com.greenwich.coporateeyeadmin.model.FileStorageProperties;
import com.greenwich.coporateeyeadmin.repo.GroupRepository;
import com.greenwich.coporateeyeadmin.repo.UserRepository;
import com.greenwich.coporateeyeadmin.util.GeneralUtils;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
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
		initUser.setPassword(GeneralUtils.bCryptEncode("admin"));
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
