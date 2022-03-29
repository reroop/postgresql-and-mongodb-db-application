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

@Slf4j
@Component
public class MongoDbRefEmploymentRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;
    private final ValidationChecks validationChecks = new ValidationChecks();

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
        if (!validationChecks.isDateInRange2010to2100(employment.getAlguse_aeg())) {
            log.info("add employment, employment date(s) out of range 2010-2100");
            return null;
        }

        //---check if employee is already actively employed in that occupation ---
        Query query = new Query(Criteria.where("amet_kood").is(employment.getAmet_kood()));
        query.addCriteria(Criteria.where("isik_id").is(new ObjectId(employment.getIsik_id())));
        query.addCriteria(Criteria.where("lopu_aeg").is(null));

        Employment possibleActiveEmployment = universalMongoTemplate.getOneByQuery(query, Employment.class);
        if (possibleActiveEmployment != null) {
            log.info("add employment, employee already actively employed in that occupation");
            return null;
        }
        //------
        Employment.EmploymentDbEntry dbEntry = new Employment.EmploymentDbEntry(
                employment.getIsik_id(),
                employment.getAmet_kood(),
                employment.getAlguse_aeg()
        );
        return universalMongoTemplate.addEntity(dbEntry);
    }

    public boolean endEmployeeActiveEmployment(Employment employment) {
        if (!validationChecks.isDateInRange2010to2100(employment.getLopu_aeg())) {
            log.info("end employment, end date not in range");
            return false;
        }

        Query queryFindPersonActiveEmploymentInOccupation = new Query();
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("isik_id").is(new ObjectId(employment.getIsik_id())));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("amet_kood").is(employment.getAmet_kood()));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("lopu_aeg").is(null));

        Employment modifiableEmployment = universalMongoTemplate.getOneByQuery(queryFindPersonActiveEmploymentInOccupation, Employment.class);
        if (!validationChecks.isFirstDateBeforeSecondDate(modifiableEmployment.getAlguse_aeg(), employment.getLopu_aeg())) {
            log.info("end active employment, start date is after requested end date");
            return false;
        }

        Update updatableInfo = new Update().set("lopu_aeg", employment.getLopu_aeg());
        return universalMongoTemplate.updateEntity(
                queryFindPersonActiveEmploymentInOccupation,
                updatableInfo,
                Employment.class
        );
    }

    public boolean endEmployeeAllEmployments(Employment employment) {
        if (!validationChecks.isDateInRange2010to2100(employment.getLopu_aeg())) {
            log.info("end all employments, end date not in range");
            return false;
        }

        Query queryFindPersonActiveEmploymentInOccupation = new Query();
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("isik_id").is(new ObjectId(employment.getIsik_id())));
        queryFindPersonActiveEmploymentInOccupation.addCriteria(Criteria.where("lopu_aeg").is(null));

        List<Employment> modifiableEmployments = universalMongoTemplate.getAllByQuery(queryFindPersonActiveEmploymentInOccupation, Employment.class);
        for (Employment e: modifiableEmployments) {
            if (!validationChecks.isFirstDateBeforeSecondDate(e.getAlguse_aeg(), employment.getLopu_aeg())) {
                log.info("end all employments, start date is after requested end date");
                return false;
            }
        }

        Update updatableInfo = new Update().set("lopu_aeg", employment.getLopu_aeg());
        return universalMongoTemplate.updateAllEntities(
                queryFindPersonActiveEmploymentInOccupation,
                updatableInfo,
                Employment.class
        );
    }
}
