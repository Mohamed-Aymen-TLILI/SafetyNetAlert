package com.safetynet.project.unitaire;

import com.safetynet.project.controller.MedicalController;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.repository.MedicalRecordRepository;
import com.safetynet.project.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

public class MedicalUnitaireTests {

    private MedicalController medicalController;

    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @BeforeEach
    private void  setUpEachTest() {
        MockitoAnnotations.initMocks(this);
        this.medicalRecordService = new MedicalRecordService(medicalRecordRepository);
        this.medicalController = new MedicalController(medicalRecordService);
    }

    @Test
    void getMedicalTests() throws Exception {
        ArrayList allergies = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies 2");
        MedicalRecords medicalRecords = MedicalRecords.builder().firstName("test").lastName("testName").allergies(allergies).build();
        List<MedicalRecords> actual = Arrays.asList(medicalRecords);
        when(this.medicalRecordRepository.findAll()).thenReturn(actual);
        Iterable<MedicalRecords> allMedicalsRecords = this.medicalController.getAllMedicalRecords();
        List<MedicalRecords> result = new ArrayList<MedicalRecords>();
        allMedicalsRecords.iterator().forEachRemaining(result::add);
        assertThat(result, hasSize(1));
    }

    @Test
    void addMedicalRecorsTests() throws Exception {
        ArrayList allergies = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies 2");
        ArrayList allergies2 = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies 2");
        MedicalRecords medicalRecords = MedicalRecords.builder().firstName("test").lastName("testName").allergies(allergies).build();
        MedicalRecords addMedical = new MedicalRecords();
        addMedical.setFirstName("hello");
        addMedical.setLastName("salut");
        addMedical.setAllergies(allergies2);
        MedicalRecords add =  this.medicalController.addMedicalRecord(addMedical);
        List<MedicalRecords> actual = Arrays.asList(medicalRecords, add);
        when(this.medicalRecordRepository.findAll()).thenReturn(actual);
        Iterable<MedicalRecords> allMedicalsRecords = this.medicalController.getAllMedicalRecords();
        List<MedicalRecords> result = new ArrayList<MedicalRecords>();
        allMedicalsRecords.iterator().forEachRemaining(result::add);
        assertThat(result, hasSize(2));

    }


    @Test
    void updateMedicalsRecordTest() throws Exception {
        ArrayList allergies = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies 2");
        ArrayList allergies2 = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies 2");
        MedicalRecords medicalRecords = MedicalRecords.builder().firstName("test").lastName("testName").allergies(allergies).build();
        when(medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(Optional.of(medicalRecords));
        this.medicalController.updateMedicalRecord(medicalRecords);
        verify(medicalRecordRepository).save(medicalRecords);
    }

    @Test
    void deleteMedicalRecordTest() throws Exception {
        ArrayList allergies = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies 2");
        ArrayList allergies2 = new ArrayList();
        allergies.add("allergie1");
        allergies.add("allergies 2");
        MedicalRecords medicalRecords = MedicalRecords.builder().firstName("test").lastName("testName").allergies(allergies).build();
        when(medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(Optional.of(medicalRecords));
        this.medicalController.deleteMedicalRecord((medicalRecords));
        verify(medicalRecordRepository).deleteMedicalRecordsByFirstNameAndLastNameAllIgnoreCase(medicalRecords.getFirstName(), medicalRecords.getLastName());
    }

}
