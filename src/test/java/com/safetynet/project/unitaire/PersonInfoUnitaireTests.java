package com.safetynet.project.unitaire;

import com.safetynet.project.controller.PersonInfoController;
import com.safetynet.project.dto.PersonDTO;
import com.safetynet.project.mapper.PersonDTOMapper;
import com.safetynet.project.mapper.PersonDTOMapperImpl;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.MedicalRecordRepository;
import com.safetynet.project.repository.PersonRepository;
import com.safetynet.project.service.DateUtils;
import com.safetynet.project.service.PersonInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PersonInfoUnitaireTests {

    private PersonInfoService personInfoService;

    private PersonInfoController personInfoController;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private PersonDTOMapper personDTOMapper;

    @BeforeEach
    private void  setUpEachTest() {
        MockitoAnnotations.initMocks(this);
        this.personDTOMapper = new PersonDTOMapperImpl();
        DateUtils dateUtils = new DateUtils();
        this.personDTOMapper.setDateUtils(dateUtils);
        this.personInfoService = new PersonInfoService(medicalRecordRepository,  personRepository, personDTOMapper);
        this.personInfoController = new PersonInfoController(personInfoService);
    }

    @Test
    void personInfoTest() throws Exception {

        ArrayList allergies = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies2");
        ArrayList medicals = new ArrayList();
        medicals.add("medicals");
        medicals.add("medicals2");
        MedicalRecords medicalRecords = MedicalRecords.builder().firstName("test").lastName("testName").allergies(allergies).medications(medicals).build();
        Person person = Person.builder().firstName("test").lastName("testName").address("Chelles").city("City").phone("841-874-7458").build();
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        List<MedicalRecords> medicalRecordList = new ArrayList<>();
        medicalRecordList.add(medicalRecords);
        when(personRepository.findAllByLastNameAllIgnoreCase(any())).thenReturn(personList);
        when(medicalRecordRepository.findAllByLastNameAllIgnoreCase(any())).thenReturn(medicalRecordList);
        List<PersonDTO> personsInfo = this.personInfoController.getPersonsInfo(person.getLastName());
        assertThat(personsInfo.size()).isEqualTo(1);
    }

    @Test
    void personCommunityEmailTest() throws Exception {
        Person person = Person.builder().firstName("test").lastName("testName").address("Chelles").city("City").phone("841-874-7458").email("chelles@gmail.com").build();
        Person person1 = Person.builder().firstName("test1").lastName("testName1").address("Chelles").city("City").phone("841-874-7458").email("chelles1@gmail.com").build();
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        when(personRepository.findAllByCityIgnoreCase(any())).thenReturn(personList);
        Iterable<String> communityEmail = this.personInfoController.getCommunityEmail(person.getCity());
        List<String> result = new ArrayList<>();
        communityEmail.iterator().forEachRemaining(result::add);
        assertThat(result.size()).isEqualTo(2);
    }
}
