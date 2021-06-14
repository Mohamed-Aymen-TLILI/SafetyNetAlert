package com.safetynet.project.controller;

import com.safetynet.project.dto.ChildAlertDTO;
import com.safetynet.project.model.FunctionalException;
import com.safetynet.project.service.ChildAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChildAlertController {

    private static final Logger logger = LogManager.getLogger(ChildAlertController.class);

    @Autowired
    private ChildAlertService childAlertService;

    @GetMapping("/childAlert")
    public List<ChildAlertDTO> getChildAlertList(@RequestParam String address) {

        logger.info("req Get endpoint 'childAlert' with address : {" + address + "}");

        List<ChildAlertDTO> childAlertDTOList = childAlertService.getChildAlertDTOListFromAddress(address);
        if (childAlertDTOList != null) {
            logger.info("response  Get endpoint 'childAlert' with address  : {" + address + "}");
            return childAlertDTOList;
        } else {
            throw new FunctionalException("childAlert.get.error");
        }
    }
}
