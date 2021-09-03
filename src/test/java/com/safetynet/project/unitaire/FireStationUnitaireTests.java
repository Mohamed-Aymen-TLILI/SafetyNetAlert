package com.safetynet.project.unitaire;

import com.safetynet.project.controller.StationController;
import com.safetynet.project.model.FireStation;
import com.safetynet.project.repository.FireStationRepository;
import com.safetynet.project.service.FireStationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class FireStationUnitaireTests {

    private StationController fireStationController;

    private FireStationService fireStationService;

    @Mock
    private FireStationRepository fireStationRepository;

    @BeforeEach
    private void  setUpEachTest() {
        MockitoAnnotations.initMocks(this);
        this.fireStationService = new FireStationService(fireStationRepository);
        this.fireStationController = new StationController(fireStationService);
    }

    @Test
    void getAllFireStation() throws Exception {
        FireStation fireStation = FireStation.builder().station(1).address("test").build();
        List<FireStation> fire = Arrays.asList(fireStation);
        when(fireStationRepository.findAll()).thenReturn(fire);
        Iterable<FireStation> allStations = this.fireStationController.getAllStations();
        List<FireStation> result =  new ArrayList<FireStation>();
        allStations.iterator().forEachRemaining(result::add);
        assertThat(result, hasSize(1));
    }

    @Test
    void addFireStation() throws Exception {
        FireStation fireStation = new FireStation();
        fireStation.setAddress(" 18 Avenue du Maréchal Foch");
        fireStation.setStation(1);
        when(this.fireStationController.addFireStation(fireStation)).thenReturn(fireStation);
        FireStation response = this.fireStationController.addFireStation(fireStation);
        Assertions.assertThat(fireStation.getAddress()).isEqualTo(" 18 Avenue du Maréchal Foch");
    }

    @Test
    void deleteFireStation() throws Exception {
        String address = "18 Avenue du Maréchal Foch";
        FireStation fireStation = FireStation.builder().station(1).address(address).build();
        List<FireStation> actual = Arrays.asList(fireStation);
        when(fireStationRepository.findDistinctByAddressIgnoreCase(address)).thenReturn(actual);
        this.fireStationController.deleteFireStation(address);
        verify(fireStationRepository).deleteByAddressIgnoreCase(address);
    }

    @Test
    void deleteFireStationWithStationNumberAndAddress() throws Exception {
        String address = "18 Avenue du Maréchal Foch";
        int station = 1;
        FireStation fireStation = FireStation.builder().station(1).address(address).build();
        when(fireStationRepository.findFirstByAddressIgnoreCaseAndStation(address, station)).thenReturn(Optional.of(fireStation));
        this.fireStationController.deleteFireStation(address, station);
        verify(fireStationRepository).deleteByAddressIgnoreCaseAndStation(address, station);
    }

    @Test
    void updateFireStation() throws Exception {
        String address = "18 Avenue du Maréchal Foch";
        FireStation fireStation = FireStation.builder().station(1).address(address).build();
        List<FireStation> list = new ArrayList<>();
        list.add(fireStation);
        when(fireStationRepository.findDistinctByAddressIgnoreCase(address)).thenReturn(list);
        this.fireStationController.updateFireStation(fireStation);
        verify(fireStationRepository).save(fireStation);
    }
}
