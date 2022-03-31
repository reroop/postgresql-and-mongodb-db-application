package com.dbapplication.services.postgre;

import com.dbapplication.models.postgre.Employee;
import com.dbapplication.repositories.postgre.PostgreTradEmployeeRepository;
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

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeByPersonId(Long personId) {
        employeeRepository.deleteById(personId);
    }
}
