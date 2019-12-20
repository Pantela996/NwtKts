package com.project.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.master.domain.Hall;

import java.util.ArrayList;

public interface HallRepository extends JpaRepository<Hall, Long>{
    ArrayList<Hall> findAll();
}
