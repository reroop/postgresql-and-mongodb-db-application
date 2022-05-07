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
        Query queryFindByPersonId = new Query(Criteria.where("person_id").is(new ObjectId(personId)));
        return universalMongoTemplate.getOneByQuery(queryFindByPersonId, Employee.class);
    }

    public Employee.EmployeeDbEntry addEmployee(Employee employee) throws Throwable {
        Employee.EmployeeDbEntry dbEntry = new Employee.EmployeeDbEntry(
                employee.getPerson_id(),
                employee.getEmployee_status_type_code()
        );
        if (employee.getMentor_id() != null) {
            dbEntry.setMentor_id(new ObjectId(employee.getMentor_id()));
        }
        if (getEmployeeByPersonId(employee.getPerson_id()) != null) {
            throw new Exception(new Throwable("this person is already registered as an employee"));
        }
        return universalMongoTemplate.addEntity(dbEntry);
    }

    public Employee deleteEmployeeByPersonId(String personId) {
        Query queryFindByPersonId = new Query(Criteria.where("person_id").is(new ObjectId(personId)));
        this.removeDeletedEmployeeAsMentorFromOtherEmployees(personId);
        return universalMongoTemplate.deleteEntity(queryFindByPersonId, Employee.class);
    }

    public boolean updateEmployee(Employee employee) throws Throwable {
        Query queryFindByPersonId = new Query(Criteria.where("person_id").is(new ObjectId(employee.getPerson_id())));

        Update updatableInfo = new Update();
        if (employee.getEmployee_status_type_code() != null) {
            updatableInfo.set("employee_status_type_code", employee.getEmployee_status_type_code());
        }
        //NOTE: mentorId is deleted if it is not set in Employee
        if (employee.getMentor_id() != null) {
            updatableInfo.set("mentor_id", new ObjectId(employee.getMentor_id()));
        } else {
            updatableInfo.unset("mentor_id");
        }
        return universalMongoTemplate.updateEntity(queryFindByPersonId, updatableInfo, Employee.class);
    }

    private boolean removeDeletedEmployeeAsMentorFromOtherEmployees(String deletedEmployeeId) {
        Query queryFindEmployeesWithDeletedMentorId = new Query().addCriteria(Criteria.where("mentor_id").is(new ObjectId(deletedEmployeeId)));
        Update updatableInfo = new Update().unset("mentor_id");
        return universalMongoTemplate.updateAllEntities(queryFindEmployeesWithDeletedMentorId, updatableInfo, Employee.class);
    }
}
