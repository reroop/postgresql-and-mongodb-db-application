package com.dbapplication.repositories.mongo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UniversalMongoTemplate {

    @Autowired
    MongoTemplate mongoTemplate;

    public <T> List<T> getAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }

    public <T> List<T> getAllByQuery(Query query, Class<T> entityClass) {
        return mongoTemplate.find(query, entityClass);
    }

    public <T> T getOneByQuery(Query query, Class<T> entityClass) {
        return mongoTemplate.findOne(query, entityClass);
    }

    public <T> T addEntity(T entity) {
        try {
            return mongoTemplate.save(entity);
        } catch (Exception e) {
            log.info(e.getMessage());
            if (e.getMessage().contains("E11000 duplicate key error collection")) {
                log.info("db already has document with these values");  //todo: query db for entry with queried values and return as error?
            }
            if (e.getMessage().contains("Document failed validation")) {
                log.info("entry failed against db validation");  //todo: return validation??
            }
            throw e;
        }
    }

    public <T> T deleteEntity(Query queryToFindEntity, Class<T> entityClass) {
        return mongoTemplate.findAndRemove(queryToFindEntity, entityClass);
    }

    public <T> List<T> deleteAllEntities(Query queryToFindEntity, Class<T> entityClass) {
        return mongoTemplate.findAllAndRemove(queryToFindEntity, entityClass);
    }

    public <T> boolean updateEntity(Query query, Update updatableInfo, Class<T> entityClass) { //returns boolean whether update was successful
        try {
            return mongoTemplate.updateFirst(query, updatableInfo, entityClass).wasAcknowledged();
        } catch (Exception e) {
            log.info(e.getMessage());
            if (e.getMessage().contains("Document failed validation")) {
                log.info("entry failed against db validation");  //todo: return validation??
            }
            throw e;
        }
    }

    public <T> boolean updateAllEntities(Query query, Update updatableInfo, Class<T> entityClass) { //returns boolean whether update was successful
        try {
            return mongoTemplate.updateMulti(query, updatableInfo, entityClass).wasAcknowledged();
        } catch (Exception e) {
            log.info(e.getMessage());
            if (e.getMessage().contains("Document failed validation")) {
                log.info("entry failed against db validation");  //todo: return validation??
            }
            throw e;
        }
    }
}
