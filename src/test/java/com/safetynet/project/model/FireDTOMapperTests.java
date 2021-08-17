package com.safetynet.project.model;

import com.safetynet.project.dto.FirePeopleDTO;
import com.safetynet.project.mapper.FirePeopleDTOMapper;
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
public class FireDTOMapperTests {

    @SpyBean
    private DateUtils dateUtilsSpy;

    @Autowired
    private FirePeopleDTOMapper fireDTOMapper;

    @Test
    public void fireDTO_MapsCorrect() {
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

        FirePeopleDTO fireDTO = fireDTOMapper.convertToFirePeopleDTO(person, medicalRecord);

        assertThat(fireDTO.getLastname()).isEqualTo(person.getLastName());
        assertThat(fireDTO.getPhone()).isEqualTo(person.getPhone());
        assertThat(fireDTO.getAllergiesList()).isEqualTo(medicalRecord.getAllergies());
        assertThat(fireDTO.getMedicationList()).isEqualTo(medicalRecord.getMedications());
        assertThat(fireDTO.getAge()).isEqualTo(Period.between(medicalRecord.getBirthdate(), nowLocalDateMock).getYears());
        assertThat(fireDTO.getStationNumberList()).isEmpty();

    }

    @Test
    public void fireDTO_NullPerson() {

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

        FirePeopleDTO fireDTO = fireDTOMapper.convertToFirePeopleDTO(null, medicalRecord);

        assertThat(fireDTO.getLastname()).isNull();
        assertThat(fireDTO.getPhone()).isNull();
        assertThat(fireDTO.getAllergiesList()).isEqualTo(medicalRecord.getAllergies());
        assertThat(fireDTO.getMedicationList()).isEqualTo(medicalRecord.getMedications());
        assertThat(fireDTO.getAge()).isEqualTo(Period.between(medicalRecord.getBirthdate(), nowLocalDateMock).getYears());
        assertThat(fireDTO.getStationNumberList()).isEmpty();
    }

    @Test
    public void fireDTO_NullMedicalRecord() {
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

        FirePeopleDTO fireDTO = fireDTOMapper.convertToFirePeopleDTO(person, medicalRecord);

        assertThat(fireDTO.getLastname()).isEqualTo(person.getLastName());
        assertThat(fireDTO.getPhone()).isEqualTo(person.getPhone());
        assertThat(fireDTO.getAllergiesList()).isEmpty();
        assertThat(fireDTO.getMedicationList()).isEmpty();
        assertThat(fireDTO.getAge()).isEqualTo(Integer.MIN_VALUE);
        assertThat(fireDTO.getStationNumberList()).isEmpty();
    }



    @Test
    public void fireDTO_NullValues() {
        assertThat(fireDTOMapper.convertToFirePeopleDTO(null, null)).isNull();
    }

}
