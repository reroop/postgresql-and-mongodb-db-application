package com.dbapplication.services.mongo.shared;

import com.dbapplication.models.mongo.shared.Occupation;
import com.dbapplication.repositories.mongo.shared.MongoDbOccupationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoDbOccupationsService {

    @Autowired
    MongoDbOccupationRepository mongoDbOccupationRepository;

    public List<Occupation> getAllOccupations() {
        return mongoDbOccupationRepository.getAllOccupations();
    }

    public Occupation getOccupationByOccupationCode(Integer occupationCode) {
        return mongoDbOccupationRepository.getOccupationByOccupationCode(occupationCode);
    }

    public Occupation addOccupation(Occupation occupation) {
        return mongoDbOccupationRepository.addOccupation(occupation);
    }

    public Occupation deleteOccupation(Integer occupationCode) {
        return mongoDbOccupationRepository.deleteOccupationByOccupationCode(occupationCode);
    }

    public boolean updateOccupation(Occupation occupation) {
        return mongoDbOccupationRepository.updateOccupation(occupation);
    }
}
