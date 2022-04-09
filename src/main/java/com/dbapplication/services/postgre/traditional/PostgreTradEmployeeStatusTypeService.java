package com.dbapplication.services.postgre.traditional;

import com.dbapplication.models.postgre.traditional.EmployeeStatusType;
import com.dbapplication.repositories.postgre.traditional.PostgreTradEmployeeStatusTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgretrad")
public class PostgreTradEmployeeStatusTypeService {

    @Autowired
    private PostgreTradEmployeeStatusTypeRepository statusTypeRepository;

    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return statusTypeRepository.findAll();
    }

    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(Integer statusTypeCode) {
        return statusTypeRepository.findById(statusTypeCode).orElse(null);
    }

    public EmployeeStatusType addEmployeeStatusType(EmployeeStatusType employeeStatusType) throws Throwable {
        if (statusTypeRepository.findById(employeeStatusType.getEmployee_status_type_code()).isPresent()) {
            throw new Exception(new Throwable("employee status type with code " + employeeStatusType.getEmployee_status_type_code() + " is already present!"));
        }
        try {
            return statusTypeRepository.save(employeeStatusType);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }
}
