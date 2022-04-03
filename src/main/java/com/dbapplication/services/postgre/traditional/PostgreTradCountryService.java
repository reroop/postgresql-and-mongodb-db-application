package com.dbapplication.services.postgre.traditional;

import com.dbapplication.models.postgre.traditional.Country;
import com.dbapplication.repositories.postgre.traditional.PostgreTradCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgretrad")
public class PostgreTradCountryService {

    @Autowired
    private PostgreTradCountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryByCountryCode(String countryCode) {
        return countryRepository.findById(countryCode).orElse(null);
    }

    public Country addCountry(Country country) {
        if (countryRepository.findById(country.getCountry_code()).isPresent()) {
            return null;
        }
        return countryRepository.save(country);
    }
}
