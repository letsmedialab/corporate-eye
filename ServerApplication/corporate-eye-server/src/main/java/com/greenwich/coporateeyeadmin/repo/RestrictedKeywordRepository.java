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

public interface RestrictedKeywordRepository extends JpaRepository<RestrictedKeyword, Long> {

	public List<RestrictedKeyword> findAll();

	@Query("SELECT u FROM RestrictedKeyword u where u.policyName like CONCAT(:keyword,'%') or u.restrictedRegex  like CONCAT(:keyword,'%') or u.restrictedKeyword  like CONCAT(:keyword,'%')")
	public List<RestrictedKeyword> search(@Param("keyword") String keyword);

	public Optional<RestrictedKeyword> findByPolicyName(String policyName);

	public List<RestrictedKeyword> findByEnabled(boolean b);
}
