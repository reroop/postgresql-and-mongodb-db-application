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

    public Country addCountry(Country country) throws Throwable {
        if (countryRepository.findById(country.getCountry_code()).isPresent()) {
            throw new Exception(new Throwable("country with code " + country.getCountry_code() + " is already present!"));
        }
        try {
            return countryRepository.save(country);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }

    }
}
