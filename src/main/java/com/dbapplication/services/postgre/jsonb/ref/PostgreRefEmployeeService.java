package com.dbapplication.services.postgre.jsonb.ref;

import com.dbapplication.models.postgre.jsonb.ref.EmployeeRef;
import com.dbapplication.models.postgre.traditional.Employee;
import com.dbapplication.repositories.postgre.jsonb.ref.PostgreRefEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("postgreref")
public class PostgreRefEmployeeService {

    @Autowired
    private PostgreRefEmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        List<EmployeeRef> repoRes = employeeRepository.findAll();
        List<Employee> res = new ArrayList<>();
        for (EmployeeRef employeeRef:repoRes) {
            res.add(new Employee(employeeRef.getPerson_id(), employeeRef.getMentor_id(), employeeRef.getEmployee_status_type_code()));
        }
        return res;
    }

    public Employee getEmployeeByPersonId(Long personId) {
        EmployeeRef employeeRef = employeeRepository.findById(personId).orElse(null);
        return employeeRef == null
                ? null
                : new Employee(employeeRef.getPerson_id(), employeeRef.getMentor_id(), employeeRef.getEmployee_status_type_code());
    }

    public EmployeeRef addEmployee(Employee employee) throws Throwable {
        if (employeeRepository.findById(employee.getPerson_id()).isPresent()) {
            throw new Exception(new Throwable("this person is already registered as an employee!"));
        }
        EmployeeRef employeeRef = new EmployeeRef(employee.getPerson_id(), employee.getMentor_id(), employee.getEmployee_status_type_code());
        try {
            return employeeRepository.save(employeeRef);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public EmployeeRef updateEmployee(Employee employee) throws Throwable {
        EmployeeRef employeeRef = new EmployeeRef(employee.getPerson_id(), employee.getMentor_id(), employee.getEmployee_status_type_code());
        try {
            return employeeRepository.save(employeeRef);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public void deleteEmployeeByPersonId(Long personId) {
        employeeRepository.deleteById(personId);
    }
}
