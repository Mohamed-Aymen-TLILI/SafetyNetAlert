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


    private PersonInfoService personInfoService;

    public PersonInfoController (PersonInfoService personInfoService) {this.personInfoService = personInfoService;}

    @GetMapping("/personInfo")
    public List<PersonDTO> getPersonsInfo( @RequestParam String lastName) {

        logger.info("Req Get  endpoint 'personInfo' lastname  {" + lastName + "} ");

        List<PersonDTO> personInfoDTOIterable = personInfoService.getPersonsInfo(lastName);
        if (personInfoDTOIterable != null) {
            logger.info("response Get  endpoint 'personInfo' with lastname  {" + lastName + "} sent ");
            return personInfoDTOIterable;
        } else {
            throw new FunctionalException("personInfo.get.error");
        }
    }


    @GetMapping("/communityEmail")
    public Iterable<String> getCommunityEmail(@RequestParam String city) {
        logger.info("Req Get  endpoint 'communityEmail' ");

        List<String> emailList = personInfoService.getAllEmailsForCity(city);
        if (emailList != null) {
            logger.info("response Get  endpoint  sent");
            return emailList;
        }
        else
        {
            throw new FunctionalException("communityEmail.get.error");
        }
    }

}
