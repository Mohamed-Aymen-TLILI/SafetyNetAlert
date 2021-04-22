package com.safetynet.project.service;

import com.safetynet.project.model.FireStation;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {
    private static final Logger logger = LogManager.getLogger(FireStationService.class);

    @Autowired
    private FireStationRepository fireStationRepository;

    /**
     * Sauvgarde List of fire's stations
     *
     * @param fireStationsList
     */

    public void saveAllFireStations(List<FireStation> fireStationsList) {
        if (fireStationsList != null && !fireStationsList.isEmpty()){
            try {
                fireStationRepository.saveAll(fireStationsList);
            } catch (Exception exception){
                logger.error("Error when save FireStationList" + exception.getMessage() + ", Stack Trace: " + exception.getStackTrace());
            }
        }
    }


    /**
     * Retourne l'ensemble des stations existantes
     *
     * @return Liste des stations
     */
    public Iterable<FireStation> getAllStations() {
        try {
            return fireStationRepository.findAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la récupération de la liste des stations : " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
            return null;
        }
    }

}
