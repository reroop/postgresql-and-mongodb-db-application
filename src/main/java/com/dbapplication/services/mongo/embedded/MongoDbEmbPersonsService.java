package com.dbapplication.services.mongo.embedded;

import com.dbapplication.models.mongo.embedded.EmbeddedEmployee;
import com.dbapplication.models.mongo.embedded.EmbeddedEmployment;
import com.dbapplication.models.mongo.embedded.EmbeddedPerson;
import com.dbapplication.models.mongo.embedded.EmbeddedUserAccount;
import com.dbapplication.repositories.mongo.embedded.MongoDbEmbPersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoDbEmbPersonsService {

    @Autowired
    private MongoDbEmbPersonRepository mongoDbEmbPersonRepository;

    public List<EmbeddedPerson> getAllPersons() {
        return mongoDbEmbPersonRepository.getAllPersons();
    }

    public EmbeddedPerson getPersonBy_id(String objectId) {
        return mongoDbEmbPersonRepository.getPersonBy_id(objectId);
    }

    public EmbeddedPerson addPerson(EmbeddedPerson embeddedPerson) {
        return mongoDbEmbPersonRepository.addPerson(embeddedPerson);
    }

    public boolean addUserAccountToPerson(String personId, EmbeddedUserAccount embeddedUserAccount) {
        return mongoDbEmbPersonRepository.addUserAccountToPerson(personId, embeddedUserAccount);
    }

    public boolean addEmployeeToPerson(String personId, EmbeddedEmployee embeddedEmployee) {
        return mongoDbEmbPersonRepository.addEmployeeToPerson(personId, embeddedEmployee);
    }

    public boolean addEmploymentToEmployee(String personId, EmbeddedEmployment embeddedEmployment) {
       return  mongoDbEmbPersonRepository.addEmploymentToEmployee(personId, embeddedEmployment);
    }

    public boolean updatePerson(EmbeddedPerson embeddedPerson) {
        return mongoDbEmbPersonRepository.updatePerson(embeddedPerson);
    }

    public boolean updateUserAccount(String personId, EmbeddedUserAccount embeddedUserAccount) {
        return mongoDbEmbPersonRepository.updateUserAccount(personId, embeddedUserAccount);
    }

    public boolean updateEmployee(String personId, EmbeddedEmployee embeddedEmployee) {
        return mongoDbEmbPersonRepository.updateEmployee(personId, embeddedEmployee);
    }

    public boolean endActiveEmployment(String personId, Integer occupationCode) {
        return mongoDbEmbPersonRepository.endActiveEmployment(personId, occupationCode);
    }
}
