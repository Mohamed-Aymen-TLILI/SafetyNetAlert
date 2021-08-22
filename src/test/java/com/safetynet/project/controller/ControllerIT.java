package com.safetynet.project.controller;


import com.safetynet.project.model.Person;
import com.safetynet.project.repository.PersonRepository;
import com.safetynet.project.service.PersonService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ControllerIT {


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

  /*  @Test
    void getFireStationTest() throws Exception {

        this.mockMvc.perform(get("/firestations")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(13)));


    }

    @Test
    void getMedicalRecordsTest() throws Exception {
        this.mockMvc.perform(get("/medicals")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(23)));

    }

    @Test
    void getEmailByCityTest() throws Exception {
        this.mockMvc.perform(get("/communityEmail?city=culver")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(15)));
    }

    @Test
    void getPhoneNumberByStationTest() throws Exception {
        this.mockMvc.perform(get("/phoneAlert?firestation=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
        this.mockMvc.perform(get("/phoneAlert?firestation=3")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)));
    }

    @Test
    void getpersonInfoByNameTest() throws Exception {
        this.mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    void getPersonsByAddressTest() throws Exception {
        this.mockMvc.perform(get("/fire?address=1509 Culver St")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void getChildrenByAddressTest() throws Exception {
        this.mockMvc.perform(get("/childAlert?address=489 Manchester St")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        this.mockMvc.perform(get("/childAlert?address=1509 Culver St")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getUserByStationNumberTest() throws Exception {
        this.mockMvc.perform(get("/firestation?stationNumber=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.fireStationPeopleDTOList", hasSize(6)))
                .andExpect(jsonPath("$.childrenNumber", is(1)))
                .andExpect(jsonPath("$.adultsNumber", is(5)));
    }

    @Test
    void floodByStationTest() throws Exception {
        this.mockMvc.perform(get("/flood/stations?stations=1,4")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is("489 Manchester St")))
                .andExpect(jsonPath("$[0].floodDTOList", hasSize(1)))
                .andExpect(jsonPath("$[1].address", is("908 73rd St")))
                .andExpect(jsonPath("$[1].floodDTOList", hasSize(2)))
                .andExpect(jsonPath("$[2].address", is("947 E. Rose Dr")))
                .andExpect(jsonPath("$[2].floodDTOList", hasSize(3)))
                .andExpect(jsonPath("$[3].address", is("644 Gershwin Cir")))
                .andExpect(jsonPath("$[3].floodDTOList", hasSize(1)))
                .andExpect(jsonPath("$[4].address", is("112 Steppes Pl")))
                .andExpect(jsonPath("$[4].floodDTOList", hasSize(3)));
    }
*/
}
