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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/person")
    public Person addPerson(@Validated @RequestBody Person person) {
        logger.info("Requête Post sur le endpoint 'person' reçue");

        Person createdPerson = personService.addPerson(person);

        if (createdPerson != null) {
            logger.info("Réponse suite au Post sur le endpoint 'person' envoyée");
            return createdPerson;
        } else {
            throw new FunctionalException("person.insert.error");

        }
    }

    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person person) {
        logger.info("Requête Put sur le endpoint 'person' reçue");

        Person updatedPerson = personService.updatePerson(person);
        if (updatedPerson != null) {
            logger.info("Réponse suite au Put sur le endpoint 'person' envoyée");
            return updatedPerson;
        } else {
            throw new FunctionalException("person.update.error");
        }
    }

    @DeleteMapping("/person")
        public Long deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("Requête Delete sur le endpoint 'person' reçue avec les paramètres firstname :" + firstName + " et lastname : " + lastName + " reçue");

        Long deleteResult = personService.deletePerson(firstName, lastName);
        if (deleteResult != null) {
            logger.info("Réponse suite au Delete sur le endpoint 'person' reçue avec les paramètres firstname :" + firstName + " et lastname : " + lastName + " envoyée");
            return deleteResult;
        } else {
            throw new FunctionalException("person.delete.error");
        }
    }

    @GetMapping("/save")
    public void LoadInitialData() throws IOException, ParseException {
        logger.info("Requête Get sur le endpoint 'save' reçue");
        safetyNetData.saveDataFromJsonFile();
        jsonReaderService.readDataFromJsonFile();
        logger.info("Réponse suite à la requête Get sur le endpoint save transmise");
    }

}
