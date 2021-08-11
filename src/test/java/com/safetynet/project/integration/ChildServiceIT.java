package com.safetynet.project.integration;


import com.safetynet.project.dto.ChildAlertDTO;
import com.safetynet.project.service.ChildAlertService;
import com.safetynet.project.service.DateUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

@Tag("IntegrationTests")
@SpringBootTest(properties = {
        "application.runner.enabled=true" })
public class ChildServiceIT {

    @Autowired
    private ChildAlertService childAlertService;

    @SpyBean
    private DateUtils dateUtilsMock;

    @Test
    public void getChildAlertIT() {
        LocalDate nowLocalDateMock = LocalDate.of(2021, 06, 10);
        when(dateUtilsMock.getNowLocalDate()).thenReturn(nowLocalDateMock);

        List<ChildAlertDTO> childAlertDTOList = childAlertService.getChildAlertDTOListFromAddress("1509 Culver St");

        assertThat(childAlertDTOList.size()).isEqualTo(2);
        assertThat(childAlertDTOList.get(0).getFamilyMembers().size()).isEqualTo(4);

        ChildAlertDTO childAlertDTOTenley = childAlertDTOList.stream().filter(childAlertDTO -> childAlertDTO.getFirstName().equalsIgnoreCase("tenley")).findFirst().get();
        assertThat(childAlertDTOTenley.getAge()).isEqualTo(9);
        assertThat(childAlertDTOTenley.getFamilyMembers()).allMatch(familyMemberDTO -> familyMemberDTO.getFirstName().equalsIgnoreCase("John")
                || familyMemberDTO.getFirstName().equalsIgnoreCase("Jacob")
                || familyMemberDTO.getFirstName().equalsIgnoreCase("Felicia")
                || familyMemberDTO.getFirstName().equalsIgnoreCase("Roger"));
    }
}
