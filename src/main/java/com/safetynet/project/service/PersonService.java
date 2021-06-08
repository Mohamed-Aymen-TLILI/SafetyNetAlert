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
    public Iterable<Person> saveAllPersons(List<Person> personList) {
        try{
             return  personRepository.saveAll(personList);

            } catch (Exception exception) {
                logger.error("Erreur while sauvegarde list of person" + exception.getMessage() + " , Stack Trace : " + exception.getStackTrace());
                return null;
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
            logger.error("Error while getting list of person : " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
            return null;
        }
    }

    /**
     * return list of exist person
     *
     * @return list of exist person
     */
    public Optional<Person> getPersonByFirstNameAndLastName(String firstname, String lastname) {
        try {
            return personRepository.findByFirstNameAndLastNameAllIgnoreCase(firstname, lastname);
        } catch (Exception exception) {
            logger.error("Error while getting a person: " + exception.getMessage() + " Stack Trace + " + exception.getStackTrace());
            return null;
        }
    }

    /**
     * save a person if doesn't exist
     *
     * @param person save a person,
     * @return save a person if doesn't exist
     */
    public Person addPerson(Person person) {
        if (person != null) {
            Optional<Person> personOptional = this.getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            if (personOptional.isPresent()) {
                logger.error("Error while saving a person");
                return null;
            } else {
                try {
                    personRepository.save(person);
                } catch (Exception exception) {
                    logger.error("Error while adding a person :" + exception.getMessage() + " StackTrace : " + exception.getStackTrace());
                    return null;
                }
            }
        }
        return person;
    }


    /**
     * Update a person if exist
     *
     * @param person to update
     * @return person update, or null object, or person doesn't exist
     */
    public Person updatePerson(Person person) {
        if (person != null) {
            Optional<Person> personOptional = this.getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName());

            if (personOptional.isPresent()) {
                Person personToUpdate = personOptional.get();

                personToUpdate.setAddress(person.getAddress());
                personToUpdate.setCity(person.getCity());
                personToUpdate.setEmail(person.getEmail());
                personToUpdate.setPhone(person.getPhone());
                personToUpdate.setZip(person.getZip());

                try {
                    personRepository.save(personToUpdate);
                    return personToUpdate;
                } catch (Exception exception) {
                    logger.error("error while updating e person : " + exception.getMessage() + " StackTrace : " + exception.getStackTrace());
                    return null;
                }
            } else {
                logger.error("error while updating e person : Person doesn't exist");
                return null;
            }
        } else {
            logger.error("error while updating e person :  object null");
            return null;
        }
    }

    /**
     * delete one person if exist
     *  @param person to update
     */
    public void deletePerson(Person person) {
                personRepository.removeByFirstNameAndLastName(person.getFirstName(), person.getLastName());
    }

}
