package com.dbapplication.services.mongo.reference;

import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.repositories.mongo.reference.MongoDbRefPersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.dbapplication.utils.ValidationChecks.isMongoPersonInfoValid;

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
        person.setReg_time(LocalDateTime.now());
        if (!isMongoPersonInfoValid(person)) {
            return null;
        }
        return mongoDbRefPersonRepository.addPerson(person);
    }

    public boolean updatePerson(Person person) throws Throwable {
        if (!isMongoPersonInfoValid(person)) {
            return false;
        }
        return mongoDbRefPersonRepository.updatePerson(person);
    }
}
