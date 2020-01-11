package com.project.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.master.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findById(Long id);

}
