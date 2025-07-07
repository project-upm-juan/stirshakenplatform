package com.upm.pfg.stirshakenplatform.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrunkGroupRepository extends JpaRepository<TrunkGroup, String> {

    TrunkGroup findByName(String name);
}