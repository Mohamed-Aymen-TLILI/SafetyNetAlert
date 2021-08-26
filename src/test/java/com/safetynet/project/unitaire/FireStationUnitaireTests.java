package com.safetynet.project.unitaire;

import com.safetynet.project.controller.FireStationController;
import com.safetynet.project.controller.StationController;
import com.safetynet.project.model.FireStation;
import com.safetynet.project.model.Person;
import com.safetynet.project.repository.FireStationRepository;
import com.safetynet.project.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
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
        fireStationService.addFireStation(fireStation);
        List<FireStation> actual = Arrays.asList(fireStation);
        when(this.fireStationRepository.findAll()).thenReturn(actual);
        Iterable<FireStation> allStations = this.fireStationController.getAllStations();
        List<FireStation> result = new ArrayList<FireStation>();
        allStations.iterator().forEachRemaining(result::add);
        assertThat(result, hasSize(1));
    }

    @Test
    void deleteFireStation() throws Exception {
        FireStation fireStation = FireStation.builder().station(1).address(" 18 Avenue du Maréchal Foch").build();
        this.fireStationRepository.deleteByAddressIgnoreCase(" 18 Avenue du Maréchal Foch");
        Iterable<FireStation> allStations = this.fireStationController.getAllStations();
        List<FireStation> result = new ArrayList<FireStation>();
        allStations.iterator().forEachRemaining(result::add);
        assertThat(result, hasSize(0));
    }
}
