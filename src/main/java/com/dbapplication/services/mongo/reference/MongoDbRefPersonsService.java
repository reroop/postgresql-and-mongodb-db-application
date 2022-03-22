package com.dbapplication.services.mongo.reference;

import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.repositories.mongo.reference.MongoDbRefPersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoDbRefPersonsService {

    @Autowired
    private MongoDbRefPersonRepository mongoDbRefPersonRepository;

    public List<Person> getAllPersons() {
        return mongoDbRefPersonRepository.getAllPersons();
    }

    public Person getPersonBy_id(String objectId) {
        return mongoDbRefPersonRepository.getPersonBy_id(objectId);
    }

    public Person getPersonByCountryCodeAndPersonalIdCode(String countryCode, String personalIdCode) {
        return mongoDbRefPersonRepository.getPersonByCountryCodeAndPersonalIdCode(countryCode, personalIdCode);
    }

    public Person addPerson(Person person) {
        return mongoDbRefPersonRepository.addPerson(person);
    }

    public Person deletePersonBy_id(String objectId) {
        return mongoDbRefPersonRepository.deletePersonBy_id(objectId);
    }

    public boolean updatePerson(Person person) {
        return mongoDbRefPersonRepository.updatePerson(person);
    }
}
