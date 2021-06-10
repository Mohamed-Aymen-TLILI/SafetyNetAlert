package com.safetynet.project.controller;

import com.safetynet.project.model.FireStation;
import com.safetynet.project.model.FunctionalException;
import com.safetynet.project.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
public class StationController {

    private static final Logger logger = LogManager.getLogger(StationController.class);

    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/firestations")
    public Iterable<FireStation> getAllStations(){
        logger.info("req get endpoint firestations  ");
        Iterable<FireStation> fireStationIterable =  fireStationService.getAllStations();
        logger.info("req get endpoint firestations done");
        return fireStationIterable;
    }

    @PostMapping("/firestation")
    public FireStation addFireStation(@Validated @RequestBody FireStation fireStation) {
        logger.info("req post endpoint firestations");

        FireStation createdFireStation = fireStationService.addFireStation(fireStation);

        if (createdFireStation != null) {
            logger.info("response post endpoint firestations");
            return createdFireStation;
        } else {
            throw new FunctionalException("firestation.insert.error");
        }
    }

    @PutMapping("/firestation")
    public FireStation updateFireStation(@RequestBody FireStation fireStation)
    {
        logger.info("rep put endpoint firestation");

        FireStation updatedFireStation = fireStationService.updateFireStation(fireStation);
        if (updatedFireStation!=null )
        {
            logger.info("response Put endpoint firestation");
            return updatedFireStation;
        }
        else
        {
            throw new FunctionalException("firestation.update.error");
        }
    }

    @DeleteMapping("/firestation/{address}")
    @Transactional
    public void deleteFireStation (@PathVariable String address) {
        logger.info("req delete endpoint firestation with adress");
        fireStationService.deleteFireStationByAddress(address);
    }

    @DeleteMapping("/firestation/{address}/{station}")
    @Transactional
    public void deleteFireStation (@PathVariable String address, @PathVariable Integer station ) {
        logger.info("req delete endpoint firestation with adress and station");
        fireStationService.deleteFireStationByAddressAndStation(address, station);
    }


}
