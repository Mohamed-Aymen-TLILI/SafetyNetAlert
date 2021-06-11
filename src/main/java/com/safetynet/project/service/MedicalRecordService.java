package com.safetynet.project.service;

import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
     * Get medical record
     *
     * @return medcal record list
     */

    public Iterable<MedicalRecords> getAllMedicalRecords(){
        try {
            return medicalRecordRepository.findAll();
        } catch (Exception exception) {
            logger.error("Error while getting a list of medical records  : " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
            return null;
        }
    }
    /**
     * add a medical recored
     * @param medicalRecord
     * @return a medical
     */
    public MedicalRecords addMedicalRecord(MedicalRecords medicalRecord) {
        if (medicalRecord != null) {
            Optional<MedicalRecords> medicalRecordOptional = this.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(),medicalRecord.getLastName());
            if (medicalRecordOptional.isPresent())
            {
                logger.error("Error while adding a  exist medical records:");
                return null;
            }
            else {
                try {
                    medicalRecordRepository.save(medicalRecord);
                }
                catch (Exception exception) {
                    logger.error("Error while adding a  medical records:" + exception.getMessage() + " StackTrace : " + exception.getStackTrace());
                    return null;
                }
            }
        }
        return medicalRecord;
    }


    /**
     * Update medical records
     * @param medicalRecord
     * @return medical records
     */
    public MedicalRecords updateMedicalRecord(final MedicalRecords medicalRecord) {
        if (medicalRecord!=null) {
            Optional<MedicalRecords> medicalRecordOptional = this.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());

            if (medicalRecordOptional.isPresent()) {
                MedicalRecords medicalRecordToUpdate = medicalRecordOptional.get();

                medicalRecordToUpdate.setMedications(medicalRecord.getMedications());
                medicalRecordToUpdate.setAllergies(medicalRecord.getAllergies());

                try {
                    medicalRecordRepository.save(medicalRecordToUpdate);
                    return medicalRecordToUpdate;
                } catch (Exception exception) {
                    logger.error("Error while updating a medical records : " + exception.getMessage() + " StackTrace : " + exception.getStackTrace());
                    return null;
                }
            } else {
                logger.error("Error while updating a medical records : this person doesen't exist");
                return null;
            }
        }
        else
        {
            logger.error("Error while updating a medical records : null object");
            return null;
        }
    }


    /**
     * delete medical record by firstName abd lastName
     * @param medicalRecords
     * @return null in case have a problem
     */
    public void deleteMedicalRecord(MedicalRecords medicalRecords) {
            medicalRecordRepository.deleteMedicalRecordsByFirstNameAndLastNameAllIgnoreCase(medicalRecords.getFirstName(), medicalRecords.getLastName());
    }





    /**
     * Get medical record by firstName abd lastName
     * @param firstname
     * @param lastname
     * @return medical records if found it
     */
    public Optional<MedicalRecords> getMedicalRecordByFirstNameAndLastName(String firstname, String lastname) {
        try {
            return medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(firstname, lastname);
        } catch (Exception exception) {
            logger.error("Error while getting a list of medical records  : " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
            return null;
        }
    }
}
