package com.safetynet.project.repository;

import com.safetynet.project.model.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecords, Long> {
}
