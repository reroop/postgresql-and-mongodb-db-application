package com.dbapplication.services.mongo;

import com.dbapplication.models.mongo.Country;
import com.dbapplication.repositories.mongo.MongoDbCountriesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoDbCountriesService {

    @Autowired
    private MongoDbCountriesRepository mongoDbCountriesRepository;


    public List<Country> getAllCountries() {
        return mongoDbCountriesRepository.getAllCountries();
    }


    public Country getCountryByCountryCode(String countryCode) {
        log.info("getcountrybycode: " + countryCode);
        Country country = mongoDbCountriesRepository.getCountryByName(countryCode);
        if (country != null) {
            log.info("country in service: " + country);
        }
        return country;
    }

    public Country addCountry(Country country) {
        return mongoDbCountriesRepository.addCountry(country);
    }

    public Country deleteCountry(String countryCode) {
        return mongoDbCountriesRepository.deleteCountry(countryCode);
    }

    public boolean updateCountryName(String countryCode, String newCountryName) {
        return mongoDbCountriesRepository.updateCountryName(countryCode, newCountryName);
    }
}
