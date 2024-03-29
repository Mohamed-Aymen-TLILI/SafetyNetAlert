package com.safetynet.project.service;


import com.safetynet.project.dto.PersonDTO;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.MedicalRecordRepository;
import com.safetynet.project.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class PersonInfoServiceTests {

    @MockBean
    private PersonRepository personRepositoryMock;

    @MockBean
    private MedicalRecordRepository medicalRecordRepositoryMock;

    @SpyBean
    private DateUtils dateUtilsSpy;

    @Autowired
    private PersonInfoService personInfoService;

    private Person person;

    private List<Person> personList;

    private List<MedicalRecords> medicalRecordList;

    @BeforeAll
    private static void setUpAllTest() {

    }

    @BeforeEach
    private void setUpEachTest() {
        person = new Person();
        person.setZip(12345);
        person.setEmail("myEmail");
        person.setPhone("myPhone");
        person.setCity("myCity");
        person.setAddress("myAddress");
        person.setFirstName("firstName");
        person.setLastName("lastName");

    }

    @Test
    public void getPersonsInfoWithNullValues() {
        assertThat(personInfoService.getPersonsInfo( null)).isNull();
        verify(personRepositoryMock, Mockito.times(0)).findAllByLastNameAllIgnoreCase(any(String.class));
        verify(medicalRecordRepositoryMock, Mockito.times(0)).findAllByLastNameAllIgnoreCase(any(String.class));
    }

    @Test
    public void getPersonsInfoMappingWithValidList() {

        MedicalRecords medicalRecord = new MedicalRecords();
        medicalRecord.setFirstName(person.getFirstName());
        medicalRecord.setLastName(person.getLastName());

        LocalDate birthDate = LocalDate.of(2000, 5, 14);

        medicalRecord.setBirthdate(birthDate);
        List<String> allergiesList = new ArrayList<>();
        allergiesList.add("Allergie 1");
        allergiesList.add("Allergie 2");

        medicalRecord.setAllergies(allergiesList);

        List<String> medicamentList = new ArrayList<>();
        medicamentList.add("medicament 1");
        medicamentList.add("medicament 2");

        medicalRecord.setMedications(medicamentList);

        Person secondPerson = new Person();
        secondPerson.setZip(12133);
        secondPerson.setEmail("");
        secondPerson.setPhone("");
        secondPerson.setCity("");
        secondPerson.setAddress("");
        secondPerson.setFirstName("second Person firstname");
        secondPerson.setLastName(person.getLastName());

        MedicalRecords secondMedicalRecord = new MedicalRecords();
        secondMedicalRecord.setFirstName(secondPerson.getFirstName());
        secondMedicalRecord.setLastName(secondPerson.getLastName());
        secondMedicalRecord.setBirthdate(LocalDate.of(1970, 12, 31));
        List<String> secondAllergiesList = new ArrayList<>();
        secondAllergiesList.add("Second Allergie 1");

        secondMedicalRecord.setAllergies(secondAllergiesList);
        secondMedicalRecord.setMedications(new ArrayList<>());

        Person thirdPerson = new Person();
        thirdPerson.setZip(12345);
        thirdPerson.setEmail("");
        thirdPerson.setPhone("");
        thirdPerson.setCity("");
        thirdPerson.setAddress("");
        thirdPerson.setFirstName("third Person firstname");
        thirdPerson.setLastName(person.getLastName());

        personList = new ArrayList<>();
        personList.add(person);
        personList.add(secondPerson);
        personList.add(thirdPerson);

        medicalRecordList = new ArrayList<>();
        medicalRecordList.add(medicalRecord);
        medicalRecordList.add(secondMedicalRecord);

        when(personRepositoryMock.findAllByLastNameAllIgnoreCase(person.getLastName())).thenReturn(personList);
        when(medicalRecordRepositoryMock.findAllByLastNameAllIgnoreCase(person.getLastName())).thenReturn(medicalRecordList);
        LocalDate nowMockLocalDate = LocalDate.of(2010, 12, 31);
        when(dateUtilsSpy.getNowLocalDate()).thenReturn(nowMockLocalDate);

        List<PersonDTO> personInfosListDTO = personInfoService.getPersonsInfo(person.getLastName());

        assertThat(personInfosListDTO).size().isEqualTo(3);
    }

}
