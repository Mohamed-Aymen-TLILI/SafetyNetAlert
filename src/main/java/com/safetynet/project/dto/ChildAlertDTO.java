package com.safetynet.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ChildAlertDTO {
    private String lastName;

    private String firstName;

    private int age;

    private List<FamilyMemberDTO> familyMembers= new ArrayList<>();
}
