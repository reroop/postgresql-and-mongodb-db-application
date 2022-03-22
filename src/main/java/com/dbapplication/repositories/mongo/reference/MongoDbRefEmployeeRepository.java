package com.dbapplication.repositories.mongo.reference;


import com.dbapplication.models.mongo.reference.Employee;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MongoDbRefEmployeeRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;

    public List<Employee> getAllEmployees() {
        return universalMongoTemplate.getAll(Employee.class);
    }

    public Employee getEmployeeByPersonId(String personId) {
        Query queryFindByPersonId = new Query(Criteria.where("isik_id").is(new ObjectId(personId)));
        return universalMongoTemplate.getOneByQuery(queryFindByPersonId, Employee.class);
    }

    public Employee.EmployeeDbEntry addEmployee(Employee employee) {
        Employee.EmployeeDbEntry dbEntry = new Employee.EmployeeDbEntry(
                employee.getIsik_id(),
                employee.getTootaja_seisundi_liik_kood()
        );
        if (employee.getMentor_id() != null) {
            dbEntry.setMentor_id(new ObjectId(employee.getMentor_id()));
        }
        return universalMongoTemplate.addEntity(dbEntry);
    }

    public Employee deleteEmployeeByPersonId(String personId) {
        Query queryFindByPersonId = new Query(Criteria.where("isik_id").is(new ObjectId(personId)));
        return universalMongoTemplate.deleteEntity(queryFindByPersonId, Employee.class);
    }

    public boolean updateEmployee(Employee employee) {
        log.info(employee.toString());
        Query queryFindByPersonId = new Query(Criteria.where("isik_id").is(new ObjectId(employee.getIsik_id())));

        Update updatableInfo = new Update();
        if (employee.getTootaja_seisundi_liik_kood() != null) {
            updatableInfo.set("tootaja_seisundi_liik_kood", employee.getTootaja_seisundi_liik_kood());
        }
        //NOTE: mentorId is deleted if it is not set in Employee
        if (employee.getMentor_id() != null) {
            updatableInfo.set("mentor_id", new ObjectId(employee.getMentor_id()));
        } else {
            updatableInfo.unset("mentor_id");
        }
        return universalMongoTemplate.updateEntity(queryFindByPersonId, updatableInfo, Employee.class);
    }
}
