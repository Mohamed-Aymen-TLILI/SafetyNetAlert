package com.safetynet.project.service;

import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MedicalRecordService {

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * Sauvegarde list of medicalRecord
     *
     * @param medicalRecordList
     */

    public void saveAllMedicalRecords(List<MedicalRecords> medicalRecordList){
        if (medicalRecordList != null && !medicalRecordList.isEmpty()){
            try {
                medicalRecordRepository.saveAll(medicalRecordList);
            } catch (Exception exception){
                logger.error("Error when save list of recordMedical");
            }
        }
    }

    /**
     * Retourne l'ensemble des medicals existantes
     *
     * @return Liste des medicals
     */

    public Iterable<MedicalRecords> getAllMedicalRecords(){
        try {
            return medicalRecordRepository.findAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la récupération de la liste des medicals : " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
            return null;
        }
    }

}
