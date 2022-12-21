package com.fournier.webapp.model;

import java.util.Objects;

public class PersonDTO {
    private final String name;


    public PersonDTO(String name){

        this.name = name;

    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(name, personDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return "PersonDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}

