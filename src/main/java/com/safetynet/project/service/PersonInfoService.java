package com.safetynet.project.service;

import com.safetynet.project.dto.PersonDTO;
import com.safetynet.project.mapper.PersonDTOMapper;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.MedicalRecordRepository;
import com.safetynet.project.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonInfoService {

    private static final Logger logger = LogManager.getLogger(PersonInfoService.class);

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonDTOMapper personDTOMapper;

    /**
     * list of person's detail
     *
     * @param firstName
     * @param lastName
     * @return list of person's detail
     */
    public List<PersonDTO> getPersonsInfo(String firstName, String lastName) {
        if (lastName != null) {
            List<Person> personList = personRepository.findAllByLastNameAllIgnoreCase(lastName);
            List<MedicalRecords> medicalRecordList = medicalRecordRepository.findAllByLastNameAllIgnoreCase(lastName);

            List<PersonDTO> personInfoDTOList = new ArrayList<>();

            personList.forEach(p -> {
                Optional<MedicalRecords> medicalRecordForPerson = findMedicalRecord(medicalRecordList, p);
                personInfoDTOList.add(personDTOMapper.personToPersonInfoDTO(p, medicalRecordForPerson.orElse(null)));
            });

            return personInfoDTOList;
        } else {
            return null;
        }
    }


    /**
     * search a medical file for person in list
     *
     * @param medicalRecordList
     * @param person
     * @return Optional medical file
     */
    private Optional<MedicalRecords> findMedicalRecord(List<MedicalRecords> medicalRecordList, Person person) {

        return medicalRecordList.stream().filter(m ->
                m.getFirstName().equalsIgnoreCase(person.getFirstName())
                        && m.getLastName().equalsIgnoreCase(person.getLastName())
        ).findFirst();
    }

}
