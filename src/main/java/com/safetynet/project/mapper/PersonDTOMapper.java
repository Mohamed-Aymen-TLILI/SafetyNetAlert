package com.safetynet.project.mapper;

import com.safetynet.project.dto.PersonDTO;
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
public abstract class PersonDTOMapper {

    @Autowired
    DateUtils dateUtils;

    @Mappings({
            @Mapping(target="lastname", source="person.lastName"),
            @Mapping(target="address", source="person.address"),
            @Mapping(target="mail", source="person.email"),
            @Mapping(target="medicationList", source="medicalRecord.medications"),
            @Mapping(target="allergiesList", source="medicalRecord.allergies"),
            @Mapping(target="age", source="medicalRecord.birthdate", qualifiedByName="calculateAge"),
    })
    public abstract PersonDTO personToPersonInfoDTO(Person person, MedicalRecords medicalRecord);


    @Named("calculateAge")
    public int getAge(LocalDate birthDate) {
        return dateUtils.getAge(birthDate);
    }
}
