package com.example.servingwebcontent;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.servingwebcontent.model.Users;

public interface UserRepository extends CrudRepository<Users, Long>{
	public List<Users> findAll();
	
	@Query("SELECT u FROM Users u where u.username like CONCAT(:keyword,'%') or u.name  like CONCAT(:keyword,'%') or u.role  like CONCAT(:keyword,'%')")
	public List<Users> search(@Param("keyword")String keyword);

	public Optional<Users> findByUsername(String username);
	
}
