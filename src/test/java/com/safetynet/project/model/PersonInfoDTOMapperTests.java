package com.safetynet.project.model;

import com.safetynet.project.dto.PersonDTO;
import com.safetynet.project.mapper.PersonDTOMapper;
import com.safetynet.project.service.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class PersonInfoDTOMapperTests {

    @SpyBean
    private DateUtils dateUtilsSpy;

    @Autowired
    private PersonDTOMapper personInfoDTOMapper;

    @Test
    public void personInfoDTO_MapsCorrect() {
        Person person = new Person();
        person.setFirstName("personFirstName");
        person.setLastName("personLastName");
        person.setAddress("personAddress");
        person.setPhone("personPhone");
        person.setZip(12345);
        person.setEmail("personMail");
        person.setCity("personCity");

        MedicalRecords medicalRecord = new MedicalRecords();
        medicalRecord.setLastName("medicalRecordLastName");
        medicalRecord.setFirstName("medicalRecordFirstNAme");
        medicalRecord.setBirthdate(LocalDate.of(2000, 01, 01));
        List<String> allergiesList = new ArrayList<>();
        allergiesList.add("Allergie 1");
        allergiesList.add("Allergie 2");

        medicalRecord.setAllergies(allergiesList);

        List<String> medicamentList = new ArrayList<>();
        medicamentList.add("medicament 1");
        medicamentList.add("medicament 2");

        medicalRecord.setMedications(medicamentList);

        LocalDate nowLocalDateMock = LocalDate.of(2010, 12, 31);
        when(dateUtilsSpy.getNowLocalDate()).thenReturn(nowLocalDateMock);

        PersonDTO personInfoDTO = personInfoDTOMapper.personToPersonInfoDTO(person, medicalRecord);

        assertThat(personInfoDTO.getAddress()).isEqualTo(person.getAddress());
        assertThat(personInfoDTO.getLastname()).isEqualTo(person.getLastName());
        assertThat(personInfoDTO.getMail()).isEqualTo(person.getEmail());
        assertThat(personInfoDTO.getAllergiesList()).isEqualTo(medicalRecord.getAllergies());
        assertThat(personInfoDTO.getMedicationList()).isEqualTo(medicalRecord.getMedications());
        assertThat(personInfoDTO.getAge()).isEqualTo(Period.between(medicalRecord.getBirthdate(), nowLocalDateMock).getYears());

    }

    @Test
    public void personInfoDTO_NullPerson() {
        Person person = null;

        MedicalRecords medicalRecord = new MedicalRecords();
        medicalRecord.setLastName("medicalRecordLastName");
        medicalRecord.setFirstName("medicalRecordFirstNAme");
        medicalRecord.setBirthdate(LocalDate.of(2000, 01, 01));
        List<String> allergiesList = new ArrayList<>();
        allergiesList.add("Allergie 1");
        allergiesList.add("Allergie 2");

        medicalRecord.setAllergies(allergiesList);

        List<String> medicamentList = new ArrayList<>();
        medicamentList.add("medicament 1");
        medicamentList.add("medicament 2");

        medicalRecord.setMedications(medicamentList);

        LocalDate nowLocalDateMock = LocalDate.of(2010, 12, 31);
        when(dateUtilsSpy.getNowLocalDate()).thenReturn(nowLocalDateMock);

        PersonDTO personInfoDTO = personInfoDTOMapper.personToPersonInfoDTO(person, medicalRecord);

        assertThat(personInfoDTO.getAddress()).isNull();
        assertThat(personInfoDTO.getLastname()).isNull();
        assertThat(personInfoDTO.getMail()).isNull();
        assertThat(personInfoDTO.getAllergiesList()).isEqualTo(medicalRecord.getAllergies());
        assertThat(personInfoDTO.getMedicationList()).isEqualTo(medicalRecord.getMedications());
        assertThat(personInfoDTO.getAge()).isEqualTo(Period.between(medicalRecord.getBirthdate(), nowLocalDateMock).getYears());

    }

    @Test
    public void personInfoDTO_NullMedicalRecord() {
        Person person = new Person();
        person.setFirstName("personFirstName");
        person.setLastName("personLastName");
        person.setAddress("personAddress");
        person.setPhone("personPhone");
        person.setZip(12345);
        person.setEmail("personMail");
        person.setCity("personCity");

        MedicalRecords medicalRecord = null;

        LocalDate nowLocalDateMock = LocalDate.of(2010, 12, 31);
        when(dateUtilsSpy.getNowLocalDate()).thenReturn(nowLocalDateMock);

        PersonDTO personInfoDTO = personInfoDTOMapper.personToPersonInfoDTO(person, medicalRecord);

        assertThat(personInfoDTO.getAddress()).isEqualTo(person.getAddress());
        assertThat(personInfoDTO.getLastname()).isEqualTo(person.getLastName());
        assertThat(personInfoDTO.getMail()).isEqualTo(person.getEmail());
        assertThat(personInfoDTO.getAllergiesList()).isEmpty();
        assertThat(personInfoDTO.getMedicationList()).isEmpty();
        assertThat(personInfoDTO.getAge()).isEqualTo(Integer.MIN_VALUE);

    }

    @Test
    public void personInfoDTO_NullValues() {
        assertThat(personInfoDTOMapper.personToPersonInfoDTO(null, null)).isNull();
    }

}
