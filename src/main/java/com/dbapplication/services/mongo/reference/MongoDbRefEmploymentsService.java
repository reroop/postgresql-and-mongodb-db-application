package com.dbapplication.services.mongo.reference;

import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.repositories.mongo.reference.MongoDbRefEmploymentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MongoDbRefEmploymentsService {

    @Autowired
    private MongoDbRefEmploymentsRepository mongoDbRefEmploymentsRepository;

    public List<Employment> getAllEmployments() {
        return mongoDbRefEmploymentsRepository.getAllEmployments();
    }

    public List<Employment> getAllEmploymentsByOccupationCode(Integer occupationCode) {
        return mongoDbRefEmploymentsRepository.getEmploymentsByOccupationCode(occupationCode);
    }

    public List<Employment> getEmployeeAllEmployments(String personId) {
        return mongoDbRefEmploymentsRepository.getEmployeeAllEmployments(personId);
    }

    public Employment.EmploymentDbEntry addEmployment(Employment employment) {
        return mongoDbRefEmploymentsRepository.addEmployment(employment);
    }

    public boolean endEmployeeActiveEmployment(String personId, Integer occupationCode) {
        return mongoDbRefEmploymentsRepository.endEmployeeActiveEmployment(personId, occupationCode);
    }
}
