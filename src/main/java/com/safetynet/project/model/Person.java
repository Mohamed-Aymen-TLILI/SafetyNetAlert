package com.safetynet.project.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity @IdClass(PersonId.class)
@Table(name="Persons")
public class Person {

    @Id
    @NotNull
    @NotEmpty
    private String firstName;

    @Id
    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String zip;

    @NotNull
    @NotEmpty
    private String phone;

    @NotNull
    @NotEmpty
    private String email;


    public Person (String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

}
