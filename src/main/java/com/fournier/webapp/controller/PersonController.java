package com.fournier.webapp.controller;


import com.fournier.webapp.model.Person;
import com.fournier.webapp.model.PersonDTO;
import com.fournier.webapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@RestController
public class PersonController {

    private final PersonService personService;

    // DI through constructor parameters to get service bean into Spring Context.
    public PersonController(PersonService personService){
        this.personService = personService;
    }


    // Value is mandatory, so PathVariable makes sense here. If it wasn't mandatory, RequestParam would have been a better choice.
    @GetMapping(value= "/person/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO findByName(@PathVariable("name") String name){
        return personService.fetchPersonByName(name);
    }


    @GetMapping("/graph")
    public Map<String, List<Object>> getGraph(){
        return personService.fetchEntireGraph();
    }


    @GetMapping("/people")
    public Collection<PersonDTO> getPeople(){

        return personService.fetchAllPeople();


    }

}
