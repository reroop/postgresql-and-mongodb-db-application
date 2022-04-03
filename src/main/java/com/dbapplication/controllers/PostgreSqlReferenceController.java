package com.dbapplication.controllers;

import com.dbapplication.models.postgre.ref.*;
import com.dbapplication.models.postgre.traditional.*;
import com.dbapplication.services.postgre.ref.*;
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
    private PostgreRefCountryService postgreRefCountryService;

    @Autowired
    private PostgreRefOccupationService postgreRefOccupationService;

    @Autowired
    private PostgreRefEmployeeStatusTypeService postgreRefEmployeeStatusTypeService;

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
        List<CountryRef> repoCountries = postgreRefCountryService.getAllCountries();
        return convertCountryRefListToCountryList(repoCountries);
    }

    @GetMapping("countries/{countryCode}")
    public Country getCountryByCountryCode(@PathVariable(value = "countryCode") String countryCode) {
        CountryRef repoCountry = postgreRefCountryService.getCountryByCountryCode(countryCode);
        return repoCountry == null ? null : convertCountryRefToCountry(repoCountry);
    }

    @PostMapping("countries")
    public CountryRef addNewCountry(@RequestBody Country.CountryDto countryDto) {
        Country country = countryDto.getCountry();
        return postgreRefCountryService.addCountry(convertCountryToCountryRef(country));
    }

    //---occupations---
    @GetMapping("occupations")
    public List<Occupation> getAllOccupations() {
        List<OccupationRef> repoRes = postgreRefOccupationService.getAllOccupations();

        return convertOccupationRefListfToOccupationList(repoRes);
    }

    @GetMapping("occupations/{occupationCode}")
    public Occupation getOccupationByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        OccupationRef occupationRef = postgreRefOccupationService.getOccupationByOccupationCode(occupationCode);
        return occupationRef == null
                ? null
                : convertOccupationRefToOccupation(occupationRef);
    }

    @PostMapping("occupations")
    public OccupationRef addNewOccupation(@RequestBody Occupation.OccupationDto occupationDto) {
        Occupation newOccupation = occupationDto.getOccupation();
        return postgreRefOccupationService.addOccupation(convertOccupationToOccupationRef(newOccupation));
    }

    //-----employee status types-----
    @GetMapping("employeeStatusTypes")
    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        List<EmployeeStatusTypeRef> repoResults = postgreRefEmployeeStatusTypeService.getAllEmployeeStatusTypes();
        return convertEmployeeStatusTypeRefListToeEmployeeStatusTypeList(repoResults);
    }

    @GetMapping("employeeStatusTypes/{employeeStatusTypeCode}")
    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(@PathVariable(value = "employeeStatusTypeCode") Integer employeeStatusTypeCode) {
        EmployeeStatusTypeRef employeeStatusTypeRef = postgreRefEmployeeStatusTypeService.getEmployeeStatusTypeByEmployeeStatusTypeCode(employeeStatusTypeCode);
        return employeeStatusTypeRef == null
                ? null
                : convertEmployeeStatusTypeRefToEmployeeStatusType(employeeStatusTypeRef);
    }

    @PostMapping("employeeStatusTypes")
    public EmployeeStatusTypeRef addNewEmployeeStatusType(@RequestBody EmployeeStatusType.EmployeeStatusTypeDto employeeStatusTypeDto) {
        EmployeeStatusType employeeStatusType = employeeStatusTypeDto.getEmployeeStatusType();
        return postgreRefEmployeeStatusTypeService.addEmployeeStatusType(convertEmployeeStatusTypeToEmployeeStatusTypeRef(employeeStatusType));
    }

    //----persons-----
    @GetMapping("persons")
    public List<Person> getAllPersons() {
        return convertPersonRefListToPersonList(postgreRefPersonService.getAllPersons());
    }

    @GetMapping("persons/{_id}")
    public Person getPersonByPerson_id(@PathVariable(value = "_id") Long _id) {
        PersonRef personRef = postgreRefPersonService.getPersonBy_id(_id);
        return personRef == null ? null : convertPersonRefToPerson(personRef);
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
        List<EmployeeRef> repoRes = postgreRefEmployeeService.getAllEmployees();
        List<Employee> res = new ArrayList<>();
        for (EmployeeRef employeeRef:repoRes) {
            res.add(new Employee(employeeRef.getPerson_id(), employeeRef.getMentor_id(), employeeRef.getEmployee_status_type_code()));
        }

        return res;
    }

    @GetMapping("employees/{personId}")
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") Long personId) {
        EmployeeRef employeeRef = postgreRefEmployeeService.getEmployeeByPersonId(personId);
        return new Employee(employeeRef.getPerson_id(), employeeRef.getMentor_id(), employeeRef.getEmployee_status_type_code());
    }

    @PostMapping("employees")
    public EmployeeRef addEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        Employee employee = employeeDto.getEmployee();
        EmployeeRef employeeRef = new EmployeeRef(employee.getPerson_id(), employee.getMentor_id(), employee.getEmployee_status_type_code());
        return postgreRefEmployeeService.addEmployee(employeeRef);
    }

    @DeleteMapping("employees/{personId}")
    public void deleteEmployeeByPersonId(@PathVariable(value="personId") Long personId) {
        postgreRefEmployeeService.deleteEmployeeByPersonId(personId);
    }

    @PutMapping("employees")
    public EmployeeRef updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) {
        Employee employee = employeeDto.getEmployee();
        EmployeeRef employeeRef = new EmployeeRef(employee.getPerson_id(), employee.getMentor_id(), employee.getEmployee_status_type_code());
        return postgreRefEmployeeService.updateEmployee(employeeRef);
    }

    //----employments-----

    @GetMapping("employments/occupationCode={occupationCode}")
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        List<EmploymentRef> repoRes = postgreRefEmploymentService.getAllEmploymentsByOccupationCode(occupationCode);
        List<Employment> result = new ArrayList<>();
        for (EmploymentRef employmentRef: repoRes) {
            Employment employment = new Employment(
                    employmentRef.getPersonId(),
                    employmentRef.getOccupationCode(),
                    employmentRef.getData().getStart_time(),
                    employmentRef.getData().getEnd_time()
            );
            result.add(employment);
        }

        return result;
    }


    @GetMapping("employments/personId={personId}")
    public List<Employment.FrontEmployment> getEmployeeAllEmployments(@PathVariable(value = "personId") Long personId) {
        List<EmploymentRef> repoResults = postgreRefEmploymentService.getEmployeeAllEmployments(personId);
        List<Employment.FrontEmployment> results = new ArrayList<>();
        for (EmploymentRef employmentRef: repoResults) {
            Employment.FrontEmployment frontEmployment = new Employment.FrontEmployment(
                    String.valueOf(employmentRef.getPersonId()),
                    employmentRef.getOccupationCode(),
                    employmentRef.getData().getStart_time(),
                    employmentRef.getData().getEnd_time()
            );
            results.add(frontEmployment);
        }
        return results;
    }

    @PostMapping("employments")
    public EmploymentRef addEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        return postgreRefEmploymentService.addEmployment(employmentDto.createPostgreRefEmployment());
    }

    @PutMapping("employments")
    public EmploymentRef endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) {
        System.out.println(employmentDto);
        return postgreRefEmploymentService.endEmployeeActiveEmployment(employmentDto.createPostgreRefEmployment());
    }

    @PutMapping("employments/endEmployments")
    public List<EmploymentRef> endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) {
        return postgreRefEmploymentService.endEmployeeAllEmployments(employmentDto.createPostgreRefEmployment());
    }

}
