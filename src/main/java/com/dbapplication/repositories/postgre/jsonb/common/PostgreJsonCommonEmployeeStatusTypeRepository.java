package com.dbapplication.repositories.postgre.jsonb.common;

import com.dbapplication.models.postgre.jsonb.common.EmployeeStatusTypePostgreJsonCommon;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile({"postgreref", "postgreemb"})
@Repository
public interface PostgreJsonCommonEmployeeStatusTypeRepository extends JpaRepository<EmployeeStatusTypePostgreJsonCommon, Integer> {
}
