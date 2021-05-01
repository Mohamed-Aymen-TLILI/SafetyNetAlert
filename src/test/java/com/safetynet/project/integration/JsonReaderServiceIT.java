package com.safetynet.project.integration;

import com.safetynet.project.service.FireStationService;
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

    @Test
    public void CheckLoadedDataFromJsonFile()
    {
        assertThat(personService.getAllPersons()).size().isEqualTo(0);
    }


}
