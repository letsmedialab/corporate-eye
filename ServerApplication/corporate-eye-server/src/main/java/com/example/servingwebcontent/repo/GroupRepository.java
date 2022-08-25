package com.example.servingwebcontent.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.servingwebcontent.model.CGroup;
import com.example.servingwebcontent.model.CUser;

public interface GroupRepository extends JpaRepository<CGroup, Long>{
	public List<CGroup> findAll();
	

	public Optional<CGroup> findByGroupName(String groupName);
	
}
