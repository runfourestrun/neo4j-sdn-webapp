

## Building a microservice with Spring Data Neo4j 🌱🌱🌱

![img.png](src/main/resources/img/img.png)

### An example of building a microservice using Spring Data Neo4j. 

> business logic is encapsulated in the PersonService class

```java
    public PersonDTO fetchPersonByName(String name){
        Map<String, Object> personParameters = Map.of("name",name);

        return this.neo4jClient.query(fetchPersonByNameQuery)
                .in(database)
                .fetchAs(PersonDTO.class)
                .mappedBy(toPersonDTO)
                .one().orElse(null);
    }
```

> You must pass a mapping BiFunction to Marshall the result of your Cypher query (which is returned as a Record type) into a POJO (plain old java object).


```java

    BiFunction<TypeSystem,Record,PersonDTO> toPersonDTO = (typeSystem, record) ->
    {
        var result_map = record.get(0).asMap();
        var name = (String) result_map.get("name");
        var age = ((Long) result_map.get("age")).intValue();

        PersonDTO personDTO = new PersonDTO(name,age);
        return personDTO;
    };

```


> The controller actually implements the RestService. Below is a get action to fetch all Person Nodes in the Graph


```java
    @GetMapping("/people")
    public Collection<PersonDTO> getPeople(){

        return personService.fetchAllPeople();


    }


```