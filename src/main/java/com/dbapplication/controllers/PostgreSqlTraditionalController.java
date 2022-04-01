package com.dbapplication.controllers;


import com.dbapplication.models.postgre.*;
import com.dbapplication.services.postgre.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("postgretrad")
@RestController
public class PostgreSqlTraditionalController {

    @Autowired
    private PostgreTradCountryService postgreTradCountryService;

    @Autowired
    private PostgreTradOccupationService postgreTradOccupationService;

    @Autowired
    private PostgreTradEmployeeStatusTypeService postgreTradEmployeeStatusTypeService;

    @Autowired
    private PostgreTradPersonService postgreTradPersonService;

    @Autowired
    private PostgreTradEmployeeService postgreTradEmployeeService;

    @Autowired
    private PostgreTradEmploymentService postgreTradEmploymentService;

    //---health checker----
    @GetMapping
    public String index() {
        return "Postgre traditional controller is running!";
    }
    //---------------------

    //--countries---
    @GetMapping("countries")
    public List<Country> getAllCountries() {
        return postgreTradCountryService.getAllCountries();
    }

    @GetMapping("countries/{countryCode}")
    public Country getCountryByCountryCode(@PathVariable(value = "countryCode") String countryCode) {
        return postgreTradCountryService.getCountryByCountryCode(countryCode);
    }

    @PostMapping("countries")
    public Country addNewCountry(@RequestBody Country.CountryDto countryDto) {
        return postgreTradCountryService.addCountry(countryDto.getCountry());
    }


    //---occupations---
    @GetMapping("occupations")
    public List<Occupation> getAllOccupations() {
        return postgreTradOccupationService.getAllOccupations();
    }

    @GetMapping("occupations/{occupationCode}")
    public Occupation getOccupationByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreTradOccupationService.getOccupationByOccupationCode(occupationCode);
    }

    @PostMapping("occupations")
    public Occupation addNewOccupation(@RequestBody Occupation.OccupationDto occupationDto) {
        return postgreTradOccupationService.addOccupation(occupationDto.getOccupation());
    }


    //-----employee status types-----
    @GetMapping("employeeStatusTypes")
    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return postgreTradEmployeeStatusTypeService.getAllEmployeeStatusTypes();
    }

    @GetMapping("employeeStatusTypes/{employeeStatusTypeCode}")
    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(@PathVariable(value = "employeeStatusTypeCode") Integer employeeStatusTypeCode) {
        return postgreTradEmployeeStatusTypeService.getEmployeeStatusTypeByEmployeeStatusTypeCode(employeeStatusTypeCode);
    }

    @PostMapping("employeeStatusTypes")
    public EmployeeStatusType addNewEmployeeStatusType(@RequestBody EmployeeStatusType.EmployeeStatusTypeDto employeeStatusTypeDto) {
        return postgreTradEmployeeStatusTypeService.addEmployeeStatusType(employeeStatusTypeDto.getEmployeeStatusType());
    }

    //----persons-----
    @GetMapping("persons")
    public List<Person> getAllPersons() {
        return postgreTradPersonService.getAllPersons();
    }

    @GetMapping("persons/{_id}")
    public Person getPersonByPerson_id(@PathVariable(value = "_id") Long _id) {
        return postgreTradPersonService.getPersonBy_id(_id);
    }

    @PostMapping("persons")
    public Person addPerson(@RequestBody Person.PersonDto personDto) {
        return postgreTradPersonService.addPerson(personDto.getPerson());
    }

    @PutMapping("persons")
    public Person updatePerson(@RequestBody Person.PersonDto personDto) {
        return postgreTradPersonService.updatePerson(personDto.getPerson());
    }

    //----employees---
    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        return postgreTradEmployeeService.getAllEmployees();
    }

    @GetMapping("employees/{personId}")
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") Long personId) {
        return postgreTradEmployeeService.getEmployeeByPersonId(personId);
    }

    @PostMapping("employees")
    public Employee addEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        return postgreTradEmployeeService.addEmployee(employeeDto.getEmployee());
    }

    @DeleteMapping("employees/{personId}")
    public void deleteEmployeeByPersonId(@PathVariable(value="personId") Long personId) {
        postgreTradEmployeeService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping("employees")
    public Employee updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        return postgreTradEmployeeService.updateEmployee(employeeDto.getEmployee());
    }

    //----employments-----

    @GetMapping("employments/occupationCode={occupationCode}")
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreTradEmploymentService.getAllEmploymentsByOccupationCode(occupationCode);
    }


    @GetMapping("employments/personId={personId}")
    public List<Employment.FrontEmployment> getEmployeeAllEmployments(@PathVariable(value = "personId") Long personId) {
        return postgreTradEmploymentService.getEmployeeAllEmployments(personId);
    }

    @PostMapping("employments")
    public Employment addEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        //System.out.println(employmentDto);
        //System.out.println("created postgreemployment");
        //System.out.println(employmentDto.createPostgreEmployment());
        return postgreTradEmploymentService.addEmployment(employmentDto.createPostgreEmployment());
    }

    @PutMapping("employments")
    public Employment endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        System.out.println(employmentDto);
        return postgreTradEmploymentService.endEmployeeActiveEmployment(employmentDto.createPostgreEmployment());
    }

    @PutMapping("employments/endEmployments")
    public List<Employment> endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) {
        return postgreTradEmploymentService.endEmployeeAllEmployments(employmentDto.createPostgreEmployment());
    }

}
