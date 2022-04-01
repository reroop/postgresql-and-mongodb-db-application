package com.dbapplication.controllers;

import com.dbapplication.models.mongo.reference.Employee;
import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.models.mongo.reference.Person;
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


    //---------------------------------------

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
    public Country addNewCountry(@RequestBody Country.CountryDto newCountryDto) {
        return mongoDbCountriesService.addCountry(newCountryDto.getCountry());
    }

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
    public Occupation addNewOccupation(@RequestBody Occupation.OccupationDto occupationDto) {
        return mongoDbOccupationsService.addOccupation(occupationDto.getOccupation());
    }

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
    public EmployeeStatusType addNewEmployeeStatusType(@RequestBody EmployeeStatusType.EmployeeStatusTypeDto employeeStatusTypeDto) {
        return mongoDbEmployeeStatusTypeService.addEmployeeStatusType(employeeStatusTypeDto.getEmployeeStatusType());
    }

    //-----------persons---------
    @GetMapping("persons")
    public List<Person> getAllPersons() {
        return mongoDbEmbPersonsService.getAllPersons();
    }

    @GetMapping("persons/{objectId}")
    public Person getPersonByPerson_id(@PathVariable(value = "objectId") String objectId) {
        return mongoDbEmbPersonsService.getPersonBy_id(objectId);
    }

    @PostMapping("persons")
    public Person addPerson(@RequestBody Person.PersonDto personDto) {
        return mongoDbEmbPersonsService.addPerson(personDto.getPerson());
    }

    @PutMapping("persons")
    public boolean updatePerson(@RequestBody Person.PersonDto personDto) {
        return mongoDbEmbPersonsService.updatePerson(personDto.getPerson());
    }

    //-----employees-------
    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        return mongoDbEmbPersonsService.getAllEmployees();
    }

    @GetMapping("employees/{personId}")
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") String personId) {
        return mongoDbEmbPersonsService.getEmployeeByPersonId(personId);
    }

    @PostMapping("employees")
    public boolean addEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        return mongoDbEmbPersonsService.addEmployee(employeeDto.getEmployee());
    }

    @DeleteMapping("employees/{personId}")
    public boolean deleteEmployeeByPersonId(@PathVariable(value="personId") String personId) {
        return mongoDbEmbPersonsService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping("employees")
    public boolean updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        return mongoDbEmbPersonsService.updateEmployee(employeeDto.getEmployee());
    }

    //----employments-----
    @GetMapping("employments/occupationCode={occupationCode}")
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbEmbPersonsService.getAllEmploymentsByOccupationCode(occupationCode);
    }

    @GetMapping("employments/personId={personId}")
    public List<Employment> getEmployeeAllEmployments(@PathVariable(value = "personId") String personId) {
        return mongoDbEmbPersonsService.getEmployeeAllEmployments(personId);
    }

    @PostMapping("employments")
    public boolean addEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        return mongoDbEmbPersonsService.addEmployment(employmentDto.getEmployment());
    }

    @PutMapping("employments")
    public boolean endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        return mongoDbEmbPersonsService.endEmployeeActiveEmployment(employmentDto.getEmployment());
    }

    @PutMapping("employments/endEmployments")
    public boolean endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) {
        return mongoDbEmbPersonsService.endEmployeeAllEmployments(employmentDto.getEmployment());
    }
}
