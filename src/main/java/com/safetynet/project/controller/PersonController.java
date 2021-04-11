package com.safetynet.project.controller;

import com.safetynet.project.model.Person;
import com.safetynet.project.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    private PersonService personService;

    @GetMapping("/persons")
    public Iterable<Person> getAllPersons() {
        logger.info("Requête Get sur le endpoint 'persons' reçue");

        Iterable<Person> personIterable = personService.getAllPersons();

        logger.info("Réponse suite à la requête Get sur le endpoint persons transmise");

        return personIterable;
    }

}
