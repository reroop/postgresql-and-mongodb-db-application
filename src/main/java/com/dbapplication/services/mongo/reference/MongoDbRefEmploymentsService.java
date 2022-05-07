package com.dbapplication.services.mongo.reference;

import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.repositories.mongo.reference.MongoDbRefEmploymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dbapplication.utils.ValidationChecks.isDateInRange2010to2100;

@Slf4j
@Component
public class MongoDbRefEmploymentsService {

    @Autowired
    private MongoDbRefEmploymentRepository mongoDbRefEmploymentRepository;

    public List<Employment> getAllEmploymentsByOccupationCode(Integer occupationCode) {
        return mongoDbRefEmploymentRepository.getEmploymentsByOccupationCode(occupationCode);
    }

    public List<Employment> getEmployeeAllEmployments(String personId) {
        return mongoDbRefEmploymentRepository.getEmployeeAllEmployments(personId);
    }

    public Employment.EmploymentDbEntry addEmployment(Employment employment) throws Throwable {
        if (!isDateInRange2010to2100(employment.getStart_time())) {
            log.info("employment dates must be in range 2010-2100! Current start time is " + employment.getStart_time());
            throw new Exception(new Throwable("employment dates must be in range 2010-2100! Current start time is " + employment.getStart_time()));
        }
        return mongoDbRefEmploymentRepository.addEmployment(employment);
    }

    public boolean endEmployeeActiveEmployment(Employment employment) throws Throwable {
        if (!isDateInRange2010to2100(employment.getEnd_time())) {
            log.info("end date " + employment.getEnd_time() + " for employment is not in range 2010-2100!");
            throw new Exception(new Throwable("end date " + employment.getEnd_time() + " for employment is not in range 2010-2100!"));
        }
        return mongoDbRefEmploymentRepository.endEmployeeActiveEmployment(employment);
    }

    public boolean endEmployeeAllEmployments(Employment employment) throws Throwable {
        if (!isDateInRange2010to2100(employment.getEnd_time())) {
            log.info("end date " + employment.getEnd_time() + " for employment is not in range 2010-2100!");
            throw new Exception(new Throwable("end date " + employment.getEnd_time() + " for employment is not in range 2010-2100!"));
        }
        return mongoDbRefEmploymentRepository.endEmployeeAllEmployments(employment);
    }

    public List<Employment> deleteAllEmployeeEmployments(String personId) {
        return mongoDbRefEmploymentRepository.deleteAllEmployeeEmployments(personId);
    }
}
