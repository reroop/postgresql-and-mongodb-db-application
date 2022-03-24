package com.dbapplication.controllers;

import com.dbapplication.models.mongo.reference.Employee;
import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.models.mongo.reference.UserAccount;
import com.dbapplication.models.mongo.shared.Country;
import com.dbapplication.models.mongo.shared.EmployeeStatusType;
import com.dbapplication.models.mongo.shared.Occupation;
import com.dbapplication.services.mongo.reference.MongoDbRefEmployeesService;
import com.dbapplication.services.mongo.reference.MongoDbRefEmploymentsService;
import com.dbapplication.services.mongo.reference.MongoDbRefPersonsService;
import com.dbapplication.services.mongo.reference.MongoDbRefUserAccountsService;
import com.dbapplication.services.mongo.shared.MongoDbCountriesService;
import com.dbapplication.services.mongo.shared.MongoDbEmployeeStatusTypeService;
import com.dbapplication.services.mongo.shared.MongoDbOccupationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Profile("mongoref")
@RequestMapping("mongoref")
@RestController
public class MongoDbReferenceController {

    @Autowired
    private MongoDbCountriesService mongoDbCountriesService;

    @Autowired
    private MongoDbOccupationsService mongoDbOccupationsService;

    @Autowired
    private MongoDbEmployeeStatusTypeService mongoDbEmployeeStatusTypeService;

    @Autowired
    private MongoDbRefPersonsService mongoDbRefPersonsService;

    @Autowired
    private MongoDbRefUserAccountsService mongoDbRefUserAccountsService;

    @Autowired
    private MongoDbRefEmployeesService mongoDbRefEmployeesService;

    @Autowired
    private MongoDbRefEmploymentsService mongoDbRefEmploymentsService;

    //---health checker----
    @GetMapping
    public String index() {
        return "MongoDB reference controller is running!";
    }
    //---------------------

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

    @DeleteMapping("countries/{countryCode}")
    public Country deleteCountry(@PathVariable(value = "countryCode") String countryCode) {
        return mongoDbCountriesService.deleteCountry(countryCode);
    }

    @PutMapping("countries")
    public boolean updateCountryName(@RequestBody Country.CountryDto countryDto) {
        return mongoDbCountriesService.updateCountry(countryDto.getCountry());
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
    public Occupation addNewOccupation(@RequestBody Occupation.OccupationDto occupationDto) {
        return mongoDbOccupationsService.addOccupation(occupationDto.getOccupation());
    }

    @DeleteMapping("occupations/{occupationCode}")
    public Occupation deleteOccupation(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbOccupationsService.deleteOccupation(occupationCode);
    }

    @PutMapping("occupations")
    public boolean updateOccupation(@RequestBody Occupation.OccupationDto occupationDto) {
        System.out.println(occupationDto.getOccupation());
        return mongoDbOccupationsService.updateOccupation(occupationDto.getOccupation());
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
    public EmployeeStatusType addNewEmployeeStatusType(@RequestBody EmployeeStatusType.EmployeeStatusTypeDto employeeStatusTypeDto) {
        return mongoDbEmployeeStatusTypeService.addEmployeeStatusType(employeeStatusTypeDto.getEmployeeStatusType());
    }

    @DeleteMapping("employeeStatusTypes/{employeeStatusTypeCode}")
    public EmployeeStatusType deleteEmployeeStatusType(@PathVariable(value = "employeeStatusTypeCode") Integer employeeStatusTypeCode) {
        return mongoDbEmployeeStatusTypeService.deleteEmployeeStatusType(employeeStatusTypeCode);
    }

    @PutMapping("employeeStatusTypes")
    public boolean updateEmployeeStatusType(@RequestBody EmployeeStatusType.EmployeeStatusTypeDto employeeStatusTypeDto) {
        return mongoDbEmployeeStatusTypeService.updateEmployeeStatusType(employeeStatusTypeDto.getEmployeeStatusType());
    }
    //-------------

    //-----------persons---------
    @GetMapping("persons")
    public List<Person> getAllPersons() {
        return mongoDbRefPersonsService.getAllPersons();
    }

    @GetMapping("persons/{objectId}")
    public Person getPersonByPerson_id(@PathVariable(value = "objectId") String objectId) {
        return mongoDbRefPersonsService.getPersonBy_id(objectId);
    }

    @GetMapping("persons/{countryCode}/{personalIdCode}")
    public Person getPersonByCountryCodeAndPersonalIdentificationCode(
            @PathVariable(value = "countryCode") String countryCode,
            @PathVariable(value = "personalIdCode") String personalIdCode) {
        return mongoDbRefPersonsService.getPersonByCountryCodeAndPersonalIdCode(countryCode, personalIdCode);
    }

    @PostMapping("persons")
    public Person addPerson(@RequestBody Person.PersonDto personDto) {
        return mongoDbRefPersonsService.addPerson(personDto.getPerson());
    }

    @DeleteMapping("persons/{objectId}")
    public Person deletePersonBy_id(@PathVariable(value = "objectId") String objectId) {
        return mongoDbRefPersonsService.deletePersonBy_id(objectId);
    }

    @PutMapping("persons")
    public boolean updatePerson(@RequestBody Person.PersonDto personDto) {
        return mongoDbRefPersonsService.updatePerson(personDto.getPerson());
    }

    //---------------

    //-----user accounts--------
    @GetMapping("userAccounts")
    public List<UserAccount> getAllUserAccounts() {
        return mongoDbRefUserAccountsService.getAllUserAccounts();
    }

    @GetMapping("userAccounts/{personId}")
    public UserAccount getUserAccountByPersonId(@PathVariable(value="personId") String personId) {
        return mongoDbRefUserAccountsService.getUserAccountByPersonId(personId);
    }

    @PostMapping("userAccounts")
    public UserAccount.UserAccountDbEntry addUserAccount(@RequestBody UserAccount userAccount) {
        return mongoDbRefUserAccountsService.addUserAccount(userAccount);
    }

    @DeleteMapping("userAccounts/{personId}")
    public UserAccount deleteUserAccountByPersonId(@PathVariable(value = "personId") String personId) {
        return mongoDbRefUserAccountsService.deleteUserAccountByPersonId(personId);
    }

    @PutMapping("userAccounts")
    public boolean updateUserAccount(@RequestBody UserAccount userAccount) {
        return mongoDbRefUserAccountsService.updateUserAccount(userAccount);
    }

    //---------------------------

    //-----employees-------
    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        return mongoDbRefEmployeesService.getAllEmployees();
    }

    @GetMapping("employees/{personId}")
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") String personId) {
        return mongoDbRefEmployeesService.getEmployeeByPersonId(personId);
    }

    @PostMapping("employees")
    public Employee.EmployeeDbEntry addEmployee(@RequestBody Employee employee) {
        return mongoDbRefEmployeesService.addEmployee(employee);
    }

    @DeleteMapping("employees/{personId}")
    public Employee deleteEmployeeByPersonId(@PathVariable(value="personId") String personId) {
        return mongoDbRefEmployeesService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping("employees")
    public boolean updateEmployee(@RequestBody Employee employee) {
        return mongoDbRefEmployeesService.updateEmployee(employee);
    }
    //--------------------

    //----employments-----
    @GetMapping("employments")
    public List<Employment> getAllEmployments() {
        return mongoDbRefEmploymentsService.getAllEmployments();
    }

    @GetMapping("employments/occupationCode={occupationCode}")
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbRefEmploymentsService.getAllEmploymentsByOccupationCode(occupationCode);
    }

    @GetMapping("employments/personId={personId}")
    public List<Employment> getEmployeeActiveEmployments(@PathVariable(value = "personId") String personId) {
        return mongoDbRefEmploymentsService.getEmployeeActiveEmployments(personId);
    }

    @PostMapping("employments")
    public Employment.EmploymentDbEntry addEmployment(@RequestBody Employment employment) {
        return mongoDbRefEmploymentsService.addEmployment(employment);
    }

    @PutMapping("employments/personId={personId}/occupationCode={occupationCode}")
    public boolean endEmployeeActiveEmployment(
            @PathVariable(value = "personId") String personId,
            @PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbRefEmploymentsService.endEmployeeActiveEmployment(personId, occupationCode);
    }

    //--------------------
}
