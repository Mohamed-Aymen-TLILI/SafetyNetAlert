package com.safetynet.project.service;

import com.safetynet.project.dto.ChildAlertDTO;
import com.safetynet.project.dto.FamilyMemberDTO;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.MedicalRecordRepository;
import com.safetynet.project.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
public class ChildAlertServiceTests {

    @MockBean
    private PersonRepository personRepositoryMock;

    @MockBean
    private MedicalRecordRepository medicalRecordRepositoryMock;

    @SpyBean
    private DateUtils dateUtilsSpy;

    @Autowired
    private ChildAlertService childAlertService;

    @BeforeAll
    private static void setUpAllTest() {

    }

    @BeforeEach
    private void setUpEachTest() {

    }


    @Test
    public void getPersonsInfoMappingWithValidList() {

        LocalDate nowLocalDateMock = LocalDate.of(2010, 1, 1);
        when(dateUtilsSpy.getNowLocalDate()).thenReturn(nowLocalDateMock);

        List<Person> personList = new ArrayList<>();

        Person child1 = new Person();
        child1.setZip(12345);
        child1.setEmail("myEmail");
        child1.setPhone("myPhone");
        child1.setCity("myCity");
        child1.setAddress("myAddress");
        child1.setFirstName("firstName");
        child1.setLastName("lastName");
        personList.add(child1);

        MedicalRecords child1medRec = new MedicalRecords();
        child1medRec.setLastName(child1.getLastName());
        child1medRec.setFirstName(child1.getFirstName());
        child1medRec.setBirthdate(nowLocalDateMock.minusYears(5));

        when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase(child1.getFirstName(), child1.getLastName())).thenReturn(Optional.of(child1medRec));


        Person mother = new Person();
        mother.setFirstName("motherFirstName");
        mother.setLastName("motherLastName");
        mother.setAddress(child1.getAddress());
        personList.add(mother);

        MedicalRecords motherMedRec = new MedicalRecords();
        motherMedRec.setLastName(mother.getLastName());
        motherMedRec.setFirstName(mother.getFirstName());
        motherMedRec.setBirthdate(nowLocalDateMock.minusYears(35));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase(mother.getFirstName(), mother.getLastName())).thenReturn(Optional.of(motherMedRec));

        Person father = new Person();
        father.setFirstName("fatherFirstName");
        father.setLastName("fatherLastName");
        father.setAddress(child1.getAddress());
        personList.add(father);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase(father.getFirstName(), father.getLastName())).thenReturn(Optional.empty());


        Person sister = new Person();
        sister.setLastName("lastnameSister");
        sister.setFirstName("firstnameSister");
        sister.setAddress(child1.getAddress());
        personList.add(sister);

        MedicalRecords sisterMedRec = new MedicalRecords();
        sisterMedRec.setLastName(sister.getLastName());
        sisterMedRec.setFirstName(sister.getFirstName());
        sisterMedRec.setBirthdate(nowLocalDateMock.minusYears(18));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase(sister.getFirstName(), sister.getLastName())).thenReturn(Optional.of(sisterMedRec));

        Person child2 = new Person();
        child2.setAddress("addressChild2");
        child2.setFirstName("firstNameChild2");
        child2.setLastName("lastNameChild2");
        personList.add(child2);

        MedicalRecords child2MedRec = new MedicalRecords();
        child2MedRec.setLastName(child2.getLastName());
        child2MedRec.setFirstName(child2.getFirstName());
        child2MedRec.setBirthdate(nowLocalDateMock.minusYears(10));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase(child2.getFirstName(), child2.getLastName())).thenReturn(Optional.of(child2MedRec));

        when(personRepositoryMock.findAllByAddressIgnoreCase(child1.getAddress())).thenReturn(personList);


        List<ChildAlertDTO> childAlertDTOList = childAlertService.getChildAlertDTOListFromAddress(child1.getAddress());

        assertThat(childAlertDTOList.size()).isEqualTo(3);

        ChildAlertDTO child1DTO = childAlertDTOList.get(0);
        List<FamilyMemberDTO> familyMembers = child1DTO.getFamilyMembers();

        assertThat(familyMembers.size()).isEqualTo(3);
        assertThat(child1DTO.getAge()).isEqualTo(5);

        ChildAlertDTO sisterDTO = childAlertDTOList.get(1);
        assertThat(sisterDTO.getAge()).isEqualTo(18);
        assertThat(child1DTO.getFamilyMembers().size()).isEqualTo(sisterDTO.getFamilyMembers().size()).isEqualTo(3);

        ChildAlertDTO child2DTO = childAlertDTOList.get(2);
        assertThat(child2DTO.getFamilyMembers()).isEmpty();



    }
}
