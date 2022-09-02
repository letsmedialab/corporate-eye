package com.greenwich.coporateeyeadmin.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.greenwich.coporateeyeadmin.model.CUser;
import com.greenwich.coporateeyeadmin.model.RestrictedEmail;
import com.greenwich.coporateeyeadmin.model.RestrictedKeyword;
import com.greenwich.coporateeyeadmin.model.RestrictedProcess;

public interface RestrictedEmailRepository extends JpaRepository<RestrictedEmail, Long>{
	
	public List<RestrictedEmail> findAll();
	
	@Query("SELECT u FROM RestrictedEmail u where u.policyName like CONCAT(:keyword,'%') or u.restrictedEmail  like CONCAT(:keyword,'%')")
	public List<RestrictedEmail> search(@Param("keyword")String keyword);


	public Optional<RestrictedEmail> findByPolicyName(String policyName);

	public List<RestrictedEmail> findByEnabled(boolean b);
}
