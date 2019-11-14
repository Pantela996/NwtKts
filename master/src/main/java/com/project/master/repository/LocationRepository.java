package com.project.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.master.domain.EventLocation;

public interface LocationRepository extends JpaRepository<EventLocation, Long>{

}
