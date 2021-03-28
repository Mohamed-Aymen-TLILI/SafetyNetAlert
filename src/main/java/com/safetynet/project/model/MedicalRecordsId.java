package com.safetynet.project.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MedicalRecordsId implements Serializable {
    private String firstName;
    private String lastName;
}
