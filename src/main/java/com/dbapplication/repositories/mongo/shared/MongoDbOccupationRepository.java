package com.dbapplication.repositories.mongo.shared;

import com.dbapplication.models.mongo.shared.Occupation;
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
public class MongoDbOccupationRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;

    public List<Occupation> getAllOccupations() {
        return universalMongoTemplate.getAll(Occupation.class);
    }

    public Occupation getOccupationByOccupationCode(Integer occupationCode) {
        Query queryFindByOccupationCode = new Query(Criteria.where("amet_kood").is(occupationCode));
        return universalMongoTemplate.getOneByQuery(queryFindByOccupationCode, Occupation.class);
    }

    public Occupation addOccupation(Occupation occupation) {
        return universalMongoTemplate.addEntity(occupation);
    }

    public Occupation deleteOccupationByOccupationCode(Integer occupationCode) {
        Query queryFindByOccupationCode = new Query(Criteria.where("amet_kood").is(occupationCode));
        return universalMongoTemplate.deleteEntity(queryFindByOccupationCode, Occupation.class);
    }

    public boolean updateOccupation(Occupation occupation) {
        Query queryFindByOccupationCode = new Query(Criteria.where("amet_kood").is(occupation.getAmet_kood()));
        Update updatableInfo = new Update();
        if (occupation.getNimetus() != null) {
            updatableInfo.set("nimetus", occupation.getNimetus());
        }
        if (occupation.getKirjeldus() != null) {
            updatableInfo.set("kirjeldus", occupation.getKirjeldus());
        }
        return universalMongoTemplate.updateEntity(queryFindByOccupationCode, updatableInfo, Occupation.class);
    }
}
