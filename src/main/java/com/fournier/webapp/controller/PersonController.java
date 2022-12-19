package com.fournier.webapp.controller;


import com.fournier.webapp.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PersonController {

    public PersonService personService;


    public PersonController(){

    }
}
