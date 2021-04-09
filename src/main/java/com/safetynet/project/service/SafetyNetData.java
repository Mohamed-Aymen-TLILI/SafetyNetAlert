package com.safetynet.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.project.model.FireStation;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SafetyNetData {

    private static final Logger logger = LogManager.getLogger(SafetyNetData.class);

    private List<Person> persons = new ArrayList<>();
    private List<MedicalRecords> medicalRecords = new ArrayList<>();
    private List<FireStation> fireStations = new ArrayList<>();

    public List<Person> readJsonFilePersons() throws IOException, ParseException {
        JsonReaderService data = new JsonReaderService();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(data.readDataFromJsonFile());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray listpersons = (JSONArray) jsonObject.get("persons");
        ObjectMapper mapper = new ObjectMapper();
        ListIterator iterator = listpersons.listIterator();
        while (iterator.hasNext()) {
            persons.add(mapper.readValue(iterator.next().toString(), Person.class));
        }
        List<Person> listePerson = new ArrayList<>(persons);
        return listePerson;
    }

    public List<FireStation> readJsonFileStation() throws IOException, ParseException {
        JsonReaderService data = new JsonReaderService();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(data.readDataFromJsonFile());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray listStations = (JSONArray) jsonObject.get("firestations");
        ObjectMapper mapper = new ObjectMapper();
        ListIterator iterator = listStations.listIterator();
        while (iterator.hasNext()) {
            fireStations.add(mapper.readValue(iterator.next().toString(), FireStation.class));

        }
        List<FireStation> fireStation = new ArrayList<>(fireStations);
        return fireStation;
    }


    public List<MedicalRecords> readJsonFileMedicalrecords() throws IOException, ParseException {
        JsonReaderService data = new JsonReaderService();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(data.readDataFromJsonFile());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray listMedical = (JSONArray) jsonObject.get("medicalrecords");
        ObjectMapper mapper = new ObjectMapper();
        ListIterator iterator = listMedical.listIterator();
        while (iterator.hasNext()) {
            medicalRecords.add(mapper.readValue(iterator.next().toString(), MedicalRecords.class));

        }
        List<MedicalRecords> listMedical1 = new ArrayList<MedicalRecords>(medicalRecords);
        return listMedical1;
    }

    public void initData() {
        try {
            this.readJsonFileStation();
            this.readJsonFilePersons();
            this.readJsonFileMedicalrecords();
        } catch (IOException e) {
            logger.error("Error initializing lists persons, firestation and medicalrecords");
            e.printStackTrace();
        } catch (ParseException e) {
            logger.error("Error initializing lists persons, firestation and medicalrecords");
            e.printStackTrace();
        }
    }

    public void linkList() {
        for (Person person : persons) {

            for (FireStation station : fireStations) {

            }

            for (MedicalRecords medicalRecord : medicalRecords) {

            }
        }
    }

    public void dataEmpty() throws IOException, ParseException {
        persons.clear();
        fireStations.clear();
        medicalRecords.clear();
        this.initData();
        this.linkList();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<MedicalRecords> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecords> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    public void setFireStations(List<FireStation> fireStations) {
        this.fireStations = fireStations;
    }


}
