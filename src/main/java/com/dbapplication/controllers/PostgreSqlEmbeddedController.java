package com.dbapplication.controllers;

import com.dbapplication.models.postgre.jsonb.common.CountryPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.EmployeeStatusTypePostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.OccupationPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.PersonStatusTypePostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.emb.EmployeeEmb;
import com.dbapplication.models.postgre.jsonb.emb.EmploymentEmb;
import com.dbapplication.models.postgre.jsonb.emb.PersonEmb;
import com.dbapplication.models.postgre.traditional.*;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonCountryService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonEmployeeStatusTypeService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonOccupationService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonPersonStatusTypeService;
import com.dbapplication.services.postgre.jsonb.emb.PostgreEmbPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dbapplication.utils.UniversalConstants.*;
import static com.dbapplication.utils.postgre.PostgreObjectConverter.*;

@Profile("postgreemb")
@RestController
public class PostgreSqlEmbeddedController {

    @Autowired
    private PostgreJsonCommonCountryService postgreJsonCommonCountryService;

    @Autowired
    private PostgreJsonCommonOccupationService postgreJsonCommonOccupationService;

    @Autowired
    private PostgreJsonCommonEmployeeStatusTypeService postgreJsonCommonEmployeeStatusTypeService;

    @Autowired
    private PostgreEmbPersonService postgreEmbPersonService;

    @Autowired
    private PostgreJsonCommonPersonStatusTypeService postgreJsonCommonPersonStatusTypeService;


    //---health checker----
    @GetMapping
    public String index() {
        return "Postgre embedded controller is running!";
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
        return postgreEmbPersonService.getAllPersons();
    }

    @GetMapping(PERSONS_ID)
    public Person getPersonByPerson_id(@PathVariable(value = "_id") Long _id) {
        return postgreEmbPersonService.getPersonBy_id(_id);
    }

    @PostMapping(PERSONS)
    public PersonEmb addPerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return postgreEmbPersonService.addPerson(convertPersonToPersonEmb(personDto.getPerson()));
    }

    @PutMapping(PERSONS)
    public PersonEmb updatePerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return postgreEmbPersonService.updatePerson(convertPersonToPersonEmb(personDto.getPerson()));
    }

    //----employees---
    @GetMapping(EMPLOYEES)
    public List<Employee> getAllEmployees() {
        return postgreEmbPersonService.getAllEmployees();
    }

    @GetMapping(EMPLOYEES_PERSONID)
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") Long personId) {
        return postgreEmbPersonService.getEmployeeByPersonId(personId);
    }

    @PostMapping(EMPLOYEES)
    public EmployeeEmb addEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        Employee employee = employeeDto.getEmployee();
        return postgreEmbPersonService.addEmployee(employee);
    }

    @DeleteMapping(EMPLOYEES_PERSONID)
    public void deleteEmployeeByPersonId(@PathVariable(value="personId") Long personId) throws Throwable {
        postgreEmbPersonService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping(EMPLOYEES)
    public EmployeeEmb updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        Employee employee = employeeDto.getEmployee();
        return postgreEmbPersonService.updateEmployee(employee);
    }

    //----employments-----

    @GetMapping(EMPLOYMENTS_BY_OCCUPATIONCODE)
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreEmbPersonService.getAllEmploymentsByOccupationCode(occupationCode);
    }

    @GetMapping(EMPLOYMENTS_BY_EMPLOYEE)
    public List<Employment.FrontEmployment> getEmployeeAllEmployments(@PathVariable(value = "personId") Long personId) throws Throwable {
        return postgreEmbPersonService.getEmployeeAllEmployments(personId);
    }

    @PostMapping(EMPLOYMENTS)
    public EmploymentEmb addEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return postgreEmbPersonService.addEmployment(
                Long.valueOf(employmentDto.getEmployment().getPerson_id()),
                employmentDto.createPostgreEmbEmployment()
        );
    }

    @PutMapping(EMPLOYMENTS)
    public EmploymentEmb endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return postgreEmbPersonService.endEmployeeActiveEmployment(
                Long.valueOf(employmentDto.getEmployment().getPerson_id()),
                employmentDto.createPostgreEmbEmployment()
        );
    }

    @PutMapping(EMPLOYMENTS_END_EMPLOYMENTS)
    public List<EmploymentEmb> endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return postgreEmbPersonService.endEmployeeAllEmployments(
                Long.valueOf(employmentDto.getEmployment().getPerson_id()),
                employmentDto.createPostgreEmbEmployment()
        );
    }
}
