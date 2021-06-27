package com.safetynet.project.repository;

import com.safetynet.project.model.FireStation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface FireStationRepository extends CrudRepository<FireStation, Long> {
    List<FireStation> findDistinctByStation(Integer station);
    Optional<FireStation> findFirstByAddressIgnoreCaseAndStation(String address, Integer station);
    List<FireStation> findDistinctByAddressIgnoreCase(String address);
    List<FireStation> findDistinctByStationIn(List<Integer> station);
    FireStation findByAddressIgnoreCase(String address);
    void deleteByAddressIgnoreCase(String address);
    void deleteByAddressIgnoreCaseAndStation(String adress, Integer station);
}
