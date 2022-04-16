package com.dbapplication.controllers;


import com.dbapplication.models.postgre.traditional.*;
import com.dbapplication.services.postgre.traditional.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dbapplication.utils.UniversalConstants.*;

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
    @GetMapping(COUNTRIES)
    public List<Country> getAllCountries() {
        return postgreTradCountryService.getAllCountries();
    }

    @GetMapping(COUNTRIES_COUNTRYCODE)
    public Country getCountryByCountryCode(@PathVariable(value = "countryCode") String countryCode) {
        return postgreTradCountryService.getCountryByCountryCode(countryCode);
    }

    @PostMapping(COUNTRIES)
    public Country addNewCountry(@RequestBody Country.CountryDto countryDto) throws Throwable {
        return postgreTradCountryService.addCountry(countryDto.getCountry());
    }


    //---occupations---
    @GetMapping(OCCUPATIONS)
    public List<Occupation> getAllOccupations() {
        return postgreTradOccupationService.getAllOccupations();
    }

    @GetMapping(OCCUPATIONS_OCCUPATIONCODE)
    public Occupation getOccupationByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreTradOccupationService.getOccupationByOccupationCode(occupationCode);
    }

    @PostMapping(OCCUPATIONS)
    public Occupation addNewOccupation(@RequestBody Occupation.OccupationDto occupationDto) throws Throwable {
        return postgreTradOccupationService.addOccupation(occupationDto.getOccupation());
    }


    //-----employee status types-----
    @GetMapping(EMPLOYEESTATUSTYPES)
    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return postgreTradEmployeeStatusTypeService.getAllEmployeeStatusTypes();
    }

    @GetMapping(EMPLOYEESTATUSTYPES_TYPECODE)
    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(@PathVariable(value = "employeeStatusTypeCode") Integer employeeStatusTypeCode) {
        return postgreTradEmployeeStatusTypeService.getEmployeeStatusTypeByEmployeeStatusTypeCode(employeeStatusTypeCode);
    }

    @PostMapping(EMPLOYEESTATUSTYPES)
    public EmployeeStatusType addNewEmployeeStatusType(@RequestBody EmployeeStatusType.EmployeeStatusTypeDto employeeStatusTypeDto) throws Throwable {
        return postgreTradEmployeeStatusTypeService.addEmployeeStatusType(employeeStatusTypeDto.getEmployeeStatusType());
    }

    //----persons-----
    @GetMapping(PERSONS)
    public List<Person> getAllPersons() {
        return postgreTradPersonService.getAllPersons();
    }

    @GetMapping(PERSONS_ID)
    public Person getPersonByPerson_id(@PathVariable(value = "_id") Long _id) {
        return postgreTradPersonService.getPersonBy_id(_id);
    }

    @PostMapping(PERSONS)
    public Person addPerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return postgreTradPersonService.addPerson(personDto.getPerson());
    }

    @PutMapping(PERSONS)
    public Person updatePerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return postgreTradPersonService.updatePerson(personDto.getPerson());
    }

    //----employees---
    @GetMapping(EMPLOYEES)
    public List<Employee> getAllEmployees() {
        return postgreTradEmployeeService.getAllEmployees();
    }

    @GetMapping(EMPLOYEES_PERSONID)
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") Long personId) {
        return postgreTradEmployeeService.getEmployeeByPersonId(personId);
    }

    @PostMapping(EMPLOYEES)
    public Employee addEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        return postgreTradEmployeeService.addEmployee(employeeDto.getEmployee());
    }

    @DeleteMapping(EMPLOYEES_PERSONID)
    public void deleteEmployeeByPersonId(@PathVariable(value="personId") Long personId) throws Throwable {
        List<Employment.FrontEmployment> employeeEmployments = postgreTradEmploymentService.getEmployeeAllEmployments(personId);
        for (Employment.FrontEmployment employment : employeeEmployments) {
            if (employment.getEnd_time() == null) {
                throw new Exception(new Throwable("employee's employment with occupation code " + employment.getOccupation_code() + " is not ended, set an end time before deleting employee!"));
            }
        }
        postgreTradEmployeeService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping(EMPLOYEES)
    public Employee updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        return postgreTradEmployeeService.updateEmployee(employeeDto.getEmployee());
    }

    //----employments-----

    @GetMapping(EMPLOYMENTS_BY_OCCUPATIONCODE)
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreTradEmploymentService.getAllEmploymentsByOccupationCode(occupationCode);
    }


    @GetMapping(EMPLOYMENTS_BY_EMPLOYEE)
    public List<Employment.FrontEmployment> getEmployeeAllEmployments(@PathVariable(value = "personId") Long personId) {
        return postgreTradEmploymentService.getEmployeeAllEmployments(personId);
    }

    @PostMapping(EMPLOYMENTS)
    public Employment addEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        Employee employee = postgreTradEmployeeService.getEmployeeByPersonId(Long.valueOf(employmentDto.getEmployment().getPerson_id()));
        if (employee.getEmployee_status_type_code() == EMPLOYEE_STATUS_HAS_FINISHED_WORKING) {
            throw new Exception(new Throwable(ADD_EMPLOYMENT_WRONG_EMPLOYEE_STATUS));
        }
        return postgreTradEmploymentService.addEmployment(employmentDto.createPostgreEmployment());
    }

    @PutMapping(EMPLOYMENTS)
    public Employment endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return postgreTradEmploymentService.endEmployeeActiveEmployment(employmentDto.createPostgreEmployment());
    }

    @PutMapping(EMPLOYMENTS_END_EMPLOYMENTS)
    public List<Employment> endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        List<Employment> endedEmployments = postgreTradEmploymentService.endEmployeeAllEmployments(employmentDto.createPostgreEmployment());
        Employee employee = postgreTradEmployeeService.getEmployeeByPersonId(Long.valueOf(employmentDto.getEmployment().getPerson_id()));
        employee.setEmployee_status_type_code(EMPLOYEE_STATUS_HAS_FINISHED_WORKING);
        postgreTradEmployeeService.updateEmployee(employee);
        return endedEmployments;
    }

}
