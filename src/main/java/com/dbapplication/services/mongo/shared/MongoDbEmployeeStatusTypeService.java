package com.dbapplication.services.mongo.shared;

import com.dbapplication.models.mongo.shared.EmployeeStatusType;
import com.dbapplication.repositories.mongo.shared.MongoDbEmployeeStatusTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoDbEmployeeStatusTypeService {

    @Autowired
    MongoDbEmployeeStatusTypeRepository mongoDbEmployeeStatusTypeRepository;

    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return mongoDbEmployeeStatusTypeRepository.getAllEmployeeStatusTypes();
    }

    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(Integer employeeStatusTypeCode) {
        return mongoDbEmployeeStatusTypeRepository.getEmployeeStatusTypeByEmployeeStatusTypeCode(employeeStatusTypeCode);
    }

    public EmployeeStatusType addEmployeeStatusType(EmployeeStatusType employeeStatusType) {
        return mongoDbEmployeeStatusTypeRepository.addEmployeeStatusType(employeeStatusType);
    }

    public EmployeeStatusType deleteEmployeeStatusType(Integer employeeStatusTypeCode) {
        return mongoDbEmployeeStatusTypeRepository.deleteEmployeeStatusTypeByEmployeeStatusTypeCode(employeeStatusTypeCode);
    }

    public boolean updateEmployeeStatusType(EmployeeStatusType employeeStatusType) {
        return mongoDbEmployeeStatusTypeRepository.updateEmployeeStatusType(employeeStatusType);
    }
}
