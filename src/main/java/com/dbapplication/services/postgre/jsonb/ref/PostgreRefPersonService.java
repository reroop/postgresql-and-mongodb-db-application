package com.dbapplication.services.postgre.jsonb.ref;

import com.dbapplication.models.postgre.jsonb.ref.PersonRef;
import com.dbapplication.models.postgre.traditional.Person;
import com.dbapplication.repositories.postgre.jsonb.ref.PostgreRefPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dbapplication.utils.ValidationChecks.isPostgreJsonRefPersonValid;
import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertPersonRefListToPersonList;
import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertPersonRefToPerson;

@Service
@Profile("postgreref")
public class PostgreRefPersonService {

    @Autowired
    private PostgreRefPersonRepository personRepository;

    public List<Person> getAllPersons() {
        return convertPersonRefListToPersonList(personRepository.findAll());
    }

    public Person getPersonBy_id(Long id) {
        PersonRef personRef = personRepository.findById(id).orElse(null);
        return personRef == null ? null : convertPersonRefToPerson(personRef);
    }

    public PersonRef addPerson(PersonRef personRef) throws Throwable {
        if (!isPostgreJsonRefPersonValid(personRef)) {
            return null;
        }
        try {
            return personRepository.save(personRef);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public PersonRef updatePerson(PersonRef personRef) throws Throwable {
        if (!isPostgreJsonRefPersonValid(personRef)) {
            return null;
        }
        try {
            return personRepository.save(personRef);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }
}
