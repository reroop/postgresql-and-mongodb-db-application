package com.dbapplication.services.postgre.traditional;

import com.dbapplication.models.postgre.traditional.PersonStatusType;
import com.dbapplication.repositories.postgre.traditional.PostgreTradPersonStatusTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgretrad")
public class PostgreTradPersonStatusTypeService {

    @Autowired
    private PostgreTradPersonStatusTypeRepository statusTypeRepository;

    public List<PersonStatusType> getAllPersonStatusTypes() {
        return statusTypeRepository.findAll();
    }

    public PersonStatusType getPersonStatusTypeByPersonStatusTypeCode(Integer statusTypeCode) {
        return statusTypeRepository.findById(statusTypeCode).orElse(null);
    }

    public PersonStatusType addPersonStatusType(PersonStatusType personStatusType) throws Throwable {
        if (statusTypeRepository.findById(personStatusType.getPerson_status_type_code()).isPresent()) {
            throw new Exception(new Throwable("person status type with code " + personStatusType.getPerson_status_type_code() + " is already present!"));
        }
        try {
            return statusTypeRepository.save(personStatusType);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }
}
