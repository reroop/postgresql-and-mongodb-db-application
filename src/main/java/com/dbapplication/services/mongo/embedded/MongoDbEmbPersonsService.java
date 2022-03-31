package com.dbapplication.services.mongo.embedded;

import com.dbapplication.models.mongo.embedded.EmbeddedEmployee;
import com.dbapplication.models.mongo.embedded.EmbeddedEmployment;
import com.dbapplication.models.mongo.embedded.EmbeddedPerson;
import com.dbapplication.models.mongo.embedded.EmbeddedUserAccount;
import com.dbapplication.models.mongo.reference.Employee;
import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.repositories.mongo.embedded.MongoDbEmbPersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MongoDbEmbPersonsService {

    @Autowired
    private MongoDbEmbPersonRepository mongoDbEmbPersonRepository;

    private Person convertEmbeddedPersonToPerson(EmbeddedPerson embeddedPerson) {
        Person person = new Person();
        person.set_id(embeddedPerson.get_id());
        person.setIsikukood(embeddedPerson.getIsikukood());
        person.setRiik_kood(embeddedPerson.getRiik_kood());
        person.setE_meil(embeddedPerson.getE_meil());
        person.setSynni_kp(embeddedPerson.getReg_aeg());
        person.setReg_aeg(embeddedPerson.getReg_aeg());

        if (embeddedPerson.getEesnimi() != null) {
            person.setEesnimi(embeddedPerson.getEesnimi());
        }
        if (embeddedPerson.getPerenimi() != null) {
            person.setPerenimi(embeddedPerson.getPerenimi());
        }
        if (embeddedPerson.getElukoht() != null) {
            person.setElukoht(embeddedPerson.getElukoht());
        }
        return person;
    }

    private EmbeddedPerson convertPersonToEmbeddedPerson(Person person) {
        EmbeddedPerson embeddedPerson = new EmbeddedPerson();
        embeddedPerson.set_id(person.get_id());
        embeddedPerson.setIsikukood(person.getIsikukood());
        embeddedPerson.setRiik_kood(person.getRiik_kood());
        embeddedPerson.setE_meil(person.getE_meil());
        embeddedPerson.setSynni_kp(person.getSynni_kp());
        embeddedPerson.setReg_aeg(person.getReg_aeg());

        if (person.getEesnimi() != null) {
            embeddedPerson.setEesnimi(person.getEesnimi());
        }
        if (person.getPerenimi() != null) {
            embeddedPerson.setPerenimi(person.getPerenimi());
        }
        if (person.getElukoht() != null) {
            embeddedPerson.setElukoht(person.getElukoht());
        }

        return embeddedPerson;
    }

    private List<Person> convertEmbeddedPersonListToPersonList(List<EmbeddedPerson> embeddedPersonList) {
        List<Person> results = new ArrayList<>();
        for (EmbeddedPerson embeddedPerson: embeddedPersonList) {
            results.add(convertEmbeddedPersonToPerson(embeddedPerson));
        }
        return results;
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

    public Person addPerson(Person person) {
        EmbeddedPerson repoResponse = mongoDbEmbPersonRepository.addPerson(convertPersonToEmbeddedPerson(person));
        System.out.println(repoResponse);
        return convertEmbeddedPersonToPerson(repoResponse);
    }

    public boolean addUserAccountToPerson(String personId, EmbeddedUserAccount embeddedUserAccount) {
        return mongoDbEmbPersonRepository.addUserAccountToPerson(personId, embeddedUserAccount);
    }

    public boolean addEmployeeToPerson(String personId, EmbeddedEmployee embeddedEmployee) {
        return mongoDbEmbPersonRepository.addEmployeeToPerson(personId, embeddedEmployee);
    }

    public boolean addEmploymentToEmployee(String personId, EmbeddedEmployment embeddedEmployment) {
       return  mongoDbEmbPersonRepository.addEmploymentToEmployee(personId, embeddedEmployment);
    }

    public boolean updatePerson(Person person) {
        return mongoDbEmbPersonRepository.updatePerson(convertPersonToEmbeddedPerson(person));
    }

    public boolean updateUserAccount(String personId, EmbeddedUserAccount embeddedUserAccount) {
        return mongoDbEmbPersonRepository.updateUserAccount(personId, embeddedUserAccount);
    }

    public boolean updateEmployee(Employee employee) {
        return mongoDbEmbPersonRepository.updateEmployee(employee.getIsik_id(), convertEmployeeToEmbeddedEmployee(employee));
    }

    /*
    public boolean endActiveEmployment(String personId, Integer occupationCode) {
        return mongoDbEmbPersonRepository.endActiveEmployment(personId, occupationCode);
    }

     */

    public boolean deletePersonUserAccount(String personId) {
        return mongoDbEmbPersonRepository.deleteUserAccount(personId);
    }

    public EmbeddedPerson deletePerson(String personId) {
        return mongoDbEmbPersonRepository.deletePerson(personId);
    }


    //-----------new methods

    private Employee convertEmbeddedPersonToEmployee(EmbeddedPerson embeddedPerson) {
        Employee employee = new Employee();
        employee.setIsik_id(embeddedPerson.get_id());
        employee.setTootaja_seisundi_liik_kood(embeddedPerson.getTootaja().getTootaja_seisundi_liik_kood());
        if (embeddedPerson.getTootaja().getMentor_id() != null) {
            employee.setMentor_id(embeddedPerson.getTootaja().getMentor_id());
        }
        return employee;
    }

    private EmbeddedEmployee convertEmployeeToEmbeddedEmployee(Employee employee) {
        EmbeddedEmployee embeddedEmployee = new EmbeddedEmployee();
        embeddedEmployee.setTootaja_seisundi_liik_kood(employee.getTootaja_seisundi_liik_kood());
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

    public boolean addEmployee(Employee employee) {
        EmbeddedEmployee convertedEmployee = convertEmployeeToEmbeddedEmployee(employee);
        return mongoDbEmbPersonRepository.addEmployeeToPerson(employee.getIsik_id(), convertedEmployee);
    }

    private EmbeddedEmployment convertEmploymentToEmbeddedEmployment(Employment employment) {
        EmbeddedEmployment embeddedEmployment = new EmbeddedEmployment();
        embeddedEmployment.setAmet_kood(employment.getAmet_kood());
        if (employment.getAlguse_aeg() != null) {
            embeddedEmployment.setAlguse_aeg(employment.getAlguse_aeg());
        }
        if (employment.getLopu_aeg() != null) {
            embeddedEmployment.setLopu_aeg(employment.getLopu_aeg());
        }
        return embeddedEmployment;
    }

    private List<Employment> convertEmbeddedEmploymentsToEmployments(EmbeddedPerson embeddedPerson) {
        String person_id = embeddedPerson.get_id();
        List<EmbeddedEmployment> embeddedEmployments = embeddedPerson.getTootaja().getAmetis_tootamine();
        List<Employment> convertedEmployments = new ArrayList<>();
        if (embeddedEmployments == null || embeddedEmployments.size() == 0) {
            return convertedEmployments;
        }
        for (EmbeddedEmployment embeddedEmployment : embeddedEmployments) {
            Employment employment = new Employment();
            employment.setIsik_id(person_id);
            employment.setAmet_kood(embeddedEmployment.getAmet_kood());
            employment.setAlguse_aeg(embeddedEmployment.getAlguse_aeg());
            if (embeddedEmployment.getLopu_aeg() != null) {
                employment.setLopu_aeg(embeddedEmployment.getLopu_aeg());
            }
            convertedEmployments.add(employment);
        }

        return convertedEmployments;
    }

    public boolean addEmployment(Employment employment) {
        return mongoDbEmbPersonRepository.addEmploymentToEmployee(employment.getIsik_id(), convertEmploymentToEmbeddedEmployment(employment));
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
                if (Objects.equals(employment.getAmet_kood(), occupationCode)) {
                    result.add(employment);
                }
            }
        }

        return result;
    }

    public boolean endEmployeeActiveEmployment(Employment employment) {
        return mongoDbEmbPersonRepository.endActiveEmployment(employment);
    }

    public boolean endEmployeeAllEmployments(Employment employment) {
        return mongoDbEmbPersonRepository.endAllEmployments(employment);
    }

    public boolean deleteEmployeeByPersonId(String personId) {
        List<Employment> employeeEmployments = getEmployeeAllEmployments(personId);
        for (Employment employment : employeeEmployments) {
            if (employment.getLopu_aeg() == null) {
                return false;
            }
        }
        return mongoDbEmbPersonRepository.deleteEmployee(personId);
    }
}
