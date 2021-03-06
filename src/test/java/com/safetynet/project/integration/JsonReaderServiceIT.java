package com.safetynet.project.integration;

import com.safetynet.project.service.FireStationService;
import com.safetynet.project.service.MedicalRecordService;
import com.safetynet.project.service.PersonService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@Tag("IntegrationTests")
@SpringBootTest(properties = {
        "application.runner.enabled=true" })
public class JsonReaderServiceIT {


    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Test
    public void CheckLoadedDataPeronFromJsonFile() {
        assertThat(personService.getAllPersons()).size().isEqualTo(23);
    }

    @Test
    public void CheckLoadedDataFireStationFromJsonFile() {
        assertThat(fireStationService.getAllStations()).size().isEqualTo(13);
    }

    @Test
    public void CheckLoadedDataMedicalsFromJsonFile() {
        assertThat(medicalRecordService.getAllMedicalRecords()).size().isEqualTo(23);
    }

}
