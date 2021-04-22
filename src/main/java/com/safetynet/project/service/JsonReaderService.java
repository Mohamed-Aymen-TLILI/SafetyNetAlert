package com.safetynet.project.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jsoniter.JsonIterator;
import java.io.FileReader;
import java.io.IOException;


@Service
public class JsonReaderService {
    private static final Logger logger = LogManager.getLogger(JsonReaderService.class);
    String filepath = "data.json";

    public String readDataFromJsonFile() throws IOException, ParseException {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filepath));
            JSONObject jsonObject = (JSONObject) obj;
            String data = JsonIterator.deserialize(jsonObject.toString()).toString();
            return data;

    }
}
