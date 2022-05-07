package com.dbapplication.services.mongo.reference;

import com.dbapplication.models.mongo.reference.Employee;
import com.dbapplication.repositories.mongo.reference.MongoDbRefEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MongoDbRefEmployeesService {

    @Autowired
    private MongoDbRefEmployeeRepository mongoDbRefEmployeeRepository;

    public List<Employee> getAllEmployees() {
        return mongoDbRefEmployeeRepository.getAllEmployees();
    }

    public Employee getEmployeeByPersonId(String personId) {
        return mongoDbRefEmployeeRepository.getEmployeeByPersonId(personId);
    }

    public Employee.EmployeeDbEntry addEmployee(Employee employee) throws Throwable {
        if (employee.getMentor_id() != null) {
            if (Objects.equals(employee.getPerson_id(), employee.getMentor_id())) {
                throw new Exception(new Throwable("person can't be his/herself mentor"));
            }
        }
        return mongoDbRefEmployeeRepository.addEmployee(employee);
    }

    public Employee deleteEmployeeByPersonId(String personId) {
        return mongoDbRefEmployeeRepository.deleteEmployeeByPersonId(personId);
    }

    public boolean updateEmployee(Employee employee) throws Throwable {
        if (employee.getMentor_id() != null) {
            if (Objects.equals(employee.getPerson_id(), employee.getMentor_id())) {
                throw new Exception(new Throwable("person can't be his/herself mentor"));
            }
        }
        return mongoDbRefEmployeeRepository.updateEmployee(employee);
    }
}
