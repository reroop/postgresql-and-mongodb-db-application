package com.dbapplication.controllers;

import com.dbapplication.models.mongo.embedded.EmbeddedEmployee;
import com.dbapplication.models.mongo.embedded.EmbeddedEmployment;
import com.dbapplication.models.mongo.embedded.EmbeddedPerson;
import com.dbapplication.models.mongo.embedded.EmbeddedUserAccount;
import com.dbapplication.models.mongo.shared.Country;
import com.dbapplication.models.mongo.shared.EmployeeStatusType;
import com.dbapplication.models.mongo.shared.Occupation;
import com.dbapplication.services.mongo.embedded.MongoDbEmbPersonsService;
import com.dbapplication.services.mongo.shared.MongoDbCountriesService;
import com.dbapplication.services.mongo.shared.MongoDbEmployeeStatusTypeService;
import com.dbapplication.services.mongo.shared.MongoDbOccupationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Profile("mongoemb")
@RequestMapping("mongoemb")
@RestController
public class MongoDbEmbeddedController {

    @Autowired
    private MongoDbCountriesService mongoDbCountriesService;

    @Autowired
    private MongoDbOccupationsService mongoDbOccupationsService;

    @Autowired
    private MongoDbEmployeeStatusTypeService mongoDbEmployeeStatusTypeService;

    @Autowired
    private MongoDbEmbPersonsService mongoDbEmbPersonsService;

    //----health checker-----
    @GetMapping
    public String index() {
        return "MongoDB embedded controller is running!";
    }
    //-----------------------

    //----countries-----
    @GetMapping("countries")
    public List<Country> getAlLCountries() {
        return mongoDbCountriesService.getAllCountries();
    }


    @GetMapping("countries/{countryCode}")
    public Country getCountryByCountryCode(@PathVariable(value = "countryCode") String countryCode) {
        return mongoDbCountriesService.getCountryByCountryCode(countryCode);
    }

    @PostMapping("countries")
    public Country addNewCountry(@RequestBody Country newCountry) {
        log.info(newCountry.toString());
        return mongoDbCountriesService.addCountry(newCountry);
    }

    @DeleteMapping("countries/{countryCode}")
    public Country deleteCountry(@PathVariable(value = "countryCode") String countryCode) {
        return mongoDbCountriesService.deleteCountry(countryCode);
    }

    @PutMapping("countries")
    public boolean updateCountryName(@RequestBody Country country) {
        return mongoDbCountriesService.updateCountry(country);
    }
    //---------------

    //-------occupations--------
    @GetMapping("occupations")
    public List<Occupation> getAllOccupations() {
        return mongoDbOccupationsService.getAllOccupations();
    }

    @GetMapping("occupations/{occupationCode}")
    public Occupation getOccupationByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbOccupationsService.getOccupationByOccupationCode(occupationCode);
    }

    @PostMapping("occupations")
    public Occupation addNewOccupation(@RequestBody Occupation occupation) {
        log.info(occupation.toString());
        return mongoDbOccupationsService.addOccupation(occupation);
    }

    @DeleteMapping("occupations/{occupationCode}")
    public Occupation deleteOccupation(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbOccupationsService.deleteOccupation(occupationCode);
    }

    @PutMapping("occupations")
    public boolean updateOccupation(@RequestBody Occupation occupation) {
        return mongoDbOccupationsService.updateOccupation(occupation);
    }
    //-------------

    //-------employee status types--------
    @GetMapping("employeeStatusTypes")
    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return mongoDbEmployeeStatusTypeService.getAllEmployeeStatusTypes();
    }

    @GetMapping("employeeStatusTypes/{employeeStatusTypeCode}")
    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(@PathVariable(value = "employeeStatusTypeCode") Integer employeeStatusTypeCode) {
        return mongoDbEmployeeStatusTypeService.getEmployeeStatusTypeByEmployeeStatusTypeCode(employeeStatusTypeCode);
    }

    @PostMapping("employeeStatusTypes")
    public EmployeeStatusType addNewEmployeeStatusType(@RequestBody EmployeeStatusType employeeStatusType) {
        log.info(employeeStatusType.toString());
        return mongoDbEmployeeStatusTypeService.addEmployeeStatusType(employeeStatusType);
    }

    @DeleteMapping("employeeStatusTypes/{employeeStatusTypeCode}")
    public EmployeeStatusType deleteEmployeeStatusType(@PathVariable(value = "employeeStatusTypeCode") Integer employeeStatusTypeCode) {
        return mongoDbEmployeeStatusTypeService.deleteEmployeeStatusType(employeeStatusTypeCode);
    }

    @PutMapping("employeeStatusTypes")
    public boolean updateEmployeeStatusType(@RequestBody EmployeeStatusType employeeStatusType) {
        return mongoDbEmployeeStatusTypeService.updateEmployeeStatusType(employeeStatusType);
    }
    //-------------

    //----------persons-------------------------
    @GetMapping("persons")
    public List<EmbeddedPerson> getAllPersons() {
        return mongoDbEmbPersonsService.getAllPersons();
    }

    @GetMapping("persons/{objectId}")
    public EmbeddedPerson getPersonBy_id(@PathVariable(value = "objectId") String objectId) {
        return mongoDbEmbPersonsService.getPersonBy_id(objectId);
    }

    @PostMapping("persons")
    public EmbeddedPerson addPerson(@RequestBody EmbeddedPerson embeddedPerson) {
        return mongoDbEmbPersonsService.addPerson(embeddedPerson);
    }

    @PostMapping("persons/{objectId}/userAccount")
    public boolean addUserAccountToPerson(
            @PathVariable(value = "objectId") String personId,
            @RequestBody EmbeddedUserAccount embeddedUserAccount) {
        return mongoDbEmbPersonsService.addUserAccountToPerson(personId, embeddedUserAccount);
    }

    @PostMapping("persons/{objectId}/employee")
    public boolean addEmployeeToPerson(
            @PathVariable(value = "objectId") String personId,
            @RequestBody EmbeddedEmployee embeddedEmployee) {
        return mongoDbEmbPersonsService.addEmployeeToPerson(personId, embeddedEmployee);
    }

    @PostMapping("persons/{objectId}/employee/employments")
    public boolean addEmploymentToEmployee(@PathVariable(value = "objectId") String objectId,
                                        @RequestBody EmbeddedEmployment embeddedEmployment) {
       return mongoDbEmbPersonsService.addEmploymentToEmployee(objectId, embeddedEmployment);
    }

    @PutMapping("persons")
    public boolean updatePerson(@RequestBody EmbeddedPerson embeddedPerson) {
        return mongoDbEmbPersonsService.updatePerson(embeddedPerson);
    }

    @PutMapping("persons/{objectId}/userAccount")
    public boolean updatePersonUserAccount(@PathVariable(value = "objectId") String personId, @RequestBody EmbeddedUserAccount embeddedUserAccount) {
        return mongoDbEmbPersonsService.updateUserAccount(personId, embeddedUserAccount);
    }

    @PutMapping("persons/{objectId}/employee")
    public boolean updatePersonEmployee(
            @PathVariable(value = "objectId") String personId,
            @RequestBody EmbeddedEmployee embeddedEmployee) {
        return mongoDbEmbPersonsService.updateEmployee(personId, embeddedEmployee);
    }

    @PutMapping("persons/{objectId}/employee/employments/{occupationCode}")
    public boolean endActiveEmploymentForEmployeeInOccupation(@PathVariable(value = "objectId") String personId,
                                                              @PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbEmbPersonsService.endActiveEmployment(personId, occupationCode);
    }


    //---------------------------------------
}
