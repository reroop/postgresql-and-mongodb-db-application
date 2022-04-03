package com.dbapplication.controllers;

import com.dbapplication.models.postgre.jsonb.common.CountryPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.EmployeeStatusTypePostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.OccupationPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.ref.*;
import com.dbapplication.models.postgre.traditional.*;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonCountryService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonEmployeeStatusTypeService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonOccupationService;
import com.dbapplication.services.postgre.jsonb.ref.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.dbapplication.utils.postgre.PostgreObjectConverter.*;

@Profile("postgreref")
@RestController
public class PostgreSqlReferenceController {

    @Autowired
    private PostgreJsonCommonCountryService postgreJsonCommonCountryService;

    @Autowired
    private PostgreJsonCommonOccupationService postgreJsonCommonOccupationService;

    @Autowired
    private PostgreJsonCommonEmployeeStatusTypeService postgreJsonCommonEmployeeStatusTypeService;

    @Autowired
    private PostgreRefPersonService postgreRefPersonService;

    @Autowired
    private PostgreRefEmployeeService postgreRefEmployeeService;

    @Autowired
    private PostgreRefEmploymentService postgreRefEmploymentService;

    //---health checker----
    @GetMapping
    public String index() {
        return "Postgre reference controller is running!";
    }
    //---------------------


    //--countries---
    @GetMapping("countries")
    public List<Country> getAllCountries() {
        return postgreJsonCommonCountryService.getAllCountries();
    }

    @GetMapping("countries/{countryCode}")
    public Country getCountryByCountryCode(@PathVariable(value = "countryCode") String countryCode) {
        return postgreJsonCommonCountryService.getCountryByCountryCode(countryCode);
    }

    @PostMapping("countries")
    public CountryPostgreJsonCommon addNewCountry(@RequestBody Country.CountryDto countryDto) {
        Country country = countryDto.getCountry();
        return postgreJsonCommonCountryService.addCountry(convertCountryToCountryPostgreJsonCommon(country));
    }

    //---occupations---
    @GetMapping("occupations")
    public List<Occupation> getAllOccupations() {
        return postgreJsonCommonOccupationService.getAllOccupations();
    }

    @GetMapping("occupations/{occupationCode}")
    public Occupation getOccupationByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreJsonCommonOccupationService.getOccupationByOccupationCode(occupationCode);
    }

    @PostMapping("occupations")
    public OccupationPostgreJsonCommon addNewOccupation(@RequestBody Occupation.OccupationDto occupationDto) {
        Occupation newOccupation = occupationDto.getOccupation();
        return postgreJsonCommonOccupationService.addOccupation(convertOccupationToOccupationPostgreJsonCommon(newOccupation));
    }

    //-----employee status types-----
    @GetMapping("employeeStatusTypes")
    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return postgreJsonCommonEmployeeStatusTypeService.getAllEmployeeStatusTypes();
    }

    @GetMapping("employeeStatusTypes/{employeeStatusTypeCode}")
    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(@PathVariable(value = "employeeStatusTypeCode") Integer employeeStatusTypeCode) {
        return postgreJsonCommonEmployeeStatusTypeService.getEmployeeStatusTypeByEmployeeStatusTypeCode(employeeStatusTypeCode);
    }

    @PostMapping("employeeStatusTypes")
    public EmployeeStatusTypePostgreJsonCommon addNewEmployeeStatusType(@RequestBody EmployeeStatusType.EmployeeStatusTypeDto employeeStatusTypeDto) {
        EmployeeStatusType employeeStatusType = employeeStatusTypeDto.getEmployeeStatusType();
        return postgreJsonCommonEmployeeStatusTypeService.addEmployeeStatusType(convertEmployeeStatusTypeToEmployeeStatusTypePostgreJsonCommon(employeeStatusType));
    }

    //----persons-----
    @GetMapping("persons")
    public List<Person> getAllPersons() {
        return postgreRefPersonService.getAllPersons();
    }

    @GetMapping("persons/{_id}")
    public Person getPersonByPerson_id(@PathVariable(value = "_id") Long _id) {
        return postgreRefPersonService.getPersonBy_id(_id);
    }

    @PostMapping("persons")
    public PersonRef addPerson(@RequestBody Person.PersonDto personDto) {
        return postgreRefPersonService.addPerson(convertPersonToPersonRef(personDto.getPerson()));
    }

    @PutMapping("persons")
    public PersonRef updatePerson(@RequestBody Person.PersonDto personDto) {
        return postgreRefPersonService.updatePerson(convertPersonToPersonRef(personDto.getPerson()));
    }

    //----employees---
    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        return postgreRefEmployeeService.getAllEmployees();
    }

    @GetMapping("employees/{personId}")
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") Long personId) {
        return postgreRefEmployeeService.getEmployeeByPersonId(personId);
    }

    @PostMapping("employees")
    public EmployeeRef addEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        Employee employee = employeeDto.getEmployee();
        return postgreRefEmployeeService.addEmployee(employee);
    }

    @DeleteMapping("employees/{personId}")
    public void deleteEmployeeByPersonId(@PathVariable(value="personId") Long personId) {
        postgreRefEmployeeService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping("employees")
    public EmployeeRef updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        Employee employee = employeeDto.getEmployee();
        return postgreRefEmployeeService.updateEmployee(employee);
    }

    //----employments-----

    @GetMapping("employments/occupationCode={occupationCode}")
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreRefEmploymentService.getAllEmploymentsByOccupationCode(occupationCode);
    }


    @GetMapping("employments/personId={personId}")
    public List<Employment.FrontEmployment> getEmployeeAllEmployments(@PathVariable(value = "personId") Long personId) {
        return postgreRefEmploymentService.getEmployeeAllEmployments(personId);
    }

    @PostMapping("employments")
    public EmploymentRef addEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        return postgreRefEmploymentService.addEmployment(employmentDto.createPostgreRefEmployment());
    }

    @PutMapping("employments")
    public EmploymentRef endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        return postgreRefEmploymentService.endEmployeeActiveEmployment(employmentDto.createPostgreRefEmployment());
    }

    @PutMapping("employments/endEmployments")
    public List<EmploymentRef> endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) {
        return postgreRefEmploymentService.endEmployeeAllEmployments(employmentDto.createPostgreRefEmployment());
    }

}
