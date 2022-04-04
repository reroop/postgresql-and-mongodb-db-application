package com.dbapplication.services.postgre.jsonb.emb;

import com.dbapplication.models.postgre.jsonb.emb.EmployeeEmb;
import com.dbapplication.models.postgre.jsonb.emb.EmploymentEmb;
import com.dbapplication.models.postgre.jsonb.emb.PersonEmb;
import com.dbapplication.models.postgre.traditional.Employee;
import com.dbapplication.models.postgre.traditional.Employment;
import com.dbapplication.models.postgre.traditional.Person;
import com.dbapplication.repositories.postgre.jsonb.emb.PostgreEmbPersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dbapplication.utils.mongodb.ValidationChecks.*;
import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertPersonEmbListToPersonList;
import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertPersonEmbToPerson;

@Slf4j
@Service
@Profile("postgreemb")
public class PostgreEmbPersonService {

    @Autowired
    private PostgreEmbPersonRepository personRepository;

    public List<Person> getAllPersons() {
        return convertPersonEmbListToPersonList(personRepository.findAll());
    }

    public Person getPersonBy_id(Long id) {
        PersonEmb personEmb = personRepository.findById(id).orElse(null);
        return personEmb == null ? null : convertPersonEmbToPerson(personEmb);
    }

    public PersonEmb addPerson(PersonEmb personEmb) {
        System.out.println(personEmb);
        return !isPostgreJsonEmbPersonValid(personEmb) ? null : personRepository.save(personEmb);
    }

    public PersonEmb updatePerson(PersonEmb personEmb) {
        return !isPostgreJsonEmbPersonValid(personEmb) ? null : personRepository.save(personEmb);
    }

    //----employee
    public List<Employee> getAllEmployees() {
        List<PersonEmb> repoRes = personRepository.findAllByEmployeeNotNull();
        List<Employee> result = new ArrayList<>();
        for (PersonEmb personEmb : repoRes) {
            Employee employee = new Employee(personEmb.get_id(), personEmb.getEmployee().getMentor_id(), personEmb.getEmployee().getEmployee_status_type_code());
            result.add(employee);
        }
        return result;
    }

    public Employee getEmployeeByPersonId(Long personId) {
        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        return personEmb == null
                ? null
                : new Employee(personEmb.get_id(), personEmb.getEmployee().getMentor_id(), personEmb.getEmployee().getEmployee_status_type_code());
    }

    public EmployeeEmb addEmployee(Employee employee) {
        EmployeeEmb employeeEmb = new EmployeeEmb(employee.getEmployee_status_type_code(), employee.getMentor_id(), null);
        PersonEmb personEmb = personRepository.findById(employee.getPerson_id()).orElse(null);
        if (personEmb==null) {
            return null;
        }
        personEmb.setEmployee(employeeEmb);
        PersonEmb savedPersonEmb = personRepository.save(personEmb);
        return savedPersonEmb.getEmployee();
    }

    public void deleteEmployeeByPersonId(Long personId) {
        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb==null) {
            return;
        }
        personEmb.setEmployee(null);
        personRepository.save(personEmb);
    }

    public EmployeeEmb updateEmployee(Employee employee) {
        PersonEmb personEmb = personRepository.findById(employee.getPerson_id()).orElse(null);
        if (personEmb == null) {
            return null;
        }
        personEmb.getEmployee().setEmployee_status_type_code(employee.getEmployee_status_type_code());
        personEmb.getEmployee().setMentor_id(employee.getMentor_id());
        PersonEmb savedPersonEmb = personRepository.save(personEmb);
        return savedPersonEmb.getEmployee();
    }

    //---employment---

    public List<Employment> getAllEmploymentsByOccupationCode(Integer occupationCode) {
        List<PersonEmb> repoRes = personRepository.findAllByEmployeeNotNull();
        List<Employment> result = new ArrayList<>();
        for (PersonEmb personEmb : repoRes) {
            if (personEmb.getEmployee().getEmployment() == null) {
                continue;
            }
            for (EmploymentEmb employmentEmb : personEmb.getEmployee().getEmployment()) {
                if (employmentEmb.getOccupation_code().equals(occupationCode)) {
                    Employment employment = new Employment(
                            personEmb.get_id(),
                            employmentEmb.getOccupation_code(),
                            employmentEmb.getStart_time(),
                            employmentEmb.getEnd_time()
                    );
                    result.add(employment);
                }
            }
        }
        return result;
    }

    public List<Employment.FrontEmployment> getEmployeeAllEmployments(Long personId) {
        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb == null) {
            return null;
        }
        List<Employment.FrontEmployment> result = new ArrayList<>();
        if (personEmb.getEmployee().getEmployment() == null) {
            return result;
        }

        for (EmploymentEmb employmentEmb : personEmb.getEmployee().getEmployment()) {
            Employment.FrontEmployment frontEmployment = new Employment.FrontEmployment(
                    String.valueOf(personEmb.get_id()),
                    employmentEmb.getOccupation_code(),
                    employmentEmb.getStart_time(),
                    employmentEmb.getEnd_time()
            );
            result.add(frontEmployment);
        }
        return result;
    }

    public EmploymentEmb addEmployment(Long personId, EmploymentEmb newEmploymentEmb) {
        if (!isDateInRange2010to2100(newEmploymentEmb.getStart_time())) {
            log.info("employmentref starttime not in range 2010-2100");
            return null;
        }

        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb == null) {
            return null;
        }

        if (personEmb.getEmployee().getEmployment() == null) {
            personEmb.getEmployee().setEmployment(List.of(newEmploymentEmb));
            personRepository.save(personEmb);
            return newEmploymentEmb;
        }

        for (EmploymentEmb employmentEmb : personEmb.getEmployee().getEmployment()) {
            if (employmentEmb.getOccupation_code().equals(newEmploymentEmb.getOccupation_code()) && employmentEmb.getEnd_time() == null) {
                log.info("already in active employment");
                return null;
            }
        }

        personEmb.getEmployee().getEmployment().add(newEmploymentEmb);
        personRepository.save(personEmb);
        return newEmploymentEmb;
    }

    public EmploymentEmb endEmployeeActiveEmployment(Long personId, EmploymentEmb endEmbEmployment) {
        if (!isDateInRange2010to2100(endEmbEmployment.getEnd_time())) {
            log.info("employmentref endtime not in range 2010-2100");
            return null;
        }

        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb == null) {
            return null;
        }

        EmploymentEmb finishedEmployment = null;
        for (EmploymentEmb employmentEmb : personEmb.getEmployee().getEmployment()) {
            if (employmentEmb.getOccupation_code().equals(endEmbEmployment.getOccupation_code()) && employmentEmb.getEnd_time() == null) {
                if (!isFirstDateBeforeSecondDate(employmentEmb.getStart_time(), endEmbEmployment.getEnd_time())) {
                    log.info("employmentemb endtime is before start time");
                    return null;
                }
                employmentEmb.setEnd_time(endEmbEmployment.getEnd_time());
                finishedEmployment = employmentEmb;
                break;
            }
        }
        personRepository.save(personEmb);
        return finishedEmployment;
    }

    public List<EmploymentEmb> endEmployeeAllEmployments(Long personId, EmploymentEmb endEmbEmployment) {
        if (!isDateInRange2010to2100(endEmbEmployment.getEnd_time())) {
            log.info("employmentref endtime not in range 2010-2100");
            return null;
        }

        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb == null || personEmb.getEmployee().getEmployment() == null) {
            log.info("personemb or employments is null");
            return null;
        }

        for (EmploymentEmb employmentEmb : personEmb.getEmployee().getEmployment()) {
            if (employmentEmb.getEnd_time() == null) {
                if (!isFirstDateBeforeSecondDate(employmentEmb.getStart_time(), endEmbEmployment.getEnd_time())) {
                    log.info("employmentemb endtime is before start time");
                    return null;
                }
                employmentEmb.setEnd_time(endEmbEmployment.getEnd_time());
            }
        }
        return personRepository.save(personEmb).getEmployee().getEmployment();


    }

}
