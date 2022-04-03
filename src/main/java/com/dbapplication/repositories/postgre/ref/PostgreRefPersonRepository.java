package com.dbapplication.repositories.postgre.ref;

import com.dbapplication.models.postgre.ref.PersonRef;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("postgreref")
@Repository
public interface PostgreRefPersonRepository extends JpaRepository<PersonRef, Long> {
}
