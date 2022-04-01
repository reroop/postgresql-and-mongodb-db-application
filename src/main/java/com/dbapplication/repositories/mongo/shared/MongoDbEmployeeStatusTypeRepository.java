package com.dbapplication.repositories.mongo.shared;

import com.dbapplication.models.mongo.shared.EmployeeStatusType;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MongoDbEmployeeStatusTypeRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;

    public List<EmployeeStatusType> getAllEmployeeStatusTypes() {
        return universalMongoTemplate.getAll(EmployeeStatusType.class);
    }

    public EmployeeStatusType getEmployeeStatusTypeByEmployeeStatusTypeCode(Integer employeeStatusTypeCode) {
        Query queryFindByEmployeeStatusTypeCode = new Query(Criteria.where("employee_status_type_code").is(employeeStatusTypeCode));
        return universalMongoTemplate.getOneByQuery(queryFindByEmployeeStatusTypeCode, EmployeeStatusType.class);
    }

    public EmployeeStatusType addEmployeeStatusType(EmployeeStatusType employeeStatusType) {
        return universalMongoTemplate.addEntity(employeeStatusType);
    }
}
