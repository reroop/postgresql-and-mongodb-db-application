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

import static com.dbapplication.utils.ValidationChecks.isMongoPersonInfoValid;

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

    public Person addPerson(Person person) throws Throwable {
        person.setReg_time(LocalDateTime.now());
        if (!isMongoPersonInfoValid(person)) {
            return null;
        }

        return universalMongoTemplate.addEntity(person);
    }

    public boolean updatePerson(Person person) throws Throwable {
        if (!isMongoPersonInfoValid(person)) {
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
