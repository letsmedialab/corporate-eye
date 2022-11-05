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
import com.greenwich.coporateeyeadmin.model.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, Long>{
	
	public List<UserLog> findAll();
	
//	@Query("SELECT e FROM UserLog e,CUser u  inner join CUser u on e.user= u.id    where e.message like CONCAT(:keyword,'%') or e.title  like CONCAT(:keyword,'%') and u.username =:userid  ")
//	public List<UserLog> search(@Param("keyword")String keyword , @Param("userid")String userid);
	
	public List<UserLog> findByUser_UsernameAndMessageStartsWithOrUser_UsernameAndTitleStartsWithOrderByDateTimeDesc(@Param("userid")String userid,@Param("keyword")String keyword1,@Param("userid")String userid2 ,@Param("keyword")String keyword2 );

}
