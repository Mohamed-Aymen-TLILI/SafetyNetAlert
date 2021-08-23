package com.safetynet.project.unitaire;

import com.safetynet.project.controller.PersonController;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.PersonRepository;
import com.safetynet.project.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

public class PersonUnitaireTests {

    private PersonController personController;

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    private void  setUpEachTest() {
        MockitoAnnotations.initMocks(this);
        this.personService = new PersonService(personRepository);
        this.personController = new PersonController(this.personService);


    }

    @Test
    void getPersonsTest() throws Exception {
        Person person = Person.builder().firstName("test").lastName("testName").address("Chelles").build();
        List<Person> actual = Arrays.asList(person);
        when(this.personRepository.findAll()).thenReturn(actual);
        Iterable<Person> allPersons = this.personController.getAllPersons();
        List<Person> result = new ArrayList<Person>();
        allPersons.iterator().forEachRemaining(result::add);
        assertThat(result, hasSize(1));
    }
}
