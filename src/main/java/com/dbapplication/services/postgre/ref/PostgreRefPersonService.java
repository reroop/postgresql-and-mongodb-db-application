package com.dbapplication.services.postgre.ref;

import com.dbapplication.models.postgre.ref.PersonRef;
import com.dbapplication.repositories.postgre.ref.PostgreRefPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Profile("postgreref")
public class PostgreRefPersonService {

    @Autowired
    private PostgreRefPersonRepository personRepository;

    public List<PersonRef> getAllPersons() {
        return personRepository.findAll();
    }

    public PersonRef getPersonBy_id(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    //todo: add, update convert empty values to nulls???
    //todo: implement date checks

    public PersonRef addPerson(PersonRef personRef) {
        //personRef.getData().setReg_time(LocalDateTime.now());
        System.out.println(personRef);
        System.out.println(personRef.getData().getBirth_date());
        System.out.println(personRef.getData().getBirth_date().getTime());
        System.out.println(personRef.getData().getReg_time());
        return personRepository.save(personRef);
    }

    public PersonRef updatePerson(PersonRef personRef) {
        return personRepository.save(personRef);
    }
}
