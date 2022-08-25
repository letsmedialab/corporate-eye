package com.example.servingwebcontent.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.servingwebcontent.model.RestrictedKeyword;
import com.example.servingwebcontent.model.CUser;

public interface RestrictedKeywordRepository extends JpaRepository<RestrictedKeyword, Long>{
	
	public List<RestrictedKeyword> findAll();
	
	@Query("SELECT u FROM RestrictedKeyword u where u.policyName like CONCAT(:keyword,'%') or u.restrictedRegex  like CONCAT(:keyword,'%') or u.restrictedKeyword  like CONCAT(:keyword,'%')")
	public List<RestrictedKeyword> search(@Param("keyword")String keyword);

	
}
