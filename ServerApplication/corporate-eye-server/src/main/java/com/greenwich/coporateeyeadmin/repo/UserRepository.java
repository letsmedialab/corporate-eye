package com.greenwich.coporateeyeadmin.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.greenwich.coporateeyeadmin.model.CUser;

public interface UserRepository extends JpaRepository<CUser, Long>{
	public List<CUser> findAll();
	
	@Query("SELECT u FROM CUser u where u.username like CONCAT(:keyword,'%') or u.name  like CONCAT(:keyword,'%') or u.role  like CONCAT(:keyword,'%')")
	public List<CUser> search(@Param("keyword")String keyword);

	public Optional<CUser> findByUsername(String username);

	public Set<CUser> findAllByUsernameIn(Set<String> keySet);

	public Optional<CUser> findByUsernameAndPassword(String userName, String password);


	public Set<CUser> findByUsernameStartsWithOrNameStartsWithOrRoleStartsWith(String keyword, String keyword2, String keyword3);
	
}
