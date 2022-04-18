package com.dbapplication.repositories.mongo.embedded;

import com.dbapplication.models.mongo.embedded.EmbeddedEmployee;
import com.dbapplication.models.mongo.embedded.EmbeddedEmployment;
import com.dbapplication.models.mongo.embedded.EmbeddedPerson;
import com.dbapplication.models.mongo.reference.Employment;
import com.dbapplication.repositories.mongo.UniversalMongoTemplate;
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

import static com.dbapplication.utils.UniversalConstants.ADD_EMPLOYMENT_WRONG_EMPLOYEE_STATUS;
import static com.dbapplication.utils.UniversalConstants.EMPLOYEE_STATUS_HAS_FINISHED_WORKING;
import static com.dbapplication.utils.ValidationChecks.*;

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

    public EmbeddedPerson addPerson(EmbeddedPerson embeddedPerson) throws Throwable {
        embeddedPerson.setReg_time(LocalDateTime.now());
        if (!isMongoEmbeddedPersonInfoValid(embeddedPerson)) {
            return null;
        }

        /**
         * Set Employee info to null even if it is set, because
         * we have other endpoints later to add Employee info to Person
         */
        embeddedPerson.setEmployee(null);
        return universalMongoTemplate.addEntity(embeddedPerson);
    }

    public boolean addEmployeeToPerson(String personObjectId, EmbeddedEmployee embeddedEmployee) throws Throwable {
        EmbeddedEmployee.EmbeddedEmployeeDbEntry dbEntry = new EmbeddedEmployee.EmbeddedEmployeeDbEntry(embeddedEmployee.getEmployee_status_type_code());
        if (embeddedEmployee.getMentor_id() != null) {
            if (Objects.equals(embeddedEmployee.getMentor_id(), personObjectId)) {
                throw new Exception(new Throwable("person can't be his/herself mentor!"));
            }
            dbEntry.setMentor_id(new ObjectId(embeddedEmployee.getMentor_id()));
        }
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personObjectId));

        //cancel if person already is registered as employee
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);
        if (person.getEmployee() != null) {
            throw new Exception(new Throwable("this person is already registered as an employee!"));
            //return false;
        }

        Update updatableInfo = new Update().set("employee", dbEntry);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean addEmploymentToEmployee(String personObjectId, EmbeddedEmployment newEmbeddedEmployment) throws Throwable {
        if (!isDateInRange2010to2100(newEmbeddedEmployment.getStart_time())) {
            log.info("employment dates must be in range 2010-2100! Current start time is " + newEmbeddedEmployment.getStart_time());
            throw new Exception(new Throwable("employment dates must be in range 2010-2100! Current start time is " + newEmbeddedEmployment.getStart_time()));
        }
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personObjectId));
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);

        //check employee status
        if (person.getEmployee().getEmployee_status_type_code() == EMPLOYEE_STATUS_HAS_FINISHED_WORKING) {
            throw new Exception(new Throwable(ADD_EMPLOYMENT_WRONG_EMPLOYEE_STATUS));
        }

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
                if (embeddedEmployment.getEnd_time() == null) {
                    log.info("add employment, employee already actively employed in that occupation");
                    throw new Exception(new Throwable("Employee is already actively employed in that occupation!"));
                }
                if (embeddedEmployment.getStart_time().equals(newEmbeddedEmployment.getStart_time())) {
                    log.info("add employment, employment with this occupation and start time already registered");
                    throw new Exception(new Throwable("Employment with this occupation and start time is already registered!"));
                }
            }
        }
        employments.add(newEmbeddedEmployment);
        Update updatableInfo = new Update().set("employee.employment", employments);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    //-----------


    //all updates
    public boolean updatePerson(EmbeddedPerson embeddedPerson) throws Throwable {
        if (!isMongoEmbeddedPersonInfoValid(embeddedPerson)) {
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
        //set tel_nr to null if not specified
        if (embeddedPerson.getTel_nr() != null && embeddedPerson.getTel_nr().length() != 0) {
            updatableInfo.set("tel_nr", embeddedPerson.getTel_nr());
        } else {
            updatableInfo.unset("tel_nr");
        }

        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean updateEmployee(String personId, EmbeddedEmployee embeddedEmployee) throws Throwable {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        Update updatableInfo = new Update();
        if (embeddedEmployee.getEmployee_status_type_code() != null) {
            updatableInfo.set("employee.employee_status_type_code", embeddedEmployee.getEmployee_status_type_code());
        }
        if (embeddedEmployee.getMentor_id() != null) {
            if (Objects.equals(embeddedEmployee.getMentor_id(), personId)) {
                throw new Exception(new Throwable("person can't be his/herself mentor!"));
            }
            updatableInfo.set("employee.mentor_id", new ObjectId(embeddedEmployee.getMentor_id()));
        } else {
            updatableInfo.unset("employee.mentor_id");
        }
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean endActiveEmployment(Employment endEmploymentInfo) throws Throwable {
        if (!isDateInRange2010to2100(endEmploymentInfo.getEnd_time())) {
            log.info("end date " + endEmploymentInfo.getEnd_time() + " for employment is not in range 2010-2100!");
            throw new Exception(new Throwable("end date " + endEmploymentInfo.getEnd_time() + " is not in range 2010-2100!"));

            //return false;
        }
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(endEmploymentInfo.getPerson_id()));
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);
        List<EmbeddedEmployment> employments = person.getEmployee().getEmployment() != null ?  person.getEmployee().getEmployment() : new ArrayList<>();
        if (employments.size() == 0) {
            return true;    //do nothing if there are no employments set; NOTE: don't make person.employments != null, because employments might be just empty
        }

        for (EmbeddedEmployment embeddedEmployment : employments) {
            if (Objects.equals(endEmploymentInfo.getOccupation_code(), embeddedEmployment.getOccupation_code()) && embeddedEmployment.getEnd_time() == null) {
                if (!isFirstDateBeforeSecondDate(embeddedEmployment.getStart_time(), endEmploymentInfo.getEnd_time())) {
                    log.info("end active employment, end date " + endEmploymentInfo.getEnd_time() + " is before start date " + embeddedEmployment.getStart_time());
                    throw new Exception(new Throwable("end date " + endEmploymentInfo.getEnd_time() + " is before start date " + embeddedEmployment.getStart_time()));
                    //return false;
                }
                embeddedEmployment.setEnd_time(endEmploymentInfo.getEnd_time());
                break;
            }
        }

        Update updatableInfo = new Update().set("employee.employment", employments);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean endAllEmployments(Employment endEmploymentInfo) throws Throwable {
        if (!isDateInRange2010to2100(endEmploymentInfo.getEnd_time())) {
            log.info("end date " + endEmploymentInfo.getEnd_time() + " for employment is not in range 2010-2100!");
            throw new Exception(new Throwable("end date " + endEmploymentInfo.getEnd_time() + " for employment is not in range 2010-2100!"));
        }
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(endEmploymentInfo.getPerson_id()));
        EmbeddedPerson person = universalMongoTemplate.getOneByQuery(queryFindByObjectId, EmbeddedPerson.class);

        List<EmbeddedEmployment> employments = person.getEmployee().getEmployment() != null ?  person.getEmployee().getEmployment() : new ArrayList<>();
        if (employments.size() == 0) {
            return true;    //do nothing if there are no employments set; NOTE: don't make person.employments != null, because employments might be just empty
        }

        for (EmbeddedEmployment embeddedEmployment : employments) {
            if (embeddedEmployment.getEnd_time() == null) {
                if (!isFirstDateBeforeSecondDate(embeddedEmployment.getStart_time(), endEmploymentInfo.getEnd_time())) {
                    log.info("end all employments, end date is before start date");
                    throw new Exception(new Throwable("end date " + endEmploymentInfo.getEnd_time() + " is before start date " + embeddedEmployment.getStart_time() + " for employment with occupation code " + embeddedEmployment.getOccupation_code()));
                }
                embeddedEmployment.setEnd_time(endEmploymentInfo.getEnd_time());
            }
        }

        Update updatableInfo = new Update().set("employee.employment", employments);
        updatableInfo.set("employee.employee_status_type_code",EMPLOYEE_STATUS_HAS_FINISHED_WORKING);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public boolean deleteEmployee(String personId) {
        Query queryFindByObjectId = new Query(Criteria.where("_id").is(personId));
        Update updatableInfo = new Update().unset("employee");
        this.removeDeletedEmployeeAsMentorFromOtherEmployees(personId);
        return universalMongoTemplate.updateEntity(queryFindByObjectId, updatableInfo, EmbeddedPerson.class);
    }

    public List<EmbeddedPerson> getAllEmployees() {
        Query queryFindEmployees = new Query(Criteria.where("employee").exists(true));
        return universalMongoTemplate.getAllByQuery(queryFindEmployees, EmbeddedPerson.class);
    }

    private boolean removeDeletedEmployeeAsMentorFromOtherEmployees(String deletedEmployeeId) {
        Query queryFindEmployeesWithDeletedMentorId = new Query();
        queryFindEmployeesWithDeletedMentorId.addCriteria(Criteria.where("employee").exists(true));
        queryFindEmployeesWithDeletedMentorId.addCriteria(Criteria.where("employee.mentor_id").is(new ObjectId(deletedEmployeeId)));

        Update updatableInfo = new Update().unset("employee.mentor_id");
        return universalMongoTemplate.updateAllEntities(queryFindEmployeesWithDeletedMentorId, updatableInfo, EmbeddedPerson.class);
    }
}
