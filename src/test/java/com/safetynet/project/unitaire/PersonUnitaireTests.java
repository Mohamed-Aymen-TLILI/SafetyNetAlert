package com.safetynet.project.unitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.safetynet.project.controller.PersonController;
import com.safetynet.project.model.FunctionalException;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.PersonRepository;
import com.safetynet.project.service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.hamcrest.core.Is.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class PersonUnitaireTests {

    private PersonController personController;

    private PersonService personService;

    private Person person;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonRepository personRepository;

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
    void addPersonTestWithError() throws Exception {
        Person person = new Person();
        this.personService.addPerson(person);
        FunctionalException message = mock(FunctionalException.class);
        when(message.getMessage()).thenReturn("person.insert.error");
        assertThat(message.getMessage(), is("person.insert.error"));
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
