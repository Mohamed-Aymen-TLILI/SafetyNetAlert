package com.safetynet.project.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.project.model.FireStation;
import com.safetynet.project.model.MedicalRecords;
import com.safetynet.project.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonReaderService {
    private static final Logger logger = LogManager.getLogger(JsonReaderService.class);

    private ObjectMapper objectMapper;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private FireStationService fireStationService;

    @Value("${jsonFilePathData}")
    private String filepath;

    @Autowired
    private JsonReaderService jsonReaderService;

    public void readDataFromJsonFile() {
        logger.debug("Démarrage du chargement du fichier data.json");

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(this.filepath));

            JSONParser jsonParser = new JSONParser();

            JSONObject jsonObject = (JSONObject) jsonParser.parse(inputStreamReader);

            List<Person> lstPerson = readListPersonFromJsonObject(jsonObject);
            personService.saveAllPersons(lstPerson);

            List<FireStation> lstFireStation = readListFireStationFromJsonObject(jsonObject);
            fireStationService.saveAllFireStations(lstFireStation);

            List<MedicalRecords> lstMedicalRecords = readListMedicalRecordFromJsonObject(jsonObject);
            medicalRecordService.saveAllMedicalRecords(lstMedicalRecords);

            inputStreamReader.close();

        } catch (IOException | ParseException | JSONException exception) {
            logger.error("Error while parsing input json file : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
        }

        logger.debug("Chargement du fichier data.json terminé");
    }

    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void onContextRefreshed() {
        if ( isDatabaseEmpty()) {
            jsonReaderService.readDataFromJsonFile();
        }
    }

    boolean isDatabaseEmpty() {
        return personService.getAllPersons().equals(0) ;
    }

    private List<Person> readListPersonFromJsonObject(JSONObject jsonObject) {
        JSONArray personsInJson = (JSONArray) jsonObject.get("persons");

        objectMapper = new ObjectMapper();
        List<Person> personList = new ArrayList<>();
        personsInJson.forEach(itemArray ->
        {
            try {
                personList.add(objectMapper.readValue(itemArray.toString(), Person.class));
            } catch (JsonProcessingException exception) {
                logger.error("Error while parsing input json file - persons : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
            }
        });

        return personList;

    }

    private List<FireStation> readListFireStationFromJsonObject(JSONObject jsonObject) throws JSONException {
        JSONArray fireStationsArrayInJson = (JSONArray) jsonObject.get("firestations");

        objectMapper = new ObjectMapper();
        List<FireStation> fireStationList = new ArrayList<>();
        fireStationsArrayInJson.forEach(itemArray ->
        {
            try {
                fireStationList.add(objectMapper.readValue(itemArray.toString(), FireStation.class));
            } catch (JsonProcessingException exception) {
                logger.error("Error while parsing input json file - firestations : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
            }
        });

        return fireStationList;

    }

    private List<MedicalRecords> readListMedicalRecordFromJsonObject(JSONObject jsonObject) {
        JSONArray medicalRecordsArrayInJson = (JSONArray) jsonObject.get("medicalrecords");

        objectMapper = new ObjectMapper();
        List<MedicalRecords> medicalRecordList = new ArrayList<>();
        medicalRecordsArrayInJson.forEach(itemArray ->
        {
            try {
                medicalRecordList.add(objectMapper.readValue(itemArray.toString(), MedicalRecords.class));
            } catch (JsonProcessingException exception) {
                logger.error("Error while parsing input json file - medicalRecords : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
            }
        });

        return medicalRecordList;

    }
}
