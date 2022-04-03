package com.dbapplication.services.postgre.ref;

import com.dbapplication.models.postgre.ref.EmployeeStatusTypeRef;
import com.dbapplication.repositories.postgre.ref.PostgreRefEmployeeStatusTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgreref")
public class PostgreRefEmployeeStatusTypeService {

    @Autowired
    private PostgreRefEmployeeStatusTypeRepository statusTypeRepository;

    public List<EmployeeStatusTypeRef> getAllEmployeeStatusTypes() {
        return statusTypeRepository.findAll();
    }

    public EmployeeStatusTypeRef getEmployeeStatusTypeByEmployeeStatusTypeCode(Integer statusTypeCode) {
        return statusTypeRepository.findById(statusTypeCode).orElse(null);
    }

    public EmployeeStatusTypeRef addEmployeeStatusType(EmployeeStatusTypeRef statusTypeRef) {
        return statusTypeRepository.findById(statusTypeRef.getEmployee_status_type_code()).isPresent()
                ? null
                : statusTypeRepository.save(statusTypeRef);
    }
}
