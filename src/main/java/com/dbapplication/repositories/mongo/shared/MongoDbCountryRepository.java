package com.dbapplication.repositories.mongo.shared;

import com.dbapplication.models.mongo.shared.Country;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MongoDbCountryRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;


    public List<Country> getAllCountries() {
        return universalMongoTemplate.getAll(Country.class);
    }

    public Country getCountryByCountryCode(String countryCode) {
        Query queryFindByCountryCode = new Query(Criteria.where("country_code").is(countryCode));
        return universalMongoTemplate.getOneByQuery(queryFindByCountryCode, Country.class);
    }

    public Country addCountry(Country country) {
        return universalMongoTemplate.addEntity(country);
    }

    public Country deleteCountryByCountryCode(String countryCode) {
        Query queryFindByCountryCode = new Query(Criteria.where("country_code").is(countryCode));
        return universalMongoTemplate.deleteEntity(queryFindByCountryCode, Country.class);
    }

    public boolean updateCountry(Country country) {
        Query queryFindByCountryCode = new Query(Criteria.where("country_code").is(country.getCountry_code()));
        Update updateCountryName = new Update().set("name", country.getName());
        return universalMongoTemplate.updateEntity(queryFindByCountryCode, updateCountryName, Country.class);
    }
}
