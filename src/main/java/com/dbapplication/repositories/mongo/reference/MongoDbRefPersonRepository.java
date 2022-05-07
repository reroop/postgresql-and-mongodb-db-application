package com.dbapplication.repositories.mongo.reference;

import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

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

    public Person addPerson(Person person) throws Throwable {
        return universalMongoTemplate.addEntity(person);
    }

    public boolean updatePerson(Person person) throws Throwable {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(person.get_id()));
        Update updatableInfo = new Update();
        if (person.getNat_id_code() != null) {
            updatableInfo.set("nat_id_code", person.getNat_id_code());
        }
        if (person.getCountry_code() != null) {
            updatableInfo.set("country_code", person.getCountry_code());
        }
        if (person.getPerson_status_type_code() != null) {
            updatableInfo.set("person_status_type_code", person.getPerson_status_type_code());
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
        //set tel_nr to null if not specified
        if (person.getTel_nr() != null && person.getTel_nr().length() != 0) {
            updatableInfo.set("tel_nr", person.getTel_nr());
        } else {
            updatableInfo.unset("tel_nr");
        }
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, Person.class);
    }
}
