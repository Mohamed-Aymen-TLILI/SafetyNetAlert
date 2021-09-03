package com.safetynet.project.unitaire;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.safetynet.project.controller.PersonController;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.PersonRepository;
import com.safetynet.project.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

public class PersonUnitaireTests {

    private PersonController personController;

    private PersonService personService;

    private Person person;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonRepository personRepository;
    private static final Logger logger = LogManager.getLogger(PersonService.class);
    String firstnameTest = "Marc";
    String lastnameTest = "Dupont";

    @BeforeEach
    private void  setUpEachTest() {
        MockitoAnnotations.initMocks(this);
        this.personService = new PersonService(personRepository);
        this.personController = new PersonController(this.personService);

       Person person = new Person();
        person.setFirstName("LORD");
        person.setLastName("Franklin");
        person.setEmail("flord@test.com");
        person.setPhone("0203003");
        person.setZip(8484848);
        person.setAddress("43 Bruyère");
        person.setCity("Albatros");
    }

    @Test
    void getPersonsTest() throws Exception {
        Person person = Person.builder().firstName("test").lastName("testName").address("Chelles").city("City").phone("841-874-7458").build();
        List<Person> actual = Arrays.asList(person);
        when(this.personRepository.findAll()).thenReturn(actual);
        Iterable<Person> allPersons = this.personController.getAllPersons();
        List<Person> result = new ArrayList<Person>();
        allPersons.iterator().forEachRemaining(result::add);
        assertThat(result, hasSize(1));
    }

    @Test
    void addPersonTest() throws Exception {
        Person person = new Person();
        person.setAddress("chelles");
        person.setLastName("test");
        person.setCity("City");
        person.setFirstName("testName");
        person.setEmail("test@test.com");
        person.setPhone("841-874-7458");
        person.setZip(97451);
        this.personService.addPerson(person);
        when(this.personController.addPerson(person)).thenReturn(person);
        Person response = personController.addPerson(person);
        try {
            assertThat(response.getFirstName()).isEqualTo("testName") ;
        }
        catch (Exception e) {
            assertThat(e.getMessage(), is("person.insert.error"));
        }
    }

    @Test
    void deletePersonTest() throws Exception {
        Person person = Person.builder().firstName("test").lastName("testName").address("Chelles").city("City").phone("841-874-7458").build();
       this.personController.deletePerson(person);
       verify(personRepository).removeByFirstNameAndLastName("test", "testName");
    }

    @Test
    void updatePersonTest() throws Exception {
        Person person = Person.builder().firstName("test").lastName("testName").address("Chelles").city("City").phone("841-874-7458").build();
       when(personRepository.findByFirstNameAndLastNameAllIgnoreCase(person.getFirstName(), person.getLastName())).thenReturn(Optional.of(person));
       this.personController.updatePerson(person);
       verify(personRepository).save(person);
    }

}
