package com.dbapplication.services.postgre.jsonb.common;

import com.dbapplication.models.postgre.jsonb.common.PersonStatusTypePostgreJsonCommon;
import com.dbapplication.models.postgre.traditional.PersonStatusType;
import com.dbapplication.repositories.postgre.jsonb.common.PostgreJsonCommonPersonStatusTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dbapplication.utils.postgre.PostgreObjectConverter.*;

@Service
@Profile({"postgreref", "postgreemb"})
public class PostgreJsonCommonPersonStatusTypeService {

    @Autowired
    private PostgreJsonCommonPersonStatusTypeRepository statusTypeRepository;

    public List<PersonStatusType> getAllPersonStatusTypes() {
        return convertPersonStatusTypePostgreJsonCommonListToPersonStatusTypeList(statusTypeRepository.findAll());
    }

    public PersonStatusType getPersonStatusTypeByPersonStatusTypeCode(Integer statusTypeCode) {
        PersonStatusTypePostgreJsonCommon personStatusTypePostgreJsonCommon = statusTypeRepository.findById(statusTypeCode).orElse(null);
        return personStatusTypePostgreJsonCommon == null
                ? null
                : convertPersonStatusTypePostgreJsonCommonToPersonStatusType(personStatusTypePostgreJsonCommon);

    }

    public PersonStatusTypePostgreJsonCommon addPersonStatusType(PersonStatusTypePostgreJsonCommon statusType) throws Throwable {
        if (statusTypeRepository.findById(statusType.getPerson_status_type_code()).isPresent()) {
            throw new Exception(new Throwable("Person status type with code " + statusType.getPerson_status_type_code() + " is already present!"));
        }
        try {
            return statusTypeRepository.save(statusType);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }
}
