package com.greenwich.coporateeyeadmin.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greenwich.coporateeyeadmin.model.MonitoredApplication;
import com.greenwich.coporateeyeadmin.model.RestrictedFile;
import com.greenwich.coporateeyeadmin.model.RestrictedProcess;

public interface MonitoredApplicationRepository extends JpaRepository<MonitoredApplication, Long>{
	
	public List<MonitoredApplication> findAll();
	
	@Query("SELECT u FROM MonitoredApplication u where u.policyName like CONCAT(:keyword,'%') or u.applicationName  like CONCAT(:keyword,'%') or u.applicationPath  like CONCAT('%',:keyword,'%')")
	public List<MonitoredApplication> search(@Param("keyword")String keyword);


	public Optional<MonitoredApplication> findByPolicyName(String policyName);

	public List<MonitoredApplication> findByEnabled(boolean b);
}
