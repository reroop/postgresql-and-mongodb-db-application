package com.dbapplication.repositories.mongo.reference;

import com.dbapplication.models.mongo.reference.UserAccount;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MongoDbRefUserAccountRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;

    public List<UserAccount> getAllUserAccounts() {
        return universalMongoTemplate.getAll(UserAccount.class);
    }

    public UserAccount getUserAccountByPersonId(String personId) {
        Query queryFindByPersonId = new Query(Criteria.where("isik_id").is(new ObjectId(personId)));
        log.info(universalMongoTemplate.getOneByQuery(queryFindByPersonId, UserAccount.class).getIsik_id().toString());
        return universalMongoTemplate.getOneByQuery(queryFindByPersonId, UserAccount.class);
    }

    public UserAccount.UserAccountDbEntry addUserAccount(UserAccount userAccount) {
        UserAccount.UserAccountDbEntry dbEntry = new UserAccount.UserAccountDbEntry(
                userAccount.getIsik_id(),
                userAccount.getParool(),
                userAccount.getOn_aktiivne());
        return universalMongoTemplate.addEntity(dbEntry);
    }

    public UserAccount deleteUserAccountByPersonId(String personId) {
        Query queryFindByPersonId = new Query(Criteria.where("isik_id").is(new ObjectId(personId)));
        return universalMongoTemplate.deleteEntity(queryFindByPersonId, UserAccount.class);
    }

    public boolean updateUserAccount(UserAccount userAccount) {
        Query queryFindByPersonId = new Query(Criteria.where("isik_id").is(new ObjectId(userAccount.getIsik_id())));

        Update updatableInfo = new Update();
        if (userAccount.getParool() != null) {
            updatableInfo.set("parool", userAccount.getParool());
        }
        if (userAccount.getOn_aktiivne() != null) {
            updatableInfo.set("kirjeldus", userAccount.getOn_aktiivne());
        }
        return universalMongoTemplate.updateEntity(queryFindByPersonId, updatableInfo, UserAccount.class);
    }
}
