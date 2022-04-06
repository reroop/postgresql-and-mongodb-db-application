package com.dbapplication.repositories.mongo.reference;

import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import com.dbapplication.utils.mongodb.ValidationChecks;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dbapplication.utils.mongodb.ValidationChecks.isDateInRange2010to2100;
import static com.dbapplication.utils.mongodb.ValidationChecks.isFirstDateBeforeSecondDate;

@Slf4j
@Component
public class MongoDbRefEmploymentRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;

    public List<Employment> getAllEmployments() {
        return universalMongoTemplate.getAll(Employment.class);
    }

    public List<Employment> getEmploymentsByOccupationCode(Integer occupationCode) {
        Query queryFindByOccupationCode = new Query(Criteria.where("occupation_code").is(occupationCode));
        queryFindByOccupationCode.addCriteria(Criteria.where("end_time").is(null));

        return universalMongoTemplate.getAllByQuery(queryFindByOccupationCode, Employment.class);
    }

    public List<Employment> getEmployeeAllEmployments(String personId) {
        Query queryFindPersonAllEmployments = new Query();
        queryFindPersonAllEmployments.addCriteria(Criteria.where("person_id").is(new ObjectId(personId)));

        return universalMongoTemplate.getAllByQuery(queryFindPersonAllEmployments, Employment.class);
    }

    public Employment.EmploymentDbEntry addEmployment(Employment employment) {
        if (!isDateInRange2010to2100(employment.getStart_time())) {
            log.info("add employment, employment date(s) out of range 2010-2100");
            return null;
        }

        //---check if employee is already actively employed in that occupation ---
        Query query = new Query(Criteria.where("occupation_code").is(employment.getOccupation_code()));
        query.addCriteria(Criteria.where("person_id").is(new ObjectId(employment.getPerson_id())));
        query.addCriteria(Criteria.where("end_time").exists(false));

        Employment possibleActiveEmployment = universalMongoTemplate.getOneByQuery(query, Employment.class);
        if (possibleActiveEmployment != null) {
            log.info("add employment, employee already actively employed in that occupation");
            return null;
        }
        //------
        Employment.EmploymentDbEntry dbEntry = new Employment.EmploymentDbEntry(
                employment.getPerson_id(),
                employment.getOccupation_code(),
                employment.getStart_time()
        );
        return universalMongoTemplate.addEntity(dbEntry);
    }

    public boolean endEmployeeActiveEmployment(Employment employment) {
        if (!isDateInRange2010to2100(employment.getEnd_time())) {
            log.info("end active employment, end date not in range");
            return false;
        }

        Query queryFindPersonActiveEmploymentInOccupation = new Query();
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("person_id").is(new ObjectId(employment.getPerson_id())));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("occupation_code").is(employment.getOccupation_code()));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("end_time").is(null));

        Employment modifiableEmployment = universalMongoTemplate.getOneByQuery(queryFindPersonActiveEmploymentInOccupation, Employment.class);
        if (!isFirstDateBeforeSecondDate(modifiableEmployment.getStart_time(), employment.getEnd_time())) {
            log.info("end active employment, end date is before start date");
            return false;
        }

        Update updatableInfo = new Update().set("end_time", employment.getEnd_time());
        return universalMongoTemplate.updateEntity(
                queryFindPersonActiveEmploymentInOccupation,
                updatableInfo,
                Employment.class
        );
    }

    public boolean endEmployeeAllEmployments(Employment employment) {
        if (!isDateInRange2010to2100(employment.getEnd_time())) {
            log.info("end all employments, end date not in range 2010-2100");
            return false;
        }

        Query queryFindPersonActiveEmploymentInOccupation = new Query();
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("person_id").is(new ObjectId(employment.getPerson_id())));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("end_time").is(null));

        List<Employment> modifiableEmployments = universalMongoTemplate.getAllByQuery(queryFindPersonActiveEmploymentInOccupation, Employment.class);
        for (Employment e: modifiableEmployments) {
            if (!isFirstDateBeforeSecondDate(e.getStart_time(), employment.getEnd_time())) {
                log.info("end all employments, end date is before start date");
                return false;
            }
        }

        Update updatableInfo = new Update().set("end_time", employment.getEnd_time());
        return universalMongoTemplate.updateAllEntities(
                queryFindPersonActiveEmploymentInOccupation,
                updatableInfo,
                Employment.class
        );
    }

    public List<Employment> deleteAllEmployeeEmployments(String personId) {
        Query queryFindEmploymentByPersonId = new Query(Criteria.where("person_id").is(new ObjectId(personId)));
        return universalMongoTemplate.deleteAllEntities(queryFindEmploymentByPersonId, Employment.class);
    }
}
