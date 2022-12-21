package com.fournier.webapp.service;

import com.fournier.webapp.model.Person;
import com.fournier.webapp.model.PersonDTO;
import com.fournier.webapp.repository.PersonRepository;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.types.TypeSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;

/***
 * @Service basically is the same as @Component
 * in Alex woolfords example he isn't interacting with the database, he is posting to Kafka: https://github.com/alexwoolford/supply-chain-newsfeed
 */
@Service
public class PersonService {

    private final Neo4jClient neo4jClient;
    private final Driver driver;
    private final DatabaseSelectionProvider databaseSelectionProvider;
    private final String fetchPersonByNameQuery = "MATCH (p:Person{name: $name}) RETURN p {.name}";

    private String database;

    private final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    /***
     * This lambda field is grossssss.
     */
    BiFunction<TypeSystem,Record,PersonDTO> toPersonDTO = (typeSystem, record) ->
    {
        var result_map = record.get(0).asMap();
        var name = (String) result_map.get("name");
        var age = ((Long) result_map.get("age")).intValue();

        PersonDTO personDTO = new PersonDTO(name,age);
        return personDTO;
    };



    public PersonService( Neo4jClient neo4jClient, Driver driver, DatabaseSelectionProvider databaseSelectionProvider){
        this.neo4jClient = neo4jClient;
        this.driver = driver;
        this.databaseSelectionProvider = databaseSelectionProvider;
        this.database = databaseSelectionProvider.getDatabaseSelection().getValue();
    }


    /***
     * neo4jClient docs: https://docs.spring.io/spring-data/neo4j/docs/current/api/org/springframework/data/neo4j/core/Neo4jClient.BindSpec.html#bindAll(java.util.Map)
     * This approach seems much more declarative/functional than the fetchEntireGraph() method.
     * @param
     * @return
     */
    public PersonDTO fetchPersonByName(String name){
        Map<String, Object> personParameters = Map.of("name",name);

        return this.neo4jClient.query(fetchPersonByNameQuery)
                .in(database)
                .fetchAs(PersonDTO.class)
                .mappedBy(toPersonDTO)
                .one().orElse(null);
    }



    public Collection<PersonDTO> fetchAllPeople(){

        String cypherQuery = "MATCH (p:Person) RETURN p";
        return this.neo4jClient.query(cypherQuery)
                .in(database)
                .fetchAs(PersonDTO.class)
                .mappedBy(toPersonDTO)
                .all();
    }


    /*** Take this approach if you don't want spring to manage the driver?
     *
     * @return
     */
    public Map<String, List<Object>> fetchEntireGraph() {

        List nodes = new ArrayList<>();
        List links = new ArrayList<>(); // In this example I'm not inserting into the List, but you.
        Optional<String> database = Optional.of(database());

        try (Session session = createSession(database)){

            var records = session.executeRead(tx -> tx.run(""
                    + "MATCH (p:Person)"
                    + "RETURN p.name as name").list());

            records.forEach(record -> {
                var person = Map.of("label","Person","name",record.get("name"));
                nodes.add(person);

            });
        }


        return Map.of("nodes",nodes);
    }

    private Session createSession (Optional < String > database) {
        return driver.session(SessionConfig.forDatabase(database.get()));
    }

    private String database() {
        return databaseSelectionProvider.getDatabaseSelection().getValue();
    }


    /**
     * This is mapping class used in the fetchPersonByName DTO.
     * @param ignored
     * @param record
     * @return
     */


}
