package com.safetynet.project.service;

import com.safetynet.project.dto.ChildAlertDTO;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.mapper.ChildAlertDTOMapper;
import com.safetynet.project.mapper.FamilyMemberDTOMapper;
import com.safetynet.project.repository.MedicalRecordRepository;
import com.safetynet.project.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChildAlertService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    ChildAlertDTOMapper childAlertDTOMapper;

    @Autowired
    FamilyMemberDTOMapper familyMemberDTOMapper;

    /**
     *
     * @param address
     * @return list child or null if address dosen't exist
     */
    public List<ChildAlertDTO> getChildAlertDTOListFromAddress(String address) {
        if (address != null) {

            List<Person> personList = personRepository.findAllByAddressIgnoreCase(address);

            List<ChildAlertDTO> childAlertDTOList = new ArrayList<>();

            personList.forEach(personIterator -> {

                Optional<MedicalRecords> medicalRecordLinkedToPersonIterator = medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(personIterator.getFirstName(), personIterator.getLastName());

                if (medicalRecordLinkedToPersonIterator.isPresent()) {

                    ChildAlertDTO childAlertDTO = childAlertDTOMapper.convertToChildAlertDTO(personIterator, medicalRecordLinkedToPersonIterator.get());

                    List<Person> personFamilyMembers = findFamilyMembers(personList,personIterator);

                    childAlertDTO.setFamilyMembers(familyMemberDTOMapper.personListToFamilyMemberDTOList(personFamilyMembers));

                    childAlertDTOList.add(childAlertDTO);
                }
            });

            return childAlertDTOList.stream().filter(childAlertDTO -> childAlertDTO.getAge() <= 18).collect(Collectors.toList());

        }
        return null;

    }

    /**
     * @param personList
     * @param person
     * @return Family members if exist
     */
    private List<Person> findFamilyMembers(List<Person> personList, Person person)
    {
        return personList.stream().
                filter(personIterator -> personIterator.getAddress().equalsIgnoreCase(person.getAddress()) &&
                        !(personIterator.getFirstName().equalsIgnoreCase(person.getFirstName()) && personIterator.getLastName().equalsIgnoreCase(person.getLastName()))).
                collect(Collectors.toList());
    }

}
