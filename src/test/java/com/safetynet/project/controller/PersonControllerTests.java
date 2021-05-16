package com.safetynet.project.controller;

import com.safetynet.project.model.Person;
import com.safetynet.project.service.PersonService;
import com.safetynet.project.service.SafetyNetData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SafetyNetData safetyNetData;

    @MockBean
    private PersonService personServiceMock;

    @Test
    public void getAllPersonsTest() throws Exception {

        Person person = new Person();
        person.setLastName("myLastName");
        person.setFirstName("myFirstName");
        person.setAddress("myAddress");
        person.setCity("myCity");
        person.setPhone("myPhone");
        person.setEmail("myEmail");
        person.setZip(2);
        List<Person> personList = new ArrayList<>();
        personList.add(person);

        when(personServiceMock.getAllPersons()).thenReturn(personList);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/persons").
                contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(jsonPath("$").isArray());
    }
    @Test
    public void deletePersonValidTest() throws Exception {
        Person person = new Person();
        person.setLastName("myLastName");
        person.setFirstName("myFirstName");
        person.setAddress("myAddress");
        person.setCity("myCity");
        person.setPhone("myPhone");
        person.setEmail("myEmail");
        person.setZip(2);

        when(personServiceMock.deletePerson(person.getFirstName(), person.getLastName())).thenReturn((long) 1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/person").
                contentType(MediaType.APPLICATION_JSON).
                param("firstname",person.getFirstName()).
                param("lastname",person.getLastName());

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}
