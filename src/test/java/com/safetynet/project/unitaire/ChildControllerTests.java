package com.safetynet.project.unitaire;

import com.safetynet.project.controller.ChildAlertController;
import com.safetynet.project.dto.ChildAlertDTO;
import com.safetynet.project.mapper.ChildAlertDTOMapper;
import com.safetynet.project.mapper.ChildAlertDTOMapperImpl;
import com.safetynet.project.mapper.FamilyMemberDTOMapper;
import com.safetynet.project.mapper.FamilyMemberDTOMapperImpl;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.MedicalRecordRepository;
import com.safetynet.project.repository.PersonRepository;
import com.safetynet.project.service.ChildAlertService;
import com.safetynet.project.service.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ChildControllerTests {

    private ChildAlertController childAlertController;

    private ChildAlertService childAlertService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;


    private ChildAlertDTOMapper childAlertDTOMapper;

    private FamilyMemberDTOMapper familyMemberDTOMapper;


    @BeforeEach
    private void setUpEachTest() {
        MockitoAnnotations.initMocks(this);
        this.childAlertDTOMapper = new ChildAlertDTOMapperImpl();
        this.familyMemberDTOMapper = new FamilyMemberDTOMapperImpl();
        DateUtils dateUtils = new DateUtils();
        this.childAlertDTOMapper.setDateUtils(dateUtils);
        this.childAlertService = new ChildAlertService(personRepository, medicalRecordRepository, childAlertDTOMapper, familyMemberDTOMapper);
        this.childAlertController = new ChildAlertController(childAlertService);
    }


    @Test
    void getChildAlertTest() throws Exception {
        ArrayList allergies = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies 2");
        Person person = Person.builder().firstName("test").lastName("testName").address("Chelles").build();
        Person person1 = Person.builder().firstName("test1").lastName("testName1").address("Chelles").build();
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        MedicalRecords medicalRecords = MedicalRecords.builder().firstName("test").lastName("testName").birthdate(LocalDate.of(2011, 01, 13)).allergies(allergies).build();
        MedicalRecords medicalRecords1 = MedicalRecords.builder().firstName("test1").lastName("testName1").birthdate(LocalDate.of(2011, 01, 13)).allergies(allergies).build();
        when(medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(any(),any())).thenReturn(Optional.of(medicalRecords));
        when(personRepository.findAllByAddressIgnoreCase("Chelles")).thenReturn(personList);
        List<ChildAlertDTO> childAlertList = this.childAlertController.getChildAlertList("Chelles");

        assertThat(childAlertList, hasSize(2));
    }
}
