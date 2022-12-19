package com.fournier.webapp.model;


import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Person {

    @Id
    private String name;


    public Person(String name) {

        this.name = name;
    }

    public String getFirstName() {
        return name;
    }


}
