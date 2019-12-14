package com.project.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.master.domain.User;

public interface LocationEventAdminRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
