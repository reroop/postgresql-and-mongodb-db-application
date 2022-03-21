package com.dbapplication.services.mongo;

import com.dbapplication.models.mongo.Country;
import com.dbapplication.repositories.mongo.MongoDbCountriesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MongoDbCountriesService {

    @Autowired
    private MongoDbCountriesRepository mongoDbCountriesRepository;

    /*
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

     */

    public Country getCountryByCountryCode(String countryCode) {
        log.info("getcountrybycode: " + countryCode);
        Country c = mongoDbCountriesRepository.getByName(countryCode);
        log.info("country in service: " + c.toString());
        return c;
        //return countryRepository.findCountryByNimetus(countryCode);
    }
}
