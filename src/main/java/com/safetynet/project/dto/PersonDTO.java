package com.safetynet.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PersonDTO {
    private String lastname;
    private String address;
    private int age = Integer.MIN_VALUE;
    private String mail;
    private List<String> medicationList = new ArrayList<>();
    private List<String> allergiesList = new ArrayList<>();

}
