package com.dbapplication.repositories.mongo.reference;

import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class MongoDbRefPersonRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;

    public List<Person> getAllPersons() {
        return universalMongoTemplate.getAll(Person.class);
    }

    public Person getPersonBy_id(String objectId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(objectId));
        return universalMongoTemplate.getOneByQuery(queryFindByObjectId, Person.class);
    }

    public Person getPersonByCountryCodeAndPersonalIdCode(String countryCode, String personalIdCode) {
        Query findByCountryAndPersonalCode = new Query();
        findByCountryAndPersonalCode.addCriteria(Criteria.where("riik_kood").is(countryCode));
        findByCountryAndPersonalCode.addCriteria(Criteria.where("isikukood").is(personalIdCode));
        return universalMongoTemplate.getOneByQuery(findByCountryAndPersonalCode, Person.class);
    }

    public Person addPerson(Person person) {
        person.setReg_aeg(LocalDateTime.now());
        return universalMongoTemplate.addEntity(person);
    }

    public Person deletePersonBy_id(String objectId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(objectId));
        return universalMongoTemplate.deleteEntity(queryFindByObjectId, Person.class);
    }

    public boolean updatePerson(Person person) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(person.get_id()));
        Update updatableInfo = new Update();
        if (person.getIsikukood() != null) {
            updatableInfo.set("isikukood", person.getIsikukood());
        }
        if (person.getRiik_kood() != null) {
            updatableInfo.set("riik_kood", person.getRiik_kood());
        }
        if (person.getE_meil() != null) {
            updatableInfo.set("e_meil", person.getE_meil());
        }
        if (person.getSynni_kp() != null) {
            updatableInfo.set("synni_kp", person.getSynni_kp());
        }
        //set firstname to null if not specified
        if (person.getEesnimi() != null && person.getEesnimi().length() != 0) {
            updatableInfo.set("eesnimi", person.getEesnimi());
        } else {
            updatableInfo.unset("eesnimi");
        }
        //set lastname to null if not specified
        if (person.getPerenimi() != null && person.getPerenimi().length() != 0) {
            updatableInfo.set("perenimi", person.getPerenimi());
        } else {
            updatableInfo.unset("perenimi");
        }
        //set address to null if not specified
        if (person.getElukoht() != null && person.getElukoht().length() != 0) {
            updatableInfo.set("elukoht", person.getElukoht());
        } else {
            updatableInfo.unset("elukoht");
        }
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, Person.class);
    }
}
