package com.safetynet.project.dto;

import lombok.Data;

@Data
public class PeopleCommunity {

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private int age = Integer.MIN_VALUE;
}
