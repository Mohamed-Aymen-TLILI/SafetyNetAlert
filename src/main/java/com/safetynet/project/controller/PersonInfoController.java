package com.safetynet.project.controller;

import com.safetynet.project.dto.PersonDTO;
import com.safetynet.project.model.FunctionalException;
import com.safetynet.project.service.PersonInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonInfoController {

    private static final Logger logger = LogManager.getLogger(PersonInfoController.class);

    @Autowired
    private PersonInfoService personInfoService;

    @GetMapping("/personInfo")
    public List<PersonDTO> getPersonsInfo(@RequestParam String firstName, @RequestParam String lastName) {

        logger.info("Req Get  endpoint 'personInfo' with firstname : {" + firstName + "} and lastname  {" + lastName + "} ");

        List<PersonDTO> personInfoDTOIterable = personInfoService.getPersonsInfo(firstName, lastName);
        if (personInfoDTOIterable != null) {
            logger.info("response Get  endpoint 'personInfo' with firstname : {" + firstName + "} and lastname  {" + lastName + "} sent ");
            return personInfoDTOIterable;
        } else {
            throw new FunctionalException("personInfo.get.error");
        }
    }




}
