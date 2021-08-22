package com.safetynet.project.controller;

import com.safetynet.project.model.FunctionalException;
import com.safetynet.project.model.Person;
import com.safetynet.project.service.JsonReaderService;
import com.safetynet.project.service.PersonService;
import com.safetynet.project.service.SafetyNetData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Component
@RestController
public class InitController {

    private static final Logger logger = LogManager.getLogger(InitController.class);



    @Autowired
    private SafetyNetData safetyNetData;

    @Autowired
    private JsonReaderService jsonReaderService;


    @GetMapping("/init")
    public void LoadInitialData() throws IOException, ParseException {
        logger.info("req Get sur le endpoint 'save'");
        safetyNetData.saveDataFromJsonFile();
        jsonReaderService.readDataFromJsonFile();
        logger.info("response Get sur le endpoint 'save'");
    }

}
