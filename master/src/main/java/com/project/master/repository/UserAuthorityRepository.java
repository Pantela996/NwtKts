package com.project.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.master.domain.User;
import com.project.master.domain.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority,Long>{
	void deleteByUser(User user);
}
