package com.safetynet.project.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Embeddable
@Entity @IdClass(MedicalRecordsId.class)
@Table(name="MedicalRecords")
public class MedicalRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MedicalRecords_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name="medications")
    private List<String> medications;

    @Column(name = "allergy")
    private List<String> allergies;

}
