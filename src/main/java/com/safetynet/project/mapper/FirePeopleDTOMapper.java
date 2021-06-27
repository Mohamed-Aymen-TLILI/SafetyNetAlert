package com.safetynet.project.mapper;

import com.safetynet.project.dto.FirePeopleDTO;
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
public abstract class FirePeopleDTOMapper {

    @Autowired
    DateUtils dateUtils;

    @Mappings({
            @Mapping(target="lastname", source="person.lastName"),
            @Mapping(target="phone", source="person.phone"),
            @Mapping(target="medicationList", source="medicalRecord.medications"),
            @Mapping(target="allergiesList", source="medicalRecord.allergies"),
            @Mapping(target="age", source="medicalRecord.birthdate", qualifiedByName="calculateAge"),
    })
    public abstract FirePeopleDTO convertToFirePeopleDTO(Person person, MedicalRecords medicalRecord);


    @Named("calculateAge")
    public int getAge(LocalDate birthDate) {
        return dateUtils.getAge(birthDate);
    }
}
