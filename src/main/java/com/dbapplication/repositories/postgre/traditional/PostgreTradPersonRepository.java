package com.dbapplication.repositories.postgre.traditional;

import com.dbapplication.models.postgre.traditional.Person;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("postgretrad")
@Repository
public interface PostgreTradPersonRepository extends JpaRepository<Person, Long> {
}
