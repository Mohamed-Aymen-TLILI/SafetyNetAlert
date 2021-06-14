package com.safetynet.project.mapper;

import com.safetynet.project.dto.FamilyMemberDTO;
import com.safetynet.project.model.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FamilyMemberDTOMapper {

    FamilyMemberDTO personToFamilyMemberDTO(Person person);

    List<FamilyMemberDTO> personListToFamilyMemberDTOList(List<Person> personList);
}
