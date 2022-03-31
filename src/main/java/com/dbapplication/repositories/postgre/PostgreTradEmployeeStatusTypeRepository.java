package com.dbapplication.repositories.postgre;

import com.dbapplication.models.postgre.EmployeeStatusType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("postgretrad")
@Repository
public interface PostgreTradEmployeeStatusTypeRepository extends JpaRepository<EmployeeStatusType, Integer> {
}
