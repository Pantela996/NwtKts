package com.project.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.master.domain.User;

public interface AdminRepository extends JpaRepository<User, Long> {
}

