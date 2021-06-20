package com.safetynet.project.utils;

import com.safetynet.project.model.FireStation;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JsonDataStructure {
    private List<Person> persons;
    private List<MedicalRecords> medicalrecords;
    private List<FireStation> firestations;

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<MedicalRecords> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecords> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    public List<FireStation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<FireStation> firestations) {
        this.firestations = firestations;
    }
}
