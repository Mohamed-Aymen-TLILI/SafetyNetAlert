package com.safetynet.project.integration;

import com.safetynet.project.model.FireStation;
import com.safetynet.project.repository.FireStationRepository;
import com.safetynet.project.service.FireStationService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

  /*  @Test
    public void deleteFireStationByAddressAndStationIT() {
        assertThat(fireStationService.deleteFireStationByAddressAndStation("951 LoneTree RD", 2));
    }

    @Test
    public void deleteFireStationByAddressAndStationNonExistingIT() {
        assertThat(fireStationService.deleteFireStationByAddressAndStation("748 Townings DR", 5));
    }

    @Test
    public void deleteFireStationByAddressIT() {
        assertThat(fireStationService.deleteFireStationByAddress("748 Townings DR"))
    }

    @Test
    public void deleteFireStationByAddressNonExistingIT() {
        verify(fireStationService.deleteFireStationByAddress("Paris city road")).;
    }
*/
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
