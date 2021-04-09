package com.safetynet.project.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name="MedicalRecords")
public class MedicalRecords {

    private static final Logger logger = LogManager.getLogger(MedicalRecords.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthdate;
    private List<String> allergies;
    private List<String> medications;
    private int age;

    public MedicalRecords() {
    }

    public MedicalRecords(String lastName, String firstName, LocalDate birthdate, List<String> allergies, List<String> medications, int age) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.allergies = allergies;
        this.medications = medications;
        this.age = age;

    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthdate() {
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

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void updatemedicalexceptFirstNameLastName(LocalDate birthdate, List<String> allergies, List<String> medications, int age) {
        this.birthdate = birthdate;
        this.allergies = allergies;
        this.medications = medications;
        this.age = age;
    }

    @Override
    public String toString() {
        return "{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthdate=" + birthdate +
                ", allergies='" + allergies + '\'' +
                ", medications='" + medications + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

}
