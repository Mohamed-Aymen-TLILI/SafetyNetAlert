package com.safetynet.project.unitaire;

import com.safetynet.project.controller.ChildAlertController;
import com.safetynet.project.dto.ChildAlertDTO;
import com.safetynet.project.mapper.ChildAlertDTOMapper;
import com.safetynet.project.mapper.FamilyMemberDTOMapper;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.MedicalRecordRepository;
import com.safetynet.project.repository.PersonRepository;
import com.safetynet.project.service.ChildAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
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
        this.childAlertService = new ChildAlertService(personRepository, medicalRecordRepository, childAlertDTOMapper, familyMemberDTOMapper);
        this.childAlertController = new ChildAlertController(childAlertService);
    }


    @Test
    void getChildAlertTest() throws Exception {
        ArrayList allergies = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies 2");
        Person person = Person.builder().firstName("test").lastName("testName").address("Chelles").build();
        this.personRepository.save(person);
        MedicalRecords medicalRecords = MedicalRecords.builder().firstName("test").lastName("testName").birthdate(LocalDate.of(2011, 01, 13)).allergies(allergies).build();
        this.medicalRecordRepository.save(medicalRecords);
        List<ChildAlertDTO> list = this.childAlertService.getChildAlertDTOListFromAddress("Chelles");
        this.childAlertController.getChildAlertList("Chelles");
        when(this.childAlertService.getChildAlertDTOListFromAddress("Chelles")).thenReturn(list);
        List<ChildAlertDTO> list1 = this.childAlertService.getChildAlertDTOListFromAddress("Chelles");
        assertThat(list1, hasSize(1));
    }

}
