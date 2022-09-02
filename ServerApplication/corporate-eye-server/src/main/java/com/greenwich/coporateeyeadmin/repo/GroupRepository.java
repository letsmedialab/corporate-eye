package com.greenwich.coporateeyeadmin.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greenwich.coporateeyeadmin.model.CGroup;
import com.greenwich.coporateeyeadmin.model.RestrictedKeyword;

public interface GroupRepository extends JpaRepository<CGroup, Long>{
	public List<CGroup> findAll();
	
	//SELECT u FROM CGroup g where g.username like CONCAT(:keyword,'%') or u.name  like CONCAT(:keyword,'%') or u.role  like CONCAT(:keyword,'%')")
	//@Query(value = "SELECT distinct g.id , (g.group_name) as group_name   FROM CGroup g ,CUser u, cuser_cgroup j where g.id = j.cgroup_id and u.id = j.cuser_id and (u.username like CONCAT(:keyword,'%') or u.name like CONCAT(:keyword,'%') or u.role like CONCAT(:keyword,'%') or g.group_name like CONCAT(:keyword,'%') )" , nativeQuery = true)
	@Query("SELECT g FROM CGroup g where g.groupName like CONCAT(:keyword,'%')")
	public List<CGroup> search(@Param("keyword")String keyword);

	public Optional<CGroup> findByGroupName(String groupName);

	public Set<CGroup> findAllByGroupNameIn(Set<String> groupNames);

	
}
