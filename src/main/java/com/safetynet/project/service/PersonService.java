package com.safetynet.project.service;

import com.safetynet.project.model.Person;
import com.safetynet.project.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private static final Logger logger = LogManager.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    /**
     * Sauvegarde List of person in file data.json
     *
     * @param personList List of person
     */
    public boolean saveAllPersons(List<Person> personList) {
        if (personList != null){
            try{
                personRepository.saveAll(personList);
                return true;
            } catch (Exception exception) {
                logger.error("Erreur while sauvegarde list of person" + exception.getMessage() + " , Stack Trace : " + exception.getStackTrace());
            }
        }
        return false;
    }


    /**
     * Retourne l'ensemble des personnes existantes
     *
     * @return Liste des personnes
     */
    public Iterable<Person> getAllPersons() {
        try {
            return personRepository.findAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la récupération de la liste des personnes : " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
            return null;
        }
    }
}
