package com.shivanshu.Assignment.config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Neo4jConfig {
    //this annotation helps to read database connection properties from application.properties file
    @Value("${neo4j.url}")
    private String url;

    @Value("${neo4j.username}")
    private String username;

    @Value("${neo4j.password}")
    private String password;

    //one mistake don't repeat - make it a bean cause repository layer needs it so bean will help to automatically inject it there
    @Bean
    public Driver neo4jDriver() { //this is a neo4j java driver, helps us in establishing a connection to the neo4j database
        //this graphDatabase driver creates a driver connection means connect to neo4j,authenticate and create driver
        return GraphDatabase.driver(url, AuthTokens.basic(username, password));
    }
}
