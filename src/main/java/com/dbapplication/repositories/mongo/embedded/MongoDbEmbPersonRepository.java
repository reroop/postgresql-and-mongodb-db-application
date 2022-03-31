package com.dbapplication.repositories.mongo.embedded;

import com.dbapplication.models.mongo.embedded.EmbeddedEmployee;
import com.dbapplication.models.mongo.embedded.EmbeddedEmployment;
import com.dbapplication.models.mongo.embedded.EmbeddedPerson;
import com.dbapplication.models.mongo.embedded.EmbeddedUserAccount;
import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
import com.dbapplication.utils.mongodb.ValidationChecks;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class MongoDbEmbPersonRepository {

    @Autowired
    private UniversalMongoTemplate universalMongoTemplate;
    private final ValidationChecks validationChecks = new ValidationChecks();


    public List<EmbeddedPerson> getAllPersons() {
        return universalMongoTemplate.getAll(EmbeddedPerson.class);
    }

    public EmbeddedPerson getPersonBy_id(String objectId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(objectId));
        return universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);
    }


    //all adds
    public EmbeddedPerson addPerson(EmbeddedPerson embeddedPerson) {
        embeddedPerson.setReg_time(LocalDateTime.now());
        if (!validationChecks.isMongoEmbeddedPersonInfoValid(embeddedPerson)) {
            return null;
        }

        /**
         * Set UserAccount and Employee info to nulls even if they are set, because
         * we have other endpoints later to add UserAccount or Employee info to Person
         */
        embeddedPerson.setEmployee(null);
        return universalMongoTemplate.addEntity(embeddedPerson);
    }

    public boolean addUserAccountToPerson(String personObjectId, EmbeddedUserAccount embeddedUserAccount) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personObjectId));
        Update updatableInfo = new Update().set("kasutajakonto", embeddedUserAccount);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean addEmployeeToPerson(String personObjectId, EmbeddedEmployee embeddedEmployee) {
        EmbeddedEmployee.EmbeddedEmployeeDbEntry dbEntry = new EmbeddedEmployee.EmbeddedEmployeeDbEntry(embeddedEmployee.getEmployee_status_type_code());
        if (embeddedEmployee.getMentor_id() != null && !Objects.equals(embeddedEmployee.getMentor_id(), personObjectId)) {
            dbEntry.setMentor_id(new ObjectId(embeddedEmployee.getMentor_id()));
        }
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personObjectId));

        //cancel if person already is registered as employee
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);
        if (person.getEmployee() != null) {
            return false;
        }

        Update updatableInfo = new Update().set("employee", dbEntry);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean addEmploymentToEmployee(String personObjectId, EmbeddedEmployment newEmbeddedEmployment) {
        if (!validationChecks.isDateInRange2010to2100(newEmbeddedEmployment.getStart_time())) {
            log.info("add employment, employment date(s) out of range 2010-2100");
            return false;
        }
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personObjectId));
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);
        List<EmbeddedEmployment> employments = person.getEmployee().getEmployment() != null ?  person.getEmployee().getEmployment() : new ArrayList<>();

        //if no employments, then we can add employment without further validating and save
        if (employments.size() == 0) {
            employments.add(newEmbeddedEmployment);
            Update updatableInfo = new Update().set("employee.employment", employments);
            return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
        }

        //check if is already active in this occupation or we're trying to save same occupation with same start date
        for (EmbeddedEmployment embeddedEmployment : employments) {
            if (Objects.equals(embeddedEmployment.getOccupation_code(), newEmbeddedEmployment.getOccupation_code())) {
                if (embeddedEmployment.getEnd_time() == null || embeddedEmployment.getStart_time().equals(newEmbeddedEmployment.getStart_time())) {
                    return false;
                }
            }
        }
        employments.add(newEmbeddedEmployment);
        Update updatableInfo = new Update().set("employee.employment", employments);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    //-----------


    //all updates
    public boolean updatePerson(EmbeddedPerson embeddedPerson) {
        if (!validationChecks.isMongoEmbeddedPersonInfoValid(embeddedPerson)) {
            return false;
        }

        Query queryFindByObjectId = new Query(Criteria.where("_id").is(embeddedPerson.get_id()));
        Update updatableInfo = new Update();
        if (embeddedPerson.getNat_id_code() != null) {
            updatableInfo.set("nat_id_code", embeddedPerson.getNat_id_code());
        }
        if (embeddedPerson.getCountry_code() != null) {
            updatableInfo.set("country_code", embeddedPerson.getCountry_code());
        }
        if (embeddedPerson.getE_mail() != null) {
            updatableInfo.set("e_mail", embeddedPerson.getE_mail());
        }
        if (embeddedPerson.getBirth_date() != null) {
            updatableInfo.set("birth_date", embeddedPerson.getBirth_date());
        }
        //set firstname to null if not specified
        if (embeddedPerson.getGiven_name() != null && embeddedPerson.getGiven_name().length() != 0) {
            updatableInfo.set("given_name", embeddedPerson.getGiven_name());
        } else {
            updatableInfo.unset("given_name");
        }
        //set lastname to null if not specified
        if (embeddedPerson.getSurname() != null && embeddedPerson.getSurname().length() != 0) {
            updatableInfo.set("surname", embeddedPerson.getSurname());
        } else {
            updatableInfo.unset("surname");
        }
        //set address to null if not specified
        if (embeddedPerson.getAddress() != null && embeddedPerson.getAddress().length() != 0) {
            updatableInfo.set("address", embeddedPerson.getAddress());
        } else {
            updatableInfo.unset("address");
        }

        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean updateEmployee(String personId, EmbeddedEmployee embeddedEmployee) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        Update updatableInfo = new Update();
        if (embeddedEmployee.getEmployee_status_type_code() != null) {
            updatableInfo.set("employee.employee_status_type_code", embeddedEmployee.getEmployee_status_type_code());
        }
        if (embeddedEmployee.getMentor_id() != null && !Objects.equals(embeddedEmployee.getMentor_id(), personId)) {
            updatableInfo.set("employee.mentor_id", embeddedEmployee.getMentor_id());
        } else {
            updatableInfo.unset("employee.mentor_id");
        }
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean endActiveEmployment(Employment endEmploymentInfo) {
        if (!validationChecks.isDateInRange2010to2100(endEmploymentInfo.getEnd_time())) {
            log.info("end active employment, end date not in range");
            return false;
        }
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(endEmploymentInfo.getPerson_id()));
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);
        List<EmbeddedEmployment> employments = person.getEmployee().getEmployment() != null ?  person.getEmployee().getEmployment() : new ArrayList<>();
        if (employments.size() == 0) {
            return true;    //do nothing if there are no employments set; NOTE: don't make person.employments != null, because employments might be just empty
        }

        for (EmbeddedEmployment embeddedEmployment : employments) {
            if (Objects.equals(endEmploymentInfo.getOccupation_code(), embeddedEmployment.getOccupation_code()) && embeddedEmployment.getEnd_time() == null) {
                if (!validationChecks.isFirstDateBeforeSecondDate(embeddedEmployment.getStart_time(), endEmploymentInfo.getEnd_time())) {
                    log.info("end active employment, end date is before start date");
                    return false;
                }
                embeddedEmployment.setEnd_time(endEmploymentInfo.getEnd_time());
                break;
            }
        }

        Update updatableInfo = new Update().set("employee.employment", employments);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean endAllEmployments(Employment endEmploymentInfo) {
        if (!validationChecks.isDateInRange2010to2100(endEmploymentInfo.getEnd_time())) {
            log.info("end all employments, end date not in range 2010-2100");
            return false;
        }
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(endEmploymentInfo.getPerson_id()));
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);

        List<EmbeddedEmployment> employments = person.getEmployee().getEmployment() != null ?  person.getEmployee().getEmployment() : new ArrayList<>();
        if (employments.size() == 0) {
            return true;    //do nothing if there are no employments set; NOTE: don't make person.employments != null, because employments might be just empty
        }

        for (EmbeddedEmployment embeddedEmployment : employments) {
            if (embeddedEmployment.getEnd_time() == null) {
                if (!validationChecks.isFirstDateBeforeSecondDate(embeddedEmployment.getStart_time(), endEmploymentInfo.getEnd_time())) {
                    log.info("end all employments, end date is before start date");
                    return false;
                }
                embeddedEmployment.setEnd_time(endEmploymentInfo.getEnd_time());
            }
        }

        Update updatableInfo = new Update().set("employee.employment", employments);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean deleteUserAccount(String personId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        Update updatableInfo = new Update().unset("kasutajakonto");

        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean deleteEmployee(String personId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        Update updatableInfo = new Update().unset("employee");

        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public EmbeddedPerson deletePerson(String personId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        return universalMongoTemplate.deleteEntity(queryFindByObjectId, EmbeddedPerson.class);
    }

    public List<EmbeddedPerson> getAllEmployees() {
        Query queryFindEmployees = new Query(Criteria.where("employee").exists(true));
        return universalMongoTemplate.getAllByQuery(queryFindEmployees, EmbeddedPerson.class);
    }
}
