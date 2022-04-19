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

import static com.dbapplication.utils.UniversalConstants.ADD_EMPLOYMENT_WRONG_EMPLOYEE_STATUS;
import static com.dbapplication.utils.UniversalConstants.EMPLOYEE_STATUS_HAS_FINISHED_WORKING;
import static com.dbapplication.utils.ValidationChecks.*;
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

    public PersonEmb addPerson(PersonEmb personEmb) throws Throwable{
        if (!isPostgreJsonEmbPersonValid(personEmb)) {
            return null;
        }
        try {
            return personRepository.save(personEmb);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public PersonEmb updatePerson(PersonEmb personEmb) throws Throwable {
        if (!isPostgreJsonEmbPersonValid(personEmb)) {
            return null;
        }
        PersonEmb person = personRepository.findById(personEmb.get_id()).orElse(null);
        if (person == null) {
            return null;
        }
        person.setCountry_code(personEmb.getCountry_code());
        person.setPerson_status_type_code(personEmb.getPerson_status_type_code());
        person.setData(personEmb.getData());
        try {
            return personRepository.save(person);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
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
        return personEmb.getEmployee() == null
                ? null
                : new Employee(personEmb.get_id(), personEmb.getEmployee().getMentor_id(), personEmb.getEmployee().getEmployee_status_type_code());
    }

    public EmployeeEmb addEmployee(Employee employee) throws Throwable {
        EmployeeEmb employeeEmb = new EmployeeEmb(employee.getEmployee_status_type_code(), employee.getMentor_id(), null);
        PersonEmb personEmb = personRepository.findById(employee.getPerson_id()).orElse(null);
        if (personEmb==null) {
            throw new Exception(new Throwable("Person not found!"));
        }
        if (personEmb.getEmployee() != null) {
            throw new Exception(new Throwable("This person is already registered as an employee!"));
        }
        personEmb.setEmployee(employeeEmb);
        try {
            PersonEmb savedPersonEmb = personRepository.save(personEmb);
            return savedPersonEmb.getEmployee();
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public void deleteEmployeeByPersonId(Long personId) throws Throwable {
        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb == null || personEmb.getEmployee() == null) {
            throw new Exception(new Throwable("Person or employee not found!"));
        }
        if (personEmb.getEmployee().getEmployment() != null) {
            for (EmploymentEmb employmentEmb : personEmb.getEmployee().getEmployment()) {
                if (employmentEmb.getEnd_time() == null) {
                    throw new Exception(new Throwable("employee's employment with occupation code " + employmentEmb.getOccupation_code() + " is not ended, set an end time before deleting employee!"));
                }
            }
        }
        personEmb.setEmployee(null);
        try {
            personRepository.save(personEmb);
            this.removeDeletedEmployeeAsMentorFromOtherEmployees(personId);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public EmployeeEmb updateEmployee(Employee employee) throws Throwable {
        PersonEmb personEmb = personRepository.findById(employee.getPerson_id()).orElse(null);
        if (personEmb == null || personEmb.getEmployee() == null) {
            throw new Exception(new Throwable("Person or employee not found!"));
        }
        personEmb.getEmployee().setEmployee_status_type_code(employee.getEmployee_status_type_code());
        personEmb.getEmployee().setMentor_id(employee.getMentor_id());
        try {
            PersonEmb savedPersonEmb = personRepository.save(personEmb);
            return savedPersonEmb.getEmployee();
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    private void removeDeletedEmployeeAsMentorFromOtherEmployees(Long deletedEmployeeId) throws Throwable {
        List<PersonEmb> repoRes = personRepository.findAllByEmployeeNotNull();
        List<PersonEmb> employeesWithChangedMentorId = new ArrayList<>();

        for (PersonEmb person : repoRes) {
            if (person.getEmployee().getMentor_id() != null && person.getEmployee().getMentor_id().equals(deletedEmployeeId)) {
                person.getEmployee().setMentor_id(null);
                employeesWithChangedMentorId.add(person);
            }
        }
        try {
            personRepository.saveAll(employeesWithChangedMentorId);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
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
                if (employmentEmb.getOccupation_code().equals(occupationCode) && employmentEmb.getEnd_time() == null) {
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

    public List<Employment.FrontEmployment> getEmployeeAllEmployments(Long personId) throws Throwable {
        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb == null) {
            throw new Exception(new Throwable("Person or employee not found!"));
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

    public EmploymentEmb addEmployment(Long personId, EmploymentEmb newEmploymentEmb) throws Throwable {
        if (!isDateInRange2010to2100(newEmploymentEmb.getStart_time())) {
            log.info("employmentemb starttime not in range 2010-2100");
            throw new Exception(new Throwable("Employment start time not in range 2010-2100! Current reg. time: " + newEmploymentEmb.getStart_time()));
        }

        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb == null) {
            throw new Exception(new Throwable("Person or employee not found!"));
        }
        if (personEmb.getEmployee().getEmployee_status_type_code() == EMPLOYEE_STATUS_HAS_FINISHED_WORKING) {
            throw new Exception(new Throwable(ADD_EMPLOYMENT_WRONG_EMPLOYEE_STATUS));
        }

        if (personEmb.getEmployee().getEmployment() == null) {
            personEmb.getEmployee().setEmployment(List.of(newEmploymentEmb));
            try {
                personRepository.save(personEmb);
                return newEmploymentEmb;
            } catch (Exception e) {
                throw new Exception(new Throwable(e.getMessage()));
            }
        }

        for (EmploymentEmb employmentEmb : personEmb.getEmployee().getEmployment()) {
            if (employmentEmb.getOccupation_code().equals(newEmploymentEmb.getOccupation_code())) {
                if (employmentEmb.getEnd_time() == null) {
                    log.info("add employment, already in active employment!");
                    throw new Exception(new Throwable("Employee is already actively employed in this occupation!"));
                }
                if (employmentEmb.getStart_time().equals(newEmploymentEmb.getStart_time())) {
                    log.info("add employment, employment with this occupation and start time already registered");
                    throw new Exception(new Throwable("Employment with this occupation and start time is already registered!"));
                }
            }
        }

        personEmb.getEmployee().getEmployment().add(newEmploymentEmb);
        try {
            personRepository.save(personEmb);
            return newEmploymentEmb;
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public EmploymentEmb endEmployeeActiveEmployment(Long personId, EmploymentEmb endEmbEmployment) throws Throwable {
        if (!isDateInRange2010to2100(endEmbEmployment.getEnd_time())) {
            log.info("employmentemb starttime not in range 2010-2100");
            throw new Exception(new Throwable("Employment end time not in range 2010-2100! Current end time: " + endEmbEmployment.getEnd_time()));
        }

        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb == null) {
            throw new Exception(new Throwable("Person or employee not found!"));
        }

        EmploymentEmb finishedEmployment = null;
        for (EmploymentEmb employmentEmb : personEmb.getEmployee().getEmployment()) {
            if (employmentEmb.getOccupation_code().equals(endEmbEmployment.getOccupation_code()) && employmentEmb.getEnd_time() == null) {
                if (!isFirstDateBeforeSecondDate(employmentEmb.getStart_time(), endEmbEmployment.getEnd_time())) {
                    log.info("employmentemb endtime is before start time");
                    throw new Exception(new Throwable("Employment end time (" + endEmbEmployment.getEnd_time() + ") is before start time " + employmentEmb.getStart_time()));
                }
                employmentEmb.setEnd_time(endEmbEmployment.getEnd_time());
                finishedEmployment = employmentEmb;
                break;
            }
        }
        try {
            personRepository.save(personEmb);
            return finishedEmployment;
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public List<EmploymentEmb> endEmployeeAllEmployments(Long personId, EmploymentEmb endEmbEmployment) throws Throwable {
        if (!isDateInRange2010to2100(endEmbEmployment.getEnd_time())) {
            log.info("Employment end time not in range 2010-2100! Current reg. time: " + endEmbEmployment.getEnd_time());
            throw new Exception(new Throwable("Employment end time not in range 2010-2100! Current reg. time: " + endEmbEmployment.getEnd_time()));
        }

        PersonEmb personEmb = personRepository.findById(personId).orElse(null);
        if (personEmb == null || personEmb.getEmployee().getEmployment() == null) {
            log.info("personemb or employments is null");
            personEmb.getEmployee().setEmployee_status_type_code(EMPLOYEE_STATUS_HAS_FINISHED_WORKING);
            personRepository.save(personEmb);
            return null;
        }

        for (EmploymentEmb employmentEmb : personEmb.getEmployee().getEmployment()) {
            if (employmentEmb.getEnd_time() == null) {
                if (!isFirstDateBeforeSecondDate(employmentEmb.getStart_time(), endEmbEmployment.getEnd_time())) {
                    log.info("employmentref endtime is before start time");
                    throw new Exception(new Throwable("Employment end time (" + endEmbEmployment.getEnd_time() + ") is before start time " + employmentEmb.getStart_time()));
                }
                employmentEmb.setEnd_time(endEmbEmployment.getEnd_time());
            }
        }
        personEmb.getEmployee().setEmployee_status_type_code(EMPLOYEE_STATUS_HAS_FINISHED_WORKING);

        try {
            return personRepository.save(personEmb).getEmployee().getEmployment();
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

}
