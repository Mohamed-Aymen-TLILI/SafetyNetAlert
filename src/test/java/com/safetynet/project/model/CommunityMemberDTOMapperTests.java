package com.safetynet.project.model;

import com.safetynet.project.dto.PeopleCommunity;
import com.safetynet.project.mapper.PeopleCommunityDTOMapper;
import com.safetynet.project.service.DateUtils;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class CommunityMemberDTOMapperTests {

    @SpyBean
    private DateUtils dateUtilsSpy;

    @Autowired
    private PeopleCommunityDTOMapper mapper;

    @Test
    public void communityMemberDTO_MapsCorrect() {
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
        medicalRecord.setBirthdate(LocalDate.of(2000,01,01));

        LocalDate nowLocalDateMock = LocalDate.of(2010,12,31);
        when(dateUtilsSpy.getNowLocalDate()).thenReturn(nowLocalDateMock);

        PeopleCommunity communityMemberDTO = mapper.personToPeopleCommunityDTO(person,medicalRecord);

        assertThat(communityMemberDTO.getAddress()).isEqualTo(person.getAddress());
        assertThat(communityMemberDTO.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(communityMemberDTO.getLastName()).isEqualTo(person.getLastName());
        assertThat(communityMemberDTO.getPhone()).isEqualTo(person.getPhone());
        assertThat(communityMemberDTO.getAge()).isEqualTo(Period.between(medicalRecord.getBirthdate(),nowLocalDateMock).getYears());


    }

    @Test
    public void communityMemberDTO_nullPerson() {
        Person person = null;

        MedicalRecords medicalRecord = new MedicalRecords();
        medicalRecord.setLastName("medicalRecordLastName");
        medicalRecord.setFirstName("medicalRecordFirstNAme");
        medicalRecord.setBirthdate(LocalDate.of(200,01,01));

        LocalDate nowLocalDateMock = LocalDate.of(2020,12,31);
        when(dateUtilsSpy.getNowLocalDate()).thenReturn(nowLocalDateMock);

        PeopleCommunity communityMemberDTO = mapper.personToPeopleCommunityDTO(person,medicalRecord);

        assertThat(communityMemberDTO.getAddress()).isNull();
        assertThat(communityMemberDTO.getFirstName()).isNull();
        assertThat(communityMemberDTO.getLastName()).isNull();
        assertThat(communityMemberDTO.getPhone()).isNull();
        assertThat(communityMemberDTO.getAge()).isEqualTo(Period.between(medicalRecord.getBirthdate(),nowLocalDateMock).getYears());


    }

    @Test
    public void communityMemberDTO_nullMedicalRecord() {
        Person person = new Person();
        person.setFirstName("personFirstName");
        person.setLastName("personLastName");
        person.setAddress("personAddress");
        person.setPhone("personPhone");
        person.setZip(12345);
        person.setEmail("personMail");
        person.setCity("personCity");

        MedicalRecords medicalRecord = null;

        LocalDate nowLocalDateMock = LocalDate.of(2020,12,31);
        when(dateUtilsSpy.getNowLocalDate()).thenReturn(nowLocalDateMock);

        PeopleCommunity communityMemberDTO = mapper.personToPeopleCommunityDTO(person,medicalRecord);

        assertThat(communityMemberDTO.getAddress()).isEqualTo(person.getAddress());
        assertThat(communityMemberDTO.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(communityMemberDTO.getLastName()).isEqualTo(person.getLastName());
        assertThat(communityMemberDTO.getPhone()).isEqualTo(person.getPhone());
        assertThat(communityMemberDTO.getAge()).isEqualTo(Integer.MIN_VALUE);

    }

    @Test
    public void communityMemberDTO_NullValues()
    {
        assertThat(mapper.personToPeopleCommunityDTO(null,null)).isNull();
    }
}
