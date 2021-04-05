package com.safetynet.project.service;

import com.safetynet.project.model.Person;
import com.safetynet.project.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void saveAllPersons(List<Person> personList) {
        if (personList != null){
            try{
                personRepository.saveAll(personList);

            } catch (Exception exception) {
                logger.error("Erreur while sauvegarde list of person" + exception.getMessage() + " , Stack Trace : " + exception.getStackTrace());
            }
        }

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

    /**
     * Sauvegarde une personne si elle n'existe pas déjà
     *
     * @param person personne à sauvegarder,
     * @return personne enregistrée, null si elle existait déjà
     */
    public Person addPerson(Person person) {
        if (person != null) {
            Optional<Person> personOptional = this.getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            if (personOptional.isPresent()) {
                logger.error("Erreur lors de l'ajout d'une personne déjà existante");
                return null;
            } else {
                try {
                    personRepository.save(person);
                } catch (Exception exception) {
                    logger.error("Erreur lors de l'ajout d'une personne :" + exception.getMessage() + " StackTrace : " + exception.getStackTrace());
                    return null;
                }
            }
        }
        return person;
    }

    /**
     * Retourne l'ensemble des personnes existantes
     *
     * @return Liste des personnes
     */
    public Optional<Person> getPersonByFirstNameAndLastName(String firstname, String lastname) {
        try {
            return personRepository.findByFirstNameAndLastNameAllIgnoreCase(firstname, lastname);
        } catch (Exception exception) {
            logger.error("Erreur lors de la récupération d'une personne : " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
            return null;
        }
    }
}
