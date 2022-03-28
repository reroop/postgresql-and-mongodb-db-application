package com.dbapplication.repositories.mongo.embedded;

import com.dbapplication.models.mongo.embedded.EmbeddedEmployee;
import com.dbapplication.models.mongo.embedded.EmbeddedEmployment;
import com.dbapplication.models.mongo.embedded.EmbeddedPerson;
import com.dbapplication.models.mongo.embedded.EmbeddedUserAccount;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class MongoDbEmbPersonRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;

    public List<EmbeddedPerson> getAllPersons() {
        return universalMongoTemplate.getAll(EmbeddedPerson.class);
    }

    public EmbeddedPerson getPersonBy_id(String objectId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(objectId));
        return universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);
    }


    //all adds
    public EmbeddedPerson addPerson(EmbeddedPerson embeddedPerson) {
        embeddedPerson.setReg_aeg(LocalDateTime.now());
        /**
         * Set UserAccount and Employee info to nulls even if they are set, because
         * we have other endpoints later to add UserAccount or Employee info to Person
         */
        embeddedPerson.setKasutajakonto(null);
        embeddedPerson.setTootaja(null);
        return universalMongoTemplate.addEntity(embeddedPerson);
    }

    public boolean addUserAccountToPerson(String personObjectId, EmbeddedUserAccount embeddedUserAccount) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personObjectId));
        Update updatableInfo = new Update().set("kasutajakonto", embeddedUserAccount);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean addEmployeeToPerson(String personObjectId, EmbeddedEmployee embeddedEmployee) {
        EmbeddedEmployee.EmbeddedEmployeeDbEntry dbEntry = new EmbeddedEmployee.EmbeddedEmployeeDbEntry(embeddedEmployee.getTootaja_seisundi_liik_kood());
        if (embeddedEmployee.getMentor_id() != null) {
            dbEntry.setMentor_id(new ObjectId(embeddedEmployee.getMentor_id()));
        }
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personObjectId));
        Update updatableInfo = new Update().set("tootaja", dbEntry);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean addEmploymentToEmployee(String personObjectId, EmbeddedEmployment embeddedEmployment) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personObjectId));
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);
        List<EmbeddedEmployment> employments = person.getTootaja().getAmetis_tootamine() != null ?  person.getTootaja().getAmetis_tootamine() : new ArrayList<>();
        employments.add(embeddedEmployment);

        Update updatableInfo = new Update().set("tootaja.ametis_tootamine", employments);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    //-----------


    //all updates
    public boolean updatePerson(EmbeddedPerson embeddedPerson) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(embeddedPerson.get_id()));
        Update updatableInfo = new Update();
        if (embeddedPerson.getIsikukood() != null) {
            updatableInfo.set("isikukood", embeddedPerson.getIsikukood());
        }
        if (embeddedPerson.getRiik_kood() != null) {
            updatableInfo.set("riik_kood", embeddedPerson.getRiik_kood());
        }
        if (embeddedPerson.getE_meil() != null) {
            updatableInfo.set("e_meil", embeddedPerson.getE_meil());
        }
        if (embeddedPerson.getSynni_kp() != null) {
            updatableInfo.set("synni_kp", embeddedPerson.getSynni_kp());
        }
        //set firstname to null if not specified
        if (embeddedPerson.getEesnimi() != null && embeddedPerson.getEesnimi().length() != 0) {
            updatableInfo.set("eesnimi", embeddedPerson.getEesnimi());
        } else {
            updatableInfo.unset("eesnimi");
        }
        //set lastname to null if not specified
        if (embeddedPerson.getPerenimi() != null && embeddedPerson.getPerenimi().length() != 0) {
            updatableInfo.set("perenimi", embeddedPerson.getPerenimi());
        } else {
            updatableInfo.unset("perenimi");
        }
        //set address to null if not specified
        if (embeddedPerson.getElukoht() != null && embeddedPerson.getElukoht().length() != 0) {
            updatableInfo.set("elukoht", embeddedPerson.getElukoht());
        } else {
            updatableInfo.unset("elukoht");
        }

        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean updateUserAccount(String personId, EmbeddedUserAccount embeddedUserAccount) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        Update updatableInfo = new Update();
        if (embeddedUserAccount.getParool() != null) {
            updatableInfo.set("kasutajakonto.parool", embeddedUserAccount.getParool());
        }
        if (embeddedUserAccount.getOn_aktiivne() != null) {
            updatableInfo.set("kasutajakonto.on_aktiivne", embeddedUserAccount.getOn_aktiivne());
        }
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean updateEmployee(String personId, EmbeddedEmployee embeddedEmployee) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        Update updatableInfo = new Update();
        if (embeddedEmployee.getTootaja_seisundi_liik_kood() != null) {
            updatableInfo.set("tootaja.tootaja_seisundi_liik_kood", embeddedEmployee.getTootaja_seisundi_liik_kood());
        }
        if (embeddedEmployee.getMentor_id() != null) {
            updatableInfo.set("tootaja.mentor_id", embeddedEmployee.getMentor_id());
        } else {
            updatableInfo.unset("tootaja.mentor_id");
        }
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean endActiveEmployment(String personId, Integer occupationCode) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);

        List<EmbeddedEmployment> employments = person.getTootaja().getAmetis_tootamine() != null ?  person.getTootaja().getAmetis_tootamine() : new ArrayList<>();
        if (employments.size() == 0) {
            return true;    //do nothing if there are no employments set; NOTE: don't make person.employments != null, because employments might be just empty
        }

        for (EmbeddedEmployment employment : employments) {
            if (Objects.equals(occupationCode, employment.getAmet_kood()) && employment.getLopu_aeg() == null) {
                employment.setLopu_aeg(LocalDateTime.now());
            }
        }

        Update updatableInfo = new Update().set("tootaja.ametis_tootamine", employments);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean deleteUserAccount(String personId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        Update updatableInfo = new Update().unset("kasutajakonto");

        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public EmbeddedPerson deletePerson(String personId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        return universalMongoTemplate.deleteEntity(queryFindByObjectId, EmbeddedPerson.class);
    }
}
