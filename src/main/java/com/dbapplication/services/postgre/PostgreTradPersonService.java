package com.dbapplication.services.postgre;

import com.dbapplication.models.postgre.Person;
import com.dbapplication.repositories.postgre.PostgreTradPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Profile("postgretrad")
public class PostgreTradPersonService {

    @Autowired
    private PostgreTradPersonRepository personRepository;

    private Person convertPersonEmptyValuesToNulls(Person person) {
        if (Objects.equals(person.getGiven_name(), "")) {
            person.setGiven_name(null);
        }
        if (Objects.equals(person.getSurname(), "")) {
            person.setSurname(null);
        }
        if (Objects.equals(person.getAddress(), "")) {
            person.setAddress(null);
        }
        return person;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonBy_id(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person addPerson(Person person) {
        return personRepository.save(convertPersonEmptyValuesToNulls(person));
    }

    public Person updatePerson(Person updatePerson) {
        return personRepository.save(convertPersonEmptyValuesToNulls(updatePerson));
    }

}
