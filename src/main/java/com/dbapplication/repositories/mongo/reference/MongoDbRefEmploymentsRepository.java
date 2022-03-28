package com.dbapplication.repositories.mongo.reference;

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
        queryFindByOccupationCode.addCriteria(Criteria.where("lopu_aeg").is(null));

        return universalMongoTemplate.getAllByQuery(queryFindByOccupationCode, Employment.class);
    }

    public List<Employment> getEmployeeAllEmployments(String personId) {
        Query queryFindPersonAllEmployments = new Query();
        queryFindPersonAllEmployments.addCriteria(Criteria.where("isik_id").is(new ObjectId(personId)));

        return universalMongoTemplate.getAllByQuery(queryFindPersonAllEmployments, Employment.class);
    }

    public Employment.EmploymentDbEntry addEmployment(Employment employment) {
        Employment.EmploymentDbEntry dbEntry = new Employment.EmploymentDbEntry(
                employment.getIsik_id(),
                employment.getAmet_kood(),
                employment.getAlguse_aeg()
        );
        return universalMongoTemplate.addEntity(dbEntry);
    }

    public boolean endEmployeeActiveEmployment(Employment employment) {
        Query queryFindPersonActiveEmploymentInOccupation = new Query();
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("isik_id").is(new ObjectId(employment.getIsik_id())));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("amet_kood").is(employment.getAmet_kood()));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("lopu_aeg").is(null));

        Update updatableInfo = new Update().set("lopu_aeg", employment.getLopu_aeg());
        return universalMongoTemplate.updateEntity(
                queryFindPersonActiveEmploymentInOccupation,
                updatableInfo,
                Employment.class
        );
    }

    public boolean endEmployeeAllEmployments(Employment employment) {
        Query queryFindPersonActiveEmploymentInOccupation = new Query();
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("isik_id").is(new ObjectId(employment.getIsik_id())));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("lopu_aeg").is(null));

        Update updatableInfo = new Update().set("lopu_aeg", employment.getLopu_aeg());
        return universalMongoTemplate.updateAllEntities(
                queryFindPersonActiveEmploymentInOccupation,
                updatableInfo,
                Employment.class
        );
    }
}
