package com.safetynet.project.repository;

import com.safetynet.project.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository  extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstNameAndLastNameAllIgnoreCase(String firstname, String lastname);
}


