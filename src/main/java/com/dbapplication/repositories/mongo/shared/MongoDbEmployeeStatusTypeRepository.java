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
        Query queryFindByEmployeeStatusTypeCode = new Query(Criteria.where("tootaja_seisundi_liik_kood").is(employeeStatusTypeCode));
        return universalMongoTemplate.getOneByQuery(queryFindByEmployeeStatusTypeCode, EmployeeStatusType.class);
    }

    public EmployeeStatusType addEmployeeStatusType(EmployeeStatusType employeeStatusType) {
        return universalMongoTemplate.addEntity(employeeStatusType);
    }

    public EmployeeStatusType deleteEmployeeStatusTypeByEmployeeStatusTypeCode(Integer employeeStatusTypeCode) {
        Query queryFindByEmployeeStatusTypeCode = new Query(Criteria.where("tootaja_seisundi_liik_kood").is(employeeStatusTypeCode));
        return universalMongoTemplate.deleteEntity(queryFindByEmployeeStatusTypeCode, EmployeeStatusType.class);
    }

    public boolean updateEmployeeStatusType(EmployeeStatusType employeeStatusType) {
        Query queryFindByEmployeeStatusTypeCode = new Query(Criteria.where("tootaja_seisundi_liik_kood").is(employeeStatusType.getTootaja_seisundi_liik_kood()));
        Update updatableInfo = new Update();
        if (employeeStatusType.getNimetus() != null) {
            updatableInfo.set("nimetus", employeeStatusType.getNimetus());
        }
        if (employeeStatusType.getKirjeldus() != null) {
            updatableInfo.set("kirjeldus", employeeStatusType.getKirjeldus());
        } else {
            updatableInfo.unset("kirjeldus");
        }
        return universalMongoTemplate.updateEntity(queryFindByEmployeeStatusTypeCode, updatableInfo, EmployeeStatusType.class);
    }
}
