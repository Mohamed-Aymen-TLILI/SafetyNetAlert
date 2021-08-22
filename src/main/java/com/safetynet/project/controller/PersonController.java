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
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }



    @GetMapping("/persons")
    public Iterable<Person> getAllPersons() {
        logger.info("req Get endpoint 'person'");

        Iterable<Person> personIterable = personService.getAllPersons();
        logger.info("response Put endpoint 'person'");

        return personIterable;
    }

    @PostMapping("/person")
    public Person addPerson(@Validated @RequestBody Person person) {
        logger.info("req Post endpoint 'person'");

        Person createdPerson = personService.addPerson(person);

        if (createdPerson != null) {
            logger.info("response Post endpoint 'person'");
            return createdPerson;
        } else {
            throw new FunctionalException("person.insert.error");

        }
    }

    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person person) {
        logger.info("req Put endpoint 'person'");

        Person updatedPerson = personService.updatePerson(person);
        if (updatedPerson != null) {
            logger.info("req next Put endpoint 'person' ");
            return updatedPerson;
        } else {
            throw new FunctionalException("person.update.error");
        }
    }

    @DeleteMapping("/person")
    @Transactional
    public void deletePerson(@RequestBody Person person) { logger.info("Req Delete  endpoint 'person'");
        personService.deletePerson(person);

    }

}
