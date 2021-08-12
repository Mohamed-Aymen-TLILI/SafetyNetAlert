package com.safetynet.project.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPersonsTest() throws Exception {

        this.mockMvc.perform(get("/persons")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(23)));


    }

    @Test
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

}
