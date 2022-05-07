package com.dbapplication.services.mongo.embedded;

import com.dbapplication.models.mongo.embedded.EmbeddedEmployee;
import com.dbapplication.models.mongo.embedded.EmbeddedEmployment;
import com.dbapplication.models.mongo.embedded.EmbeddedPerson;
import com.dbapplication.models.mongo.reference.Employee;
import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.repositories.mongo.embedded.MongoDbEmbPersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dbapplication.utils.ValidationChecks.isDateInRange2010to2100;
import static com.dbapplication.utils.ValidationChecks.isMongoEmbeddedPersonInfoValid;

@Slf4j
@Service
public class MongoDbEmbPersonsService {

    @Autowired
    private MongoDbEmbPersonRepository mongoDbEmbPersonRepository;

    private Person convertEmbeddedPersonToPerson(EmbeddedPerson embeddedPerson) {
        Person person = new Person();
        person.set_id(embeddedPerson.get_id());
        person.setNat_id_code(embeddedPerson.getNat_id_code());
        person.setCountry_code(embeddedPerson.getCountry_code());
        person.setPerson_status_type_code(embeddedPerson.getPerson_status_type_code());
        person.setE_mail(embeddedPerson.getE_mail());
        person.setBirth_date(embeddedPerson.getBirth_date());
        person.setReg_time(embeddedPerson.getReg_time());
        person.setGiven_name(embeddedPerson.getGiven_name());
        person.setSurname(embeddedPerson.getSurname());
        person.setAddress(embeddedPerson.getAddress());
        person.setTel_nr(embeddedPerson.getTel_nr());

        return person;
    }

    private EmbeddedPerson convertPersonToEmbeddedPerson(Person person) {
        EmbeddedPerson embeddedPerson = new EmbeddedPerson();
        embeddedPerson.set_id(person.get_id());
        embeddedPerson.setNat_id_code(person.getNat_id_code());
        embeddedPerson.setCountry_code(person.getCountry_code());
        embeddedPerson.setPerson_status_type_code(person.getPerson_status_type_code());
        embeddedPerson.setE_mail(person.getE_mail());
        embeddedPerson.setBirth_date(person.getBirth_date());
        embeddedPerson.setReg_time(person.getReg_time());
        embeddedPerson.setTel_nr(person.getTel_nr());
        embeddedPerson.setGiven_name(person.getGiven_name());
        embeddedPerson.setSurname(person.getSurname());
        embeddedPerson.setAddress(person.getAddress());

        return embeddedPerson;
    }

    public List<Person> getAllPersons() {
        List<EmbeddedPerson> repoResults = mongoDbEmbPersonRepository.getAllPersons();
        List<Person> convertedResults = new ArrayList<>();
        for (EmbeddedPerson embeddedPerson: repoResults) {
            convertedResults.add(convertEmbeddedPersonToPerson(embeddedPerson));
        }
        return convertedResults;
    }

    public Person getPersonBy_id(String objectId) {
        return convertEmbeddedPersonToPerson(mongoDbEmbPersonRepository.getPersonBy_id(objectId));
    }

    public Person addPerson(Person person) throws Throwable {
        EmbeddedPerson embeddedPerson = convertPersonToEmbeddedPerson(person);
        embeddedPerson.setReg_time(LocalDateTime.now());
        if (!isMongoEmbeddedPersonInfoValid(embeddedPerson)) {
            return null;
        }
        EmbeddedPerson repoResponse = mongoDbEmbPersonRepository.addPerson(embeddedPerson);
        return convertEmbeddedPersonToPerson(repoResponse);
    }

    public boolean updatePerson(Person person) throws Throwable {
        EmbeddedPerson embeddedPerson = convertPersonToEmbeddedPerson(person);
        if (!isMongoEmbeddedPersonInfoValid(embeddedPerson)) {
            return false;
        }
        return mongoDbEmbPersonRepository.updatePerson(embeddedPerson);
    }

    public boolean updateEmployee(Employee employee) throws Throwable {
        if (employee.getMentor_id() != null) {
            if (Objects.equals(employee.getMentor_id(), employee.getPerson_id())) {
                throw new Exception(new Throwable("person can't be his/herself mentor!"));
            }
        }
        return mongoDbEmbPersonRepository.updateEmployee(employee.getPerson_id(), convertEmployeeToEmbeddedEmployee(employee));
    }

    private Employee convertEmbeddedPersonToEmployee(EmbeddedPerson embeddedPerson) {
        Employee employee = new Employee();
        employee.setPerson_id(embeddedPerson.get_id());
        employee.setEmployee_status_type_code(embeddedPerson.getEmployee().getEmployee_status_type_code());
        if (embeddedPerson.getEmployee().getMentor_id() != null) {
            employee.setMentor_id(embeddedPerson.getEmployee().getMentor_id());
        }
        return employee;
    }

    private EmbeddedEmployee convertEmployeeToEmbeddedEmployee(Employee employee) {
        EmbeddedEmployee embeddedEmployee = new EmbeddedEmployee();
        embeddedEmployee.setEmployee_status_type_code(employee.getEmployee_status_type_code());
        if (employee.getMentor_id() != null) {
            embeddedEmployee.setMentor_id(employee.getMentor_id());
        }
        return embeddedEmployee;
    }

    public List<Employee> getAllEmployees() {
        List<EmbeddedPerson> embeddedPersonEmployeeList = mongoDbEmbPersonRepository.getAllEmployees();
        List<Employee> convertedResults = new ArrayList<>();
        for (EmbeddedPerson embeddedPerson : embeddedPersonEmployeeList) {
            convertedResults.add(convertEmbeddedPersonToEmployee(embeddedPerson));
        }
        return convertedResults;
    }

    public Employee getEmployeeByPersonId(String personId) {
        EmbeddedPerson embeddedPerson = mongoDbEmbPersonRepository.getPersonBy_id(personId);
        return convertEmbeddedPersonToEmployee(embeddedPerson);
    }

    public boolean addEmployee(Employee employee) throws Throwable {
        EmbeddedEmployee convertedEmployee = convertEmployeeToEmbeddedEmployee(employee);
        if (convertedEmployee.getMentor_id() != null) {
            if (Objects.equals(convertedEmployee.getMentor_id(), employee.getPerson_id())) {
                throw new Exception(new Throwable("person can't be his/herself mentor!"));
            }
        }
        return mongoDbEmbPersonRepository.addEmployeeToPerson(employee.getPerson_id(), convertedEmployee);
    }

    private EmbeddedEmployment convertEmploymentToEmbeddedEmployment(Employment employment) {
        EmbeddedEmployment embeddedEmployment = new EmbeddedEmployment();
        embeddedEmployment.setOccupation_code(employment.getOccupation_code());
        if (employment.getStart_time() != null) {
            embeddedEmployment.setStart_time(employment.getStart_time());
        }
        if (employment.getEnd_time() != null) {
            embeddedEmployment.setEnd_time(employment.getEnd_time());
        }
        return embeddedEmployment;
    }

    private List<Employment> convertEmbeddedEmploymentsToEmployments(EmbeddedPerson embeddedPerson) {
        String person_id = embeddedPerson.get_id();
        List<EmbeddedEmployment> embeddedEmployments = embeddedPerson.getEmployee().getEmployment();
        List<Employment> convertedEmployments = new ArrayList<>();
        if (embeddedEmployments == null || embeddedEmployments.size() == 0) {
            return convertedEmployments;
        }
        for (EmbeddedEmployment embeddedEmployment : embeddedEmployments) {
            Employment employment = new Employment();
            employment.setPerson_id(person_id);
            employment.setOccupation_code(embeddedEmployment.getOccupation_code());
            employment.setStart_time(embeddedEmployment.getStart_time());
            if (embeddedEmployment.getEnd_time() != null) {
                employment.setEnd_time(embeddedEmployment.getEnd_time());
            }
            convertedEmployments.add(employment);
        }

        return convertedEmployments;
    }

    public boolean addEmployment(Employment employment) throws Throwable {
        if (!isDateInRange2010to2100(employment.getStart_time())) {
            log.info("employment dates must be in range 2010-2100! Current start time is " + employment.getStart_time());
            throw new Exception(new Throwable("employment dates must be in range 2010-2100! Current start time is " + employment.getStart_time()));
        }
        return mongoDbEmbPersonRepository.addEmploymentToEmployee(employment.getPerson_id(), convertEmploymentToEmbeddedEmployment(employment));
    }

    public List<Employment> getEmployeeAllEmployments(String personId) {
        EmbeddedPerson embeddedPerson = mongoDbEmbPersonRepository.getPersonBy_id(personId);
        return convertEmbeddedEmploymentsToEmployments(embeddedPerson);
    }

    public List<Employment> getAllEmploymentsByOccupationCode(Integer occupationCode) {
        List<EmbeddedPerson> embeddedPersonEmployeeList = mongoDbEmbPersonRepository.getAllEmployees();
        List<Employment> result = new ArrayList<>();

        for (EmbeddedPerson embeddedPerson: embeddedPersonEmployeeList) {
            List<Employment> personAllEmploymentsConverted = convertEmbeddedEmploymentsToEmployments(embeddedPerson);
            for (Employment employment : personAllEmploymentsConverted) {
                if (Objects.equals(employment.getOccupation_code(), occupationCode) && employment.getEnd_time() == null) {
                    result.add(employment);
                }
            }
        }

        return result;
    }

    public boolean endEmployeeActiveEmployment(Employment employment) throws Throwable {
        if (!isDateInRange2010to2100(employment.getEnd_time())) {
            log.info("end date " + employment.getEnd_time() + " for employment is not in range 2010-2100!");
            throw new Exception(new Throwable("end date " + employment.getEnd_time() + " is not in range 2010-2100!"));
        }
        return mongoDbEmbPersonRepository.endActiveEmployment(employment);
    }

    public boolean endEmployeeAllEmployments(Employment employment) throws Throwable {
        if (!isDateInRange2010to2100(employment.getEnd_time())) {
            log.info("end date " + employment.getEnd_time() + " for employment is not in range 2010-2100!");
            throw new Exception(new Throwable("end date " + employment.getEnd_time() + " for employment is not in range 2010-2100!"));
        }
        return mongoDbEmbPersonRepository.endAllEmployments(employment);
    }

    public boolean deleteEmployeeByPersonId(String personId) {
        List<Employment> employeeEmployments = getEmployeeAllEmployments(personId);
        for (Employment employment : employeeEmployments) {
            if (employment.getEnd_time() == null) {
                return false;
            }
        }
        return mongoDbEmbPersonRepository.deleteEmployee(personId);
    }
}
