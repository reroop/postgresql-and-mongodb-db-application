package com.dbapplication.repositories.postgre;

import com.dbapplication.models.postgre.Occupation;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("postgretrad")
@Repository
public interface PostgreTradOccupationRepository extends JpaRepository<Occupation, Integer> {
}
