package com.greenwich.coporateeyeadmin.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.greenwich.coporateeyeadmin.model.CUser;
import com.greenwich.coporateeyeadmin.model.RestrictedKeyword;
import com.greenwich.coporateeyeadmin.model.RestrictedProcess;
import com.greenwich.coporateeyeadmin.model.RestrictedUrl;

public interface RestrictedUrlRepository extends JpaRepository<RestrictedUrl, Long>{
	
	public List<RestrictedUrl> findAll();
	
	@Query("SELECT u FROM RestrictedUrl u where u.policyName like CONCAT(:keyword,'%') or u.restrictedUrl  like CONCAT(:keyword,'%')")
	public List<RestrictedUrl> search(@Param("keyword")String keyword);


	public Optional<RestrictedUrl> findByPolicyName(String policyName);

	public List<RestrictedUrl> findByEnabled(boolean b);
}
