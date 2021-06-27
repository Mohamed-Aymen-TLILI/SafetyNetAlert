package com.safetynet.project.service;

import com.safetynet.project.dto.FirePeopleDTO;
import com.safetynet.project.dto.FireStationPeopleDTO;
import com.safetynet.project.dto.PeopleCommunity;
import com.safetynet.project.mapper.FirePeopleDTOMapper;
import com.safetynet.project.mapper.PeopleCommunityDTOMapper;
import com.safetynet.project.model.FireStation;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.FireStationRepository;
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
public class FireStationPeopleService {

    private static final Logger logger = LogManager.getLogger(FireStationPeopleService.class);

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PeopleCommunityDTOMapper peopleCommunityDTOMapper;

    @Autowired
    private FirePeopleDTOMapper firePeopleDTOMapper;

    /**
     * return list of people and detail for every person live around a station
     *
     * @param stationNumber
     * @return list of FireStationPeople and number of adult and Child
     */
    public FireStationPeopleDTO getFireStationPeople(Integer stationNumber) {

        List<Person> personList = getPersonListByStationNumber(stationNumber);

        if (personList != null) {
            List<PeopleCommunity> peopleCommunityDTOList = new ArrayList<>();

            personList.forEach(p -> {
                Optional<MedicalRecords> optionalMedicalRecord = medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(p.getFirstName(), p.getLastName());
                peopleCommunityDTOList.add(peopleCommunityDTOMapper.personToPeopleCommunityDTO(p, optionalMedicalRecord.orElse(null)));
            });

            FireStationPeopleDTO fireStationPeopleDTO = new FireStationPeopleDTO();
            fireStationPeopleDTO.setFireStationPeopleDTOList(peopleCommunityDTOList);

            return fireStationPeopleDTO;
        } else {
            return null;
        }
    }


    /**
     * @param stationNumber
     * @return list of phone number for every peron live around fireStation
     */
    public List<String> getPhoneListByStationNumber(Integer stationNumber) {

        List<Person> personList = getPersonListByStationNumber(stationNumber);

        if (personList != null) {
            return personList.stream().filter(p-> p.getPhone() != null && !p.getPhone().isEmpty()).
                    map(p -> p.getPhone()).distinct().collect(Collectors.toList());
        } else {
            return null;
        }
    }


    /**
     * return list of person by address and number of FireStation
     * @param address
     * @return list of person by address and number station of FireStation
     */
    public List<FirePeopleDTO> getFireInfoByAddress(String address) {

        if (address != null) {
            try {

                List<FireStation> fireStation = fireStationRepository.findDistinctByAddressIgnoreCase(address);
                List<Integer> fireStationNumberList = getStationNumberList(fireStation);
                List<Person> personList = personRepository.findAllByAddressIgnoreCase(address);

                List<FirePeopleDTO> FirePeopleDTOList = new ArrayList<>();

                personList.forEach(p -> {
                    FirePeopleDTO firePeopleDTO = null;
                    Optional<MedicalRecords> medicalRecord = medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(p.getFirstName(), p.getLastName());
                    firePeopleDTO = firePeopleDTOMapper.convertToFirePeopleDTO(p, medicalRecord.orElse(null));
                    firePeopleDTO.setStationNumberList(fireStationNumberList);
                    FirePeopleDTOList.add(firePeopleDTO);
                });

                return FirePeopleDTOList;

            } catch (Exception exception) {
                logger.error("error to get list of person and station fire around this address: " + exception.getMessage() + " Stack Trace : " + exception.getStackTrace());
                return null;
            }

        }
        return null;
    }

    /**
     * List of FireStationNumber
     *
     * @param
     * @return List of FireStationNumber
     */
    private List<Integer> getStationNumberList(List<FireStation> fireStationList) {
        if (fireStationList != null) {
            return fireStationList.stream().filter(fireStation -> fireStation.getAddress() != null && !fireStation.getAddress().isEmpty())
                    .map(fireStation -> fireStation.getStation()).collect(Collectors.toList());
        } else {
            return null;
        }
    }


    /**
     * list of person live around a station
     *
     * @param stationNumber
     * @return list of person around a station
     */
    private List<Person> getPersonListByStationNumber(Integer stationNumber) {
        if (stationNumber != null) {
            try {
                List<FireStation> fireStationList = fireStationRepository.findDistinctByStation(stationNumber);

                List<String> addressList = getAddressListFromFireStationList(fireStationList);

                return personRepository.findAllByAddressInOrderByAddress(addressList);

            } catch (Exception exception) {
                logger.error("error when get person around FaireStation: " + exception.getMessage() + " Stack Trace : " + exception.getStackTrace());
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     *
     * @param fireStationList
     * @return list of address'fireStation
     */
    private List<String> getAddressListFromFireStationList(List<FireStation> fireStationList) {
        List<String> addressList = new ArrayList<>();
        if (fireStationList != null) {
            fireStationList.forEach(fireStationIterator -> {
                if (fireStationIterator.getAddress() != null && !fireStationIterator.getAddress().isEmpty()) {
                    addressList.add(fireStationIterator.getAddress());
                }
            });
        }
        return addressList.stream().distinct().collect(Collectors.toList());
    }

}
