package com.dbapplication.repositories.postgre.jsonb.common;

import com.dbapplication.models.postgre.jsonb.common.OccupationPostgreJsonCommon;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("postgreref")
@Repository
public interface PostgreJsonCommonOccupationRepository extends JpaRepository<OccupationPostgreJsonCommon, Integer> {
}
