package com.safetynet.project.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FirePeopleDTO {
    private String lastname;
    private String phone;
    private int age= Integer.MIN_VALUE;
    private List<String> medicationList = new ArrayList<>();
    private List<String> allergiesList = new ArrayList<>();
    private List<Integer> stationNumberList = new ArrayList<>();
}
