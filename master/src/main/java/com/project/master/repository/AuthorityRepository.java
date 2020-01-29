package com.project.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.master.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	Optional<Authority> findByName(String name);
}
