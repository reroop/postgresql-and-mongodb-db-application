package com.dbapplication.controllers;

import com.dbapplication.models.mongo.reference.Employee;
import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.models.mongo.shared.Country;
import com.dbapplication.models.mongo.shared.EmployeeStatusType;
import com.dbapplication.models.mongo.shared.Occupation;
import com.dbapplication.services.mongo.reference.MongoDbRefEmployeesService;
import com.dbapplication.services.mongo.reference.MongoDbRefEmploymentsService;
import com.dbapplication.services.mongo.reference.MongoDbRefPersonsService;
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

    //-----------persons---------
    @GetMapping("persons")
    public List<Person> getAllPersons() {
        return mongoDbRefPersonsService.getAllPersons();
    }

    @GetMapping("persons/{objectId}")
    public Person getPersonByPerson_id(@PathVariable(value = "objectId") String objectId) {
        return mongoDbRefPersonsService.getPersonBy_id(objectId);
    }

    @PostMapping("persons")
    public Person addPerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return mongoDbRefPersonsService.addPerson(personDto.getPerson());
    }

    @PutMapping("persons")
    public boolean updatePerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return mongoDbRefPersonsService.updatePerson(personDto.getPerson());
    }


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
    public Employee.EmployeeDbEntry addEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        return mongoDbRefEmployeesService.addEmployee(employeeDto.getEmployee());
    }

    @DeleteMapping("employees/{personId}")
    public Employee deleteEmployeeByPersonId(@PathVariable(value="personId") String personId) throws Throwable {
        List<Employment> employments = this.getEmployeeAllEmployments(personId);
        for (Employment employment: employments) {
            if (employment.getEnd_time() == null) {
                throw new Exception(new Throwable("employee has active employments, end those before deleting employee!"));
            }
        }
        mongoDbRefEmploymentsService.deleteAllEmployeeEmployments(personId);
        return mongoDbRefEmployeesService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping("employees")
    public boolean updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        return mongoDbRefEmployeesService.updateEmployee(employeeDto.getEmployee());
    }
    //--------------------

    @GetMapping("employments/occupationCode={occupationCode}")
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbRefEmploymentsService.getAllEmploymentsByOccupationCode(occupationCode);
    }

    @GetMapping("employments/personId={personId}")
    public List<Employment> getEmployeeAllEmployments(@PathVariable(value = "personId") String personId) {
        return mongoDbRefEmploymentsService.getEmployeeAllEmployments(personId);
    }

    @PostMapping("employments")
    public Employment.EmploymentDbEntry addEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return mongoDbRefEmploymentsService.addEmployment(employmentDto.getEmployment());
    }

    @PutMapping("employments")
    public boolean endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return mongoDbRefEmploymentsService.endEmployeeActiveEmployment(employmentDto.getEmployment());
    }

    @PutMapping("employments/endEmployments")
    public boolean endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return mongoDbRefEmploymentsService.endEmployeeAllEmployments(employmentDto.getEmployment());
    }

    //--------------------
}
