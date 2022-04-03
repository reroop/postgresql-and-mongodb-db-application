package com.dbapplication.services.postgre.ref;

import com.dbapplication.models.postgre.ref.EmployeeRef;
import com.dbapplication.repositories.postgre.ref.PostgreRefEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgreref")
public class PostgreRefEmployeeService {

    @Autowired
    private PostgreRefEmployeeRepository employeeRepository;

    public List<EmployeeRef> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public EmployeeRef getEmployeeByPersonId(Long personId) {
        return employeeRepository.findById(personId).orElse(null);
    }

    public EmployeeRef addEmployee(EmployeeRef employeeRef) {
        return employeeRepository.save(employeeRef);
    }

    public EmployeeRef updateEmployee(EmployeeRef employeeRef) {
        return employeeRepository.save(employeeRef);
    }

    public void deleteEmployeeByPersonId(Long personId) {
        employeeRepository.deleteById(personId);
    }
}
