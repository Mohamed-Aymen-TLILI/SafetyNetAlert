package com.safetynet.project.controller;

import com.safetynet.project.model.Person;
import com.safetynet.project.service.JsonReaderService;
import com.safetynet.project.service.PersonService;
import com.safetynet.project.service.SafetyNetData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@RestController
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private SafetyNetData safetyNetData;

    @Autowired
    private JsonReaderService jsonReaderService;

    @GetMapping("/persons")
    public Iterable<Person> getAllPersons() {
        logger.info("Requête Get sur le endpoint 'persons' reçue");

        Iterable<Person> personIterable = personService.getAllPersons();
        logger.info("Réponse suite à la requête Get sur le endpoint persons transmise");

        return personIterable;
    }

    @GetMapping("/save")
    public void LoadInitialData() throws IOException, ParseException {
        logger.info("Requête Get sur le endpoint 'save' reçue");
        safetyNetData.saveDataFromJsonFile();
        jsonReaderService.readDataFromJsonFile();
        logger.info("Réponse suite à la requête Get sur le endpoint save transmise");
    }
}
