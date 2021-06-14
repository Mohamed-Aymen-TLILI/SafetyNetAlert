package com.safetynet.project.mapper;

import com.safetynet.project.dto.ChildAlertDTO;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.service.AgeCalculator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Mapper(componentModel = "spring", uses = FamilyMemberDTOMapper.class)
public abstract class ChildAlertDTOMapper {

    @Mappings({
            @Mapping(target = "lastName", source = "person.lastName"),
            @Mapping(target = "firstName", source = "person.firstName"),
            @Mapping(target = "age", source = "medicalRecords.birthDate", qualifiedByName = "calculateAge"),
    })
    public abstract ChildAlertDTO convertToChildAlertDTO(Person person, MedicalRecords medicalRecord);

    @Autowired
    AgeCalculator ageCalculator;

    @Named("calculateAge")
    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return ageCalculator.calculateAge(birthDate, currentDate);
    }

}
