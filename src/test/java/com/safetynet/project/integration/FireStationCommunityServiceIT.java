package com.safetynet.project.integration;

import com.safetynet.project.dto.*;
import com.safetynet.project.service.DateUtils;
import com.safetynet.project.service.FireStationPeopleService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Tag("IntegrationTests")
@SpringBootTest(properties = {
        "application.runner.enabled=true" })
public class FireStationCommunityServiceIT {

    @Autowired
    private FireStationPeopleService fireStationPeopleService;

    @SpyBean
    private DateUtils dateUtilsMock;

    @Test
    public void getFireStationCommunityIT() {

        LocalDate nowLocalDateMock = LocalDate.of(2021, 8, 12);
        when(dateUtilsMock.getNowLocalDate()).thenReturn(nowLocalDateMock);

        FireStationPeopleDTO fireStationPeopleDTO = fireStationPeopleService.getFireStationPeople(4);

        List<PeopleCommunity> fireStationPeopleDTOList = fireStationPeopleDTO.getFireStationPeopleDTOList();

        assertThat(fireStationPeopleDTOList.size()).isEqualTo(4);
        assertThat(fireStationPeopleDTO.getAdultsNumber()).isEqualTo(4);
        assertThat(fireStationPeopleDTO.getChildrenNumber()).isEqualTo(0);

        PeopleCommunity peopleCommunityLily = new PeopleCommunity();
        peopleCommunityLily.setAddress("489 Manchester St");
        peopleCommunityLily.setFirstName("Lily");
        peopleCommunityLily.setLastName("Cooper");
        peopleCommunityLily.setPhone("841-874-9845");
        peopleCommunityLily.setAge(27);

        assertThat(fireStationPeopleDTOList).contains(peopleCommunityLily);

        PeopleCommunity peopleCommunityTony = new PeopleCommunity();
        peopleCommunityTony.setAddress("112 Steppes Pl");
        peopleCommunityTony.setFirstName("Tony");
        peopleCommunityTony.setLastName("Cooper");
        peopleCommunityTony.setPhone("841-874-6874");
        peopleCommunityTony.setAge(27);
        assertThat(fireStationPeopleDTOList).contains(peopleCommunityTony);

        PeopleCommunity peopleCommunityRon = new PeopleCommunity();
        peopleCommunityRon.setAddress("112 Steppes Pl");
        peopleCommunityRon.setFirstName("Ron");
        peopleCommunityRon.setLastName("Peters");
        peopleCommunityRon.setPhone("841-874-8888");
        peopleCommunityRon.setAge(56);
        assertThat(fireStationPeopleDTOList).contains(peopleCommunityRon);

        PeopleCommunity peopleCommunityAllison = new PeopleCommunity();
        peopleCommunityAllison.setAddress("112 Steppes Pl");
        peopleCommunityAllison.setFirstName("Allison");
        peopleCommunityAllison.setLastName("Boyd");
        peopleCommunityAllison.setPhone("841-874-9888");
        peopleCommunityAllison.setAge(56);
        assertThat(fireStationPeopleDTOList).contains(peopleCommunityAllison);
    }


    @Test
    public void getPhoneListByStationNumberIT() {
        List<String> phoneList = fireStationPeopleService.getPhoneListByStationNumber(1);

        assertThat(phoneList.size()).isEqualTo(4);
        assertThat(phoneList).containsExactlyInAnyOrder("841-874-8547", "841-874-7462", "841-874-6512",
                "841-874-7784");
    }

    @Test
    @Transactional
    public void getFloodInfoByStationsIT() {
        LocalDate nowLocalDateMock = LocalDate.of(2020, 12, 1);
        when(dateUtilsMock.getNowLocalDate()).thenReturn(nowLocalDateMock);

        List<Integer> stationsList = new ArrayList<>();
        //"489 Manchester St","112 Steppes Pl"
        stationsList.add(4);
        //"951 LoneTree Rd","892 Downing Ct","29 15th St"
        stationsList.add(2);

        List<FloodListDTO> stationFloodInfoDTOList = fireStationPeopleService.getFloodInfoByStations(stationsList);
        assertThat(stationFloodInfoDTOList).size().isEqualTo(5);

        //"489 Manchester St"
        FloodListDTO stationFloodInfoDTOManchester = new FloodListDTO();
        stationFloodInfoDTOManchester.setAddress("489 Manchester St");
        List<FloodDTO> floodInfoDTOListManchester = new ArrayList<>();

        floodInfoDTOListManchester.add(initFloodInfoDTO(26, "841-874-9845", "Cooper", new ArrayList<>(), new ArrayList<>()));

        FloodListDTO foundManchester = stationFloodInfoDTOList.stream().filter(stationFloodInfoDTO -> stationFloodInfoDTO.getAddress().equalsIgnoreCase(stationFloodInfoDTOManchester.getAddress())).findFirst().get();
        assertThat(foundManchester.getFloodDTOList()).containsAll(floodInfoDTOListManchester);

        //"112 Steppes Pl"
        FloodListDTO stationFloodInfoDTOSteppesPl = new FloodListDTO();
        stationFloodInfoDTOSteppesPl.setAddress("112 Steppes Pl");
        List<FloodDTO> floodInfoDTOListSteppes = new ArrayList<>();
        //{ "firstName":"Ron", "lastName":"Peters","phone":"841-874-8888",
        //{ "birthdate":"04/06/1965", "medications":[], "allergies":[] },
        floodInfoDTOListSteppes.add(initFloodInfoDTO(55, "841-874-8888", "Peters",
                new ArrayList<>(), new ArrayList<>()));

        //{ "firstName":"Allison", "lastName":"Boyd", "phone":"841-874-9888",
        //{ "birthdate":"03/15/1965", "medications":["aznol:200mg"], "allergies":["nillacilan"] },
        floodInfoDTOListSteppes.add(initFloodInfoDTO(55, "841-874-9888", "Boyd",
                Arrays.asList(new String[]{ "aznol:200mg" }), Arrays.asList(new String[]{ "nillacilan" })));

        //{"firstName":"Tony", "lastName":"Cooper", "phone":"841-874-6874", ,
        //{ "birthdate":"03/06/1994", "medications":["hydrapermazol:300mg", "dodoxadin:30mg"], "allergies":["shellfish"] },
        floodInfoDTOListSteppes.add(initFloodInfoDTO(26, "841-874-6874", "Cooper",
                Arrays.asList(new String[]{ "hydrapermazol:300mg", "dodoxadin:30mg" }),
                Arrays.asList(new String[]{ "shellfish" })));

        stationFloodInfoDTOSteppesPl.setFloodDTOList(floodInfoDTOListSteppes);

        FloodListDTO foundSteppes = stationFloodInfoDTOList.stream().filter(stationFloodInfoDTO -> stationFloodInfoDTO.getAddress().equalsIgnoreCase(stationFloodInfoDTOSteppesPl.getAddress())).findFirst().get();
        assertThat(foundSteppes.getFloodDTOList()).containsAll(floodInfoDTOListSteppes);
    }

    private FloodDTO initFloodInfoDTO(int age, String phone, String lastname, List<String> medication, List<String> allergies) {
        FloodDTO floodInfoDTO = new FloodDTO();
        floodInfoDTO.setAge(age);
        floodInfoDTO.setPhone(phone);
        floodInfoDTO.setLastname(lastname);
        floodInfoDTO.setAllergiesList(allergies);
        floodInfoDTO.setMedicationList(medication);
        return floodInfoDTO;
    }

    private FirePeopleDTO initFireDTO(int age, String phone, String lastname, List<String> medication, List<String> allergies,
                                List<Integer> stationsList) {
        FirePeopleDTO fireDTO = new FirePeopleDTO();
        fireDTO.setStationNumberList(stationsList);
        fireDTO.setLastname(lastname);
        fireDTO.setMedicationList(medication);
        fireDTO.setAllergiesList(allergies);
        fireDTO.setPhone(phone);
        fireDTO.setAge(age);
        return fireDTO;
    }

    @Test
    @Transactional
    public void getFireInfoByAddressIT() {
        //"112 Steppes Pl"
        List<FirePeopleDTO> fireDTOList = new ArrayList<>();

        List<Integer> stationsList = new ArrayList<>();
        stationsList.add(3);
        stationsList.add(4);

        //{ "firstName":"Ron", "lastName":"Peters","phone":"841-874-8888",
        //{ "birthdate":"04/06/1965", "medications":[], "allergies":[] },
        fireDTOList.add(initFireDTO(56, "841-874-8888", "Peters",
                new ArrayList<>(), new ArrayList<>(),stationsList));

        //{ "firstName":"Allison", "lastName":"Boyd", "phone":"841-874-9888",
        //{ "birthdate":"03/15/1965", "medications":["aznol:200mg"], "allergies":["nillacilan"] },
        fireDTOList.add(initFireDTO(56, "841-874-9888", "Boyd",
                Arrays.asList(new String[]{ "aznol:200mg" }),
                Arrays.asList(new String[]{ "nillacilan" }),stationsList));

        //{"firstName":"Tony", "lastName":"Cooper", "phone":"841-874-6874", ,
        //{ "birthdate":"03/06/1994", "medications":["hydrapermazol:300mg", "dodoxadin:30mg"], "allergies":["shellfish"] },
        fireDTOList.add(initFireDTO(27, "841-874-6874", "Cooper",
                Arrays.asList(new String[]{ "hydrapermazol:300mg", "dodoxadin:30mg" }),
                Arrays.asList(new String[]{ "shellfish" }),stationsList));

        assertThat(fireStationPeopleService.getFireInfoByAddress("112 Steppes Pl")).containsAll(fireDTOList);

    }
}
