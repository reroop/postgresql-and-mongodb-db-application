package com.dbapplication.repositories.postgre.jsonb.emb;

import com.dbapplication.models.postgre.jsonb.emb.PersonEmb;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("postgreemb")
@Repository
public interface PostgreEmbPersonRepository extends JpaRepository<PersonEmb, Long> {

    List<PersonEmb> findAllByEmployeeNotNull();

}
