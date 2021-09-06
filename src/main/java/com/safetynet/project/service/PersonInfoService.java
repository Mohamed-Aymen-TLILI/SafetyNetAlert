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
import java.util.stream.Collectors;

@Service
public class PersonInfoService {

    private static final Logger logger = LogManager.getLogger(PersonInfoService.class);


    private MedicalRecordRepository medicalRecordRepository;

    private PersonRepository personRepository;

    private PersonDTOMapper personDTOMapper;

    public PersonInfoService ( MedicalRecordRepository medicalRecordRepository, PersonRepository personRepository, PersonDTOMapper personDTOMapper){
        this.medicalRecordRepository = medicalRecordRepository;
        this.personDTOMapper = personDTOMapper;
        this.personRepository = personRepository;
    }

    /**
     * list of person's detail
     *
     * @param lastName
     * @return list of person's detail
     */
    public List<PersonDTO> getPersonsInfo( String lastName) {
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
     * list of person's mail
     *
     * @return list of mail
     */
    public List<String> getAllEmailsForCity(String city) {
        if (city != null) {
            try {
                List<Person> personList = personRepository.findAllByCityIgnoreCase(city);
                if (personList != null) {
                    return personList.stream().filter(p -> p.getEmail() != null && !p.getEmail().isEmpty()).
                            map(personIterator -> personIterator.getEmail()).distinct().collect(Collectors.toList());
                } else {
                    logger.info("return null list");
                    return null;
                }
            } catch (Exception exception) {
                logger.error("error to get list of person: " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
                return null;
            }
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
