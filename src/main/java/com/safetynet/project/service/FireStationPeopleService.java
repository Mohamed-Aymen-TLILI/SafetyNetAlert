package com.safetynet.project.service;

import com.safetynet.project.dto.*;
import com.safetynet.project.mapper.FirePeopleDTOMapper;
import com.safetynet.project.mapper.FloodDTOMapper;
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

import java.util.*;
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

    @Autowired
    private FloodDTOMapper floodDTOMapper;

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
     * list of house cover by station
     *
     * @param stations numberList
     * @return list of house cover by station
     */
    public List<FloodListDTO> getFloodInfoByStations(List<Integer> stations) {
        if (stations != null) {
            try {
                Map<String, List<Person>> mapListPersonByStationAddress = getPersonsByStationNumber(stations);

                List<FloodListDTO> floodInfoDTOList = new ArrayList<>();

                for (String fireStationAddress : mapListPersonByStationAddress.keySet()) {

                    FloodListDTO stationFloodInfoDTO = new FloodListDTO();
                    stationFloodInfoDTO.setAddress(fireStationAddress);

                    List<Person> personLinkedToFireStation = mapListPersonByStationAddress.get(fireStationAddress);

                    if (personLinkedToFireStation != null) {
                        List<FloodDTO> floodInfo = new ArrayList<>();

                        personLinkedToFireStation.forEach(p -> {
                            Optional<MedicalRecords> optionalMedicalRecord = medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(p.getFirstName(), p.getLastName());
                            floodInfo.add(floodDTOMapper.convertToFloodDTO(p, optionalMedicalRecord.orElse(null)));
                        });
                        stationFloodInfoDTO.setFloodDTOList(floodInfo);
                        floodInfoDTOList.add(stationFloodInfoDTO);
                    }
                }
                return floodInfoDTOList;
            } catch (Exception exception) {
                logger.error("error to get list of person's information for water problem : " + exception.getMessage() + " Stack Trace : " + exception.getStackTrace());
                return null;
            }
        }
        return null;
    }


    /**
     * list of person cover by station
     * @param stationsNumberList
     * @return list of person cover by station
     */
    private Map<String, List<Person>> getPersonsByStationNumber(List<Integer> stationsNumberList) {

        try {
            List<FireStation> fireStationList = fireStationRepository.findDistinctByStationIn(stationsNumberList.stream().distinct().collect(Collectors.toList()));

            List<String> addressList = getAddressListFromFireStationList(fireStationList);

            List<Person> personList = personRepository.findAllByAddressInOrderByAddress(addressList);

            Map<String, List<Person>> mapPersonByStationAddress = new HashMap<>();

            addressList.forEach(addressIterator ->
                    {
                        List<Person> personListByAdress = personList.stream().filter(person -> person.getAddress().equalsIgnoreCase(addressIterator)).collect(Collectors.toList());
                        mapPersonByStationAddress.put(addressIterator, personListByAdress);
                    }
            );
            return mapPersonByStationAddress;
        } catch (Exception exception) {
            logger.error("error to get list of person cover by FireStation : " + exception.getMessage() + " Stack Trace : " + exception.getStackTrace());
            return null;
        }
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
