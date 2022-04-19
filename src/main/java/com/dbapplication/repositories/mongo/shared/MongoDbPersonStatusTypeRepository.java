package com.dbapplication.repositories.mongo.shared;

import com.dbapplication.models.mongo.shared.PersonStatusType;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MongoDbPersonStatusTypeRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;

    public List<PersonStatusType> getAllPersonStatusTypes() {
        return universalMongoTemplate.getAll(PersonStatusType.class);
    }

    public PersonStatusType getPersonStatusTypeByPersonStatusTypeCode(Integer personStatusTypeCode) {
        Query queryFindByPersonStatusTypeCode = new Query(Criteria.where("person_status_type_code").is(personStatusTypeCode));
        return universalMongoTemplate.getOneByQuery(queryFindByPersonStatusTypeCode, PersonStatusType.class);
    }

    public PersonStatusType addPersonStatusType(PersonStatusType personStatusType) {
        return universalMongoTemplate.addEntity(personStatusType);
    }
}
