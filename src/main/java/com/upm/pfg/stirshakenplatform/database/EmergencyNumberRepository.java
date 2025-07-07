package com.upm.pfg.stirshakenplatform.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyNumberRepository extends JpaRepository<EmergencyNumber, String> {

    @Query("SELECT e FROM EmergencyNumber e WHERE e.countryId = :countryId AND e.emNumber = :emNumber")
    EmergencyNumber findByEmNumber(@Param("countryId") String countryId, @Param("emNumber") String emNumber);
}