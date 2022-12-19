package com.fournier.webapp.repository;

import com.fournier.webapp.model.Person;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends Repository<Person,String> {


    @Query("MATCH (person:Person) WHERE p.firstName CONTAINS $firstname RETURN person")
    List<Person> findPeopleByFirstName(@Param("firstname") String firstName);
}
