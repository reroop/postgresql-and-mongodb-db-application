package com.dbapplication.repositories.mongo.reference;

import com.dbapplication.models.mongo.reference.Employee;
import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class MongoDbRefEmploymentsRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;

    public List<Employment> getAllEmployments() {
        return universalMongoTemplate.getAll(Employment.class);
    }

    public List<Employment> getEmploymentsByOccupationCode(Integer occupationCode) {
        Query queryFindByOccupationCode = new Query(Criteria.where("amet_kood").is(occupationCode));
        return universalMongoTemplate.getAllByQuery(queryFindByOccupationCode, Employment.class);
    }

    public List<Employment> getEmployeeActiveEmployments(String personId) {
        Query queryFindPersonActiveEmployments = new Query();
        queryFindPersonActiveEmployments.addCriteria(Criteria.where("isik_id").is(new ObjectId(personId)));
        queryFindPersonActiveEmployments.addCriteria(Criteria.where("lopu_aeg").is(null));

        return universalMongoTemplate.getAllByQuery(queryFindPersonActiveEmployments, Employment.class);
    }

    public Employment.EmploymentDbEntry addEmployment(Employment employment) {
        Employment.EmploymentDbEntry dbEntry = new Employment.EmploymentDbEntry(
                employment.getIsik_id(),
                employment.getAmet_kood(),
                employment.getAlguse_aeg()
        );
        return universalMongoTemplate.addEntity(dbEntry);
    }

    public boolean endEmployeeActiveEmployment(String personId, Integer occupationCode) {
        Query queryFindPersonActiveEmploymentInOccupation = new Query();
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("isik_id").is(new ObjectId(personId)));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("amet_kood").is(occupationCode));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("lopu_aeg").is(null));

        Update updatableInfo = new Update().set("lopu_aeg", LocalDateTime.now());
        return universalMongoTemplate.updateEntity(
                queryFindPersonActiveEmploymentInOccupation,
                updatableInfo,
                Employment.class
        );
    }
}
