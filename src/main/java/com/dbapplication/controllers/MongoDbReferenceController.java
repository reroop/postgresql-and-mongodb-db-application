package com.dbapplication.controllers;

import com.dbapplication.models.mongo.Country;
import com.dbapplication.services.mongo.MongoDbCountriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("mongoref")
@RestController
public class MongoDbReferenceController {

    @Autowired
    private MongoDbCountriesService countriesService;

    @GetMapping
    public String index() {
        return "MongoDB reference controller is running!";
    }

    @GetMapping("countries")
    public List<Country> getAlLCountries() {
        return countriesService.getAllCountries();
    }


    @GetMapping("countries/{countryCode}")
    public Country getCountryByCountryCode(@PathVariable(value="countryCode") String countryCode) {
        return countriesService.getCountryByCountryCode(countryCode);
    }

    @PostMapping("countries")
    public Country addNewCountry(@RequestBody Country newCountry) {
        log.info(newCountry.toString());
        return countriesService.addCountry(newCountry);
    }

    @DeleteMapping("countries/{countryCode}")
    public Country deleteCountry(@PathVariable(value="countryCode") String countryCode) {
        return countriesService.deleteCountry(countryCode);
    }

    @PutMapping("countries")
    public boolean updateCountryName(@RequestBody Country country) {
        return countriesService.updateCountryName(country.getRiik_kood(), country.getNimetus());
    }
}
