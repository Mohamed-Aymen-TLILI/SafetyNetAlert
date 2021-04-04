package com.safetynet.project.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity @IdClass(PersonId.class)
@Table(name="Persons")
public class Person {

    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Id
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Id
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zip", nullable = false)
    private String zip;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;


}
