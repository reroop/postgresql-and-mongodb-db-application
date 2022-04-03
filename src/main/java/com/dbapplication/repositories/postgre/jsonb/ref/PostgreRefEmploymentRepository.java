package com.dbapplication.repositories.postgre.jsonb.ref;

import com.dbapplication.models.postgre.jsonb.ref.EmploymentRef;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("postgreref")
@Repository
public interface PostgreRefEmploymentRepository extends JpaRepository<EmploymentRef, Long> {

    List<EmploymentRef> findAllByOccupationCode(Integer occupationCode);

    List<EmploymentRef> findAllByPersonId(Long personId);

    List<EmploymentRef> findAllByPersonIdAndOccupationCode(Long personId, Integer occupationCode);

}
