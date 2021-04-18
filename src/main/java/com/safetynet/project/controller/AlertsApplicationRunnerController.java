package com.safetynet.project.controller;

import com.safetynet.project.service.SafetyNetData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class AlertsApplicationRunnerController  implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LogManager.getLogger(AlertsApplicationRunnerController.class);

    @Autowired
    private SafetyNetData safetyNetData;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        {
            safetyNetData.saveDataFromJsonFile();
        }
    }

}
