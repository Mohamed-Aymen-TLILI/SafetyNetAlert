package com.safetynet.project.repository;

import com.safetynet.project.model.Person;
import org.springframework.data.repository.CrudRepository;




public interface PersonRepository  extends CrudRepository<Person, Long> {
}


