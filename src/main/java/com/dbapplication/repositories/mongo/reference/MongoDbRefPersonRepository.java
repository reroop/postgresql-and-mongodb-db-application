package com.dbapplication.repositories.mongo.reference;

import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import com.dbapplication.utils.mongodb.ValidationChecks;
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
    private final ValidationChecks validationChecks = new ValidationChecks();

    public List<Person> getAllPersons() {
        return universalMongoTemplate.getAll(Person.class);
    }

    public Person getPersonBy_id(String objectId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(objectId));
        return universalMongoTemplate.getOneByQuery(queryFindByObjectId, Person.class);
    }

    public Person getPersonByCountryCodeAndPersonalIdCode(String countryCode, String personalIdCode) {
        Query findByCountryAndPersonalCode = new Query();
        findByCountryAndPersonalCode.addCriteria(Criteria.where("country_code").is(countryCode));
        findByCountryAndPersonalCode.addCriteria(Criteria.where("nat_id_code").is(personalIdCode));
        return universalMongoTemplate.getOneByQuery(findByCountryAndPersonalCode, Person.class);
    }

    public Person addPerson(Person person) {
        person.setReg_time(LocalDateTime.now());
        if (!validationChecks.isMongoPersonInfoValid(person)) {
            return null;
        }

        return universalMongoTemplate.addEntity(person);
    }

    public Person deletePersonBy_id(String objectId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(objectId));
        return universalMongoTemplate.deleteEntity(queryFindByObjectId, Person.class);
    }

    public boolean updatePerson(Person person) {
        if (!validationChecks.isMongoPersonInfoValid(person)) {
            return false;
        }

        Query queryFindByObjectId = new Query(Criteria.where("_id").is(person.get_id()));
        Update updatableInfo = new Update();
        if (person.getNat_id_code() != null) {
            updatableInfo.set("nat_id_code", person.getNat_id_code());
        }
        if (person.getCountry_code() != null) {
            updatableInfo.set("country_code", person.getCountry_code());
        }
        if (person.getE_mail() != null) {
            updatableInfo.set("e_mail", person.getE_mail());
        }
        if (person.getBirth_date() != null) {
            updatableInfo.set("birth_date", person.getBirth_date());
        }
        //set firstname to null if not specified
        if (person.getGiven_name() != null && person.getGiven_name().length() != 0) {
            updatableInfo.set("given_name", person.getGiven_name());
        } else {
            updatableInfo.unset("given_name");
        }
        //set lastname to null if not specified
        if (person.getSurname() != null && person.getSurname().length() != 0) {
            updatableInfo.set("surname", person.getSurname());
        } else {
            updatableInfo.unset("surname");
        }
        //set address to null if not specified
        if (person.getAddress() != null && person.getAddress().length() != 0) {
            updatableInfo.set("address", person.getAddress());
        } else {
            updatableInfo.unset("address");
        }
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, Person.class);
    }
}
