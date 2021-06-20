package com.safetynet.project.controller;

import com.safetynet.project.dto.FireStationPeopleDTO;
import com.safetynet.project.model.FunctionalException;
import com.safetynet.project.service.FireStationPeopleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

    private static final Logger logger = LogManager.getLogger(FireStationController.class);

    @Autowired
    FireStationPeopleService fireStationPeopleService;

    @GetMapping("/firestaion")
    public FireStationPeopleDTO getFireStationPeople(@RequestParam Integer stationNumber)  {
        logger.info("req Get endpoint 'firestaion' with stationNumber : {" + stationNumber + "}");

        FireStationPeopleDTO fireStationPeopleDTO = fireStationPeopleService.getFireStationPeople(stationNumber);

        if (fireStationPeopleDTO != null) {
            logger.info("fireStationPeopleService.getFireStationPeople.success");
            return fireStationPeopleDTO;
    } else {
        throw new FunctionalException("fireStationPeopleService.getFireStationPeople.error");
        }
    }
}
