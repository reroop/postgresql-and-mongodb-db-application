package com.dbapplication.services.postgre.ref;

import com.dbapplication.models.postgre.ref.CountryRef;
import com.dbapplication.repositories.postgre.ref.PostgreRefCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgreref")
public class PostgreRefCountryService {

    @Autowired
    private PostgreRefCountryRepository countryRepository;

    public List<CountryRef> getAllCountries() {
        return countryRepository.findAll();
    }

    public CountryRef getCountryByCountryCode(String countryCode) {
        return countryRepository.findById(countryCode).orElse(null);
    }

    public CountryRef addCountry(CountryRef countryRef) {
        if (countryRepository.findById(countryRef.getCountry_code()).isPresent()) {
            return null;
        }
        return countryRepository.save(countryRef);
    }
}
