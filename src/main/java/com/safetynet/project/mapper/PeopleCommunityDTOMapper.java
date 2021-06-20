package com.safetynet.project.mapper;

import com.safetynet.project.dto.PeopleCommunity;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.service.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public abstract class PeopleCommunityDTOMapper {

    @Autowired
    DateUtils dateUtils;

    @Mappings({
            @Mapping(target="lastName", source="person.lastName"),
            @Mapping(target="firstName", source="person.firstName"),
            @Mapping(target = "address", source = "person.address"),
            @Mapping(target = "phone", source = "person.phone"),
            @Mapping(target="age", source="medicalRecord.birthdate", qualifiedByName="calculateAge"),
    })
    public abstract PeopleCommunity personToPeopleCommunityDTO(Person person, MedicalRecords medicalRecord);

    @Named("calculateAge")
    public int getAge(LocalDate birthDate) {
        return dateUtils.getAge(birthDate);
    }
}
