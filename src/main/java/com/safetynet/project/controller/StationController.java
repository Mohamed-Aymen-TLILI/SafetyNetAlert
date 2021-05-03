package com.safetynet.project.controller;

import com.safetynet.project.model.FireStation;
import com.safetynet.project.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class StationController {

    private static final Logger logger = LogManager.getLogger(StationController.class);

    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/stations")
    public Iterable<FireStation> getAllStations(){
        logger.info("Requête Get sur le endpoint 'FireStation' reçue");
        Iterable<FireStation> fireStationIterable =  fireStationService.getAllStations();
        logger.info("Réponse suite à la requête Get sur le endpoint persons transmise");
        return fireStationIterable;
    }

}
