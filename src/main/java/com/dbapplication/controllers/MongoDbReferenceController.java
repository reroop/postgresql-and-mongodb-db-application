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

import static com.dbapplication.utils.UniversalConstants.*;

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
    //-------------

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
        return mongoDbRefPersonsService.getAllPersons();
    }

    @GetMapping(PERSONS_OBJECTID)
    public Person getPersonByPerson_id(@PathVariable(value = "objectId") String objectId) {
        return mongoDbRefPersonsService.getPersonBy_id(objectId);
    }

    @PostMapping(PERSONS)
    public Person addPerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return mongoDbRefPersonsService.addPerson(personDto.getPerson());
    }

    @PutMapping(PERSONS)
    public boolean updatePerson(@RequestBody Person.PersonDto personDto) throws Throwable {
        return mongoDbRefPersonsService.updatePerson(personDto.getPerson());
    }

    //-----employees-------
    @GetMapping(EMPLOYEES)
    public List<Employee> getAllEmployees() {
        return mongoDbRefEmployeesService.getAllEmployees();
    }

    @GetMapping(EMPLOYEES_PERSONID)
    public Employee getEmployeeByPersonId(@PathVariable(value = "personId") String personId) {
        return mongoDbRefEmployeesService.getEmployeeByPersonId(personId);
    }

    @PostMapping(EMPLOYEES)
    public Employee.EmployeeDbEntry addEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        return mongoDbRefEmployeesService.addEmployee(employeeDto.getEmployee());
    }

    @DeleteMapping(EMPLOYEES_PERSONID)
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

    @PutMapping(EMPLOYEES)
    public boolean updateEmployee(@RequestBody Employee.EmployeeDto employeeDto) throws Throwable {
        return mongoDbRefEmployeesService.updateEmployee(employeeDto.getEmployee());
    }
    //--------------------

    @GetMapping(EMPLOYMENTS_BY_OCCUPATIONCODE)
    public List<Employment> getAllEmploymentsByOccupationCode(@PathVariable(value = "occupationCode") Integer occupationCode) {
        return mongoDbRefEmploymentsService.getAllEmploymentsByOccupationCode(occupationCode);
    }

    @GetMapping(EMPLOYMENTS_BY_EMPLOYEE)
    public List<Employment> getEmployeeAllEmployments(@PathVariable(value = "personId") String personId) {
        return mongoDbRefEmploymentsService.getEmployeeAllEmployments(personId);
    }

    @PostMapping(EMPLOYMENTS)
    public Employment.EmploymentDbEntry addEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        Employee employee = mongoDbRefEmployeesService.getEmployeeByPersonId(employmentDto.getEmployment().getPerson_id());
        if (employee.getEmployee_status_type_code() == EMPLOYEE_STATUS_HAS_FINISHED_WORKING) {
            throw new Exception(new Throwable(ADD_EMPLOYMENT_WRONG_EMPLOYEE_STATUS));
        }
        return mongoDbRefEmploymentsService.addEmployment(employmentDto.getEmployment());
    }

    @PutMapping(EMPLOYMENTS)
    public boolean endEmployeeActiveEmployment(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        return mongoDbRefEmploymentsService.endEmployeeActiveEmployment(employmentDto.getEmployment());
    }

    @PutMapping(EMPLOYMENTS_END_EMPLOYMENTS)
    public boolean endEmployeeAllEmployments(@RequestBody Employment.EmploymentDto employmentDto) throws Throwable {
        boolean wereAllEmploymentsEnded = mongoDbRefEmploymentsService.endEmployeeAllEmployments(employmentDto.getEmployment());
        Employee employee = mongoDbRefEmployeesService.getEmployeeByPersonId(employmentDto.getEmployment().getPerson_id());
        employee.setEmployee_status_type_code(EMPLOYEE_STATUS_HAS_FINISHED_WORKING);
        mongoDbRefEmployeesService.updateEmployee(employee);
        return wereAllEmploymentsEnded;
    }

    //--------------------
}
