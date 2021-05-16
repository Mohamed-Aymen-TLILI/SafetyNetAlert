package com.safetynet.project.repository;

import com.safetynet.project.model.Person;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface PersonRepository  extends CrudRepository<Person, Long> {
    Optional<Person> findByFirstNameAndLastNameAllIgnoreCase(String firstname, String lastname);
    List<Person> findAllByLastNameAllIgnoreCase(String lastname);
    List<Person> findAllByAddressIgnoreCase(String address);
    List<Person> findAllByCityIgnoreCase(String city);

    long removeByFirstNameAndLastName(String firstName, String lastName);
}


