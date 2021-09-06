package com.safetynet.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FloodDTO {
    private String lastname;
    private String phone;
    private int age= Integer.MIN_VALUE;
    private List<String> medicationList = new ArrayList<>();
    private List<String> allergiesList = new ArrayList<>();
}
