package com.safetynet.project.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.List;

@Entity
public class MedicalRecords {

    private static final Logger logger = LogManager.getLogger(MedicalRecords.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
    private String birthdate;
    @ElementCollection
    private List<String> allergies;
    @ElementCollection
    private List<String> medications;

    public MedicalRecords() {
    }

    public MedicalRecords(Long id, String lastName, String firstName, String birthdate, List<String> allergies, List<String> medications) throws ParseException {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.allergies = allergies;
        this.medications = medications;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    @Override
    public String toString() {
        return "{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthdate=" + birthdate +
                ", allergies='" + allergies + '\'' +
                ", medications='" + medications + '\'' +
                '}';
    }


}
