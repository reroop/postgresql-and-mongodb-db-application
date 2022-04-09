package com.dbapplication.services.postgre.traditional;

import com.dbapplication.models.postgre.traditional.Employee;
import com.dbapplication.repositories.postgre.traditional.PostgreTradEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgretrad")
public class PostgreTradEmployeeService {

    @Autowired
    private PostgreTradEmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByPersonId(Long personId) {
        return employeeRepository.findById(personId).orElse(null);
    }

    public Employee addEmployee(Employee employee) throws Throwable {
        if (employeeRepository.findById(employee.getPerson_id()).isPresent()) {
            throw new Exception(new Throwable("this person is already registered as an employee!"));
        }
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }

    }

    public Employee updateEmployee(Employee employee) throws Throwable {
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public void deleteEmployeeByPersonId(Long personId) {
        employeeRepository.deleteById(personId);
    }
}
