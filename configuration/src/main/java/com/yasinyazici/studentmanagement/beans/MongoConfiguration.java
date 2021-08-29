package com.yasinyazici.studentmanagement.beans;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    //TODO insert credentials via environment
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://yasin:yazici@localhost:27017/");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "studentmanagement");
    }
}
