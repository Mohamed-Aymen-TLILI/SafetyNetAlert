package com.safetynet.project.controller;


import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.service.FireStationService;
import com.safetynet.project.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class MedicalController {
    private static final Logger logger = LogManager.getLogger(MedicalController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicals")
    public Iterable<MedicalRecords> getAllMedicalRecords(){

        logger.info("Requête Get sur le endpoint 'MedicalRecords' reçue");
        Iterable<MedicalRecords> medicalRecordsIterable = medicalRecordService.getAllMedicalRecords();
        logger.info("Réponse suite à la requête Get sur le endpoint 'MedicalRecords' transmise");
        return medicalRecordsIterable;
    }
}
