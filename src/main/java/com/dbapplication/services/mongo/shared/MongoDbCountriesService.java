package com.dbapplication.services.mongo.shared;

import com.dbapplication.models.mongo.shared.Country;
import com.dbapplication.repositories.mongo.shared.MongoDbCountriesRepository;
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
        return mongoDbCountriesRepository.getCountryByCountryCode(countryCode);
    }

    public Country addCountry(Country country) {
        return mongoDbCountriesRepository.addCountry(country);
    }

    public Country deleteCountry(String countryCode) {
        return mongoDbCountriesRepository.deleteCountryByCountryCode(countryCode);
    }

    public boolean updateCountry(Country country) {
        return mongoDbCountriesRepository.updateCountry(country);
    }
}
