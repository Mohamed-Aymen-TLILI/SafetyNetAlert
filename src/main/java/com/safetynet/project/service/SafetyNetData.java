package com.safetynet.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.project.model.FireStation;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class SafetyNetData {

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private FireStationService fireStationService;

    String fileName = "data.json";
    private static final Logger logger = LogManager.getLogger(SafetyNetData.class);
    private final List<Person> lstPerson = new ArrayList<>();
    private final List<MedicalRecords> lstMedicalRecords = new ArrayList<>();
    private final List<FireStation> lstFireStation = new ArrayList<>();

    public void saveDataFromJsonFile() {
        logger.debug("Démarrage du chargement du fichier data.json");

        try {

            List<Person> lstPerson = readListPersonFromJsonObject();
            personService.saveAllPersons(lstPerson);

            List<FireStation> lstFireStation = readListFireStationFromJsonObject();
            fireStationService.saveAllFireStations(lstFireStation);

            List<MedicalRecords> lstMedicalRecords = readListMedicalRecordFromJsonObject();
            medicalRecordService.saveAllMedicalRecords(lstMedicalRecords);


        } catch (IOException | ParseException exception) {
            logger.error("Error while parsing input json file : " + exception.getMessage() + " Stack Strace : " + Arrays.toString(exception.getStackTrace()));
        }

        logger.debug("Chargement du fichier data.json terminé");
    }

    private List<Person> readListPersonFromJsonObject() throws IOException, ParseException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(this.fileName));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(inputStreamReader);
        JSONArray listPersons = (JSONArray) jsonObject.get("persons");
        ObjectMapper mapper = new ObjectMapper();
        ListIterator iterator = listPersons.listIterator();
        while (iterator.hasNext()) {
            try {
                lstPerson.add(mapper.readValue(iterator.next().toString(), Person.class));
            } catch (JsonProcessingException exception) {
                logger.error("Error while parsing input json file - persons : " + exception.getMessage() + " Stack Strace : " + Arrays.toString(exception.getStackTrace()));
            }
        }

        return lstPerson;

    }

    private List<FireStation> readListFireStationFromJsonObject() throws IOException, ParseException{
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(this.fileName));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(inputStreamReader);
        JSONArray listPersons = (JSONArray) jsonObject.get("firestations");
        ObjectMapper mapper = new ObjectMapper();
        ListIterator iterator = listPersons.listIterator();
        while (iterator.hasNext()) {
            try {
                lstFireStation.add(mapper.readValue(iterator.next().toString(), FireStation.class));
            } catch (JsonProcessingException exception) {
                logger.error("Error while parsing input json file - persons : " + exception.getMessage() + " Stack Strace : " + Arrays.toString(exception.getStackTrace()));
            }
        }

        return lstFireStation;

    }

    private List<MedicalRecords> readListMedicalRecordFromJsonObject() throws IOException, ParseException{
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(this.fileName));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(inputStreamReader);
        JSONArray listPersons = (JSONArray) jsonObject.get("medicalrecords");
        ObjectMapper mapper = new ObjectMapper();
        ListIterator iterator = listPersons.listIterator();
        while (iterator.hasNext()) {
            try {
                lstMedicalRecords.add(mapper.readValue(iterator.next().toString(), MedicalRecords.class));
            } catch (JsonProcessingException exception) {
                logger.error("Error while parsing input json file - persons : " + exception.getMessage() + " Stack Strace : " + Arrays.toString(exception.getStackTrace()));
            }
        }

        return lstMedicalRecords;

    }
}



