package com.dbapplication.repositories.mongo;

import com.dbapplication.models.mongo.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MongoDbCountriesRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Country> getAllCountries() {
        return mongoTemplate.findAll(Country.class);
    }

    public Country getCountryByName(String countryCode) {
        Query queryFindByCountryCode = new Query(Criteria.where("riik_kood").is(countryCode));
        Country result = mongoTemplate.findOne(queryFindByCountryCode, Country.class);
        if (result == null) {
            return null;
        }
        log.info("returning:" + result);
        return result;
    }

    public Country addCountry(Country country) {
        try {
            return mongoTemplate.save(country);
        } catch (Exception e) {
            log.info(e.getCause().getMessage());
            if (e.getMessage().contains("E11000 duplicate key error collection")) { //todo: make this string as constant somewhere
                log.info("db already has document with these values");  //todo: query db for entry with queried values and return as error?
            }
            if (e.getMessage().contains("Document failed validation")) { //todo: make this string as constant somewhere
                log.info("entry failed against db validation");  //todo: return validation??
            }
            return null;
        }
    }

    public Country deleteCountry(String countryCode) {
        Query queryFindByCountryCode = new Query(Criteria.where("riik_kood").is(countryCode));
        return mongoTemplate.findAndRemove(queryFindByCountryCode, Country.class);
    }

    public boolean updateCountryName(String countryCode, String newCountryName) {
        try {
            Query queryFindByCountryCode = new Query(Criteria.where("riik_kood").is(countryCode));
            Update updateCountryName = new Update().set("nimetus", newCountryName);
            return mongoTemplate.updateFirst(queryFindByCountryCode, updateCountryName, Country.class).wasAcknowledged();
        } catch (Exception e) {
            if (e.getMessage().contains("Document failed validation")) { //todo: make this string as constant somewhere
                log.info("entry failed against db validation");  //todo: return validation??
            }
            return false;
        }

    }
}
