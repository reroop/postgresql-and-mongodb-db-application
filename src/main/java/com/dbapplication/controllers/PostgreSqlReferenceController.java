package com.dbapplication.controllers;

import com.dbapplication.models.postgre.jsonb.common.CountryPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.EmployeeStatusTypePostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.OccupationPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.PersonStatusTypePostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.ref.*;
import com.dbapplication.models.postgre.traditional.*;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonCountryService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonEmployeeStatusTypeService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonOccupationService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonPersonStatusTypeService;
import com.dbapplication.services.postgre.jsonb.ref.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dbapplication.utils.UniversalConstants.*;
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

    @Autowired
    private PostgreJsonCommonPersonStatusTypeService postgreJsonCommonPersonStatusTypeService;

    //---health checker----
    @GetMapping
    public String index() {
        return "Postgre reference controller is running!";
    }
    //---------------------


    //--countries---
    @GetMapping(COUNTRIES)
    public List<Country> getAllCountries() {
        return postgreJsonCommonCountryService.getAllCountries();
    }

    @GetMapping(COUNTRIES_COUNTRYCODE)
    public Country getCountryByCountryCode(@PathVariable(value = "countryCode") String countryCode) {
        return postgreJsonCommonCountryService.getCountryByCountryCode(countryCode);
    }

    @PostMapping(COUNTRIES)
    public CountryPostgreJsonCommon addNewCountry(@RequestBody Country.CountryDto countryDto) throws Throwable {
        Country country = countryDto.getCountry();
        return postgreJsonCommonCountryService.addCountry(convertCountryToCountryPostgreJsonCommon(country));
    }

    //---occupations---
    @GetMapping(OCCUPATIONS)
    public List<Occupation> getAllOccupations() {
        return postgreJsonCommonOccupationService.getAllOccupations();
    }

    @GetMapping(OCCUPATIONS_OCCUPATIONCODE)
    public Occupation getOccupationByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreJsonCommonOccupationService.getOccupationByOccupationCode(occupationCode);
    }

    @PostMapping(OCCUPATIONS)
    public OccupationPostgreJsonCommon addNewOccupation(@RequestBody Occupation.OccupationDto occupationDto) throws Throwable {
        Occupation newOccupation = occupationDto.getOccupation();
        return postgreJsonCommonOccupationService.addOccupation(convertOccupationToOccupationPostgreJsonCommon(newOccupation));
    }

    //-----employee status types-----
    @GetMapping(EMPLOYEESTATUSTYPES)
    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return postgreJsonCommonEmployeeStatusTypeService.getAllEmployeeStatusTypes();
    }

    @GetMapping(EMPLOYEESTATUSTYPES_TYPECODE)
    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(@PathVariable(value = "employeeStatusTypeCode") Integer employeeStatusTypeCode) {
        return postgreJsonCommonEmployeeStatusTypeService.getEmployeeStatusTypeByEmployeeStatusTypeCode(employeeStatusTypeCode);
    }

    @PostMapping(EMPLOYEESTATUSTYPES)
    public EmployeeStatusTypePostgreJsonCommon addNewEmployeeStatusType(@RequestBody EmployeeStatusType.EmployeeStatusTypeDto employeeStatusTypeDto) throws Throwable {
        EmployeeStatusType employeeStatusType = employeeStatusTypeDto.getEmployeeStatusType();
        return postgreJsonCommonEmployeeStatusTypeService.addEmployeeStatusType(convertEmployeeStatusTypeToEmployeeStatusTypePostgreJsonCommon(employeeStatusType));
    }

    //---------person status types-------
    @GetMapping(PERSONSTATUSTYPES)
    public List<PersonStatusType> getAllPersonStatusTypes() {
        return postgreJsonCommonPersonStatusTypeService.getAllPersonStatusTypes();
    }

    @GetMapping(PERSONSTATUSTYPES_TYPECODE)
    public PersonStatusType getPersonStatusTypeByPersonStatusTypeCode(@PathVariable(value= "personStatusTypeCode") Integer personStatusTypeCode) {
        return postgreJsonCommonPersonStatusTypeService.getPersonStatusTypeByPersonStatusTypeCode(personStatusTypeCode);
    }

    @PostMapping(PERSONSTATUSTYPES)
    public PersonStatusTypePostgreJsonCommon addNewPersonStatusType(@RequestBody PersonStatusType.PersonStatusTypeDto personStatusTypeDto) throws Throwable {
        PersonStatusType personStatusType = personStatusTypeDto.getPersonStatusType();
        return postgreJsonCommonPersonStatusTypeService.addPersonStatusType(convertPersonStatusTypeToPersonStatusTypePostgreJsonCommon(personStatusType));
    }

    //----persons-----
    @GetMapping(PERSONS)
    public List<Person> getAllPersons() {
        return postgreRefPersonService.getAllPersons();
    }

    @GetMapping(PERSONS_ID)
    public Person getPersonByPerson_id(@PathVariable(value = "_id") Long _id) {
        return postgreRefPersonService.getPersonBy_id(_id);
    }

    @PostMapping(PERSONS)
    public PersonRef addPerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return postgreRefPersonService.addPerson(convertPersonToPersonRef(personDto.getPerson()));
    }

    @PutMapping(PERSONS)
    public PersonRef updatePerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return postgreRefPersonService.updatePerson(convertPersonToPersonRef(personDto.getPerson()));
    }

    //----employees---
    @GetMapping(EMPLOYEES)
    public List<Employee> getAllEmployees() {
        return postgreRefEmployeeService.getAllEmployees();
    }

    @GetMapping(EMPLOYEES_PERSONID)
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") Long personId) {
        return postgreRefEmployeeService.getEmployeeByPersonId(personId);
    }

    @PostMapping(EMPLOYEES)
    public EmployeeRef addEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        Employee employee = employeeDto.getEmployee();
        return postgreRefEmployeeService.addEmployee(employee);
    }

    @DeleteMapping(EMPLOYEES_PERSONID)
    public void deleteEmployeeByPersonId(@PathVariable(value="personId") Long personId) throws Throwable {
        List<Employment.FrontEmployment> employeeEmployments = postgreRefEmploymentService.getEmployeeAllEmployments(personId);
        for (Employment.FrontEmployment employment : employeeEmployments) {
            if (employment.getEnd_time() == null) {
                throw new Exception(new Throwable("employee's employment with occupation code " + employment.getOccupation_code() + " is not ended, set an end time before deleting employee!"));
            }
        }
        postgreRefEmployeeService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping(EMPLOYEES)
    public EmployeeRef updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        Employee employee = employeeDto.getEmployee();
        return postgreRefEmployeeService.updateEmployee(employee);
    }

    //----employments-----

    @GetMapping(EMPLOYMENTS_BY_OCCUPATIONCODE)
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreRefEmploymentService.getAllEmploymentsByOccupationCode(occupationCode);
    }

    @GetMapping(EMPLOYMENTS_BY_EMPLOYEE)
    public List<Employment.FrontEmployment> getEmployeeAllEmployments(@PathVariable(value = "personId") Long personId) {
        return postgreRefEmploymentService.getEmployeeAllEmployments(personId);
    }

    @PostMapping(EMPLOYMENTS)
    public EmploymentRef addEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        Employee employee = postgreRefEmployeeService.getEmployeeByPersonId(Long.valueOf(employmentDto.getEmployment().getPerson_id()));
        if (employee.getEmployee_status_type_code() == EMPLOYEE_STATUS_HAS_FINISHED_WORKING) {
            throw new Exception(new Throwable(ADD_EMPLOYMENT_WRONG_EMPLOYEE_STATUS));
        }
        return postgreRefEmploymentService.addEmployment(employmentDto.createPostgreRefEmployment());
    }

    @PutMapping(EMPLOYMENTS)
    public EmploymentRef endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return postgreRefEmploymentService.endEmployeeActiveEmployment(employmentDto.createPostgreRefEmployment());
    }

    @PutMapping(EMPLOYMENTS_END_EMPLOYMENTS)
    public List<EmploymentRef> endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        List<EmploymentRef> endedEmplyoments =  postgreRefEmploymentService.endEmployeeAllEmployments(employmentDto.createPostgreRefEmployment());
        Employee employee = postgreRefEmployeeService.getEmployeeByPersonId(Long.valueOf(employmentDto.getEmployment().getPerson_id()));
        employee.setEmployee_status_type_code(EMPLOYEE_STATUS_HAS_FINISHED_WORKING);
        postgreRefEmployeeService.updateEmployee(employee);
        return endedEmplyoments;
    }

}
