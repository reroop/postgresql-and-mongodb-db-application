package com.dbapplication.services.postgre.jsonb.common;

import com.dbapplication.models.postgre.jsonb.common.EmployeeStatusTypePostgreJsonCommon;
import com.dbapplication.models.postgre.traditional.EmployeeStatusType;
import com.dbapplication.repositories.postgre.jsonb.common.PostgreJsonCommonEmployeeStatusTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertEmployeeStatusTypePostgreJsonCommonListToEmployeeStatusTypeList;
import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertEmployeeStatusTypePostgreJsonCommonToEmployeeStatusType;

@Service
@Profile("postgreref")
public class PostgreJsonCommonEmployeeStatusTypeService {

    @Autowired
    private PostgreJsonCommonEmployeeStatusTypeRepository statusTypeRepository;

    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return convertEmployeeStatusTypePostgreJsonCommonListToEmployeeStatusTypeList(statusTypeRepository.findAll());
    }

    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(Integer statusTypeCode) {
        EmployeeStatusTypePostgreJsonCommon employeeStatusTypePostgreJsonCommon = statusTypeRepository.findById(statusTypeCode).orElse(null);
        return employeeStatusTypePostgreJsonCommon == null
                ? null
                : convertEmployeeStatusTypePostgreJsonCommonToEmployeeStatusType(employeeStatusTypePostgreJsonCommon);

    }

    public EmployeeStatusTypePostgreJsonCommon addEmployeeStatusType(EmployeeStatusTypePostgreJsonCommon statusTypeRef) {
        return statusTypeRepository.findById(statusTypeRef.getEmployee_status_type_code()).isPresent()
                ? null
                : statusTypeRepository.save(statusTypeRef);
    }
}
