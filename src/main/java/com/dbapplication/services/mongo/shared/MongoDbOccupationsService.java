package com.dbapplication.services.mongo.shared;

import com.dbapplication.models.mongo.shared.Occupation;
import com.dbapplication.repositories.mongo.shared.MongoDbOccupationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoDbOccupationsService {

    @Autowired
    MongoDbOccupationsRepository mongoDbOccupationsRepository;

    public List<Occupation> getAllOccupations() {
        return mongoDbOccupationsRepository.getAllOccupations();
    }

    public Occupation getOccupationByOccupationCode(Integer occupationCode) {
        return mongoDbOccupationsRepository.getOccupationByOccupationCode(occupationCode);
    }

    public Occupation addOccupation(Occupation occupation) {
        return mongoDbOccupationsRepository.addOccupation(occupation);
    }

    public Occupation deleteOccupation(Integer occupationCode) {
        return mongoDbOccupationsRepository.deleteOccupationByOccupationCode(occupationCode);
    }

    public boolean updateOccupation(Occupation occupation) {
        return mongoDbOccupationsRepository.updateOccupation(occupation);
    }
}
