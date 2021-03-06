package com.safetynet.project.integration;

import com.safetynet.project.model.FireStation;
import com.safetynet.project.repository.FireStationRepository;
import com.safetynet.project.service.FireStationService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("IntegrationTests")
@SpringBootTest(properties = {
        "application.runner.enabled=true" })
public class FireStationCRUD_IT {

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Test
    public void addFireStationIT() {
        FireStation fireStation = new FireStation();
        fireStation.setStation(40);
        fireStation.setAddress("Adresse de la nouvelle caserne de pompiers n°40");

        fireStationService.addFireStation(fireStation);

        Optional<FireStation> foundFireStation = fireStationRepository.findFirstByAddressIgnoreCaseAndStation(fireStation.getAddress(), fireStation.getStation());

        assertThat(foundFireStation.get().getStation()).isEqualTo(fireStation.getStation());
        assertThat(foundFireStation.get().getAddress()).isEqualTo(fireStation.getAddress());

    }

    @Test
    public void updateFireStationIT()
    {
        FireStation fireStation = new FireStation();
        fireStation.setAddress("29 15th st");
        fireStation.setStation(5);

        fireStationService.updateFireStation(fireStation);

        FireStation foundFireStation = fireStationRepository.findFirstByAddressIgnoreCaseAndStation(fireStation.getAddress(),fireStation.getStation()).get();

        assertThat(foundFireStation.getStation()).isEqualTo(fireStation.getStation());
        assertThat(foundFireStation.getAddress()).isEqualToIgnoringCase(fireStation.getAddress());
    }
}
