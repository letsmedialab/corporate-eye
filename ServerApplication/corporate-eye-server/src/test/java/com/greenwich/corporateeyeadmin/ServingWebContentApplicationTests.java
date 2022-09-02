package com.greenwich.corporateeyeadmin;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.greenwich.coporateeyeadmin.ServingWebContentApplication;
import com.greenwich.coporateeyeadmin.model.CUser;
import com.greenwich.coporateeyeadmin.model.EventLog;
import com.greenwich.coporateeyeadmin.model.UserLog;
import com.greenwich.coporateeyeadmin.repo.EventLogRepository;
import com.greenwich.coporateeyeadmin.repo.UserLogRepository;
import com.greenwich.coporateeyeadmin.repo.UserRepository;



@SpringBootTest(classes = ServingWebContentApplication.class)
class ServingWebContentApplicationTests {

	@Autowired
	private EventLogRepository evenLogRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserLogRepository userLogRepository;
	
	@Test
	void insertEventLog() {
		
		EventLog eventLog = new EventLog();
		Optional<CUser> cuser = userRepository.findByUsername("admin");
		Assert.isTrue(cuser.isPresent());
		eventLog.setDeleted(false);
		eventLog.setMessage("admineventlog1");
		eventLog.setTitle("adminlogalpha");
		eventLog.setUser(cuser.get());
		evenLogRepository.save(eventLog);
		
		
		
	}
	
	@Test
	void insertEventLogPinky() {
		
		EventLog eventLog = new EventLog();
		Optional<CUser> cuser = userRepository.findByUsername("pinky");
		Assert.isTrue(cuser.isPresent());
		eventLog.setDeleted(false);
		eventLog.setMessage("pinkyeventlog1");
		eventLog.setTitle("pinkylogdelta");
		eventLog.setUser(cuser.get());
		evenLogRepository.save(eventLog);
		
		
		
	}
	
	@Test
	void insertUserLog() {
		
		UserLog userlog = new UserLog();
		Optional<CUser> cuser = userRepository.findByUsername("admin");
		Assert.isTrue(cuser.isPresent());
		userlog.setDeleted(false);
		userlog.setMessage("adminuserlog1");
		userlog.setTitle("admin userlog echo");
		userlog.setUser(cuser.get());
		userLogRepository.save(userlog);
		
		
		
	}
	
	@Test
	void insertUserLogPinky() {
		
		UserLog userlog = new UserLog();
		Optional<CUser> cuser = userRepository.findByUsername("pinky");
		Assert.isTrue(cuser.isPresent());
		userlog.setDeleted(false);
		userlog.setMessage("pinky user log sierra");
		userlog.setTitle("pinkyuser");
		userlog.setUser(cuser.get());
		userLogRepository.save(userlog);
		
		
		
	}

}
