package com.safetynet.project.controller;

import com.safetynet.project.service.SafetyNetData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AlertsApplicationRunnerController {

    private static final Logger logger = LogManager.getLogger(AlertsApplicationRunnerController.class);

    @Autowired
    private SafetyNetData safetyNetData;

    public void loadInitialData()
    {
        safetyNetData.saveDataFromJsonFile();
    }
}
