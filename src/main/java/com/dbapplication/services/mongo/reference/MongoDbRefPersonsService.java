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

    public Person addPerson(Person person) throws Throwable {
        return mongoDbRefPersonRepository.addPerson(person);
    }

    public boolean updatePerson(Person person) throws Throwable {
        return mongoDbRefPersonRepository.updatePerson(person);
    }
}
