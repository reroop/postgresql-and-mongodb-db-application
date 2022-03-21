package com.dbapplication.repositories.mongo;

import com.dbapplication.models.mongo.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MongoDbCountriesRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Country getByName(String name) {
        Query query = new Query(Criteria.where("riik_kood").is(name));

        List<Country> result = mongoTemplate.find(query, Country.class);
        for (Country country: result) {
            log.info("found country:");
            log.info(String.valueOf(country));
        }
        log.info("returning:" + result.get(0).toString());
        return result.get(0);
    }
}
