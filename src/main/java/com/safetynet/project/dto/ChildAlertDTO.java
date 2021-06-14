package com.safetynet.project.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChildAlertDTO {
    private String lastName;

    private String firstName;

    private int age;

    private List<FamilyMemberDTO> familyMembers= new ArrayList<>();
}
