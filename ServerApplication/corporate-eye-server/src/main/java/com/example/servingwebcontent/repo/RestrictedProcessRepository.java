package com.example.servingwebcontent.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.servingwebcontent.model.RestrictedKeyword;
import com.example.servingwebcontent.model.RestrictedProcess;
import com.example.servingwebcontent.model.CUser;

public interface RestrictedProcessRepository extends JpaRepository<RestrictedProcess, Long>{
	
	public List<RestrictedProcess> findAll();
	
	@Query("SELECT u FROM RestrictedProcess u where u.policyName like CONCAT(:keyword,'%') or u.restrictedProcess  like CONCAT(:keyword,'%')")
	public List<RestrictedProcess> search(@Param("keyword")String keyword);


	public Optional<RestrictedProcess> findByPolicyName(String policyName);
}
