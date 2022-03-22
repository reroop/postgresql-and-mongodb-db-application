package com.dbapplication.services.mongo.reference;

import com.dbapplication.models.mongo.reference.UserAccount;
import com.dbapplication.repositories.mongo.reference.MongoDbRefUserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoDbRefUserAccountsService {

    @Autowired
    private MongoDbRefUserAccountRepository mongoDbRefUserAccountRepository;

    public List<UserAccount> getAllUserAccounts() {
        return mongoDbRefUserAccountRepository.getAllUserAccounts();
    }

    public UserAccount getUserAccountByPersonId(String personId) {
        return mongoDbRefUserAccountRepository.getUserAccountByPersonId(personId);
    }

    public UserAccount.UserAccountDbEntry addUserAccount(UserAccount userAccount) {
        return mongoDbRefUserAccountRepository.addUserAccount(userAccount);
    }

    public UserAccount deleteUserAccountByPersonId(String personId) {
        return mongoDbRefUserAccountRepository.deleteUserAccountByPersonId(personId);
    }

    public boolean updateUserAccount(UserAccount userAccount) {
        return mongoDbRefUserAccountRepository.updateUserAccount(userAccount);
    }
}
