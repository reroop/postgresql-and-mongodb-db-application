package com.dbapplication.controllers;

import com.dbapplication.models.postgre.jsonb.common.CountryPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.EmployeeStatusTypePostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.OccupationPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.emb.EmployeeEmb;
import com.dbapplication.models.postgre.jsonb.emb.EmploymentEmb;
import com.dbapplication.models.postgre.jsonb.emb.PersonEmb;
import com.dbapplication.models.postgre.traditional.*;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonCountryService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonEmployeeStatusTypeService;
import com.dbapplication.services.postgre.jsonb.common.PostgreJsonCommonOccupationService;
import com.dbapplication.services.postgre.jsonb.emb.PostgreEmbPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    //---health checker----
    @GetMapping
    public String index() {
        return "Postgre embedded controller is running!";
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
        return postgreEmbPersonService.getAllPersons();
    }

    @GetMapping("persons/{_id}")
    public Person getPersonByPerson_id(@PathVariable(value = "_id") Long _id) {
        return postgreEmbPersonService.getPersonBy_id(_id);
    }

    @PostMapping("persons")
    public PersonEmb addPerson(@RequestBody Person.PersonDto personDto) {
        return postgreEmbPersonService.addPerson(convertPersonToPersonEmb(personDto.getPerson()));
    }

    @PutMapping("persons")
    public PersonEmb updatePerson(@RequestBody Person.PersonDto personDto) {
        return postgreEmbPersonService.updatePerson(convertPersonToPersonEmb(personDto.getPerson()));
    }

    //----employees---
    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        return postgreEmbPersonService.getAllEmployees();
    }

    @GetMapping("employees/{personId}")
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") Long personId) {
        return postgreEmbPersonService.getEmployeeByPersonId(personId);
    }

    @PostMapping("employees")
    public EmployeeEmb addEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        Employee employee = employeeDto.getEmployee();
        return postgreEmbPersonService.addEmployee(employee);
    }

    @DeleteMapping("employees/{personId}")
    public void deleteEmployeeByPersonId(@PathVariable(value="personId") Long personId) {
        postgreEmbPersonService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping("employees")
    public EmployeeEmb updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        Employee employee = employeeDto.getEmployee();
        return postgreEmbPersonService.updateEmployee(employee);
    }

    //----employments-----

    @GetMapping("employments/occupationCode={occupationCode}")
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return postgreEmbPersonService.getAllEmploymentsByOccupationCode(occupationCode);
    }

    @GetMapping("employments/personId={personId}")
    public List<Employment.FrontEmployment> getEmployeeAllEmployments(@PathVariable(value = "personId") Long personId) {
        return postgreEmbPersonService.getEmployeeAllEmployments(personId);
    }

    @PostMapping("employments")
    public EmploymentEmb addEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        return postgreEmbPersonService.addEmployment(
                Long.valueOf(employmentDto.getEmployment().getPerson_id()),
                employmentDto.createPostgreEmbEmployment()
        );
    }

    @PutMapping("employments")
    public EmploymentEmb endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        return postgreEmbPersonService.endEmployeeActiveEmployment(
                Long.valueOf(employmentDto.getEmployment().getPerson_id()),
                employmentDto.createPostgreEmbEmployment()
        );
    }

    @PutMapping("employments/endEmployments")
    public List<EmploymentEmb> endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) {
        return postgreEmbPersonService.endEmployeeAllEmployments(
                Long.valueOf(employmentDto.getEmployment().getPerson_id()),
                employmentDto.createPostgreEmbEmployment()
        );
    }
}
