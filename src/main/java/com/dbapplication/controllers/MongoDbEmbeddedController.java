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

import static com.dbapplication.utils.UniversalConstants.*;

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
    @GetMapping(COUNTRIES)
    public List<Country> getAlLCountries() {
        return mongoDbCountriesService.getAllCountries();
    }


    @GetMapping(COUNTRIES_COUNTRYCODE)
    public Country getCountryByCountryCode(@PathVariable(value = "countryCode") String countryCode) {
        return mongoDbCountriesService.getCountryByCountryCode(countryCode);
    }

    @PostMapping(COUNTRIES)
    public Country addNewCountry(@RequestBody Country.CountryDto newCountryDto) {
        return mongoDbCountriesService.addCountry(newCountryDto.getCountry());
    }

    //-------occupations--------
    @GetMapping(OCCUPATIONS)
    public List<Occupation> getAllOccupations() {
        return mongoDbOccupationsService.getAllOccupations();
    }

    @GetMapping(OCCUPATIONS_OCCUPATIONCODE)
    public Occupation getOccupationByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbOccupationsService.getOccupationByOccupationCode(occupationCode);
    }

    @PostMapping(OCCUPATIONS)
    public Occupation addNewOccupation(@RequestBody Occupation.OccupationDto occupationDto) {
        return mongoDbOccupationsService.addOccupation(occupationDto.getOccupation());
    }

    //-------employee status types--------
    @GetMapping(EMPLOYEESTATUSTYPES)
    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return mongoDbEmployeeStatusTypeService.getAllEmployeeStatusTypes();
    }

    @GetMapping(EMPLOYEESTATUSTYPES_TYPECODE)
    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(@PathVariable(value = "employeeStatusTypeCode") Integer employeeStatusTypeCode) {
        return mongoDbEmployeeStatusTypeService.getEmployeeStatusTypeByEmployeeStatusTypeCode(employeeStatusTypeCode);
    }

    @PostMapping(EMPLOYEESTATUSTYPES)
    public EmployeeStatusType addNewEmployeeStatusType(@RequestBody EmployeeStatusType.EmployeeStatusTypeDto employeeStatusTypeDto) {
        return mongoDbEmployeeStatusTypeService.addEmployeeStatusType(employeeStatusTypeDto.getEmployeeStatusType());
    }

    //-----------persons---------
    @GetMapping(PERSONS)
    public List<Person> getAllPersons() {
        return mongoDbEmbPersonsService.getAllPersons();
    }

    @GetMapping(PERSONS_OBJECTID)
    public Person getPersonByPerson_id(@PathVariable(value = "objectId") String objectId) {
        return mongoDbEmbPersonsService.getPersonBy_id(objectId);
    }

    @PostMapping(PERSONS)
    public Person addPerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return mongoDbEmbPersonsService.addPerson(personDto.getPerson());
    }

    @PutMapping(PERSONS)
    public boolean updatePerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return mongoDbEmbPersonsService.updatePerson(personDto.getPerson());
    }

    //-----employees-------
    @GetMapping(EMPLOYEES)
    public List<Employee> getAllEmployees() {
        return mongoDbEmbPersonsService.getAllEmployees();
    }

    @GetMapping(EMPLOYEES_PERSONID)
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") String personId) {
        return mongoDbEmbPersonsService.getEmployeeByPersonId(personId);
    }

    @PostMapping(EMPLOYEES)
    public boolean addEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        return mongoDbEmbPersonsService.addEmployee(employeeDto.getEmployee());
    }

    @DeleteMapping(EMPLOYEES_PERSONID)
    public boolean deleteEmployeeByPersonId(@PathVariable(value="personId") String personId) {
        return mongoDbEmbPersonsService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping(EMPLOYEES)
    public boolean updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        return mongoDbEmbPersonsService.updateEmployee(employeeDto.getEmployee());
    }

    //----employments-----
    @GetMapping(EMPLOYMENTS_BY_OCCUPATIONCODE)
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbEmbPersonsService.getAllEmploymentsByOccupationCode(occupationCode);
    }

    @GetMapping(EMPLOYMENTS_BY_EMPLOYEE)
    public List<Employment> getEmployeeAllEmployments(@PathVariable(value = "personId") String personId) {
        return mongoDbEmbPersonsService.getEmployeeAllEmployments(personId);
    }

    @PostMapping(EMPLOYMENTS)
    public boolean addEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return mongoDbEmbPersonsService.addEmployment(employmentDto.getEmployment());
    }

    @PutMapping(EMPLOYMENTS)
    public boolean endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return mongoDbEmbPersonsService.endEmployeeActiveEmployment(employmentDto.getEmployment());
    }

    @PutMapping(EMPLOYMENTS_END_EMPLOYMENTS)
    public boolean endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return mongoDbEmbPersonsService.endEmployeeAllEmployments(employmentDto.getEmployment());
    }
}
