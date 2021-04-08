package com.safetynet.project.repository;

import com.safetynet.project.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FireStationRepository extends JpaRepository<FireStation, Long> {
}
