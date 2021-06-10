package com.safetynet.project.service;

import com.safetynet.project.model.FireStation;
import com.safetynet.project.repository.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FireStationService {
    private static final Logger logger = LogManager.getLogger(FireStationService.class);

    @Autowired
    private FireStationRepository fireStationRepository;

    /**
     * Sauvgarde List of fire's stations
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
     * return list of exist station
     * @return station list
     */
    public Iterable<FireStation> getAllStations() {
        try {
            return fireStationRepository.findAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la récupération de la liste des stations : " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
            return null;
        }
    }


    /**
     * add a station if dosen't exist
     *
     * @param fireStation
     * @return null if has a problem
     */
    public FireStation addFireStation(FireStation fireStation) {
        if (fireStation != null) {
            Optional<FireStation> existingFireStation = fireStationRepository.findFirstByAddressIgnoreCaseAndStation(fireStation.getAddress(), fireStation.getStation());
            if (existingFireStation.isPresent()) {
                logger.error("error when add an existing station");
                return null;
            } else {
                try {
                    fireStationRepository.save(fireStation);
                } catch (Exception exception) {
                    logger.error("error when add a station : " + exception.getMessage() + " Stack Trace : " + exception.getStackTrace());
                    return null;
                }
            }

        }
        return fireStation;
    }


    /**
     * update station number
     *
     * @param fireStation
     * @return null if has a problem when updating
     */
    public FireStation updateFireStation(FireStation fireStation) {
        if (fireStation != null) {
            List<FireStation> sameAdresseFireStationList = fireStationRepository.findDistinctByAddressIgnoreCase(fireStation.getAddress());

            if (sameAdresseFireStationList .isEmpty()) {
                logger.error("error when updating not exist station ");
                return null;

            } else {

                for (FireStation fireStationToUpdate : sameAdresseFireStationList)
                {
                    fireStationToUpdate.setStation(fireStation.getStation());
                    try {
                        fireStationRepository.save(fireStationToUpdate);
                    } catch (Exception exception) {
                        logger.error("error when updating Fire station:" + exception.getMessage() +
                                " Stack Trace : " + exception.getStackTrace());
                        return null;
                    }
                }
                return fireStation;
            }
        }
        return null;
    }


    /**
     * delete a Fire station with address
     * @param address
     * @return null if address not found
    */
    public void deleteFireStationByAddress(String address) {
        if (address != null) {
            List<FireStation> existAddressFireStationList = fireStationRepository.findDistinctByAddressIgnoreCase(address);
            if (existAddressFireStationList.isEmpty()){
                logger.error("error when delete a station with address doesn't exist");
                return;
            } else {
                try {
                    fireStationRepository.deleteByAddressIgnoreCase(address);
                } catch (Exception e) {
                    logger.error("error when delete a station");
                    return;
                }
            }
        }
        return;
    }

    /**
     * delete a Fire station with address And station
     * @param address
     * @param station
     * @return null if address not found
     */
    public void deleteFireStationByAddressAndStation(String address, Integer station) {
        if (address != null && station != null) {
            Optional<FireStation> existAddressFireStationList = fireStationRepository.findFirstByAddressIgnoreCaseAndStation(address, station);
            if (existAddressFireStationList.isEmpty()){
                logger.error("error when delete a station with address doesn't exist");
                return;
            } else {
                try {
                    fireStationRepository.deleteByAddressIgnoreCaseAndStation(address, station);
                } catch (Exception e) {
                    logger.error("error when delete a station");
                    return;
                }
            }
        }
        return;
    }
}
