package com.dbapplication.services.mongo.shared;

import com.dbapplication.models.mongo.shared.PersonStatusType;
import com.dbapplication.repositories.mongo.shared.MongoDbPersonStatusTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoDbPersonStatusTypeService {

    @Autowired
    MongoDbPersonStatusTypeRepository mongoDbPersonStatusTypeRepository;

    public List<PersonStatusType> getAllPersonStatusTypes() {
        return mongoDbPersonStatusTypeRepository.getAllPersonStatusTypes();
    }

    public PersonStatusType getPersonStatusTypeByPersonStatusTypeCode(Integer personStatusTypeCode) {
        return mongoDbPersonStatusTypeRepository.getPersonStatusTypeByPersonStatusTypeCode(personStatusTypeCode);
    }

    public PersonStatusType addPersonStatusType(PersonStatusType personStatusType) {
        return mongoDbPersonStatusTypeRepository.addPersonStatusType(personStatusType);
    }
}
