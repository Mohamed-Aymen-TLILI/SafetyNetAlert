package com.safetynet.project;

import com.safetynet.project.controller.AlertsApplicationRunnerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@ConditionalOnProperty(
        prefix = "application.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
@Repository
public class AlertsApplicationRunner implements CommandLineRunner {

    @Autowired
    private AlertsApplicationRunnerController alertsApplicationRunnerController;

    @Override
    public void run(final String... args) throws Exception {

        alertsApplicationRunnerController.loadInitialData();
    }
}
