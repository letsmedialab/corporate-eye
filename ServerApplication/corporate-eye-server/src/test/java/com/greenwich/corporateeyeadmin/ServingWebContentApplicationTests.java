package com.greenwich.corporateeyeadmin;

import java.util.Date;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.greenwich.coporateeyeadmin.ServingWebContentApplication;
import com.greenwich.coporateeyeadmin.repo.EventLogRepository;
import com.greenwich.coporateeyeadmin.repo.GroupRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedEmailRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedFileRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedKeywordRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedProcessRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedUrlRepository;
import com.greenwich.coporateeyeadmin.repo.UserLogRepository;
import com.greenwich.coporateeyeadmin.repo.UserRepository;
import com.mysql.cj.x.protobuf.MysqlxNotice.GroupReplicationStateChanged;

@SpringBootTest(classes = ServingWebContentApplication.class)
class ServingWebContentApplicationTests {

	@Autowired
	private EventLogRepository evenLogRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserLogRepository userLogRepository;

	
	@Autowired
	private RestrictedKeywordRepository restrictedKeywordRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private RestrictedProcessRepository processRepository;
	
	@Autowired
	private RestrictedEmailRepository emailRepository;
	
	@Autowired
	private RestrictedUrlRepository urlRepository;
	
	@Autowired
	private RestrictedFileRepository fileRepository;
	

	
	@Autowired
	private EventLogRepository eventLogRepository;

//	@Test
//	void insertEventLog() {
//		
//		EventLog eventLog = new EventLog();
//		Optional<CUser> cuser = userRepository.findByUsername("admin");
//		Assert.isTrue(cuser.isPresent());
//		eventLog.setDeleted(false);
//		eventLog.setMessage("admineventlog1");
//		eventLog.setTitle("adminlogalpha");
//		eventLog.setUser(cuser.get());
//		evenLogRepository.save(eventLog);
//		
//		
//		
//	}
//	
//	@Test
//	void insertEventLogPinky() {
//		
//		EventLog eventLog = new EventLog();
//		Optional<CUser> cuser = userRepository.findByUsername("pinky");
//		Assert.isTrue(cuser.isPresent());
//		eventLog.setDeleted(false);
//		eventLog.setMessage("pinkyeventlog1");
//		eventLog.setTitle("pinkylogdelta");
//		eventLog.setUser(cuser.get());
//		evenLogRepository.save(eventLog);
//		
//		
//		
//	}
//	
//	@Test
//	void insertUserLog() {
//		
//		UserLog userlog = new UserLog();
//		Optional<CUser> cuser = userRepository.findByUsername("admin");
//		Assert.isTrue(cuser.isPresent());
//		userlog.setDeleted(false);
//		userlog.setMessage("adminuserlog1");
//		userlog.setTitle("admin userlog echo");
//		userlog.setUser(cuser.get());
//		userLogRepository.save(userlog);
//		
//		
//		
//	}
//	
//	@Test
//	void insertUserLogPinky() {
//		
//		UserLog userlog = new UserLog();
//		Optional<CUser> cuser = userRepository.findByUsername("pinky");
//		Assert.isTrue(cuser.isPresent());
//		userlog.setDeleted(false);
//		userlog.setMessage("pinky user log sierra");
//		userlog.setTitle("pinkyuser");
//		userlog.setUser(cuser.get());
//		userLogRepository.save(userlog);
//		
//		
//		
//	}

	@Test
	void fixNullDate() {

		userRepository.saveAll(userRepository.findAll().stream().map(q -> {

			q.setCreatedDate(new Date());
			q.setModifiedDate(new Date());
			return q;

		}).collect(Collectors.toList()));
		
		groupRepository.saveAll(groupRepository.findAll().stream().map(q -> {

			q.setCreatedDate(new Date());
			q.setModifiedDate(new Date());
			return q;

		}).collect(Collectors.toList()));
		
		restrictedKeywordRepository.saveAll(restrictedKeywordRepository.findAll().stream().map(q -> {

			q.setCreatedDate(new Date());
			q.setModifiedDate(new Date());
			return q;

		}).collect(Collectors.toList()));
		
		emailRepository.saveAll(emailRepository.findAll().stream().map(q -> {

			q.setCreatedDate(new Date());
			q.setModifiedDate(new Date());
			return q;

		}).collect(Collectors.toList()));
		
		urlRepository.saveAll(urlRepository.findAll().stream().map(q -> {

			q.setCreatedDate(new Date());
			q.setModifiedDate(new Date());
			return q;

		}).collect(Collectors.toList()));
		
		processRepository.saveAll(processRepository.findAll().stream().map(q -> {

			q.setCreatedDate(new Date());
			q.setModifiedDate(new Date());
			return q;

		}).collect(Collectors.toList()));
		
		fileRepository.saveAll(fileRepository.findAll().stream().map(q -> {

			q.setCreatedDate(new Date());
			q.setModifiedDate(new Date());
			return q;

		}).collect(Collectors.toList()));
		
		

	}

}
