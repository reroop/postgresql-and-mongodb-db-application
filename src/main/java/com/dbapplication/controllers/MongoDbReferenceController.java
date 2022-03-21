package com.dbapplication.controllers;

import com.dbapplication.models.mongo.Country;
import com.dbapplication.services.mongo.MongoDbCountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public String getAlLCountries() {
        return "getcountries";
        //return countriesService.getAllCountries();
    }


    @GetMapping("countries/{countryCode}")
    public Country getCountryByCountryCode(@PathVariable(value="countryCode") String countryCode) {
        //return countriesService.getCountryByCountryCode(countryCode);
        return countriesService.getCountryByCountryCode(countryCode);
    }
}
