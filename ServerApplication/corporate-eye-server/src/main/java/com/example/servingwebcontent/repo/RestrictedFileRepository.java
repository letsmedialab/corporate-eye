package com.example.servingwebcontent.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.servingwebcontent.model.RestrictedFile;
import com.example.servingwebcontent.model.RestrictedProcess;

public interface RestrictedFileRepository extends JpaRepository<RestrictedFile, Long>{
	
	public List<RestrictedFile> findAll();
	
	@Query("SELECT u FROM RestrictedFile u where u.policyName like CONCAT(:keyword,'%') or u.restrictedFileName  like CONCAT(:keyword,'%') or u.hashValue  like CONCAT(:keyword,'%')")
	public List<RestrictedFile> search(@Param("keyword")String keyword);


	public Optional<RestrictedFile> findByPolicyName(String policyName);
}
