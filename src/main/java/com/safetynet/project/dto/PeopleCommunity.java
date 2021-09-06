package com.safetynet.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PeopleCommunity {

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private int age = Integer.MIN_VALUE;
}
