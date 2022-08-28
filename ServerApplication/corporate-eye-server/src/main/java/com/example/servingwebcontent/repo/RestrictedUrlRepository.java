package com.example.servingwebcontent.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.servingwebcontent.model.RestrictedKeyword;
import com.example.servingwebcontent.model.RestrictedProcess;
import com.example.servingwebcontent.model.RestrictedUrl;
import com.example.servingwebcontent.model.CUser;

public interface RestrictedUrlRepository extends JpaRepository<RestrictedUrl, Long>{
	
	public List<RestrictedUrl> findAll();
	
	@Query("SELECT u FROM RestrictedUrl u where u.policyName like CONCAT(:keyword,'%') or u.restrictedUrl  like CONCAT(:keyword,'%')")
	public List<RestrictedUrl> search(@Param("keyword")String keyword);


	public Optional<RestrictedUrl> findByPolicyName(String policyName);
}
