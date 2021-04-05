package com.safetynet.project.controller;

import com.safetynet.project.model.FunctionalException;
import com.safetynet.project.model.Person;
import com.safetynet.project.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

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
}
