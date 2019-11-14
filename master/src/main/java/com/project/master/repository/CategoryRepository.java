package com.project.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.master.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{


}
