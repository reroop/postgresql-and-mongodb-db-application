package com.dbapplication.services.postgre.traditional;

import com.dbapplication.models.postgre.traditional.Person;
import com.dbapplication.repositories.postgre.traditional.PostgreTradPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertPersonEmptyValuesToNulls;

@Service
@Profile("postgretrad")
public class PostgreTradPersonService {

    @Autowired
    private PostgreTradPersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonBy_id(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person addPerson(Person person) throws Throwable {
        try {
            return personRepository.save(convertPersonEmptyValuesToNulls(person));
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public Person updatePerson(Person updatePerson) throws Throwable {
        try {
            return personRepository.save(convertPersonEmptyValuesToNulls(updatePerson));
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

}
