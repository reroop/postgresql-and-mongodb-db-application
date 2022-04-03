package com.dbapplication.repositories.postgre.ref;

import com.dbapplication.models.postgre.ref.EmployeeRef;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("postgreref")
@Repository
public interface PostgreRefEmployeeRepository extends JpaRepository<EmployeeRef, Long> {
}
