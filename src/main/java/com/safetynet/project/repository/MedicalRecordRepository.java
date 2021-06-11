package com.safetynet.project.repository;

import com.safetynet.project.model.MedicalRecords;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface MedicalRecordRepository extends CrudRepository<MedicalRecords, Long> {
    List<MedicalRecords> findAllByLastNameAllIgnoreCase(String lastname);
    Optional<MedicalRecords> findByFirstNameAndLastNameAllIgnoreCase(String firstname, String lastname);
    void deleteMedicalRecordsByFirstNameAndLastNameAllIgnoreCase(String firstname, String lastname);
    void removeMedicalRecordsByFirstNameAndLastNameAllIgnoreCase(String firstname, String lastname);
}
