package com.safetynet.project.model;

import com.safetynet.project.dto.ChildAlertDTO;
import com.safetynet.project.mapper.ChildAlertDTOMapper;
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
public class ChildAlertDTOMapperTests {

    @SpyBean
    DateUtils dateUtilsSpy;

    @Autowired
    private ChildAlertDTOMapper childAlertDTOMapper;

    @Test
    public void convertToChildAlertDTO_MapsCorrect() {
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

        ChildAlertDTO childAlertDTO = childAlertDTOMapper.convertToChildAlertDTO(person, medicalRecord);

        assertThat(childAlertDTO.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(childAlertDTO.getLastName()).isEqualTo(person.getLastName());
        assertThat(childAlertDTO.getAge()).isEqualTo(Period.between(medicalRecord.getBirthdate(), nowLocalDateMock).getYears());
    }

    @Test
    public void convertToChildAlertDTO_nullPerson() {

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

        ChildAlertDTO childAlertDTO = childAlertDTOMapper.convertToChildAlertDTO(null, medicalRecord);

        assertThat(childAlertDTO.getFirstName()).isNull();
        assertThat(childAlertDTO.getLastName()).isNull();
        assertThat(childAlertDTO.getAge()).isEqualTo(Period.between(medicalRecord.getBirthdate(), nowLocalDateMock).getYears());

    }

    @Test
    public void convertToChildAlertDTO_nullMedicalRecords() {
        Person person = new Person();
        person.setFirstName("personFirstName");
        person.setLastName("personLastName");
        person.setAddress("personAddress");
        person.setPhone("personPhone");
        person.setZip(12345);
        person.setEmail("personMail");
        person.setCity("personCity");

        LocalDate nowLocalDateMock = LocalDate.of(2010, 12, 31);
        when(dateUtilsSpy.getNowLocalDate()).thenReturn(nowLocalDateMock);

        ChildAlertDTO childAlertDTO = childAlertDTOMapper.convertToChildAlertDTO(person, null);

        assertThat(childAlertDTO.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(childAlertDTO.getLastName()).isEqualTo(person.getLastName());

    }

    @Test
    public void convertToChildAlertDTO_nullValues() {

        assertThat(childAlertDTOMapper.convertToChildAlertDTO(null,null)).isNull();
    }
}
