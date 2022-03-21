package com.dbapplication.utils.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import javax.annotation.PostConstruct;

@Configuration
public class MongoConfiguration {

    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    @PostConstruct
    public void disableMongoTypeMapper() {  //disable setting _class for documents
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
}
