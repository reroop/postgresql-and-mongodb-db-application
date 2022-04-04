package com.dbapplication.services.postgre.jsonb.common;

import com.dbapplication.models.postgre.jsonb.common.CountryPostgreJsonCommon;
import com.dbapplication.models.postgre.traditional.Country;
import com.dbapplication.repositories.postgre.jsonb.common.PostgreJsonCommonCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertCountryPostgreJsonCommonListToCountryList;
import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertCountryPostgreJsonCommonToCountry;

@Service
@Profile({"postgreref", "postgreemb"})
public class PostgreJsonCommonCountryService {

    @Autowired
    private PostgreJsonCommonCountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return convertCountryPostgreJsonCommonListToCountryList(countryRepository.findAll());
    }

    public Country getCountryByCountryCode(String countryCode) {
        CountryPostgreJsonCommon repoCountry = countryRepository.findById(countryCode).orElse(null);
        return repoCountry == null ? null : convertCountryPostgreJsonCommonToCountry(repoCountry);
    }

    public CountryPostgreJsonCommon addCountry(CountryPostgreJsonCommon countryPostgreJsonCommon) {
        if (countryRepository.findById(countryPostgreJsonCommon.getCountry_code()).isPresent()) {
            return null;
        }
        return countryRepository.save(countryPostgreJsonCommon);
    }
}
