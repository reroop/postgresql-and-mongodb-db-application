package com.dbapplication.services.mongo.shared;

import com.dbapplication.models.mongo.shared.Country;
import com.dbapplication.repositories.mongo.shared.MongoDbCountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoDbCountriesService {

    @Autowired
    private MongoDbCountryRepository mongoDbCountryRepository;


    public List<Country> getAllCountries() {
        return mongoDbCountryRepository.getAllCountries();
    }


    public Country getCountryByCountryCode(String countryCode) {
        return mongoDbCountryRepository.getCountryByCountryCode(countryCode);
    }

    public Country addCountry(Country country) {
        return mongoDbCountryRepository.addCountry(country);
    }
}
