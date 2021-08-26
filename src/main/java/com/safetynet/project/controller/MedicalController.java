package com.safetynet.project.controller;


import com.safetynet.project.model.FunctionalException;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Component
@RestController
public class MedicalController {
    private static final Logger logger = LogManager.getLogger(MedicalController.class);

    private MedicalRecordService medicalRecordService;

    public MedicalController (MedicalRecordService medicalRecordService){
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/medicals")
    public Iterable<MedicalRecords> getAllMedicalRecords(){

        logger.info("req Get endpoint MedicalRecords");
        Iterable<MedicalRecords> medicalRecordsIterable = medicalRecordService.getAllMedicalRecords();
        logger.info("req next Get endpoint MedicalRecords");
        return medicalRecordsIterable;
    }

    @PostMapping("/medicalRecord")
    public MedicalRecords  addMedicalRecord(@Validated @RequestBody MedicalRecords  medicalRecord) {
        logger.info("req Post endpoint MedicalRecords");

        MedicalRecords  createdMedicalRecord = medicalRecordService.addMedicalRecord(medicalRecord);

        if (createdMedicalRecord != null) {
            logger.info("req Post endpoint MedicalRecords sent");
            return createdMedicalRecord;
        } else {
            throw new FunctionalException("medicalRecord.insert.error");

        }
    }

    @PutMapping("/medicalRecord")
    public MedicalRecords updateMedicalRecord(@RequestBody MedicalRecords medicalRecord) {
        logger.info("req Put  endpoint medicalrecord");

        MedicalRecords updatedMedicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);
        if (updatedMedicalRecord != null) {
            logger.info("req Put  endpoint medicalrecord sent");
            return updatedMedicalRecord;
        } else {
            throw new FunctionalException("medicalRecord.update.error");
        }
    }

    @DeleteMapping("/medicalRecord")
    @Transactional
    public void deleteMedicalRecord(@RequestBody MedicalRecords medicalRecords) {
        logger.info("req Delete  endpoint medicalrecord");
        medicalRecordService.deleteMedicalRecord(medicalRecords);
    }


}
