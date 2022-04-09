package com.dbapplication.services.mongo.reference;

import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.repositories.mongo.reference.MongoDbRefEmploymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MongoDbRefEmploymentsService {

    @Autowired
    private MongoDbRefEmploymentRepository mongoDbRefEmploymentRepository;

    public List<Employment> getAllEmployments() {
        return mongoDbRefEmploymentRepository.getAllEmployments();
    }

    public List<Employment> getAllEmploymentsByOccupationCode(Integer occupationCode) {
        return mongoDbRefEmploymentRepository.getEmploymentsByOccupationCode(occupationCode);
    }

    public List<Employment> getEmployeeAllEmployments(String personId) {
        return mongoDbRefEmploymentRepository.getEmployeeAllEmployments(personId);
    }

    public Employment.EmploymentDbEntry addEmployment(Employment employment) throws Throwable {
        return mongoDbRefEmploymentRepository.addEmployment(employment);
    }

    public boolean endEmployeeActiveEmployment(Employment employment) throws Throwable {
        return mongoDbRefEmploymentRepository.endEmployeeActiveEmployment(employment);
    }

    public boolean endEmployeeAllEmployments(Employment employment) throws Throwable {
        return mongoDbRefEmploymentRepository.endEmployeeAllEmployments(employment);
    }

    public List<Employment> deleteAllEmployeeEmployments(String personId) {
        return mongoDbRefEmploymentRepository.deleteAllEmployeeEmployments(personId);
    }
}
