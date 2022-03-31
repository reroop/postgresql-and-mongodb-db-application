package com.dbapplication.repositories.postgre;

import com.dbapplication.models.postgre.Employment;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("postgretrad")
@Repository
public interface PostgreTradEmploymentRepository extends JpaRepository<Employment, Employment.EmploymentCompositeKey> {

    List<Employment> findAllByOccupationCode(Integer occupationCode);

    List<Employment> findAllByPersonId(Long personId);

    List<Employment> findAllByPersonIdAndOccupationCode(Long personId, Integer occupationCode);
}
