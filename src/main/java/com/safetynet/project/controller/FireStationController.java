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

import java.util.List;

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

    @GetMapping("/phoneAlert")
    public List<String> getPhoneListByFireStation(@RequestParam Integer firestation){
        logger.info("req Get endpoint 'phoneAlert' with stationNumber : {"  + firestation.toString() +  "}");
        List<String> phoneList = fireStationPeopleService.getPhoneListByStationNumber(firestation);;
        if (phoneList != null) {
            logger.info("fireStationPeopleService.getPhoneListByStationNumber.success");
            return phoneList;
        } else {
            throw new FunctionalException("fireStationPeopleService.getPhoneListByStationNumber.error");
        }
    }

}
