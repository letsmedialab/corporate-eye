package com.greenwich.coporateeyeadmin.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.greenwich.coporateeyeadmin.model.CUser;
import com.greenwich.coporateeyeadmin.model.EventLog;
import com.greenwich.coporateeyeadmin.model.RestrictedEmail;
import com.greenwich.coporateeyeadmin.model.RestrictedKeyword;
import com.greenwich.coporateeyeadmin.model.RestrictedProcess;

public interface EventLogRepository extends JpaRepository<EventLog, Long>{
	
	public List<EventLog> findAll();
	
	@Query("SELECT e FROM EventLog e left join CUser u on e.user = u.id where e.message like CONCAT(:keyword,'%') or e.title  like CONCAT(:keyword,'%') and u.username =:userid  ")
	public List<EventLog> search(@Param("keyword")String keyword , @Param("keyword")String userid);





	public List<EventLog> findByUser_UsernameAndIdOrUser_UsernameAndMessageStartsWithOrUser_UsernameAndTitleStartsWithOrderByDateTimeDesc(String userid, Long id,String userid2,
			String keyword,String userid3, String keyword2);

}
