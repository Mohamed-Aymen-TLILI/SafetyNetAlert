package com.safetynet.project.service;

import com.safetynet.project.model.FireStation;
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

    public boolean saveAllFireStations(List<FireStation> fireStationsList) {
        if (fireStationsList != null && !fireStationsList.isEmpty()){
            try {
                fireStationRepository.saveAll(fireStationsList);
                return true;
            } catch (Exception exception){
                logger.error("Error when save FireStationList" + exception.getMessage() + ", Stack Trace: " + exception.getStackTrace());
            }
        }
        return false;
    }
}
